package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private PieceType type;


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
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (type) {
            case BISHOP:
                return bishopMoves(board, myPosition);
            case KNIGHT:
                return knightMoves(board, myPosition);
            case ROOK:
                return rookMoves(board, myPosition);
            case PAWN:
                return pawnMoves(board, myPosition);
            case QUEEN:
                return queenMoves(board, myPosition);
            case KING:
                return kingMoves(board, myPosition);
            default:
                throw new IllegalArgumentException("Unknown chess piece type");
        }

        public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for bishop moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }

        public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for knight moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }

        public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for rook moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }

        public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for pawn moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }

        public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for queen moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }

        public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
            // Implementation for king moves
            // Add valid moves to a Collection<ChessMove> and return it
            return new ArrayList<>();
        }
}

