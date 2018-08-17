package sample.Bonuses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Map.GameMap;
import sample.Player.Player;
import sample.ResourceManager;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class BonusGold extends Bonus {
    private int gold;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.GOLD);
        gc.setStroke(Color.GOLD);
        gc.drawImage(image, x + align, y + align, size, size);
    }

    @Override
    public void Interact(Player player) {
        player.addGold(gold);
    }

    public BonusGold(double x, double y, int size) {
        super(x, y, size);
        Random rand = new Random();
        this.image = ResourceManager.getManager().getImage("gold");
        this.gold = rand.nextInt(100);
    }
}
