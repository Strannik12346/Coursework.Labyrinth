package sample.Enemies.KillerStrategy;

import javafx.scene.image.Image;
import sample.Enemies.Killer;
import sample.Map.CellPosition;

public abstract class KillerStrategy {
    public abstract CellPosition NextPosition(CellPosition current, Killer killer);

    public abstract Image getImage();
}
