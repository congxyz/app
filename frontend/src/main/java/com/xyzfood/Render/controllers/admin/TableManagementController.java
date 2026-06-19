package com.xyzfood.Render.controllers.admin;

import java.sql.Time;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;


public class TableManagementController {
    @FXML private GridPane tableGrid;

    @FXML 
    private void initialize() {
        renderTables();
    }

    private void renderTables() {
        AppExecutor.getExecutor().submit(() -> {
        try {
            var tables = AppConfig.getInstance().getAdminService().getTables();
            Platform.runLater(() -> {
                tableGrid.getChildren().clear();
                for (int i = 0; i < tables.size(); i++) {
                    Table table = tables.get(i);
                    Button button = new Button("Bàn " + table.getNumber() + "\n" + table.getSeats() + " người\n" + table.getStatusText());
                    button.setGraphic(IconUtil.create("fas-chair","#00D56F", 20));
                    button.setGraphicTextGap(10);
                    button.getStyleClass().add("table-free");
                    tableGrid.add(button, i % 5, i / 5);
                }
            });
        } catch (Exception e) {
            Platform.runLater(() -> ToastUtil.show("Lỗi khi tải dữ liệu bàn"));
        }
    });

    }
}
