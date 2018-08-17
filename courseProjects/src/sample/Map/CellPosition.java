package sample.Map;

import sample.Panels.BestPlayersPanel;
import sample.Panels.GoldPanel;
import sample.Panels.LivesPanel;

public class CellPosition {
    public Cell cell;
    public int i;
    public int j;

    public void RemoveBorders(CellPosition another) {
        if (this.i == another.i) {
            if (this.j == another.j + 1) {
                // we are lower
                this.cell.setTop(false);
                another.cell.setBottom(false);
            }
            else if (this.j == another.j - 1) {
                // we are higher
                this.cell.setBottom(false);
                another.cell.setTop(false);
            }
        }
        else if (this.j == another.j) {
            if (this.i == another.i + 1) {
                // we are to the right
                this.cell.setLeft(false);
                another.cell.setRight(false);
            }
            else if (this.i == another.i - 1) {
                // we are to the left
                this.cell.setRight(false);
                another.cell.setLeft(false);
            }
        }
    }

    private CellPosition prev;

    public CellPosition getPrev() {
        return prev;
    }

    public void setPrev(CellPosition another) {
        this.prev = another;
    }

    public CellPosition(Cell cell, int i, int j) {
        this.cell = cell;
        this.i = i;
        this.j = j;
    }
}
