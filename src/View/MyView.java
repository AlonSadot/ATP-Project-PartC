package View;

import javafx.application.Application;

import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.animation.*;

public class  MyView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{200.0, 50.0, 400.0, 50.0, 450.0, 150.0, 400.0, 250.0, 200.0, 250.0, 150.0, 150.0,});
        //Setting the fill color for the hexagon
        hexagon.setFill(Color.DARKCYAN);

        //Creating a rotate transition
        RotateTransition rotateTransition = new RotateTransition();

        //Setting the duration for the transition
        rotateTransition.setDuration(Duration.millis(2000));

        //Setting the node for the transition
        rotateTransition.setNode(hexagon);

        //Setting the angle of the rotation
        rotateTransition.setByAngle(360);

        //Setting the cycle count for the transition
        rotateTransition.setCycleCount(100);

        //Setting auto reverse value to false
        rotateTransition.setAutoReverse(false);

        //Playing the animation
        rotateTransition.play();

        //Creating a Group object
        Group roots = new Group(hexagon);

        //Creating a scene object
        Scene scene = new Scene(roots, 600, 300);

        //Setting title to the Stage
        primaryStage.setTitle("sup BITCH");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
