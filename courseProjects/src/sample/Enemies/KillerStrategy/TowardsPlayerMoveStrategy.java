package sample.Enemies.KillerStrategy;

import javafx.scene.image.Image;
import sample.Enemies.Killer;
import sample.Map.Cell;
import sample.Map.CellPosition;
import sample.Map.GameMap;
import sample.ResourceManager;

import java.util.LinkedList;

public class TowardsPlayerMoveStrategy extends KillerStrategy{
    private CellPosition FindPathToPlayer(CellPosition start){
        Cell[][] cells = GameMap.getMap().getCells();
        CellPosition player_pos = GameMap.getMap().getPlayerPosition();
        LinkedList<CellPosition> next_positions = new LinkedList<>();
        boolean[][] visited = new boolean[cells.length][cells.length];

        CellPosition current;
        next_positions.add(start);

        while (true) {
            current = next_positions.poll();
            if (current.cell.allowsRight() && !visited[current.i + 1][current.j]) {
                CellPosition right = new CellPosition(cells[current.i + 1][current.j], current.i + 1, current.j);
                next_positions.add(right);
                right.setPrev(current);
                visited[current.i + 1][current.j] = true;

                if (current.i + 1 == player_pos.i && current.j == player_pos.j)
                    return right;
            }

            if (current.cell.allowsLeft() && !visited[current.i - 1][current.j]) {
                CellPosition left = new CellPosition(cells[current.i - 1][current.j], current.i - 1, current.j);
                next_positions.add(left);
                left.setPrev(current);
                visited[current.i - 1][current.j] = true;

                if (current.i - 1 == player_pos.i && current.j == player_pos.j)
                    return left;
            }

            if (current.cell.allowsUp() && !visited[current.i][current.j - 1]) {
                CellPosition up = new CellPosition(cells[current.i][current.j - 1], current.i, current.j - 1);
                next_positions.add(up);
                up.setPrev(current);
                visited[current.i][current.j - 1] = true;

                if (current.i == player_pos.i && current.j - 1 == player_pos.j)
                    return up;
            }

            if (current.cell.allowsDown() && !visited[current.i][current.j + 1]) {
                CellPosition down = new CellPosition(cells[current.i][current.j + 1], current.i, current.j + 1);
                next_positions.add(down);
                down.setPrev(current);
                visited[current.i][current.j + 1] = true;

                if (current.i == player_pos.i && current.j + 1 == player_pos.j)
                    return down;
            }
        }
    }

    @Override
    public CellPosition NextPosition(CellPosition current, Killer killer){
        CellPosition end = FindPathToPlayer(current);
        while(end.getPrev() != current)
            end = end.getPrev();
        return end;
    }

    @Override
    public Image getImage() {
        return ResourceManager.getManager().getImage("killer_clever");
    }
}
