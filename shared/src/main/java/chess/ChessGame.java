package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();


    public ChessGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private void nextTurn() {
        setTeamTurn((getTeamTurn() == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE);
    }

    private boolean compromisedKing (TeamColor teamColor) {
        return isInCheck(teamColor) || isInStalemate(teamColor) || isInCheckmate(teamColor);
    }

    private boolean isValidMove(ChessMove move, TeamColor teamColor) {
        ChessPosition endPosition = move.getEndPosition();

        ChessBoard boardCOPY = new ChessBoard();

        try {
            setTeamTurn(teamColor);
            makeMove(move);
        }

        catch (InvalidMoveException e) {
            return false;
        }

        if (compromisedKing(teamColor)) return false;

        board = new ChessBoard();
        return true;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) return null;

        TeamColor pieceColor = piece.getTeamColor();
        setTeamTurn(pieceColor);

        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        for (ChessMove move : moves) {
            boolean invalid = false;
            ChessBoard boardCopy = new ChessBoard(board);
            try {
                setTeamTurn(piece.getTeamColor());
                makeMove(move);
            } catch (InvalidMoveException e) {
                invalid = true;
            }
            if (compromisedKing(piece.getTeamColor())) invalid = true;
            if (!invalid) validMoves.add(move);
            board = new ChessBoard(boardCopy);
        }
        return validMoves;
    }

    public boolean isOut (ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition endPosition = move.getEndPosition(), startPosition = move.getStartPosition();
        ChessPiece piece = board.getPiece(startPosition);
        ChessPiece occupyingPiece = board.getPiece(endPosition);

        if (isOut(move.getStartPosition()) || isOut(move.getEndPosition())) throw new InvalidMoveException("Out of bounds.");

        if (piece.getTeamColor() != this.getTeamTurn()) throw new InvalidMoveException("Not turn.");

        if (!piece.pieceMoves(board, startPosition).contains(move)) throw new InvalidMoveException("Illegal move.");

        board.addPiece(endPosition, piece); board.addPiece(startPosition, null);

        if (isInCheck(getTeamTurn())) {
            board.addPiece(startPosition, piece); board.addPiece(endPosition, occupyingPiece);
            throw new InvalidMoveException("King is in check");
        }

        if (move.getPromotionPiece() != null) {
            ChessPiece.PieceType promotionPiece = move.getPromotionPiece(); TeamColor pieceColor = piece.getTeamColor();
            board.addPiece(endPosition, new ChessPiece(pieceColor, promotionPiece));
        }

        nextTurn();

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (isInCheck(teamColor) && isInStalemate(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInStalemate(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ChessGame chessGame=(ChessGame) object;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
