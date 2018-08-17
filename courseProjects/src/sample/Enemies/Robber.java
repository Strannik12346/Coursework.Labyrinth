package sample.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Player.Player;
import sample.ResourceManager;

import java.util.Random;

public class Robber extends Enemy {
    private int gold;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.CHOCOLATE);
        gc.setStroke(Color.CHOCOLATE);
        gc.drawImage(image, x + align, y + align, size, size);
    }

    @Override
    public void Interact(Player player) throws Exception{
        player.rmGold(this.gold);
    }

    public Robber(double x, double y, int size) {
        super(x, y, size);
        Random rand = new Random();
        this.gold = rand.nextInt(100);
        this.image = ResourceManager.getManager().getImage("robber");
    }
}
