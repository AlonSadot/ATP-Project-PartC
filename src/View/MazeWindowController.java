package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MazeWindowController implements Initializable, Observer {
    private Stage currStage;
    public MyViewModel myViewModel;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplay mazeDisplay;
    public Label playerRow;
    public Label playerCol;

    public void buttonTrying(ActionEvent event) throws IOException {
        Scene root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene1 = new Scene(root);
        currStage.setScene(root);
        currStage.show();
    }

    public void setMazeViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplay.requestFocus();
    }

    public void generateMaze(ActionEvent actionEvent) {
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());

        myViewModel.generateMaze(rows, cols);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        myViewModel.solveMaze();
    }

    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }


//    public void openFile(ActionEvent actionEvent) {
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Open maze");
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
//        fc.setInitialDirectory(new File("./resources"));
//        File chosen = fc.showOpenDialog(null);
//        //...
//    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change) {
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //playerRow.textProperty().bind(updatePlayerRow);
        //playerCol.textProperty().bind(updatePlayerCol);
    }

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    private void mazeSolved() {
        mazeDisplay.setSolution(myViewModel.getSolution());
    }

    private void playerMoved() {
        mazeDisplay.setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        mazeDisplay.drawMaze(myViewModel.getMaze());
    }
}
