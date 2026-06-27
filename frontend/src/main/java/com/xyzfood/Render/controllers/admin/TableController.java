package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.AppExecutor;
import com.xyzfood.Render.models.Table;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TableController {

    @FXML private Label titleLabel;
    @FXML private TextField numberField;
    @FXML private TextField floorField;
    @FXML private ComboBox<Integer> seatsBox;
    @FXML private Button saveButton;

    @FXML
    public void initialize() {
        seatsBox.getItems().addAll(2, 4, 6, 8, 10);
        seatsBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void saveTable() {
        try {
            Table table = new Table(java.lang.Integer.parseInt(numberField.getText()), java.lang.Integer.parseInt(floorField.getText()), seatsBox.getValue(), false);
            AppExecutor.getExecutor().submit(() -> {
                    APIResponse response = AppConfig.getInstance().getAdminService().saveTable(table);
                    Platform.runLater(() -> {
                        ToastUtil.show(response.getMessage());
                        if (response.getSuccess()) {
                            close();
                        }
                    });
                });
        } catch (NumberFormatException e) {
            ToastUtil.show("Vui lòng nhập đúng thông tin");
            return;
        }
        
    }

    @FXML
    private void close() {
        App.showTables();
    }
}
