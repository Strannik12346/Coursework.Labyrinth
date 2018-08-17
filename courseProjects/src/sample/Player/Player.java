package sample.Player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.MapObject.MapObject;
import sample.Player.PlayerState.CryingState;
import sample.Player.PlayerState.PlayerState;

public class Player extends MapObject {
    private int lives;
    private int gold;
    private PlayerState state;

    public int getLives() {
        return lives;
    }

    public void addLive() {
        lives++;
    }

    public void rmLive() throws Exception {
        lives--;
        if (lives == 0)
            throw new PlayerLoseException();
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int addition) {
        gold += addition;
        state = state.ChangeState();
    }

    public void rmGold(int removing) throws Exception {
        gold -= removing;
        while (gold < 0) {
            rmLive();
            addGold(60);
        }
        state = state.ChangeState();
    }

    public void moveRight(int shift, int maxCoord) throws Exception {
        if (x + shift <= maxCoord) {
            this.x += shift;
            this.rmGold(3);
        }
    }

    public void moveLeft(int shift, int minCoord) throws Exception {
        if (x - shift >= minCoord) {
            this.x -= shift;
            this.rmGold(3);
        }
    }

    public void moveUp(int shift, int minCoord) throws Exception {
        if (y - shift >= minCoord) {
            this.y -= shift;
            this.rmGold(3);
        }
    }

    public void moveDown(int shift, int maxCoord) throws Exception {
        if (y  + shift <= maxCoord) {
            this.y += shift;
            this.rmGold(3);
        }
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.drawImage(state.getImage(), x + align, y + align, size, size);
    }

    @Override
    public void Interact(Player player) {

    }

    public Player(double x, double y, int size)
    {
        super(x, y, size);
        this.lives = 3;
        this.gold = 250;
        this.state = new CryingState(this);
    }
}
