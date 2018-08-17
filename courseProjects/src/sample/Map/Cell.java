package sample.Map;

import javafx.scene.canvas.GraphicsContext;

public class Cell {
    private boolean top;
    private boolean bottom;
    private boolean right;
    private boolean left;
    private int size;

    public boolean allowsUp() {
        return !top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean allowsDown() {
        return !bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean allowsRight() {
        return !right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean allowsLeft() {
        return !left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void Draw(GraphicsContext gc, int x, int y) {
        if (left)
            gc.strokeLine(x, y, x, y + size);
        if (top)
            gc.strokeLine(x, y, x + size, y);
        if (right)
            gc.strokeLine(x + size, y, x + size, y + size);
        if (bottom)
            gc.strokeLine(x, y + size, x + size, y + size);
    }

    public Cell(int size) {
        this.size = size;
        this.left = true;
        this.top = true;
        this.right = true;
        this.bottom = true;
    }
}
