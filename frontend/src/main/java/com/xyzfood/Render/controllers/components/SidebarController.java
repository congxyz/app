package com.xyzfood.Render.controllers.components;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.SessionManager;
import com.xyzfood.Render.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SidebarController {
    @FXML private Label userLabel;
    @FXML private Label roleLabel;
    @FXML private Button customerDashboardButton;
    @FXML private Button bookingButton;
    @FXML private Button cartButton;
    @FXML private Button myReservationsButton;
    @FXML private Button adminDashboardButton;
    @FXML private Button customersButton;
    @FXML private Button reservationsButton;
    @FXML private Button tablesButton;
    @FXML private Button adminCartButton;

    @FXML
    private void initialize() {
        User user = SessionManager.getInstance().getCurrentUser();
        userLabel.setText(user.getFullName());
        roleLabel.setText(user.isAdmin() ? "Quản trị viên" : "Khách hàng");

        boolean admin = user.isAdmin();
        setVisible(customerDashboardButton, !admin);
        setVisible(bookingButton, !admin);
        setVisible(cartButton, !admin);
        setVisible(myReservationsButton, !admin);
        setVisible(adminDashboardButton, admin);
        setVisible(customersButton, admin);
        setVisible(reservationsButton, admin);
        setVisible(tablesButton, admin);
        setVisible(adminCartButton, admin);
        markActive();
    }

    private void setVisible(Button button, boolean visible) {
        button.setVisible(visible);
        button.setManaged(visible);
    }

    @FXML private void openCustomerDashboard() { App.showCustomerDashboard(); }
    @FXML private void openBooking() { App.showBooking(); }
    @FXML private void openCart() { App.showCart(); }
    @FXML private void openMyReservations() { App.showMyReservations(); }
    @FXML private void openAdminDashboard() { App.showAdminDashboard(); }
    @FXML private void openCustomers() { App.showCustomers(); }
    @FXML private void openReservations() { App.showReservations(); }
    @FXML private void openTables() { App.showTables(); }
    @FXML private void openAdminCart() { App.showAdminCart(); }
    @FXML private void logout() { App.showLogin(); }

    private void markActive() {
        switch (App.getCurrentView()) {
            case "admin-dashboard" -> adminDashboardButton.getStyleClass().add("nav-button-active");
            case "customers" -> customersButton.getStyleClass().add("nav-button-active");
            case "reservations" -> reservationsButton.getStyleClass().add("nav-button-active");
            case "tables" -> tablesButton.getStyleClass().add("nav-button-active");
            case "admin-cart" -> adminCartButton.getStyleClass().add("nav-button-active");
            case "customer-dashboard" -> customerDashboardButton.getStyleClass().add("nav-button-active");
            case "booking" -> bookingButton.getStyleClass().add("nav-button-active");
            case "cart" -> cartButton.getStyleClass().add("nav-button-active");
            case "my-reservations" -> myReservationsButton.getStyleClass().add("nav-button-active");
            default -> {
            }
        }
    }
}
