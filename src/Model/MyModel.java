package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import Client.IClientStrategy;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols}; toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(rows * cols) + 100 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        playerRow = maze.getStartPosition().getRowIndex();
                        playerCol = maze.getStartPosition().getColumnIndex();
                    } catch (Exception e) { e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
        //we will call the server to generate the maze. we're using a client which asks to generate a maze
        setChanged();
        notifyObservers("maze generated");
        movePlayer(playerRow,playerCol);
    }

    private void movePlayer(int playerRow, int playerCol) {
        this.playerRow = playerRow;
        this.playerCol = playerCol;
        setChanged();
        notifyObservers("player moved");
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void solveMaze() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                solution = new Solution(mazeSolutionSteps);
                            } catch (Exception e) { e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
        //since we have the solve maze server, we will have to ask it to solve it.
        setChanged();
        notifyObservers("maze solved");
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {

    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {

        int[][] mazeBody = maze.getMatrix();

        switch (direction){
            case UP -> {
                if (playerRow >0)
                    playerRow--;
            }
            case DOWN -> {
                if (playerRow < mazeBody.length-1)
                    playerRow++;
            }
            case LEFT -> {
                if (playerCol>0)
                    playerCol--;
            }
            case RIGHT -> {
                if (playerCol>mazeBody[0].length-1)
                    playerCol++;
            }
        }
        notifyMovement();
    }



    private void notifyMovement() {
        setChanged(); // <- in order to notify something changed, we need to use set changed and the "notify observer"
        notifyObservers("player moved");
    }

}
