package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int hashCode () {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ChessPosition chessMove = (ChessPosition) object;
        return this.row == chessMove.row && this.col == chessMove.col;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", row, col);
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }
}
