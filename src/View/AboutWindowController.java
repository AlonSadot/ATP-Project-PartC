package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class AboutWindowController implements Initializable, Observer {
    public Stage currStage;
    public AnchorPane mainPane;
    public ImageView mainImageView;

    public void backToMenu(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
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
