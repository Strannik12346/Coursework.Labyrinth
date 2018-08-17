package sample;

import javafx.scene.control.Alert;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import sample.Map.GameMap;
import sample.Player.PlayerLoseException;
import sample.Player.PlayerWinException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static void saveResult(String name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./users.txt", true));
            String level = String.valueOf(GameMap.getMap().getLevel());
            String gold = String.valueOf(GameMap.getMap().getPlayerGold());
            writer.append(level + " lvl [" + gold + " gold] : " + name + "\n");
            writer.flush();
            writer.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveAndExit() {
        TextInputDialog dialog = new TextInputDialog("UnnamedUser");
        dialog.setTitle("Game Over");
        dialog.setHeaderText("What a nice game!");
        dialog.setContentText("Please enter your name:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ResourceManager.getManager().getImage("main"));

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> saveResult(name));
        System.exit(0);
    }

    public static void message(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Title");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        // Get the Stage and add custom icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ResourceManager.getManager().getImage("main"));
        alert.showAndWait();
    }

    public static void continueOrFinish(GameMap map, KeyEvent e) {
        try {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W)
                map.MovePlayerUp();
            if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S)
                map.MovePlayerDown();
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A)
                map.MovePlayerLeft();
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D)
                map.MovePlayerRight();
        }
        catch (PlayerWinException ex) {
            message(ex.getMessage());
            map.Reload();
        }
        catch (PlayerLoseException ex) {
            saveAndExit();
        }
        catch(Exception ex) {
            message("Something went wrong.");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DEATHLABIRINTH");
        primaryStage.getIcons().add(new Image("file:./src/sample/Images/icon.png"));
        primaryStage.setResizable(false);

        Group root = new Group();
        Canvas canvas = new Canvas(880, 620);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameMap map = GameMap.getMap();
        map.DrawAll(gc);

        EventHandler<KeyEvent> moveHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                continueOrFinish(map, e);
                gc.clearRect(0, 0, 880, 620);
                map.DrawAll(gc);
            }
        };

        EventHandler<WindowEvent> closeHandler = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveAndExit();
            }
        };

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, moveHandler);
        primaryStage.setOnCloseRequest(closeHandler);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
