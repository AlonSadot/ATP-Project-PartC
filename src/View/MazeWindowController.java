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
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MazeWindowController implements Initializable, Observer {
    public MyViewModel myViewModel;
    public MazeDisplay mazeDisplay = new MazeDisplay();
    public static int mazeRows;
    public static int mazeCols;
    public static String loadedName = null;
    public static boolean mazeType = true;



    public static void setMazeType(boolean state) {
        MazeWindowController.mazeType = state;
    }

    public static void setLoadedName(String name) {
        MazeWindowController.loadedName = name;
    }

    public static void setMazeRows(int mazeRows) {
        MazeWindowController.mazeRows = mazeRows;
    }

    public static void setMazeCols(int mazeCols) {
        MazeWindowController.mazeCols = mazeCols;
    }


    public Pane main_pane;
    public Button tryingButton;


    public void returnBack(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
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


    public void solveMaze(ActionEvent actionEvent) {
        MyViewController.mouseAudio();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        myViewModel.solveMaze();
    }

    public void SaveMaze(ActionEvent actionEvent){
        MyViewController.mouseAudio();
        Popup popup = new Popup();
        Label label = new Label("This is America");
        popup.getContent().add(label);
        label.setMinWidth(80);
        label.setMinHeight(50);
        label.setStyle(" -fx-background-color: black;");
        popup.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        String pepe = "Sturgian Fian Champion 260 bow skill";
        myViewModel.saveMaze(pepe);
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

//    addKeyListener(new KeyListener() {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//
//
//            addMouseWheelListener(new MouseWheelListener() {
//
//                @Override
//                public void mouseScrolled(MouseEvent g) {
//                    if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
//                        performZoom(g.count);
//                    }
//                }
//            });
//
//        }
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
            case "maze loaded" -> mazeLoaded();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    public MazeWindowController() throws IOException{
        IModel model = new MyModel();
        myViewModel = new MyViewModel(model);
        setMazeViewModel(myViewModel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mazeType == true){
            this.myViewModel.generateMaze(mazeRows, mazeCols);
        }
        else{
            this.myViewModel.loadMaze(loadedName);
        }
    }

    private void mazeSolved() {
        mazeDisplay.setSolution(myViewModel.getSolution());
    }

    private void playerMoved() {
        setPlayerPosition(myViewModel.getPlayerRow(), myViewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        mazeDisplay.drawMaze(myViewModel.getMaze());
        loadedName = null;
    }

    private void mazeLoaded(){ mazeDisplay.drawMaze(myViewModel.getMaze()); }
}
