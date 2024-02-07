package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;


    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
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

    private boolean compromisedKing (ChessPiece king, TeamColor teamColor) {
        return king != null && king.getTeamColor() == teamColor && king.getPieceType() == ChessPiece.PieceType.KING;
    }


    public void updatePiece (ChessMove move, ChessPiece chessPiece) {
        board.addPiece(move.getStartPosition(), chessPiece); board.removePiece(move.getEndPosition());
    }


    private void tryMove (ChessMove move) {
        ChessPiece chessPiece = board.getPiece(move.getStartPosition());
        if (move.getPromotionPiece() != null) chessPiece.promotePiece(move.getPromotionPiece());
        updatePiece(move, chessPiece);
    }

    private void cancelMove (ChessMove move, ChessPiece antes) {
        ChessPiece chessPiece = board.getPiece(move.getEndPosition());
        if (move.getPromotionPiece() != null) chessPiece.promotePiece(ChessPiece.PieceType.PAWN);
        board.addPiece(move.getStartPosition(), chessPiece); board.addPiece(move.getEndPosition(), antes);
    }

    private boolean isPiece (ChessPiece chessPiece, TeamColor color) {
        return chessPiece != null && chessPiece.getTeamColor() == color;
    }

    private Collection<ChessPosition> returnAllPositionsOfTeam (TeamColor color) {
        HashSet<ChessPosition> possiblePositions = new HashSet<>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece chessPiece = board.getPiece(position);
                if (isPiece(chessPiece, color)) possiblePositions.add(position);
            }
        }
        return possiblePositions;
    }

    private Collection<ChessMove> returnAllMovesOfTeam (TeamColor color) {
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        for (ChessPosition positions : returnAllPositionsOfTeam(color)) possibleMoves.addAll(board.getPiece(positions).pieceMoves(board, positions));
        return possibleMoves;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> okayMoves = new HashSet<>();
        ChessPiece chessPiece = board.getPiece(startPosition);
        for (ChessMove move : chessPiece.pieceMoves(board, startPosition)) {
            ChessPiece antes = board.getPiece(move.getEndPosition());
            tryMove(move);
            if (!isInCheck(chessPiece.getTeamColor())) okayMoves.add(move);
            cancelMove(move, antes);
        }
        return okayMoves;
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
        ChessPosition initialPosition = move.getStartPosition();
        ChessPiece piece = board.getPiece(initialPosition);
        if (piece.getTeamColor() != getTeamTurn()) throw new InvalidMoveException("Not your turn yet");
        if (!validMoves(initialPosition).contains(move)) throw new InvalidMoveException("Cannot move there");
        tryMove(move);
        nextTurn();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opposingTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        for (ChessMove move : returnAllMovesOfTeam(opposingTeam)) {
            if (compromisedKing(board.getPiece(move.getEndPosition()), teamColor)) return true;
        }
        return false;

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
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        Collection<ChessPosition> possiblePositions = returnAllPositionsOfTeam(teamColor);
        for (ChessPosition position : possiblePositions) possibleMoves.addAll(validMoves(position));
        return possibleMoves.isEmpty();
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
