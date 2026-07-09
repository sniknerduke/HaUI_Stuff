import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class JavafxCounter extends Application {
    private TextField tfCount;
    private Button btnCount;
    private int count = 0;

    @Override
    public void start(Stage primaryStage) {
        // Allocate controls
        tfCount = new TextField("0");
        tfCount.setEditable(false);
        tfCount.setPrefColumnCount(5);
        btnCount = new Button("Count");

        // Attach event handler
        btnCount.setOnAction(evt -> {
            count++;
            tfCount.setText(count + "");
        });

        // Set up layout
        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        
        // Add children
        root.getChildren().addAll(new Label("Count:"), tfCount, btnCount);

        // Build Scene
        Scene scene = new Scene(root, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Counter");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}