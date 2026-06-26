package com.xyzfood.Render;

import java.io.IOException;

import com.xyzfood.Render.controllers.admin.FoodController;
import com.xyzfood.Render.config.SessionManager;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.utils.AnimationUtil;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.controllers.components.Cleanable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private static StackPane appRoot;
    private static Stage primaryStage;
    private static String currentView = "";
    private static Object currentController = null;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        appRoot = new StackPane();
        ToastUtil.install(appRoot);

        Scene scene = new Scene(appRoot, 1180, 720);
        scene.getStylesheets().add(App.class.getResource("/com/xyzfood/Render/styles/colors.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("/com/xyzfood/Render/styles/main.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("/com/xyzfood/Render/styles/animations.css").toExternalForm());

        stage.setTitle("XYZ Food");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/icons/icon.png")));
        stage.setMinWidth(1024);
        stage.setMinHeight(650);
        stage.setScene(scene);
        showLogin();
        stage.show();
    }

    public static void showLogin() {
        currentView = "login";
        SessionManager.getInstance().logout();
        setRoot("/com/xyzfood/Render/views/auth/login.fxml");
    }

    public static void showRegister() {
        currentView = "register";
        setRoot("/com/xyzfood/Render/views/auth/register.fxml");
    }

    public static void showHomeByRole() {
        if (SessionManager.getInstance().getCurrentUser().isAdmin()) {
            showAdminDashboard();
        } else {
            showCustomerDashboard();
        }
    }

    public static void showCustomerDashboard() {
        currentView = "customer-dashboard";
        setRoot("/com/xyzfood/Render/views/customer/dashboard.fxml");
    }

    public static void showBooking() {
        currentView = "booking";
        setRoot("/com/xyzfood/Render/views/customer/booking.fxml");
    }

    public static void showCart() {
        currentView = "cart";
        setRoot("/com/xyzfood/Render/views/customer/cart.fxml");
    }

    public static void showMyReservations() {
        currentView = "my-reservations";
        setRoot("/com/xyzfood/Render/views/customer/my_reservations.fxml");
    }

    public static void showAdminDashboard() {
        currentView = "admin-dashboard";
        setRoot("/com/xyzfood/Render/views/admin/admin_dashboard.fxml");
    }

    public static void showCustomers() {
        currentView = "customers";
        setRoot("/com/xyzfood/Render/views/admin/customers.fxml");
    }

    public static void showReservations() {
        currentView = "reservations";
        setRoot("/com/xyzfood/Render/views/admin/reservations.fxml");
    }

    public static void showTables() {
        currentView = "tables";
        setRoot("/com/xyzfood/Render/views/admin/tables.fxml");
    }

    public static void showaddTables() {
        currentView = "addtable";
        setRoot("/com/xyzfood/Render/views/admin/add_table.fxml");
    }

    public static void showAdminCart() {
        currentView = "admin-cart";
        setRoot("/com/xyzfood/Render/views/admin/admin_cart.fxml");
    }
    public static void showFood() {
        showFood(null);
    }
    public static String getCurrentView() {
        return currentView;
    }

    public static void showFood(Food food) {
        currentView = "food";
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/xyzfood/Render/views/admin/food.fxml"));
            Parent view = loader.load();
            FoodController controller = loader.getController();
            controller.setFood(food);
            appRoot.getChildren().setAll(view);
            ToastUtil.install(appRoot);
            AnimationUtil.fadeIn(view);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load FXML: /com/xyzfood/Render/views/admin/food.fxml", ex);
        }
    }

    public static void showChatbot() {
        currentView = "Chatbot";
        setRoot("/com/xyzfood/Render/views/components/AIChatView.fxml");
    }

    private static void setRoot(String fxmlPath) {
        try {
            if (currentController != null && currentController instanceof Cleanable ) {
                ((Cleanable) currentController).clean();
            }
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent view = loader.load();
            currentController = loader.getController();
            appRoot.getChildren().setAll(view);
            ToastUtil.install(appRoot);
            AnimationUtil.fadeIn(view);
        } catch (IOException ex) {
            throw new IllegalStateException("Không load được FXML: " + fxmlPath, ex);
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
