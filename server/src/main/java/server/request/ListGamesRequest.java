package server.request;

public class ListGamesRequest {
  private String authToken;

  public ListGamesRequest (String authToken) {
    this.authToken = authToken;
  }
}
