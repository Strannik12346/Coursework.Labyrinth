package sample.Player;

public class PlayerLoseException extends Exception {
    @Override
    public String getMessage() {
        return "Now you fail . . .";
    }
}
