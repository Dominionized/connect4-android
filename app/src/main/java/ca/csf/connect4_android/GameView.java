package ca.csf.connect4_android;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.csf.connect4.shared.Observer;
import ca.csf.connect4.shared.models.Cell;

/**
 * Created by dom on 15/12/15.
 */
public class GameView extends RelativeLayout implements Observer{

    private GameController controller;

    private TextView log;
    private TableLayout grid;
    private Button[] buttonRow;
    private ImageView[][] board;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void generateUi(int width, int height) {

        log = (TextView) findViewById(R.id.text_log);
        log.setMovementMethod(new ScrollingMovementMethod());

        grid = (TableLayout) findViewById(R.id.table_layout);
        grid.setStretchAllColumns(true);

        board = new ImageView[width][height];
        buttonRow = new Button[width];


        // Create button row
        TableRow btnRow = new TableRow(controller);
        for (int i = 0; i < width; i++){
            Button btn = new Button(controller, null, R.attr.buttonStyleSmall);
            btn.setText(Integer.toString(i));
            btn.setMinWidth(0);
            btn.setMinHeight(0);
            btnRow.addView(btn);
            buttonRow[i] = btn;
        }
        grid.addView(btnRow);

        // Create grid
        for (int i = 0; i < height; i++) {
            TableRow row = new TableRow(controller);
            for (int j = 0; j < width; j++) {
                ImageView cell = new ImageView(controller);
                cell.setImageResource(R.drawable.black_token);
                row.addView(cell);
                board[j][i] = cell;
            }
            grid.addView(row);
        }
    }

    public void setController(GameController ctrl) {
        this.controller = ctrl;
    }

    @Override
    public void updateCell(int x, int y, Cell.CellType cellType) {
        Integer resourceId = null;
        switch (cellType) {
            case RED:
                resourceId = R.drawable.red_token;
                break;
            case BLACK:
                resourceId = R.drawable.black_token;
                break;
            case EMPTY:
                resourceId = 0;
                break;
        }
        board[x][y].setImageResource(resourceId);
    }

    @Override
    public void gameWon(String s) {
        log.append("Game won\n");
    }

    @Override
    public void columnFull(int i) {
        buttonRow[i].setEnabled(false);
    }

    @Override
    public void boardFull() {
        log.append("Board is full\n");
    }

    @Override
    public void gameResigned(String s) {
        log.append("Game resigned\n");
    }

    public void logLine(String l) {
        log.append(l + "\n");
    }
}
