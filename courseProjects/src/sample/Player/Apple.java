package sample.Player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import sample.MapObject.MapObject;
import sample.ResourceManager;

public class Apple extends MapObject {
    private Image image;

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.drawImage(image, x + align, y + align, size, size);
    }

    @Override
    public void Interact(Player player) throws Exception {
        throw new PlayerWinException();
    }

    public Apple(double x, double y, int size) {
        super(x, y, size);
        this.image = ResourceManager.getManager().getImage("apple");
    }
}
