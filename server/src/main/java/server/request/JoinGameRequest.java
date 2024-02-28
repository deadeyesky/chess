package server.request;

public class JoinGameRequest {
  private final String playerColor;
  private final String gameID;

  public JoinGameRequest (String playerColor, String gameID) {
    this.playerColor = playerColor;
    this.gameID = gameID;
  }

  public String getPlayerColor () {
    return this.playerColor;
  }

  public String getGameID () {
    return this.gameID;
  }
}
