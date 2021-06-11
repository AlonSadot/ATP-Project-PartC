package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Optional;

public class MazeDisplay extends Canvas {

    private Maze maze;
    private Solution solution;
    private int playerRow = 0;
    private int playerCol = 0;
    String picturePath = "file:./resources/images/DragonDown.png";
    public static int DragonColor = 1;
    public static MediaPlayer mediaPlayer;
    private double cellHeight,cellWidth;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    public static boolean mazeType = true;


    public static void setDragonColor(int dragonColor) { MazeDisplay.DragonColor = dragonColor; }

    @Override
    public boolean isResizable(){
        return true;
    }

    @Override
    public double minHeight(double width)
    {
        return 350;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double width)
    {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height)
    {
        return 0;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        draw();
    }


    public void setPlayerPosition(int row, int col) {
        if (DragonColor == 1){
            if (row > playerRow)
                picturePath = "file:./resources/images/RedDragonDown.png";
            else if (row < playerRow)
                picturePath = "file:./resources/images/RedDragonUp.png";
            else if (col >playerCol)
                picturePath = "file:./resources/images/RedDragonRight.png";
            else
                picturePath = "file:./resources/images/RedDragonLeft.png";
        }
        else if (DragonColor == 2){
            if (row > playerRow)
                picturePath = "file:./resources/images/BlueDragonDown.png";
            else if (row < playerRow)
                picturePath = "file:./resources/images/BlueDragonUp.png";
            else if (col >playerCol)
                picturePath = "file:./resources/images/BlueDragonRight.png";
            else
                picturePath = "file:./resources/images/BlueDragonLeft.png";
        }
        else{
            if (row > playerRow)
                picturePath = "file:./resources/images/YellowDragonDown.png";
            else if (row < playerRow)
                picturePath = "file:./resources/images/YellowDragonUp.png";
            else if (col >playerCol)
                picturePath = "file:./resources/images/YellowDragonRight.png";
            else
                picturePath = "file:./resources/images/YellowDragonLeft.png";
        }


        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(Solution solution){
        this.solution = solution;
        draw();
    }

    public int getPlayerCol(){
        return playerCol;
    }

    public int getPlayerRow(){
        return playerRow;
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }



    public void drawMaze(Maze maze) {
        this.maze = maze;
        draw();
    }



    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        // need to be implemented
        Image solutionImage = new Image("/images/FireSolve.png");

        Position sol;
        for (AState state : solution.getSolutionPath()){
            sol = (Position)(state).getObject();
            graphicsContext.drawImage(solutionImage, sol.getColumnIndex()*cellWidth, sol.getRowIndex()*cellHeight, cellWidth, cellHeight);

        }

        System.out.println("drawing solution...");
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    private void draw() {
        if(maze != null){
            int[][] mazeBody = maze.getMatrix();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = mazeBody.length;
            int cols = mazeBody[0].length;

            cellHeight = canvasHeight / rows;
            cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
                drawSolution(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        Image playerImage =  new Image(picturePath);
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else{
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
            }
        }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        int[][] mazeBody = maze.getMatrix();

        graphicsContext.setFill(Color.RED);
        javafx.scene.image.Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mazeBody[i][j] == 1){
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null) {
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
        Image startingPoint = new Image("file:./resources/images/BluePort.png");
        Image goalPoint = new Image("file:./resources/images/DragonEgg.png");
        double x,y;
        x = maze.getStartPosition().getColumnIndex()*cellWidth;
        y = maze.getStartPosition().getRowIndex()*cellHeight;
        graphicsContext.drawImage(startingPoint,x,y,cellWidth,cellHeight);
        x = maze.getGoalPosition().getColumnIndex()*cellWidth;
        y = maze.getGoalPosition().getRowIndex()*cellHeight;
        graphicsContext.drawImage(goalPoint,x,y,cellWidth,cellHeight);

    }

}
