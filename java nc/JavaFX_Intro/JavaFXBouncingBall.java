import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXBouncingBall extends Application {
   // Constants
   private static final int CANVAS_WIDTH = 640;
   private static final int CANVAS_HEIGHT = 480;
   private static final int UPDATE_PERIOD = 50; // milliseconds

   // Bouncing ball (via Circle Shape)
   private Circle ball;
   private int centerX = 280, centerY = 220;  // Center (x, y)
   private int radius = 190;
   private int xStep = 3, yStep = 5; // displacement per step in x, y

   @Override
   public void start(final Stage primaryStage) {
      // Setup scene and stage
      Pane canvas = new Pane();
      canvas.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
      canvas.setBackground(new Background(
            new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
      primaryStage.setScene(new Scene(canvas));
      primaryStage.setTitle("JavaFX Bouncing Ball");
      primaryStage.show();

      // Setup ball
      ball = new Circle(centerX, centerY, radius, Color.LIGHTSKYBLUE);
      canvas.getChildren().addAll(ball);

      // Setup a Timeline for Animation
      Timeline loop = new Timeline(new KeyFrame(Duration.millis(UPDATE_PERIOD), evt -> {
         // Update the (x, y)
         centerX += xStep;
         centerY += yStep;
         if (centerX + radius > CANVAS_WIDTH || centerX - radius < 0) {
            xStep = -xStep;
         }
         if (centerY + radius > CANVAS_HEIGHT || centerY - radius < 0) {
            yStep = -yStep;
         }
         ball.setCenterX(centerX);
         ball.setCenterY(centerY);
      }));

      // Repeat the KeyFrame indefinitely
      loop.setCycleCount(Timeline.INDEFINITE);
      loop.play();
   }

   public static void main(final String[] args) {
      launch(args);
   }
}