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

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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
            moveList.add(new ChessMove(myPosition, newPosition, null)); return true;
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
        PieceType type = piece.getPieceType();
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        int[][] bishopMoveSet = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] rookMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}};
        int[][] kingMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] queenMoveSet = {{1,0}, {-1,0}, {0,-1}, {0,1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        int[][] knightMoveSet = {{-2,1}, {-2,-1}, {-1,2}, {-1,-2}, {1,2}, {1,-2}, {2,1}, {2,-1}};

        return switch (type) {
            case BISHOP -> addTrajectories(possibleMoves, board, myPosition, bishopMoveSet, 8);
            case KING -> addTrajectories(possibleMoves, board, myPosition, kingMoveSet, 1);
            case ROOK -> addTrajectories(possibleMoves, board, myPosition, rookMoveSet, 8);
            case QUEEN -> addTrajectories(possibleMoves, board, myPosition, queenMoveSet, 8);
            case KNIGHT -> addTrajectories(possibleMoves, board, myPosition, knightMoveSet, 1); 
            case PAWN -> addPawnTrajectory(possibleMoves, board, myPosition);
        };
    }

    private HashSet<ChessMove> addTrajectories(HashSet<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition, int[][] directions, int distance) {
        for (int[] movement : directions) {
            for (int length = 1; length <= distance; length++) {
                int row = myPosition.getRow() + (length * movement[0]);
                int col = myPosition.getColumn() + (length * movement[1]);

                if (row < 1 || row > 8 || col < 1 || col > 8) break;

                ChessPosition newPosition = new ChessPosition(row, col);
                if (!approveMove(possibleMoves, board, myPosition, newPosition)) break;
            }
        }
        return possibleMoves;
    }

    private HashSet<ChessMove> addPawnTrajectory(HashSet<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow(), col = myPosition.getColumn();
        int directionMultiplier = (pieceColor == ChessGame.TeamColor.WHITE) ? 1: -1;

        ChessPiece.PieceType[] upgrades = {PieceType.KNIGHT, PieceType.ROOK, PieceType.QUEEN, PieceType.BISHOP};

        ChessPosition ahead = new ChessPosition(row + directionMultiplier, col);
        if (board.getPiece(ahead) == null) {
            if (row + directionMultiplier == 1 || row + directionMultiplier == 8) {
                for (ChessPiece.PieceType upgrade : upgrades) pawnMove(possibleMoves, myPosition, ahead, new ChessPiece.PieceType[]{upgrade}, true);
            } else {
                pawnMove(possibleMoves, myPosition, ahead, null, false);

                if (row == 2 || row == 7) {
                    ChessPosition moveTwo = new ChessPosition(row + 2 * directionMultiplier, col);
                    if (board.getPiece(moveTwo) == null) pawnMove(possibleMoves, myPosition, moveTwo, null, false);
                }
            }
        }

        pawnAttack(possibleMoves, board, myPosition, directionMultiplier, upgrades, col - 1);
        pawnAttack(possibleMoves, board, myPosition, directionMultiplier, upgrades, col + 1);

        return possibleMoves;
    }

    private void pawnAttack(HashSet<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition, int fwd, PieceType[] upgrade, int tCol) {
        int row = myPosition.getRow();
        ChessPosition target = new ChessPosition(row + fwd, tCol);
        if (tCol >= 1 && tCol <= 8 && board.getPiece(target) != null && board.getPiece(target).getTeamColor() != pieceColor) {
            if (row + fwd == 1 || row + fwd == 8) {
                for (PieceType piece : upgrade) {
                    pawnMove(possibleMoves, myPosition, target, new PieceType[]{piece}, true);
                }
            } else {
                pawnMove(possibleMoves, myPosition, target, null, false);
            }
        }
    }

    private void pawnMove(HashSet<ChessMove> moveList, ChessPosition myPosition, ChessPosition target, PieceType[] upgrade, boolean isPromoted) {
        if (isPromoted) {
            for (PieceType up : upgrade) {
                moveList.add(new ChessMove(myPosition, target, up));
            }
        } else moveList.add(new ChessMove(myPosition, target, null));
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