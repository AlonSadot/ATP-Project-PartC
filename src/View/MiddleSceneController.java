package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MiddleSceneController implements Initializable, Observer {
    public Stage currStage;

    public Button Play_button;
    public TextField RowText;
    public TextField ColText;



    public void returnToMenu(ActionEvent event) throws IOException {
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }


    public void playButton(ActionEvent event) throws IOException{
        MazeWindowController.setMazeRows(Integer.parseInt(RowText.getText()));
        MazeWindowController.setMazeCols(Integer.parseInt(ColText.getText()));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeWindow.fxml"));
        fxmlLoader.load();
        MyViewController.mediaPlayer.stop();
        Parent root2 = FXMLLoader.load(getClass().getResource("MazeWindow.fxml"));
        Play_button.getScene().setRoot(root2);

    }



    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean playing = MyViewController.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
        if (!playing)
            MyViewController.music();
    }
}
