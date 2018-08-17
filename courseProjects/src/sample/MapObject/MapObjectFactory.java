package sample.MapObject;

import javafx.scene.image.Image;
import sample.Player.Apple;
import sample.Bonuses.BonusGold;
import sample.Bonuses.BonusLive;
import sample.Player.Player;
import sample.SpecObjects.BlackBox;
import sample.SpecObjects.Teleport;
import sample.Enemies.Killer;
import sample.Enemies.Robber;

public class MapObjectFactory {
    public MapObject CreateObject(AvailableObjects type, double x, double y, int size) {
        switch(type) {
            case Player: return new Player(x, y, size);
            case Apple: return new Apple(x, y, size);
            case BonusGold: return new BonusGold(x, y, size);
            case BonusLive: return new BonusLive(x, y, size);
            case Killer: return new Killer(x, y, size);
            case Robber: return new Robber(x, y, size);
            case BlackBox: return new BlackBox(x, y, size);
            case Teleport: return new Teleport(x, y, size);
            default: return null;
        }
    }
}
