package ca.csf.connect4_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.csf.connect4.shared.models.Cell;

public class GameController extends AppCompatActivity {

    GameView view;

    private static final int GAME_WIDTH = 7;
    private static final int GAME_HEIGHT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        view = (GameView) findViewById(R.id.game_view);

        view.setController(this);
        view.generateUi(GAME_WIDTH, GAME_HEIGHT);
    }

    public void connectToServer(View view) {
        String address = ((TextView) findViewById(R.id.address_field)).getText().toString();
        int port = Integer.parseInt(((TextView) findViewById(R.id.port_field)).getText().toString());
        this.view.updateCell(0, 0, Cell.CellType.RED);
        this.view.updateCell(0,1, Cell.CellType.BLACK);
        this.view.updateCell(0,2, Cell.CellType.EMPTY);
        this.view.boardFull();
        this.view.columnFull(5);
    }
}
