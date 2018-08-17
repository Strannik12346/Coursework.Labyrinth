package sample.Panels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Player.Player;
import sample.ResourceManager;



public class GoldPanel extends Panel {
    private Image image;

    @Override
    public void Draw(GraphicsContext gc, Player player) {
        gc.setFill(Color.GOLDENROD);
        gc.setStroke(Color.GOLDENROD);
        gc.drawImage(image, x, y, 80, 80);

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(String.valueOf(player.getGold()), x + 70, y + 80);
    }

    public GoldPanel(int x, int y) {
        super(x, y);
        this.image = ResourceManager.getManager().getImage("gold");
    }
}
