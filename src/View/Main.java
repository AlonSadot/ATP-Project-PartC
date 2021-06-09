package View;

import Model.IModel;
import Model.MyModel;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    static boolean playing = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        fxmlLoader.load();


        if (MyViewController.mediaPlayer != null)
            playing = MyViewController.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
        if (!playing)
            MyViewController.music();

        Scene scene = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        launch(args);
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();

    }
}
