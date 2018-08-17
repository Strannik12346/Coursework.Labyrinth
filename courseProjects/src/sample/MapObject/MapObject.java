package sample.MapObject;

import javafx.scene.canvas.GraphicsContext;
import sample.Player.Player;

public abstract class MapObject {
    protected double x;
    protected double y;
    protected int size;
    protected double align;

    public double getX() {
        return x;
    }

    public void setX(double value) {
        x = value;
    }

    public double getY() {
        return y;
    }

    public void setY(double value) {
        y = value;
    }

    public void setSize(int value) { size = value; }

    public void refreshAlign() { this.align = 0.25 * size; }

    public abstract void Draw(GraphicsContext gc);

    public abstract void Interact(Player player) throws Exception;

    public MapObject(double x, double y, int size) {
        this.size = size;
        this.align = 0.25 * size;
        this.x = x;
        this.y = y;
    }
}