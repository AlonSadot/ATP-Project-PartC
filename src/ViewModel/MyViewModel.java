package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;

    public MyViewModel(IModel model){
        this.model = model;
        this.model.assignObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }


    public void generateMaze(int rows, int cols){
        model.generateMaze(rows,cols);
    }

    public void saveMaze(String name){
        model.saveMaze(name);
    }


    public Maze getMaze(){
        return model.getMaze();
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction;
        switch (keyEvent.getCode()){
            case NUMPAD8 -> direction = MovementDirection.UP;
            case NUMPAD2 -> direction = MovementDirection.DOWN;
            case NUMPAD4 -> direction = MovementDirection.LEFT;
            case NUMPAD6 -> direction = MovementDirection.RIGHT;
            case NUMPAD9 -> direction = MovementDirection.UPRIGHT;
            case NUMPAD7 -> direction = MovementDirection.UPLEFT;
            case NUMPAD3 -> direction = MovementDirection.DOWNRIGHT;
            case NUMPAD1 -> direction = MovementDirection.DOWNLEFT;
            default -> {return;}
        }
    model.updatePlayerLocation(direction);
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){ return model.getPlayerCol(); }

    public void loadMaze(String mazeName){
        model.loadMaze(mazeName);
    }

}

