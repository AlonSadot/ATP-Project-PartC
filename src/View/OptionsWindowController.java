package View;

import Server.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import static View.MiddleSceneController.isNumeric;

public class OptionsWindowController implements Initializable, Observer {

    public Stage currStage;
    public AnchorPane mainPane;
    public ImageView mainImageView;
    public RadioButton Type1,Type2,Type3,Alg1,Alg2,Alg3;
    public TextField poolSize;
    public CheckBox sKey;


    public void backToMenu(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }

    public void ApplyButton(ActionEvent event){
        Configurations config = Configurations.getInstance();
        if (isNumeric(poolSize.getText()) && Integer.parseInt(poolSize.getText()) >= 0)
            config.setProperty("threadPoolSize",poolSize.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Threadpool invalid input value");
            alert.setContentText("Please enter a whole number above 0 in the text fields");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.show();
            return;
        }
        if (Type1.isSelected())
            config.setProperty("mazeGeneratingAlgorithm","MyGenerator");
        else if (Type2.isSelected())
            config.setProperty("mazeGeneratingAlgorithm","Simple");
        else
            config.setProperty("mazeGeneratingAlgorithm","Empty");
        if (Alg1.isSelected())
            config.setProperty("mazeSearchingAlgorithm","BFS");
        else if (Alg2.isSelected())
            config.setProperty("mazeSearchingAlgorithm","DFS");
        else
            config.setProperty("mazeSearchingAlgorithm","Best");




    }


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainImageView.fitWidthProperty().bind(mainPane.widthProperty());
        mainImageView.fitHeightProperty().bind(mainPane.heightProperty());
    }
}
