package server.request;

public class LogoutRequest {
  private final String authToken;

  public LogoutRequest (String authToken) {
    this.authToken = authToken;
  }
}
