package com.xyzfood.Render.controllers.admin;

import java.sql.Time;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.App;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.services.AdminService;
import com.xyzfood.Render.dto.Response.APIResponse;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class TableManagementController {
    @FXML private GridPane tableGrid;

    private final AdminService adminservice = AppConfig.getInstance().getAdminService();

    @FXML 
    private void initialize() {
        renderTables();
    }

    private void renderTables() {
        AppExecutor.getExecutor().submit(() -> {
        try {
            var tables = adminservice.getTables().stream().filter(table -> !table.getDelete()).toList();
            Platform.runLater(() -> {
                tableGrid.getChildren().clear();
                for (int i = 0; i < tables.size(); i++) {
                    Table table = tables.get(i);
                    Button button = new Button("Bàn " + table.getNumber() + "\n" + table.getSeats() + " người");
                    button.setGraphic(IconUtil.create("fas-chair","#00D56F", 20));
                    button.setGraphicTextGap(10);
                    button.getStyleClass().add("table-free");
                    button.setOnAction(event -> showDialog(table.getNumber()));
                    tableGrid.add(button, i % 5, i / 5);
                }
            });
        } catch (Exception e) {
            Platform.runLater(() -> ToastUtil.show("Lỗi khi tải dữ liệu bàn"));
        }
    });

    }

    @FXML
    private void addTable() {
        App.showaddTables();
    }

    private void showDialog(int tableNumber) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            APIResponse ApiResponse = adminservice.deleteTable(tableNumber);
            ToastUtil.show(ApiResponse.getMessage());
            renderTables();
        } else {
            App.showTables();
        }
    }

}
