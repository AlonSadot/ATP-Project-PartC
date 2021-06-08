package View;

import Model.IModel;
import Server.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import Model.*;
import ViewModel.*;

public class Main extends Application {

    static boolean playing = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        fxmlLoader.load();

//        IModel model = new MyModel();
//        MyViewModel viewModel = new MyViewModel(model);
//        MyViewController viewController = fxmlLoader.getController();

//        viewController.setMyViewModel(viewModel);

        if (MyViewController.mediaPlayer != null)
            playing = MyViewController.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
        if (!playing)
            MyViewController.music();

        Scene scene = FXMLLoader.load(getClass().getResource("MyView.fxml"));
//        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();


        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
