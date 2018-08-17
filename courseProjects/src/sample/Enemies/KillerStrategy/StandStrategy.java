package sample.Enemies.KillerStrategy;

import javafx.scene.image.Image;
import sample.Enemies.Killer;
import sample.Map.CellPosition;
import sample.ResourceManager;

public class StandStrategy extends KillerStrategy {
    @Override
    public CellPosition NextPosition(CellPosition current, Killer killer){
        return current;
    }

    @Override
    public Image getImage() {
        return ResourceManager.getManager().getImage("killer_standing");
    }
}
