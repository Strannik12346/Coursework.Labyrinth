package sample.Panels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Player.Player;
import sample.ResourceManager;

public class LivesPanel extends Panel {
    private Image image;

    @Override
    public void Draw(GraphicsContext gc, Player player) {
        gc.setFill(Color.ORANGERED);
        gc.setStroke(Color.ORANGERED);
        gc.drawImage(image, x, y, 80, 80);

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(String.valueOf(player.getLives()), x + 70, y + 80);
    }

    public LivesPanel(int x, int y) {
        super(x, y);
        this.image = ResourceManager.getManager().getImage("heart");
    }
}
