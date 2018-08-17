package sample.Panels;

import javafx.scene.canvas.GraphicsContext;
import sample.Player.Player;

public abstract class Panel {
    protected int x;
    protected int y;

    public abstract void Draw(GraphicsContext gc, Player player);

    public Panel(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
