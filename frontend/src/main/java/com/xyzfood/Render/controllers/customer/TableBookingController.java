package com.xyzfood.Render.controllers.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.config.SessionManager;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.models.FoodOrder;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.services.AdminService;
import com.xyzfood.Render.services.FoodService;
import com.xyzfood.Render.services.ReservationService;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.utils.DateUtil;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;
import com.xyzfood.Render.controllers.components.Cleanable;

public class TableBookingController implements Cleanable {
    @FXML private GridPane tableGrid;

    private final AdminService adminService = AppConfig.getInstance().getAdminService();
    private final FoodService foodService = AppConfig.getInstance().getFoodService();
    private final ReservationService reservationService = AppConfig.getInstance().getReservationService();
    private Timeline timeline;
    private volatile boolean loading = false;

    @FXML
    private void initialize() {
        renderTables();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> renderTables()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void refresh() {
        renderTables();
    }

    private void renderTables() {
        if (loading) return;
        loading = true;
        tableGrid.getChildren().clear();
        AppExecutor.getExecutor().submit(() -> {
            try{
                List<Table> tables = adminService.getTables();
                Platform.runLater(() -> {
                try {
                    for (int i = 0; i < tables.size(); i++) {
                        Table table = tables.get(i);
                        Button button = new Button("Bàn " + table.getNumber() + "\n" + table.getSeats() + " người\n" + table.getStatusText());
                        button.setGraphic(IconUtil.create("fas-chair", table.isReserved() ? "#FF1748" : "#00D56F", 20));
                        button.setGraphicTextGap(10);
                        button.getStyleClass().add(table.isReserved() ? "table-reserved" : "table-free");
                        button.setOnAction(event -> openBookingDialog(table));
                        tableGrid.add(button, i % 5, i / 5);}
                    }finally {
                        loading = false;
                    }  });
                }
            catch(Exception ex){
                Platform.runLater(() -> ToastUtil.show("Lỗi khi tải thông tin bàn: " + ex.getMessage()));}
            finally {
                loading = false;}
            });
        }
        
    

    private void openBookingDialog(Table table) {
        if (table.isReserved()) {
            ToastUtil.show("Bàn này đã có người đặt");
            return;
        }

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Đặt bàn số " + table.getNumber());
        dialog.getDialogPane().getStyleClass().add("booking-dialog");
        dialog.getDialogPane().getStylesheets().addAll(App.getPrimaryStage().getScene().getStylesheets());

        ButtonType cancelType = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType confirmType = new ButtonType("Xác nhận đặt bàn", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelType, confirmType);

        Label tableBadge = new Label(String.valueOf(table.getNumber()));
        tableBadge.getStyleClass().add("dialog-table-badge");
        VBox tableInfo = new VBox(6, new Label("Bàn " + table.getNumber()), new Label("Sức chứa: " + table.getSeats() + " người"));
        HBox tableCard = new HBox(18, tableBadge, tableInfo);
        tableCard.getStyleClass().add("dialog-table-card");

        Spinner<Integer> peopleSpinner = new Spinner<>();
        peopleSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, table.getSeats(), Math.min(2, table.getSeats())));
        peopleSpinner.setEditable(true);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        ComboBox<String> timeBox = new ComboBox<>(FXCollections.observableArrayList("07:00", "10:00", "11:30", "14:00", "15:00", "18:00", "19:30", "21:00"));
        timeBox.getSelectionModel().select("07:00");

        TextArea noteArea = new TextArea();
        noteArea.setPromptText("Yêu cầu đặc biệt (tùy chọn)");
        noteArea.setPrefRowCount(3);
        ComboBox<Food> foodBox = new ComboBox<>();
        AppExecutor.getExecutor().submit(() -> {
            var foods = foodService.getFoods();
            Platform.runLater(() -> {
                foodBox.setItems(FXCollections.observableArrayList(foods));
                foodBox.getSelectionModel().selectFirst();});
        });
        
        foodBox.setConverter(new StringConverter<>() {
            @Override public String toString(Food food) { return food == null ? "" : food.getName() + " - " + food.getPriceText(); }
            @Override public Food fromString(String string) { return null; }
        });
        

        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        VBox selectedFoods = new VBox(6);
        selectedFoods.getStyleClass().add("dialog-food-list");
        selectedFoods.getChildren().add(new Label("Chưa có món nào"));
        List<FoodOrder> cart = new ArrayList<>();

        Button addFood = new Button("Thêm");
        addFood.setGraphic(IconUtil.create("fas-plus", "#FFFFFF", 14));
        addFood.setGraphicTextGap(8);
        addFood.getStyleClass().add("ghost-button");
        addFood.setOnAction(event -> {
            Food food = foodBox.getValue();
            if (food == null) {
                return;
            }
            int quantity = quantitySpinner.getValue();
            cart.add(new FoodOrder(food.getName(), quantity, food.getPrice()*quantity ));
            selectedFoods.getChildren().setAll(cart.stream().map(item -> new Label("• " + item.getDisplayText())).toList());
        });

        HBox row1 = new HBox(24, field("Số người", peopleSpinner), field("Thời gian đặt", new HBox(10, datePicker, timeBox)));
        HBox foodRow = new HBox(12, foodBox, quantitySpinner, addFood);
        HBox.setHgrow(foodBox, javafx.scene.layout.Priority.ALWAYS);

        Label preorderLabel = new Label("Đặt món trước (tùy chọn)");
        preorderLabel.setGraphic(IconUtil.create("fas-utensils", "#FB7185", 18));
        preorderLabel.setGraphicTextGap(8);
        preorderLabel.getStyleClass().add("section-title");

        VBox content = new VBox(22,
                tableCard,
                row1,
                field("Ghi chú", noteArea),
                new Region(),
                preorderLabel,
                foodRow,
                selectedFoods);
        content.setPrefWidth(760);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(type -> type == confirmType);
        dialog.showAndWait().filter(Boolean::booleanValue).ifPresent(result -> {
            try {
                AppExecutor.getExecutor().submit(() -> {
                    String reservationCode;
                    do {
                        reservationCode = "RES-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
                    } while (reservationService.checkReservationCode(reservationCode));
                    APIResponse APIResponse = reservationService.bookTable( reservationCode, SessionManager.getInstance().getCurrentUser().getCustomerId(), table.getNumber(),
                            DateUtil.combine(datePicker.getValue(), timeBox.getValue()), peopleSpinner.getValue(), "ĐÃ XÁC NHẬN", noteArea.getText());
                    if(cart.size() > 0){
                        for(FoodOrder foodOrder : cart){
                                foodService.createFoodOrder(reservationCode,foodOrder.getFoodName(),foodOrder.getQuantity());
                        }
                    }
                Platform.runLater(() -> {
                    ToastUtil.show(APIResponse.getMessage());
                    App.showMyReservations();});
                });
            } catch (IllegalArgumentException ex) {
                ToastUtil.show(ex.getMessage());
            }
        });
    }

    private VBox field(String label, javafx.scene.Node node) {
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("muted");
        VBox box = new VBox(8, labelNode, node);
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
