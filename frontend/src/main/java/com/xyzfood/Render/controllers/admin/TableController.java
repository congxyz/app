package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.AppExecutor;
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
    @FXML private ComboBox<Interger> seatsBox;
    @FXML private Button saveButton;

    @FXML
    public void initialize() {
        seatsBox.getItems().addAll(2, 4, 6, 8, 10);
        seatsBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void saveTable() {
        try {
            Table table = new Table(Integer.parseInt(numberField.getText(), Integer.parseInt(floorField.getText()), seatsBox.getValue()));
        } catch (NumberFormatException e) {
            ToastUtil.show("Vui lòng nhập đúng thông tin");
            return;
        }
        AppExecutor.getExecutor().submit(() -> {
                    APIResponse response = AppConfig.getInstance().getAdminService().saveTable(table);
                    Platform.runLater(() -> {
                        ToastUtil.show(response.getMessage());
                        if (response.getSuccess()) {
                            close();
                        }
                    });
                });
    }

    @FXML
    private void close() {
        App.showTables();
    }
}
