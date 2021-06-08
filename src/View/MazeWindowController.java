package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
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

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = 1.0d;
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);


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

    public static double clamp(double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }


    public void setPivot( double x, double y) {
        main_pane.setTranslateX(main_pane.getTranslateX()-x);
        main_pane.setTranslateY(main_pane.getTranslateY()-y);
    }

    public void setCenterPivot(){
        main_pane.setTranslateX(main_pane.getTranslateX()/1.5);
        main_pane.setTranslateY(main_pane.getTranslateY()/1.5);
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {
            boolean flag=false;
            double delta = 1.2;

            double scale = getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0) {
                scale /= delta; // הקטנה
                flag = true;
            }
            else {
                scale *= delta;
            }
            scale = clamp( scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (main_pane.getBoundsInParent().getWidth()/2 + main_pane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (main_pane.getBoundsInParent().getHeight()/2 + main_pane.getBoundsInParent().getMinY()));

            setScale(scale);

            if (!flag)
                setPivot(f*dx, f*dy);
            else {
                setCenterPivot();
//                System.out.println("Reached");
            }
            event.consume();

            }
    };

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
        main_pane.scaleXProperty().bind(myScale);
        main_pane.scaleYProperty().bind(myScale);
        //main_scene.addEventFilter( ScrollEvent.ANY, getOnScrollEventHandler());
        main_pane.addEventFilter( ScrollEvent.ANY, getOnScrollEventHandler());

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

    private void mazeLoaded(){ mazeDisplay.drawMaze(myViewModel.getMaze()); }
}

