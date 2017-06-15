package app.fishing.my.biggestcatch;

import java.io.Serializable;

/**
 * Created by darko on 5/31/17.
 */

public class Fish implements Serializable {

    private String fisherman = "default";
    private int size = 0;
    private String type = "fisherman";

    public Fish(String type, int size, String fisherman){
        this.size = size;
        this.type = type;
        this.fisherman = fisherman;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {

        return type;
    }

    public int getSize() {
        return size;
    }

    public void setFisherman(String fisherman) {
        this.fisherman = fisherman;
    }

    public String getFisherman() {

        return fisherman;
    }
}
