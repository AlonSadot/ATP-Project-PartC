package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplay extends Canvas {

    private Maze maze;
    private Solution solution;
    private int playerRow = 0;
    private int playerCol = 0;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();




    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
    }

    public void setSolution(Solution solution){
        this.solution = solution;
        //draw();
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
        System.out.println("drawing solution...");
    }

    private void draw() {
        if(maze != null){
            int[][] mazeBody = maze.getMatrix();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = mazeBody.length;
            int cols = mazeBody[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

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
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        int[][] mazeBody = maze.getMatrix();

        graphicsContext.setFill(Color.RED);
        javafx.scene.image.Image wallImage = null;
//        try{
//            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
//        } catch (FileNotFoundException e) {
//            System.out.println("There is no wall image file");
//        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mazeBody[i][j] == 1){
                    //if it is a wall:
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
    }

}
