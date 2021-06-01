package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    protected Object object;
    protected AState cameFrom;
    protected double cost;

    public double getCost() {
        return cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public Object getObject() {
        return object;
    }

    public abstract String getHashCode();

    public abstract boolean equals(AState other);

    public String toString(){
        return object.toString();
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}