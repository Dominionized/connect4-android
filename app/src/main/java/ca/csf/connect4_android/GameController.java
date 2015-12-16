package ca.csf.connect4_android;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import net.sf.lipermi.net.IClientListener;
import net.sf.lipermi.net.Server;

import java.io.IOException;
import java.net.ConnectException;

import ca.csf.connect4.shared.Connect4Server;
import ca.csf.connect4.shared.models.Cell;

public class GameController extends AppCompatActivity {

    protected GameView view;

    private CallHandler callHandler;
    private Client client;
    private Connect4Server server;

    private static final int GAME_WIDTH = 7;
    private static final int GAME_HEIGHT = 6;

    public GameController() {
        callHandler = new CallHandler();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        view = (GameView) findViewById(R.id.game_view);

        view.setController(this);
        view.generateUi(GAME_WIDTH, GAME_HEIGHT);
    }

    public void connectToServer(View view) throws InterruptedException {
        String address = ((TextView) findViewById(R.id.address_field)).getText().toString();
        int port = Integer.parseInt(((TextView) findViewById(R.id.port_field)).getText().toString());

        try {
            client = new Client(address, port, callHandler);
            server = client.getGlobal(ca.csf.connect4.shared.Connect4Server.class);
            client.addClientListener(new ClientListener());
        } catch(ConnectException e) {
            this.view.logLine("Failed to connect to " + address + ":" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientListener implements IClientListener {
        @Override
        public void disconnected() {
            System.out.println("Disconnected");
        }
    }
}
