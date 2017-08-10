package TransparentSplashScreen;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private Stage initialStage;
    private static final int SPLASH_WIDTH = 600;
    private static final int SPLASH_HEIGHT = 600;

    @Override
    public void start(Stage initialStage) throws IOException, InterruptedException {
        this.initialStage = initialStage;
        showSplashScreen();
        initialStage.show();
//        System.out.println("Before pause: " + LocalTime.now());
//        Thread.sleep(3000);
//        System.out.println("After pause: " + LocalTime.now());
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initialStage.close();
                try {
                    showMainStage();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        delay.play();
    }

    private void showSplashScreen() throws InterruptedException, IOException {
        ImageView img = new ImageView(new Image("TransparentSplashScreen/Admiralty.png"));
        img.setFitHeight(SPLASH_HEIGHT);
        img.setFitWidth(SPLASH_WIDTH);
        Pane splashPane = new Pane();
        splashPane.setPrefWidth(SPLASH_HEIGHT);
        splashPane.setPrefWidth(SPLASH_WIDTH);
        splashPane.getChildren().add(img);

        splashPane.setStyle("-fx-background-color: transparent;");

        Scene splashScene = new Scene(splashPane, SPLASH_WIDTH, SPLASH_HEIGHT, Color.TRANSPARENT);
        initialStage.setScene(splashScene);
        initialStage.initStyle(StageStyle.TRANSPARENT);

        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initialStage.setScene(splashScene);
        initialStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initialStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);

        initialStage.show();

    }
    private void showMainStage() throws IOException, InterruptedException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
