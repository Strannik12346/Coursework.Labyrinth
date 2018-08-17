package sample.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Enemies.Killer;
import sample.Enemies.KillerStrategy.StandStrategy;
import sample.Enemies.KillerStrategy.StandardMoveStrategy;
import sample.Enemies.KillerStrategy.TowardsPlayerMoveStrategy;
import sample.MapObject.AvailableObjects;
import sample.MapObject.MapObject;
import sample.MapObject.MapObjectFactory;
import sample.Panels.BestPlayersPanel;
import sample.Panels.GoldPanel;
import sample.Panels.LivesPanel;
import sample.Player.Apple;
import sample.Player.Player;

import java.util.*;

public class GameMap {
    // levels
    private int[] level_size = {10, 12, 15, 20, 30, 40, 50, 60};
    private int level = 0;

    public int getLevel() {
        return level;
    }

    private int NextLevelSize() {
        if (level < level_size.length) {
            int result = level_size[level];
            level++;
            return result;
        }
        else return 60;
    }

    // Matrix
    private Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    private MapObject[][] objects;
    private int numberOfObjects;
    private MapObjectFactory factory = new MapObjectFactory();

    public int getSize() {
        return cells.length;
    }

    // Max indexes of the matrix
    private int max_i;
    private int max_j;

    // Safe adding to objects, helps us to count objects
    private void AddObject(MapObject obj, int i, int j) {
        if (objects[i][j] == null) {
            objects[i][j] = obj;
            numberOfObjects++;
        }
        else {
            objects[i][j] = obj;
        }
    }

    // Safe delete from the table, helps us to count objects
    private void RmObject(int i, int j) {
        if (objects[i][j] != null) {
            objects[i][j] = null;
            numberOfObjects--;
        }
    }

    // Player is here
    private Player player;
    private int player_i;
    private int player_j;

    public CellPosition getPlayerPosition() {
        return new CellPosition(cells[player_i][player_j], player_i, player_j);
    }

    public int getPlayerGold() {
        if (player.getGold() < 0)
            return 0;
        return player.getGold();
    }

    // Game panels
    private LivesPanel lives;
    private GoldPanel gold;
    private BestPlayersPanel results;

    public void DrawAll(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.CHOCOLATE);
        gc.setLineWidth(1);

