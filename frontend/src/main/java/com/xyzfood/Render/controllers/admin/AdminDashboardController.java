package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.application.Platform;
import java.util.List;
import com.xyzfood.Render.controllers.components.Cleanable;

public class AdminDashboardController implements Cleanable {
    @FXML private Label customerCountLabel;
    @FXML private Label reservedCountLabel;
    @FXML private Label reservationCountLabel;
    @FXML private Label confirmedChartLabel;
    @FXML private Label freeChartLabel;
    @FXML private TableView<Reservation> recentTable;
    private final ObservableList<Reservation> reservationsData = FXCollections.observableArrayList();
    private Timeline timeline;
    private volatile boolean loading = false;

    @FXML
    private void initialize() {
        AppExecutor.getExecutor().submit(() -> {
            var adminService = AppConfig.getInstance().getAdminService();
            var reservations = AppConfig.getInstance().getReservationService().getAllReservations();
            long reserved = reservations.size();
            Platform.runLater(() -> {
                customerCountLabel.setText(String.valueOf(adminService.getCustomers().stream().filter(user -> !user.isAdmin()).count()));
                reservedCountLabel.setText(String.valueOf(reserved));
                reservationCountLabel.setText(String.valueOf(reservations.size()));
                confirmedChartLabel.setText(String.valueOf(reservations.size()));
                reservationsData.setAll(reservations);
        });});
        addColumn("Mã đặt", "reservationCode");
        addColumn("Khách hàng", "customerName");
        addColumn("Bàn", "tableNumberText");
        addColumn("Khách", "guestCount");
        addColumn("Thời gian", "timeText");
        addColumn("Trạng thái", "status");
        recentTable.setItems(reservationsData);
        recentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
        if(loading) return;
        loading = true;
        AppExecutor.getExecutor().submit(() -> {
        try {
            List<Reservation> Reservations = AppConfig.getInstance().getReservationService().getAllReservations();
            Platform.runLater(() -> reservationsData.setAll(Reservations));}
        catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            loading = false;
        }
        });
    }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
            System.out.println("Timeline stopped");
        }
    }

    private void addColumn(String title, String property) {
        TableColumn<Reservation, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        if (property.equals("customerName")) {
                column.setMinWidth(220);
            }
        recentTable.getColumns().add(column);
    }
}
