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

        switch (type) {
            case BISHOP:
                addTrajectories(possibleMoves, board, myPosition, bishopMoveSet); break;

//            case KNIGHT:
//                addTrajectories(possibleMoves, board, myPosition,);
        }
        return possibleMoves;
    }

    private void addTrajectories(HashSet<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, int[][] directions) {
        for (int[] dir : directions) {
            for (int mul = 1; mul <= 8; mul++) {
                int row = myPosition.getRow() + (mul * dir[0]);
                int col = myPosition.getColumn() + (mul * dir[1]);

                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(row, col);
                if (!approveMove(moveList, board, myPosition, newPosition)) break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}