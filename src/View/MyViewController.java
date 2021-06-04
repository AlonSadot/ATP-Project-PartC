package View;


//import java.awt.event.ActionEvent;
import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController implements IView, Initializable, Observer {

    public MyViewModel myViewModel;
    private Stage currStage;
    private Scene scene1,scene2;
    private Label label;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplay mazeDisplay;
    public Label playerRow;
    public Label playerCol;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

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
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplay.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void buttonStart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeWindow.fxml"));
        fxmlLoader.load();
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MazeWindowController mazeViewController = fxmlLoader.getController();
        mazeViewController.setMazeViewModel(viewModel);
        Scene root = FXMLLoader.load(getClass().getResource("MazeWindow.fxml"));
        currStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //scene1 = new Scene(root);
        currStage.setScene(root);
        currStage.show();
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        mazeViewController.myViewModel.generateMaze(rows, cols);
    }

    private void mazeGenerated() {
        mazeDisplay.drawMaze(myViewModel.getMaze());
    }

    private void mazeSolved() {
        mazeDisplay.setSolution(myViewModel.getSolution());
    }

    private void playerMoved() {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
