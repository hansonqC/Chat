package pl.hansonq.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.hansonq.Models.SocketConnector;
import pl.hansonq.Models.SocketObserver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, SocketObserver {

    @FXML
    private TextArea textMessages;

    @FXML
    private TextField textWriteMessage;

    @FXML
    private Button buttonSend;

    private SocketConnector socketConnector = SocketConnector.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        clickEnterOnWriteMessage();
         textMessages.setWrapText(true);
        socketConnector.connect();
        socketConnector.registerObserver(this);
       clickButtonSend();

    }

    private void clickEnterOnWriteMessage() {
        textWriteMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    socketConnector.sendMessage(textWriteMessage.getText());
                    textWriteMessage.clear();
                }
            }
        });
    }

    public void clickButtonSend(){
        buttonSend.setOnMouseClicked(e->sendAndClear());
    }
    public void sendAndClear(){
        if(!textWriteMessage.getText().isEmpty())
        socketConnector.sendMessage(textWriteMessage.getText());
        textWriteMessage.clear();
    }
    public void onMessage(String s) {
        textMessages.appendText(s);
    }
}