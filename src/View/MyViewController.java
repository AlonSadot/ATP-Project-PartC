package View;



import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController implements IView, Initializable, Observer {

    public MyViewModel myViewModel;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
//    public MazeDisplay mazeDisplay;
    public Label playerRow;
    public Label playerCol;
    public static MediaPlayer mediaPlayer;

    @FXML
    private Button Start_button;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    public static void music() {
        Media media = new Media(Paths.get("./resources/music/HeroesGrassTheme.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
//        String change = (String) arg;
//        switch (change){
//            case "maze generated" -> mazeGenerated();
//            case "player moved" -> playerMoved();
//            case "maze solved" -> mazeSolved();
//            default -> System.out.println("Not implemented change: " + change);
//        }
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }


    public void buttonStart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MiddleScene.fxml"));
        fxmlLoader.load();
//        mediaPlayer.stop();
        Parent root2 = FXMLLoader.load(getClass().getResource("MiddleScene.fxml"));
        Start_button.getScene().setRoot(root2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
