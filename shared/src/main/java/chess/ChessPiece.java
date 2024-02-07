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
    private PieceType type;

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

    public void promotePiece (PieceType promotionPiece) {
        type = promotionPiece;
    }

    private boolean outOfBounds (int row, int col) {
        return (row < 1 || row > 8 || col < 1 || col > 8);
    }

    private boolean noPieceAhead (ChessBoard board, ChessPosition newPosition) {
        return board.getPiece(newPosition) == null;
    }

    private boolean enemyAhead (ChessBoard board, ChessPosition myPosition, ChessPosition newPosition) {
        return board.getPiece(newPosition).pieceColor != board.getPiece(myPosition).pieceColor;
    }

    //private

    private boolean approveMove(HashSet<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, ChessPosition newPosition) {
        if (noPieceAhead(board, newPosition)) {
            moveList.add(new ChessMove(myPosition, newPosition, null)); return true;
        }

        else if (enemyAhead(board, myPosition, newPosition)) {
                moveList.add(new ChessMove(myPosition, newPosition, null));
        }

        return false;
    }

    private boolean endOfBoard (int row, int fwd) {
        return (row + fwd == 1 || row + fwd == 8);
    }

    private boolean pawnAtStart (int row) {
        return row == 2 || row == 7;
    }

    private boolean noMoreForwardMovement (ChessBoard board, int tCol, ChessPosition target) {
        return tCol >= 1 && tCol <= 8 && !noPieceAhead(board, target) && board.getPiece(target).getTeamColor() != pieceColor;
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

    private HashSet<ChessMove> addTrajectories(HashSet<ChessMove> possibleMoves,
                                               ChessBoard board,
                                               ChessPosition myPosition,
                                               int[][] directions,
                                               int distance) {
        for (int[] movement : directions) {
            for (int length = 1; length <= distance; length++) {
                int row = myPosition.getRow() + (length * movement[0]);
                int col = myPosition.getColumn() + (length * movement[1]);

                if (outOfBounds(row, col)) break;

                ChessPosition newPosition = new ChessPosition(row, col);
                if (!approveMove(possibleMoves, board, myPosition, newPosition)) break;
            }
        }
        return possibleMoves;
    }

    private HashSet<ChessMove> addPawnTrajectory(HashSet<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int directionMultiplier = (pieceColor == ChessGame.TeamColor.WHITE) ? 1: -1;

        ChessPiece.PieceType[] upgrades = {
                PieceType.KNIGHT,
                PieceType.ROOK,
                PieceType.QUEEN,
                PieceType.BISHOP
        };

        ChessPosition ahead = new ChessPosition(row + directionMultiplier, col);
        if (noPieceAhead(board, ahead)) {
            if (endOfBoard(row, directionMultiplier)) {
                for (ChessPiece.PieceType upgrade : upgrades) pawnMove(possibleMoves, myPosition, ahead, new ChessPiece.PieceType[]{upgrade}, true);
            }

            else {
                pawnMove(possibleMoves, myPosition, ahead, null, false);

                if (pawnAtStart(row)) {
                    ChessPosition moveTwo = new ChessPosition(row + 2 * directionMultiplier, col);
                    if (noPieceAhead(board, moveTwo)) {
                        pawnMove(possibleMoves, myPosition, moveTwo, null, false);
                    }
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
        if (noMoreForwardMovement(board, tCol, target)) {
            if (endOfBoard(row, fwd)) {
                for (PieceType up : upgrade) {
                    pawnMove(possibleMoves, myPosition, target, new PieceType[]{up}, true);
                }
            } else {
                pawnMove(possibleMoves, myPosition, target, null, false);
            }
        }
    }

    private void pawnMove(HashSet<ChessMove> possibleMoves, ChessPosition myPosition, ChessPosition enemy, PieceType[] upgrade, boolean isPromoted) {
        if (isPromoted) {
            for (PieceType up : upgrade) {
                possibleMoves.add(new ChessMove(myPosition, enemy, up));
            }
        } else possibleMoves.add(new ChessMove(myPosition, enemy, null));
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