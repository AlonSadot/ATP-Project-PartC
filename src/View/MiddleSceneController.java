package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    public ImageView mainImageView;
    public AnchorPane mainPane;
    public RadioButton rb1;
    public RadioButton rb2;
    public RadioButton rb3;

    public void returnToMenu(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }


    public void playButton(ActionEvent event) throws IOException{
        MyViewController.mouseAudio();
        if (rb1.isSelected())
            MazeDisplay.setDragonColor(1);
        else if (rb2.isSelected())
            MazeDisplay.setDragonColor(2);
        else if (rb3.isSelected())
            MazeDisplay.setDragonColor(3);
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dragon not chosen");
            alert.setContentText("Please choose your desired drake");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.show();
            return;
        }
        if ((isNumeric(RowText.getText()) && isNumeric(ColText.getText())) && (Integer.parseInt(RowText.getText()) >= 2 && Integer.parseInt(ColText.getText()) >= 2)){
                MazeWindowController.setMazeRows(Integer.parseInt(RowText.getText()));
                MazeWindowController.setMazeCols(Integer.parseInt(ColText.getText()));
                MazeWindowController.setMazeType(true);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeWindow.fxml"));
                fxmlLoader.load();
                MyViewController.mediaPlayer.stop();
                MyViewController.playing=false;
                Parent root2 = FXMLLoader.load(getClass().getResource("MazeWindow.fxml"));
                Play_button.getScene().setRoot(root2);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong dimensions");
            alert.setContentText("Please enter whole numbers above 2 in the text fields");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.show();
            return;
        }


    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!MyViewController.playing)
            MyViewController.music();


        mainImageView.fitWidthProperty().bind(mainPane.widthProperty());
        mainImageView.fitHeightProperty().bind(mainPane.heightProperty());
    }
}
