package pl.hansonq.Models;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukasz on 2017-11-04.
 */
@ClientEndpoint
public class SocketConnector {
    private static SocketConnector ourInstance = new SocketConnector();
    public static SocketConnector getInstance() {
        return ourInstance;
    }

    private WebSocketContainer container;
    private Session session;
    private List<SocketObserver> socketObservers;

    private SocketConnector() {
        container = ContainerProvider.getWebSocketContainer();
        socketObservers = new ArrayList<>();
    }

    public void connect() {
        URI uri = URI.create("ws://localhost:8090/chat");
        try {
            container.connectToServer(this, uri);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Połączono!");
    }

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        System.out.println("POLACZONO");
    }

    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message){
        socketObservers.forEach(s -> s.onMessage(message));
    }


    public void registerObserver(SocketObserver observer) {
        socketObservers.add(observer);
    }
}
