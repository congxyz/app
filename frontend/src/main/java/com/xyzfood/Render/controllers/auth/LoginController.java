package com.xyzfood.Render.controllers.auth;

import java.util.Optional;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.config.SessionManager;
import com.xyzfood.Render.models.User;
import com.xyzfood.Render.services.AuthService;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.ValidatorUtil;
import com.xyzfood.Render.dto.Response.LoginResponse;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink registerLink;

    private final AuthService authService = AppConfig.getInstance().getAuthService();

    @FXML
    private void initialize() {
        
        registerLink.setOnAction(event -> App.showRegister());
    }

    @FXML
    private void handleLogin() {
        String error = ValidatorUtil.requireLogin(usernameField.getText(), passwordField.getText());
        if (error != null) {
            ToastUtil.show(error);
            return;
        }
        AppExecutor.getExecutor().submit(() -> {
        LoginResponse loginResponse = authService.login(usernameField.getText(), passwordField.getText());
        Platform.runLater(() -> {
        if (loginResponse.isSuccess()) {
            User user = loginResponse.getUser();
            String token = loginResponse.getToken();
                ToastUtil.show(loginResponse.getMessage());
                SessionManager.getInstance().login(new User(user.getCustomerId(), user.getFullName(), user.getUsername(), user.getRole(), user.getCreatedAt()),token);
                App.showHomeByRole();
        }
        else{
                ToastUtil.show(loginResponse.getMessage());
                return;
            }
        });
        }); 
    }
}