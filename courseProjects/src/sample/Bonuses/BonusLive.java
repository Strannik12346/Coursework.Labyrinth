package sample.Bonuses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Player.Player;
import sample.ResourceManager;

public class BonusLive extends Bonus {
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.drawImage(image, x + align, y + align, size, size);
    }

    @Override
    public void Interact(Player player) {
        player.addLive();
    }

    public BonusLive(double x, double y, int size) {
        super(x, y, size);
        this.image = ResourceManager.getManager().getImage("heart");
    }
}
