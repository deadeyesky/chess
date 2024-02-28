package server;

import com.google.gson.Gson;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/game", (request, response) -> ));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


    private String registerRoutine (Request request, Response response) {
        response.header("Content-Type", "application/json");
        Gson gson = new Gson();
        Reg
    }
}
