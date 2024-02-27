package server.request;

public class CreateGameRequest {
  private final String gameName;

  public CreateGameRequest(String gameName) {
    this.gameName = gameName;
  }

  public String getName() {
    return gameName;
  }
}
