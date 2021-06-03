package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MazeWindow {
    private Stage currStage;

    public void buttonTrying(ActionEvent event) throws IOException {
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //scene1 = new Scene(root);
        currStage.setScene(root);
        currStage.show();
    }
}
