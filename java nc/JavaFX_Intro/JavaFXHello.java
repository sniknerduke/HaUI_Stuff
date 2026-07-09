import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXHello extends Application {
    private Button btnHello; // Declare a "Button" control

    @Override
    public void start(Stage primaryStage) {
        // Construct the "Button" and attach an "EventHandler"
        btnHello = new Button();
        btnHello.setText("Say Hello");
        
        // Using JDK 8 Lambda Expression to construct an EventHandler<ActionEvent>
        btnHello.setOnAction(evt -> System.out.println("Hello World!")); 
        
        // Construct a scene graph with a root node
        StackPane root = new StackPane();
        root.getChildren().add(btnHello); // Add button to the layout

        // Construct a scene with the root node, width and height
        Scene scene = new Scene(root, 300, 250);

        // Set the scene of the stage
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}