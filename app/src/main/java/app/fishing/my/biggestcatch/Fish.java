package app.fishing.my.biggestcatch;

/**
 * Created by darko on 5/31/17.
 */

public class Fish {

    private String fisherman = "default";
    private double size = 0.00;
    private String type = "fisherman";

    public Fish(String type, double size, String fisherman){
        this.size = size;
        this.type = type;
        this.fisherman = fisherman;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getType() {

        return type;
    }

    public double getSize() {
        return size;
    }

    public void setFisherman(String fisherman) {
        this.fisherman = fisherman;
    }

    public String getFisherman() {

        return fisherman;
    }
}
