package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Reservation;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.application.Platform;
import com.xyzfood.Render.controllers.components.Cleanable;

public class ReservationManagementController implements Cleanable {
    @FXML private TableView<Reservation> reservationTable;
    
    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private Timeline timeline;
    private volatile boolean loading = false;

    @FXML
    private void initialize() {
        addColumn("Mã đặt", "reservationCode", 150);
        addColumn("Khách hàng", "customerName", 220);
        addColumn("Customer ID", "customerId", 150);
        addColumn("Bàn", "tableNumberText", 100);
        addColumn("Khách", "guestCount", 90);
        addColumn("Thời gian", "timeText", 190);
        addColumn("Trạng thái", "status", 160);
        addActionColumn();
        refresh();
        reservationTable.setItems(reservationList);
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> refresh()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML   
    private void refresh() {
        if (loading) return;
        loading = true;
        AppExecutor.getExecutor().submit(() -> {
        try {
            var reservations = AppConfig.getInstance().getReservationService().getAllReservations();
            Platform.runLater(() -> reservationList.setAll(reservations));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loading = false;
        }
});
    }

    private void addColumn(String title, String property, double width) {
        TableColumn<Reservation, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        reservationTable.getColumns().add(column);
    }

    private void addActionColumn() {
        TableColumn<Reservation, Void> column = new TableColumn<>("Hành động");
        column.setPrefWidth(190);
        column.setCellFactory(col -> new TableCell<>() {
            private final Button completeButton = new Button("Hoàn thành");
            {
                completeButton.setGraphic(IconUtil.create("fas-check-circle", "#34D399", 14));
                completeButton.setGraphicTextGap(8);
                completeButton.getStyleClass().add("ghost-button");
                completeButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    AppExecutor.getExecutor().submit(() -> {
                        APIResponse APIResponse = AppConfig.getInstance().getReservationService().updateReservationStatus(reservation.getReservationCode(),"ĐÃ HOÀN THÀNH");
                        Platform.runLater(() -> {
                            ToastUtil.show(APIResponse.getMessage());
                            refresh();
                        });
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 ||
                    getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }
                else {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    String status = reservation.getStatus();
                    boolean isReserved = "ĐÃ XÁC NHẬN".equals(status);
                    setGraphic(isReserved ? completeButton : null);
                }
            }
        });
        reservationTable.getColumns().add(column);
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
        }
    }    
}
