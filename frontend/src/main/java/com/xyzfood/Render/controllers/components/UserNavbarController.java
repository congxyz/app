package com.xyzfood.Render.controllers.components;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserNavbarController {
    @FXML private Button homeButton;
    @FXML private Button bookingButton;
    @FXML private Button menuButton;
    @FXML private Button reservationsButton;
    @FXML private Label greetingLabel;

    @FXML
    private void initialize() {
        greetingLabel.setText("Xin chào, " + SessionManager.getInstance().getCurrentUser().getFullName());
        switch (App.getCurrentView()) {
            case "customer-dashboard" -> homeButton.getStyleClass().add("user-nav-active");
            case "booking" -> bookingButton.getStyleClass().add("user-nav-active");
            case "cart" -> menuButton.getStyleClass().add("user-nav-active");
            case "my-reservations" -> reservationsButton.getStyleClass().add("user-nav-active");
            default -> {
            }
        }
    }

    @FXML private void openHome() { App.showCustomerDashboard(); }
    @FXML private void openBooking() { App.showBooking(); }
    @FXML private void openMenu() { App.showCart(); }
    @FXML private void openReservations() { App.showMyReservations(); }
    @FXML private void logout() { App.showLogin(); }

    @FXML public void openChatbot(){
        
    }
}
