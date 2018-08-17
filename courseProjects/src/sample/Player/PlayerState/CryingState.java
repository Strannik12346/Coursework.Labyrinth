package sample.Player.PlayerState;

import sample.Player.Player;
import sample.ResourceManager;

public class CryingState extends PlayerState {
    public CryingState(Player player) {
        super(player);
        image = ResourceManager.getManager().getImage("smile_crying");
    }

    @Override
    public PlayerState ChangeState() {
        if (player.getGold() >= 300)
            return new SadState(player);
        else
            return this;
    }
}
