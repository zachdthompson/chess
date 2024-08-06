package websocket.messages;

public class LoadGameMessage extends ServerMessage {

    private final String gameMessage;

    public LoadGameMessage(String gameMessage) {
        super(ServerMessageType.LOAD_GAME);
        this.gameMessage = gameMessage;
    }

}
