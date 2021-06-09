package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class MazeWindowController implements Initializable, Observer {
    public static int mazeRows;
    public static int mazeCols;
    public static String loadedName = null;
    public static boolean mazeType = true;
    public static MediaPlayer mediaPlayer;
    public MyViewModel myViewModel;
    public MazeDisplay mazeDisplay = new MazeDisplay();
    public Pane main_pane;
    public Button backButton;
    public static double x,y;


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

    public void returnBack(ActionEvent event) throws IOException {
        MyViewController.mouseAudio();
        mediaPlayer.stop();
        Parent root2 = FXMLLoader.load(getClass().getResource("MiddleScene.fxml"));
        backButton.getScene().setRoot(root2);
    }


    public void mouseClickedCanvas(MouseEvent event){
        x=event.getSceneX();
        y=event.getSceneX();
    }

    public void mouseDragged(MouseEvent event){
        double tempx = event.getSceneX();
        double tempy = event.getSceneY();
        try {
            Robot r = new Robot();

            if (tempx < x && tempy == y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD4);
                System.out.println("went left");
                x=tempx;
                y=tempy;
            }
             if (tempx > x && tempy == y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD6);
                System.out.println("went right");
                x=tempx;
                y=tempy;
            }
             if (tempx == x && tempy > y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD2);
                System.out.println("went down");
                x=tempx;
                y=tempy;
            }
             if (tempx == x && tempy < y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD8);
                System.out.println("went up");
                x=tempx;
                y=tempy;
            }
             if (tempx < x && tempy < y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD1);
                System.out.println("went left down");
                x=tempx;
                y=tempy;
            }
             if (tempx < x && tempy > y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD7);
                System.out.println("went left up");
                x=tempx;
                y=tempy;
            }
             if (tempx > x && tempy > y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD9);
                System.out.println("went right up");
                x=tempx;
                y=tempy;
            }
             if (tempx < x && tempy < y) {
                r.keyPress(java.awt.event.KeyEvent.VK_NUMPAD3);
                System.out.println("went right down");
                x=tempx;
                y=tempy;
            }

        } catch (AWTException e) {
        }
    }
//    public void mouseDragged(MouseDragEvent e) {
//
//        int y = e.getY();
//        if (y < previousY) {
//            System.out.println("UP");
//        } else if (y > previousY) {
//            System.out.println("DOWN");
//        }
//
//        previousY = y;
//    }

//    public void dragHandle(MouseEvent event) {
//        if(!event.isPrimaryButtonDown())
//            return;
//        double x,y,tx,ty;
//        x=event.getSceneX();
//        y=event.getSceneY();
//        Node node = (Node)event.getSource();
//        tx=node.getTranslateX();
//        ty = node.getTranslateY();
//        double scale = getScale();
//        node.setTranslateX(tx+((event.getSceneX()-x)/scale));
//        node.setTranslateY(ty+((event.getSceneY()-y)/scale));
//        event.consume();
//    }

        public void setMazeViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
        this.myViewModel.addObserver(this);
    }

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();


    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplay.requestFocus();
    }

    public static void goalReached()
    {
        mediaPlayer.stop();
        Media mediaMusic = new Media(Paths.get("./resources/music/GoalReached.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(mediaMusic);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.3);
    }

    public static void playMusic(){
        Media mediaMusic = new Media(Paths.get("./resources/music/MazeTravel.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(mediaMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
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
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Saving Maze:");
        textInputDialog.setTitle("Saving Maze");
        textInputDialog.setContentText("Please enter the saved maze name:");
        Optional<String> result = textInputDialog.showAndWait();
//        String pepe = "Sturgian Fian Champion 260 bow skill";
        if (result.get() != null)
            myViewModel.saveMaze(result.get());
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setContentText("Please enter a valid name");
            alert.show();
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.movePlayer(keyEvent);
        keyEvent.consume();

        if (myViewModel.getPlayerRow() == myViewModel.getMaze().getGoalPosition().getRowIndex() && myViewModel.getPlayerCol() == myViewModel.getMaze().getGoalPosition().getColumnIndex()){
            goalReached();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You have won !");
            alert.setHeaderText("");
            alert.setContentText("");
            Image image = new Image("/images/youWin.jpg");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            ButtonType returnButton = new ButtonType("Back to main menu");
            ButtonType restartButton = new ButtonType("Restart maze");
            alert.getButtonTypes().setAll(returnButton, restartButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == returnButton){
                try {
                    returnBack(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                setPlayerPosition(myViewModel.getMaze().getStartPosition().getRowIndex(),myViewModel.getMaze().getStartPosition().getColumnIndex());
                myViewModel.setPlayerRow(myViewModel.getMaze().getStartPosition().getRowIndex());
                myViewModel.setPlayerCol(myViewModel.getMaze().getStartPosition().getColumnIndex());
            }
            alert.show();
        }
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
        main_pane.setTranslateX(main_pane.getTranslateX()/50);
        main_pane.setTranslateY(main_pane.getTranslateY()/50);
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void zoomHandle(ScrollEvent event) {
        if(event.isControlDown()) {
            boolean flag = false;
            double delta = 1.2;

            double scale = getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0) {
                scale /= delta;
                flag = true;
            } else {
                scale *= delta;
            }
            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale) - 1;

            double dx = (event.getSceneX() - (main_pane.getBoundsInParent().getWidth() / 2 + main_pane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (main_pane.getBoundsInParent().getHeight() / 2 + main_pane.getBoundsInParent().getMinY()));

            setScale(scale);

            if (!flag)
                setPivot(f * dx, f * dy);
            else {
                setCenterPivot();
            }
            event.consume();
        }
    }

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
        playMusic();
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

