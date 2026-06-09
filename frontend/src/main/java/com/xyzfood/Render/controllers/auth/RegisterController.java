package com.xyzfood.Render.controllers.auth;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.User;
import com.xyzfood.Render.services.AuthService;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.ValidatorUtil;
import com.xyzfood.Render.dto.Response.RegisterResponse;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;

public class RegisterController {
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final AuthService authService = AppConfig.getInstance().getAuthService();

    @FXML
    private void handleRegister() {
        String error = ValidatorUtil.requireRegister(fullNameField.getText(), usernameField.getText(),
                passwordField.getText(), confirmPasswordField.getText());
        if (error != null) {
            ToastUtil.show(error);
            return;
        }

            AppExecutor.getExecutor().submit(() -> {
            RegisterResponse registerResponse = authService.register(fullNameField.getText(), usernameField.getText(), passwordField.getText());
            Platform.runLater(() -> ToastUtil.show(registerResponse.getMessage()));
            }); 
    }

    @FXML
    private void backToLogin() {
        App.showLogin();
    }
}

