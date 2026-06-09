package com.xyzfood.Render.controllers.admin;

import java.sql.Time;

import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;
import com.xyzfood.Render.controllers.components.Cleanable;

public class TableManagementController implements Cleanable {
    @FXML private GridPane tableGrid;

    private Timeline timeline;
    private volatile boolean loading = false;

    @FXML
    private void initialize() {
        renderTables();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> renderTables()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void refresh() {
        renderTables();
    }

    private void renderTables() {
        if (loading) return;
        loading = true;
        AppExecutor.getExecutor().submit(() -> {
        try {
            var tables = AppConfig.getInstance().getAdminService().getTables();
            Platform.runLater(() -> {
                tableGrid.getChildren().clear();
                for (int i = 0; i < tables.size(); i++) {
                    Table table = tables.get(i);
                    Button button = new Button("Bàn " + table.getNumber() + "\n" + table.getSeats() + " người\n" + table.getStatusText());
                    button.setGraphic(IconUtil.create("fas-chair", table.isReserved() ? "#FF1748" : "#00D56F", 20));
                    button.setGraphicTextGap(10);
                    button.getStyleClass().add(table.isReserved() ? "table-reserved" : "table-free");
                    tableGrid.add(button, i % 5, i / 5);
                }
            });
        } catch (Exception e) {
            Platform.runLater(() -> ToastUtil.show("Lỗi khi tải dữ liệu bàn"));
        }
        finally {
            loading = false;
        }});
    }

    @Override
    public void clean() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
