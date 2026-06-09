package com.xyzfood.Render.controllers.customer;

import java.util.List;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.config.SessionManager;
import com.xyzfood.Render.models.FoodOrder;
import com.xyzfood.Render.models.Reservation;
import com.xyzfood.Render.utils.DateUtil;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.services.FoodService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.utils.ToastUtil;

public class MyReservationsController {
    private final FoodService foodService = AppConfig.getInstance().getFoodService();
    @FXML private VBox reservationsBox;

    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void refresh() {
        AppExecutor.getExecutor().submit(() -> {
            var reservations = AppConfig.getInstance().getReservationService()
                    .getReservationsByCustomer(SessionManager.getInstance().getCurrentUser().getCustomerId());
            Platform.runLater(() -> {
                reservations.forEach(reservation -> {
                    AppExecutor.getExecutor().submit(() -> {
                        List<FoodOrder> foodOrders = foodService.foodOrderforreservationCode(reservation.getReservationCode());
                        Platform.runLater(() -> {
                            reservationsBox.getChildren().add(createReservationCard(reservation, foodOrders));
                        });
                    });
                });
            });
        });
    }

    private VBox createReservationCard(Reservation reservation,List<FoodOrder> foodOrders) {
        VBox card = new VBox(20);
        card.getStyleClass().add("admin-card");
        card.setStyle("-fx-padding: 28;");

        Label id = new Label(reservation.getReservationCode());
        id.getStyleClass().add("id-text");
        Label status = new Label(reservation.getStatus());
        boolean isReserved = reservation.getStatus().equals("ĐÃ XÁC NHẬN");
        status.getStyleClass().addAll("status-pill", isReserved ? "pill-success" : "pill-info");
        HBox title = new HBox(14, id, status, new Region());
        HBox.setHgrow(title.getChildren().get(2), javafx.scene.layout.Priority.ALWAYS);
        if (isReserved) {
                    Button cancel = new Button("Hủy đặt bàn");
                    cancel.setGraphic(IconUtil.create("fas-times", "#FFFFFF", 14));
                    cancel.setGraphicTextGap(8);
                    cancel.getStyleClass().add("danger-button");
                    cancel.setOnAction(e -> { 
                        AppExecutor.getExecutor().submit(() -> {
                            APIResponse response = AppConfig.getInstance().getReservationService().updateReservationStatus(reservation.getReservationCode(),"ĐÃ HUỶ");
                            Platform.runLater(() -> {
                                ToastUtil.show(response.getMessage());
                                if(response.getSuccess()) refresh();
                            });
                        });
                    });
                    title.getChildren().add(cancel);
                }
    
        HBox details = new HBox(90);
        details.getChildren().addAll(
                field("Bàn", reservation.getTableNumberText()),
                field("Số khách", reservation.getGuestCount() + " người"),
                field("Thời gian", reservation.getTimeText()),
                field("Ngày đặt", DateUtil.formatDateTime(reservation.getCreatedAt())));

        VBox foods = new VBox(6);
        foods.getChildren().add(new Label("Món đặt trước:"));
        if ( foodOrders == null) {
            foods.getChildren().add(new Label("Chưa có món nào"));
        } else {
            float Total = 0;
            for (FoodOrder order : foodOrders) {
                foods.getChildren().add(new Label("• " + order.getDisplayText()));
                Total += order.getSubtotal();
            }
            Label total = new Label("Tổng: " + String.format("%,.0f đ", Total));
            total.getStyleClass().add("text-warning");
            foods.getChildren().add(total);
        }
        foods.getStyleClass().add("muted");

        card.getChildren().addAll(title, details, foods);
        return card;
    }

    private VBox field(String label, String value) {
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("muted");
        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("food-name");
        return new VBox(6, labelNode, valueNode);
    }
}
