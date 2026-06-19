package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Reservation;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    @FXML private TextField searchField;
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

    @FXML
    private void searchReservation() {
        String keyword = searchField.getText() == null ? "" : searchField.getText();
        AppExecutor.getExecutor().submit(() -> {
            var data = AppConfig.getInstance().getReservationService().getAllReservations().stream()
                .filter(reservation -> reservation.getTimeText().contains(keyword))
                .toList();
            Platform.runLater(() -> reservationTable.setItems(FXCollections.observableArrayList(data)));
        });
    }

    private void addColumn(String title, String property, double width) {
        TableColumn<Reservation, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        reservationTable.getColumns().add(column);
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
        }
    }    
}
