package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    private boolean approveMove(HashSet<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, ChessPosition newPosition) {
        if (board.getPiece(newPosition) == null) {
            moveList.add(new ChessMove(myPosition, newPosition, null));
            return true;
        }

        else if (board.getPiece(newPosition).pieceColor != board.getPiece(myPosition).pieceColor) {
                moveList.add(new ChessMove(myPosition, newPosition, null));
        }

        return false;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        ChessPiece.PieceType type = piece.getPieceType();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        int[][] bishopMoveSet = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] rookMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}};
        int[][] kingMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] queenMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] knightMoveSet = {{-2,1}, {-2,-1}, {-1,2}, {-1,-2}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};

        switch (type) {
            case BISHOP:
                addTrajectories(possibleMoves, board, myPosition, bishopMoveSet, 8); break;

            case KING:
                addTrajectories(possibleMoves, board, myPosition, kingMoveSet, 1); break;

            case ROOK:
                addTrajectories(possibleMoves, board, myPosition, rookMoveSet, 8); break;

            case QUEEN:
                addTrajectories(possibleMoves, board, myPosition, queenMoveSet, 8); break;
        }
        return possibleMoves;
    }

    private void addTrajectories(HashSet<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, int[][] directions, int distance) {
        for (int[] movement : directions) {
            for (int length = 1; length <= distance; length++) {
                int row = myPosition.getRow() + (length * movement[0]);
                int col = myPosition.getColumn() + (length * movement[1]);

                if (row < 1 || row > 8 || col < 1 || col > 8) break;

                ChessPosition newPosition = new ChessPosition(row, col);
                if (!approveMove(moveList, board, myPosition, newPosition)) break;
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ChessPiece that = (ChessPiece) object;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}