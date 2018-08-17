package sample.Enemies;

import javafx.scene.image.Image;
import sample.MapObject.MapObject;

public abstract class Enemy extends MapObject {
    protected Image image;

    public abstract Image getImage();

    Enemy(double x, double y, int size) {
        super(x, y, size);
    }
}
