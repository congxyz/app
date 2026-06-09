package com.xyzfood.Render.controllers.admin;

import java.applet.AppletContext;
import java.io.File;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.dto.Response.FoodImageUploadResponse;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class FoodController {
    private Food food;
    private File selectedImageFile;
    private String currentImagePath;

    @FXML private Label titleLabel;
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField imagePathField;
    @FXML private ImageView imagePreview;
    @FXML private Button saveButton;

    @FXML
    public void initialize() {
        categoryBox.getItems().addAll(
                "Cơm",
                "Mì",
                "Lẩu",
                "Đồ uống",
                "Tráng miệng",
                "Hải sản"
        );
        categoryBox.getSelectionModel().selectFirst();
    }

    public void setFood(Food food) {
        this.food = food;
        selectedImageFile = null;

        if (food == null) {
            titleLabel.setText("Thêm món ăn mới");
            saveButton.setText("Lưu món ăn");
            currentImagePath = null;
            imagePathField.clear();
            imagePreview.setImage(null);
            return;
        }

        titleLabel.setText("Sửa món ăn");
        saveButton.setText("Cập nhật");
        currentImagePath = food.getImage_path();
        nameField.setText(food.getName());
        descriptionArea.setText(food.getDescription());
        priceField.setText(String.format("%.0f", food.getPrice()));
        categoryBox.getSelectionModel().select(food.getCategory());
        imagePathField.setText(currentImagePath);
        loadPreview(currentImagePath);
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh món ăn");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.webp"));
        File file = fileChooser.showOpenDialog(App.getPrimaryStage());
        if (file == null) {
            return;
        }

        selectedImageFile = file;
        imagePathField.setText(file.getAbsolutePath());
        loadPreview(file.toURI().toString());
    }

    @FXML
    private void updateFood() {
        String foodName = nameField.getText() == null ? "" : nameField.getText().trim();
        String description = descriptionArea.getText() == null ? "" : descriptionArea.getText().trim();
        String category = categoryBox.getValue();

        if (foodName.isBlank() || description.isBlank() || category == null || category.isBlank()) {
            ToastUtil.show("Vui lòng nhập đầy đủ thông tin món ăn");
            return;
        }

        float price;
        try {
            price = Float.parseFloat(priceField.getText().trim().replace(",", ""));
        } catch (Exception ex) {
            ToastUtil.show("Giá món ăn không hợp lệ");
            return;
        }
        if (price <= 0) {
            ToastUtil.show("Giá món ăn phải lớn hơn 0");
            return;
        }

        
        if (selectedImageFile != null) {
            AppExecutor.getExecutor().submit(() -> {
                FoodImageUploadResponse uploadResponse = AppConfig.getInstance().getFoodService().uploadFoodImage(selectedImageFile);
                String imagePath = uploadResponse.getImageUrl();
                if (!uploadResponse.getSuccess()) {
                    Platform.runLater(() -> ToastUtil.show(uploadResponse.getMessage()));
                    return;
                }
                if (imagePath == null || imagePath.isBlank()) {
                    Platform.runLater(() -> ToastUtil.show("Vui lòng chọn ảnh món ăn"));
                    return;
                }
                Food foodRequest = new Food(foodName, description, category, price, imagePath);
                APIResponse response = AppConfig.getInstance().getFoodService().saveFood(foodRequest);
                    Platform.runLater(() -> {
                        ToastUtil.show(response.getMessage());
                        if (response.getSuccess()) {
                            close();
                        }
                    });
                });
        }
        else {
            String imagePath = currentImagePath;
            if (imagePath == null || imagePath.isBlank()) {
                    ToastUtil.show("Vui lòng chọn ảnh món ăn");
                    return;
                }
            Food foodRequest = new Food(foodName, description, category, price, imagePath);
                AppExecutor.getExecutor().submit(() -> {
                    APIResponse response = AppConfig.getInstance().getFoodService().saveFood(foodRequest);
                    Platform.runLater(() -> {
                        ToastUtil.show(response.getMessage());
                        if (response.getSuccess()) {
                            close();
                        }
                    });
                });
        }
        
    }

    private void loadPreview(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            imagePreview.setImage(null);
            return;
        }

        try {
            imagePreview.setImage(new Image(imagePath, 220, 140, true, true, true));
        } catch (IllegalArgumentException ex) {
            imagePreview.setImage(null);
        }
    }

    @FXML
    private void close() {
        App.showAdminCart();
    }
}
