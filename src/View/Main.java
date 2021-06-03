package View;

import Server.*;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
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


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        //Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(stage, 1000, 700));
//        primaryStage.show();
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
//        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
//        solveSearchProblemServer.start();
        mazeGeneratingServer.start();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