        // drawing cells
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells.length; ++j) {
                cells[j][i].Draw(gc, 10 + 600 / cells.length * j, 10 + 600 / cells.length * i);
            }
        }

        // drawing objects
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells.length; ++j) {
                if (objects[i][j] != null) {
                    MapObject map_obj = objects[i][j];
                    map_obj.Draw(gc);
                }
            }
        }

        gc.setFont(new Font(20));

        // drawing panels
        lives.Draw(gc, player);
        gold.Draw(gc, player);
        results.Draw(gc, player);

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(String.valueOf(numberOfObjects), 20, 20);
    }

    private void TryInteraction(int i, int j) throws Exception{
        if (objects[i][j] != player && objects[i][j] != null)
            objects[i][j].Interact(player);
    }

    public void MovePlayerUp() throws Exception {
        Cell c = cells[player_i][player_j];
        if (c.allowsUp()) {
            RmObject(player_i, player_j);
            player.moveUp(600 / cells.length, 10);
            if (player_j != 0)
                player_j--;
            TryInteraction(player_i, player_j);
            AddObject(player, player_i, player_j);
            MoveKillers();
        }
    }

    public void MovePlayerDown() throws Exception {
        Cell c = cells[player_i][player_j];
        if (c.allowsDown()) {
            RmObject(player_i, player_j);
            player.moveDown(600 / cells.length, 10 + max_j * 600 / cells.length);
            if (player_j != max_j)
                player_j++;
            TryInteraction(player_i, player_j);
            AddObject(player, player_i, player_j);
            MoveKillers();
        }
    }

    public void MovePlayerRight() throws Exception {
        Cell c = cells[player_i][player_j];
        if (c.allowsRight()) {
            RmObject(player_i, player_j);
            player.moveRight(600 / cells.length, 10 + max_i * 600 / cells.length);
            if (player_i != max_i)
                player_i++;
            TryInteraction(player_i, player_j);
            AddObject(player, player_i, player_j);
            MoveKillers();
        }
    }

    public void MovePlayerLeft() throws Exception {
        Cell c = cells[player_i][player_j];
        if (c.allowsLeft()) {
            RmObject(player_i, player_j);
            player.moveLeft(600 / cells.length, 10);
            if (player_i != 0)
                player_i--;
            TryInteraction(player_i, player_j);
            AddObject(player, player_i, player_j);
            MoveKillers();
        }
    }

    public void MovePlayerTo(int i, int j, int x, int y) throws Exception {
        RmObject(player_i, player_j);

        player_i = i;
        player_j = j;

        player.setX(x);
        player.setY(y);

        TryInteraction(player_i, player_j);
        AddObject(player, player_i, player_j);
    }

    private void MoveKillerOrInteract(int i, int j, int new_i, int new_j) throws Exception {
        if (i == new_i && j == new_j) {
            return;
        }

        double x = 10 + 600.0 / cells.length * i;
        double y = 10 + 600.0 / cells.length * j;

        double new_x = 10 + 600.0 / cells.length * new_i;
        double new_y = 10 + 600.0 / cells.length * new_j;

        MapObject obj1 = objects[i][j];
        MapObject obj2 = objects[new_i][new_j];

        if (obj2 instanceof Player) {
            TryInteraction(i, j);
            RmObject(i, j);
        }
        else if (obj2 instanceof Killer) {
            Killer killer1 = (Killer) obj1;
            Killer killer2 = (Killer) obj2;

            killer1.setPrev(new CellPosition(cells[new_i][new_j], new_i, new_j));
            killer2.setPrev(new CellPosition(cells[i][j], i, j));
        }
        else {
            objects[i][j] = obj2;
            objects[new_i][new_j] = obj1;

            if (obj1 != null){
                obj1.setX(new_x);
                obj1.setY(new_y);
            }

            if (obj2 != null) {
                obj2.setX(x);
                obj2.setY(y);
            }
        }
    }

    private void MoveKillers() throws Exception {
        Stack<CellPosition> killers_pos = new Stack<>();
        Stack<Killer> killers = new Stack<>();

        for (int i = 0; i < objects.length; ++i) {
            for (int j = 0; j < objects.length; ++j) {
                if (objects[i][j] instanceof Killer) {
                    killers_pos.push(new CellPosition(cells[i][j], i, j));
                    killers.push((Killer) objects[i][j]);
                }
            }
        }

        while (!killers_pos.isEmpty()) {
            CellPosition current_position = killers_pos.pop();
            Killer killer = killers.pop();
            CellPosition new_position = killer.Move(current_position);
            MoveKillerOrInteract(current_position.i, current_position.j, new_position.i, new_position.j);
        }
    }

    private boolean IsInTable(CellPosition cp) {
        return (cp.i >= 0 && cp.j >= 0 &&
                cp.i <= max_i && cp.j <= max_j);
    }

    private ArrayList<CellPosition> GetNeighbours(boolean[][] done, CellPosition current) {
        ArrayList<CellPosition> arr = new ArrayList<>();

        CellPosition left = new CellPosition(null, current.i - 1, current.j);
        CellPosition right = new CellPosition(null, current.i + 1, current.j);
        CellPosition up = new CellPosition(null, current.i, current.j - 1);
        CellPosition down = new CellPosition(null, current.i, current.j + 1);

        if (IsInTable(right) && !done[right.i][right.j]) {
            right.cell = cells[right.i][right.j];
            arr.add(right);
        }

        if (IsInTable(left) && !done[left.i][left.j]) {
            left.cell = cells[left.i][left.j];
            arr.add(left);
        }

        if (IsInTable(down) && !done[down.i][down.j]) {
            down.cell = cells[down.i][down.j];
            arr.add(down);
        }

        if (IsInTable(up) && !done[up.i][up.j]) {
            up.cell = cells[up.i][up.j];
            arr.add(up);
        }

        return arr;
    }

    private void MakeLabirinth() {
        int size = cells.length;
        boolean[][] done = new boolean[size][size];
        Stack<CellPosition> stack = new Stack<>();
        Random rnd = new Random();

        CellPosition current = new CellPosition(cells[0][max_j], 0, max_j);
        CellPosition next;
        done[0][max_j] = true;

        int unvisitedCount = size * size - 1;
        do {
            ArrayList<CellPosition> neighbours = GetNeighbours(done, current);

            if (neighbours.size() != 0) {
                stack.push(current);
                int randomNum = rnd.nextInt(neighbours.size());
                next = neighbours.get(randomNum);
                current.RemoveBorders(next);

                current = next;
                done[current.i][current.j] = true;
                unvisitedCount--;
            }
            else if (!stack.isEmpty()){
                current = stack.pop();
            }
            else {
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        if (!done[i][j]) {
                            current = new CellPosition(cells[i][j], i, j);
                        }
                    }
                }
            }
        }
        while (unvisitedCount != 0);
    }

    private void InitializeCells(int size) {
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                cells[i][j] = new Cell(600 / size);
    }

    private void AddToRandomCoordinates(MapObject obj, Random rand) {
        int rand_i;
        int rand_j;
        do {
            rand_i = rand.nextInt(cells.length);
            rand_j = rand.nextInt(cells.length);
        }
        while (objects[rand_i][rand_j] != null);

        double x_coord = 10 + 600.0 / cells.length * rand_i;
        double y_coord = 10 + 600.0 / cells.length * rand_j;

        obj.setX(x_coord);
        obj.setY(y_coord);

        AddObject(obj, rand_i, rand_j);
    }

    private void InitializeEnvironment() {
        int obj_size = 400 / cells.length;
        Random rand = new Random();

        for (int i = 0; i < cells.length * 2; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.BonusGold, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);
        }

        for (int i = 0; i < 1; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.BonusLive, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);
        }

        for (int i = 0; i < 3; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.Robber, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);
        }

        for (int i = 0; i < 3; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.BlackBox, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);
        }

        for (int i = 0; i < 1; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.Teleport, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);
        }
    }

    private void InitializeKillers() {
        int obj_size = 400 / cells.length;
        Random rand = new Random();

        for (int i = 0; i < 2; ++i) {
            MapObject obj = factory.CreateObject(AvailableObjects.Killer, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);

            Killer killer = (Killer) obj;
            if (level == 1) {
                killer.setStrategy(new StandStrategy());
            }
            else {
                killer.setStrategy(new StandardMoveStrategy());
            }
        }

        if (level > 4) {
            MapObject obj = factory.CreateObject(AvailableObjects.Killer, 0, 0, obj_size);
            AddToRandomCoordinates(obj, rand);

            Killer killer = (Killer) obj;
            killer.setStrategy(new TowardsPlayerMoveStrategy());
        }
    }

    private void InitializePlayer() {
        // Adding player
        player_i = 0;
        player_j = max_j;

        double x = 10;
        double y = 10 + 600.0 / cells.length * player_j;
        int size = 400 / cells.length;

        if (player == null) {
            player = (Player) factory.CreateObject(AvailableObjects.Player, x, y, size);
        }
        else {
            player.setX(x);
            player.setY(y);
            player.setSize(size);
            player.refreshAlign();
        }
        AddObject(player, player_i, player_j);
    }

    private void InitializeApple() {
        // Adding apple
        Apple apple = new Apple(10 + 600 / cells.length * player_j, 10, 400 / cells.length);
        AddObject(apple, cells.length - 1, 0);
    }

    public void Reload() {
        int size = NextLevelSize();

        max_i = size - 1;
        max_j = size - 1;

        numberOfObjects = 0;
        cells = new Cell[size][size];
        objects = new MapObject[size][size];

        InitializeCells(size);
        InitializePlayer();
        InitializeApple();
        InitializeEnvironment();
        InitializeKillers();

        gold = new GoldPanel(700, 100);
        lives = new LivesPanel(700, 200);
        results = new BestPlayersPanel(650, 360);

        MakeLabirinth();
    }

    private GameMap()
    {
        Reload();
    }

    private static GameMap map;

    public static GameMap getMap() {
        if (map == null) {
            map = new GameMap();
        }
        return map;
    }
}