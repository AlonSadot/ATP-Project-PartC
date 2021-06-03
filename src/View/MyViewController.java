package View;


//import java.awt.event.ActionEvent;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController implements IView, Initializable, Observer {

    private MyViewModel myViewModel;
    private Stage currStage;
    private Scene scene1,scene2;
    private Label label;

//    public void genTest(ActionEvent actionEvent){
//        System.out.println("Good Try");
//
//    }

    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void buttonStart(ActionEvent event) throws IOException {
        Scene root = FXMLLoader.load(getClass().getResource("MazeWindow.fxml"));
        currStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //scene1 = new Scene(root);
        currStage.setScene(root);
        currStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
