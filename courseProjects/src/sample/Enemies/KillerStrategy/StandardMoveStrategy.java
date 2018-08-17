package sample.Enemies.KillerStrategy;

import javafx.scene.image.Image;
import sample.Enemies.Killer;
import sample.Map.Cell;
import sample.Map.CellPosition;
import sample.Map.GameMap;
import sample.ResourceManager;

import java.util.ArrayList;
import java.util.Random;

public class StandardMoveStrategy extends KillerStrategy {
    @Override
    public CellPosition NextPosition(CellPosition current, Killer killer) {
        Random rand = new Random();
        Cell[][] cells = GameMap.getMap().getCells();

        Cell current_cell = current.cell;
        ArrayList<CellPosition> probable_next = new ArrayList<>();

        if (current_cell.allowsRight()) {
            CellPosition right = new CellPosition(cells[current.i + 1][current.j], current.i + 1, current.j);
            if (killer.getPrev() == null || killer.getPrev().cell != right.cell)
                probable_next.add(right);
        }

        if (current_cell.allowsLeft()) {
            CellPosition left = new CellPosition(cells[current.i - 1][current.j], current.i - 1, current.j);
            if (killer.getPrev() == null || killer.getPrev().cell != left.cell)
                probable_next.add(left);
        }

        if (current_cell.allowsUp()) {
            CellPosition up = new CellPosition(cells[current.i][current.j - 1], current.i, current.j - 1);
            if (killer.getPrev() == null || killer.getPrev().cell != up.cell) {
                probable_next.add(up);
            }
        }

        if (current_cell.allowsDown()) {
            CellPosition down = new CellPosition(cells[current.i][current.j + 1], current.i, current.j + 1);
            if (killer.getPrev() == null || killer.getPrev().cell != down.cell) {
                probable_next.add(down);
            }
        }

        CellPosition next;
        if (probable_next.size() > 0) {
            int index = rand.nextInt(probable_next.size());
            next = probable_next.get(index);
        }
        else {
            next = killer.getPrev();
        }

        killer.setPrev(current);
        return next;
    }

    @Override
    public Image getImage() {
        return ResourceManager.getManager().getImage("killer_standard");
    }
}
