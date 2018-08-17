package sample.Panels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BestPlayersPanel extends Panel {
    private List<String> bestPlayers;

    @Override
    public void Draw(GraphicsContext gc, Player player) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setFont(new Font("Arial", 14));

        int h = 0;

        for (int i = 0; i < Math.min(bestPlayers.size(), 10); ++i) {
            String user = bestPlayers.get(i);
            gc.fillText(user, x, y + h);
            h += 20;
        }
    }

    public BestPlayersPanel(int x, int y) {
        super(x, y);
        bestPlayers = new ArrayList<>();

        BufferedReader reader;
        try {
            String line;
            reader = new BufferedReader(new FileReader("./users.txt"));
            while ((line = reader.readLine()) != null)
                bestPlayers.add(line);

            bestPlayers.sort(new Comparator<String>() {
                @Override
                public int compare(String first, String second) {
                    try {
                        String str_first = first.split(" ")[0];
                        String str_second = second.split(" ")[0];
                        int first_level = Integer.parseInt(str_first);
                        int second_level = Integer.parseInt(str_second);
                        return second_level - first_level;
                    }
                    catch(Exception ex) {
                        return -1;
                    }
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
