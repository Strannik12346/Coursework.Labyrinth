package sample.Bonuses;

import javafx.scene.image.Image;
import sample.MapObject.MapObject;

public abstract class Bonus extends MapObject {
    protected Image image;

    public abstract Image getImage();

    Bonus(double x, double y, int size) {
        super(x, y, size);
    }
}
