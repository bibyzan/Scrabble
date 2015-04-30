import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class Position implements Serializable {
    private int row, column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public ArrayList<Position> getAdjacentPositions() {
        ArrayList<Position> positions = new ArrayList<Position>();

        positions.add(new Position(row + 1, column));
        positions.add(new Position(row - 1, column));
        positions.add(new Position(row, column + 1));
        positions.add(new Position(row, column - 1));

        return positions;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        return column == position.column;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
