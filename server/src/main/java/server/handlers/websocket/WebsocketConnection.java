package server.handlers.websocket;

import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;

import java.io.IOException;

public class WebsocketConnection {

    public final Session session;
    public final UserData userData;
    public final int gameID;

    /**
     * Represents a websocket connection to the server
     * @param session The websocket session
     * @param userData The UserData of the user connected
     */
    public WebsocketConnection(Session session, UserData userData, Integer gameID) {
        this.session = session;
        this.userData = userData;
        this.gameID = gameID;
    }

    /**
     * Sends a message to the websocket session
     * @param message The string message to be sent
     * @throws IOException When something goes wrong with the socket
     */
    void sendMessage(String message) throws IOException {
        session.getRemote().sendString(message);
    }

    /**
     * Sends an error message to the connection
     * @param message The error message to send
     * @throws IOException When something is wrong with the socket
     */
    void sendError(String message) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage("ERROR: " + message);
        session.getRemote().sendString(errorMessage.toString());
    }
}
