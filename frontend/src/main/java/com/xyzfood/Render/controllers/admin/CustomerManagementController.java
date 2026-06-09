package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.User;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.application.Platform;
import com.xyzfood.Render.controllers.components.Cleanable;

public class CustomerManagementController implements Cleanable {
    @FXML private TextField searchField;
    @FXML private TableView<User> customerTable;
    private Timeline timeline;
    private volatile boolean loading = false;

    @FXML
    private void initialize() {
        addColumn("Customer ID", "customerId", 220);
        addColumn("Họ tên", "fullName", 360);
        addColumn("Tài khoản", "username", 220);
        addColumn("Ngày đăng ký", "createdAt", 240);
        loadCustomers();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            if (searchField.getText() == null || searchField.getText().trim().isEmpty()) {
                loadCustomers();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void searchCustomer() {
        String keyword = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        AppExecutor.getExecutor().submit(() -> {
            var data = AppConfig.getInstance().getAdminService().getCustomers().stream()
                .filter(user -> keyword.isEmpty() || user.getCustomerId().toLowerCase().contains(keyword)
                        || user.getFullName().toLowerCase().contains(keyword)
                        || user.getUsername().toLowerCase().contains(keyword))
                .toList();
            Platform.runLater(() -> customerTable.setItems(FXCollections.observableArrayList(data)));
        });
    }

    private void loadCustomers() {
        if (loading) return;
            loading = true;
        AppExecutor.getExecutor().submit(() -> {
            try {
            var customers = AppConfig.getInstance().getAdminService().getCustomers();
            Platform.runLater(() -> customerTable.setItems(FXCollections.observableArrayList(customers)));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                loading = false;
            }
        });
    }

    private void addColumn(String title, String property, double width) {
        TableColumn<User, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        customerTable.getColumns().add(column);
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
            System.out.println("Timeline stopped");
        }
    }
}
