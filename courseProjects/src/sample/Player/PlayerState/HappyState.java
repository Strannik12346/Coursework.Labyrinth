package sample.Player.PlayerState;

import sample.Player.Player;
import sample.ResourceManager;

public class HappyState extends PlayerState {
    public HappyState(Player player) {
        super(player);
        image = ResourceManager.getManager().getImage("smile_happy");
    }

    @Override
    public PlayerState ChangeState() {
        if (player.getGold() < 1200)
            return new MiddleState(player);
        else
            return this;
    }
}
