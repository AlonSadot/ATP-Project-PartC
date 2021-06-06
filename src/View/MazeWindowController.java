package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MazeWindowController implements Initializable, Observer {
    private Stage currStage;
    public MyViewModel myViewModel;
    public MazeDisplay mazeDisplay = new MazeDisplay();
    public int mazeRows;
    public int mazeCols;
    public Label playerRow;
    public Label playerCol;

    @FXML
    public Pane main_pane;
    public Button tryingButton;

    public void returnBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MiddleScene.fxml"));
        fxmlLoader.load();

        Parent root2 = FXMLLoader.load(getClass().getResource("MiddleScene.fxml"));
        tryingButton.getScene().setRoot(root2);
    }

    public void setMazeViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();


    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplay.requestFocus();
    }

    public void generateMaze(ActionEvent actionEvent) throws IOException {




        this.myViewModel.generateMaze(10, 10);
        //        int rows = Integer.valueOf(textField_mazeRows.getText());
//        int cols = Integer.valueOf(textField_mazeColumns.getText());
//        main_pane.setPadding(new Insets(100,100,100,100));
//        if (main_pane == null)
//            System.out.println("pane is null");
//        Image image1 = new Image("file:/D:/LocalTemps/Avatar6.jpg");
//        ImageView imageView1 = new ImageView(image1);
//        imageView1.setFitHeight(250);
//        imageView1.setFitWidth(250);
//        imageView1.setX(100);
//        imageView1.setY(200);
//        main_pane.getChildren().add(imageView1);
//        mazeDisplay.scaleXProperty().bind(mazeDisplay.getScene().widthProperty().multiply(0.5));
//        mazeDisplay.scaleYProperty().bind(mazeDisplay.getScene().heightProperty().multiply(0.5));



//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MazeWindow.fxml"));
//        try {
//            fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        IModel model = new MyModel();
//        this.myViewModel = new MyViewModel(model);
//        MazeWindowController mazeWindowController = fxmlLoader.getController();
//        mazeWindowController.setMazeViewModel(myViewModel);
//
//        mazeRows = 50;
//        mazeCols = 50;

//        this.myViewModel.generateMaze(mazeRows, mazeCols);

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

    public void setPlayerPosition(int row, int col){
        mazeDisplay.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
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

    public MazeWindowController() throws IOException{
        IModel model = new MyModel();
        myViewModel = new MyViewModel(model);
        setMazeViewModel(myViewModel);

        InvalidationListener listener = new InvalidationListener(){
            @Override
            public void invalidated(javafx.beans.Observable o) {
                mazeDisplay.draw();
            }
        };
        mazeDisplay.widthProperty().addListener(listener);
        mazeDisplay.heightProperty().addListener(listener);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        int rows = Integer.valueOf(textField_mazeRows.getText());
//        int cols = Integer.valueOf(textField_mazeColumns.getText());

        //        playerRow.textProperty().bind(updatePlayerRow);
//        playerCol.textProperty().bind(updatePlayerCol);
    }

    private void mazeSolved() {
        mazeDisplay.setSolution(myViewModel.getSolution());
    }

    private void playerMoved() {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        mazeDisplay.drawMaze(myViewModel.getMaze());
    }
}
