package sample.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Enemies.KillerStrategy.KillerStrategy;
import sample.Enemies.KillerStrategy.RandomMoveStrategy;
import sample.Enemies.KillerStrategy.StandardMoveStrategy;
import sample.Enemies.KillerStrategy.TowardsPlayerMoveStrategy;
import sample.Map.Cell;
import sample.Map.CellPosition;
import sample.Map.GameMap;
import sample.MapObject.MapObject;
import sample.Player.Player;
import sample.ResourceManager;

import java.util.ArrayList;
import java.util.Random;

public class Killer extends Enemy {
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.setStroke(Color.CYAN);
        gc.drawImage(strategy.getImage(), x + align, y + align, size, size);
    }

    private CellPosition prev;

    public CellPosition getPrev() {
        return prev;
    }

    private KillerStrategy strategy = new StandardMoveStrategy();

    public void setStrategy(KillerStrategy value) {
        this.strategy = value;
    }

    public CellPosition Move(CellPosition current) {
        return strategy.NextPosition(current, this);
    }

    public void setPrev(CellPosition value) {
        prev = value;
    }

    @Override
    public void Interact(Player player) throws Exception {
        player.rmLive();
    }

    public Killer(double x, double y, int size) {
        super(x, y, size);
        this.image = ResourceManager.getManager().getImage("killer");
    }
}