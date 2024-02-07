package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor team;
    private ChessBoard board;


    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        team = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
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
        board.addPiece(move.getEndPosition(), chessPiece);
        board.removePiece(move.getStartPosition());
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

    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        for (ChessMove move : piece.pieceMoves(board, startPosition)) {
            ChessPiece before = board.getPiece(move.getEndPosition());
            tryMove(move);
            if (!isInCheck(piece.getTeamColor())) validMoves.add(move);
            cancelMove(move, before);
        }
        return validMoves;
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
        if (!isInCheck(teamColor)) return false;
        else {
            for (ChessMove move : returnAllMovesOfTeam(teamColor)) {
                ChessPiece antes = board.getPiece(move.getEndPosition());
                tryMove(move);
                if (!isInCheck(teamColor)) {
                    cancelMove(move, antes);
                    return false;
                }
                cancelMove(move, antes);
            }
            return true;
        }
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
        return board;
    }
}
