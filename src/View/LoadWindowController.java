package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class LoadWindowController  implements Initializable, Observer {
    public Stage currStage;
    public ListView listView;
    public Label errorLabel;
    public MyViewModel myViewModel;
    public Button loadButton;

    public void backToMenu(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }

    public void LoadMaze(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        MultipleSelectionModel mazeFile = listView.getSelectionModel();
        String mazeName = mazeFile.getSelectedItems().toString();
        mazeName = mazeName.substring(1,mazeName.length()-1);
        if (mazeFile.getSelectedItems().size() == 0)
            errorLabel.setText("Please choose a maze to load");
        else{
            MazeWindowController.setLoadedName(mazeName);
            MazeWindowController.setMazeType(false);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeWindow.fxml"));
            fxmlLoader.load();
            MyViewController.mediaPlayer.stop();
            Parent root2 = FXMLLoader.load(getClass().getResource("MazeWindow.fxml"));
            loadButton.getScene().setRoot(root2);
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File directory = new File("Saved_Mazes");
        File[] contents = directory.listFiles();


        for ( File f : contents) {
            listView.getItems().add(f.getPath().substring(12));
        }

    }
}

