package vishaak_m.xox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayerActivity extends AppCompatActivity {

    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,reset;
    TextView playerX,playerO;
    int player = 1,score = 0;
    char winner;
    char grid[][] = {{'-','-','-'},{'-','-','-'},{'-','-','-'}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Grid
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        reset = findViewById(R.id.resetGrid);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGrid();
                playerX.setText(""+0);
                playerO.setText(""+0);
            }
        });

        //Score
        playerX = findViewById(R.id.playerX);
        playerO = findViewById(R.id.playerO);


        //Logic
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(a1,0,0);
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(a2,0,1);
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(a3,0,2);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(b1,1,0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(b2,1,1);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(b3,1,2);
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(c1,2,0);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(c2,2,1);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelect(c3,2,2);
            }
        });


    }

    private void buttonSelect(Button btn,int x,int y) {
        String object = "-";
        if(player == 1){
            object = "X";
        }
        else{
            object = "O";
        }

        btn.setText(object);
        btn.setEnabled(false);

        grid[x][y] = object.charAt(0);

        if(player == 1) winner = 'X';
        else winner = 'O';

        if(isWin()){
            resetGrid();
            if(winner == 'X'){
                score = Integer.parseInt(""+playerX.getText());
                score++;
                playerX.setText(""+score);
            }
            else{
                score = Integer.parseInt(""+playerO.getText());
                score++;
                playerO.setText(""+score);
            }
            Toast.makeText(getApplicationContext(),winner+" Won the Round :)",Toast.LENGTH_SHORT).show();
        }

        if(draw()){
            resetGrid();
            Toast.makeText(getApplicationContext(),"Draw !!!",Toast.LENGTH_SHORT).show();
        }
        player++;
        if(player == 3) player = 1;
    }

    private boolean draw() {
        int count = 0;
        Button btnGrid[] = {a1,a2,a3,b1,b2,b3,c1,c2,c3};
        for( Button test : btnGrid){
            if(test.getText().toString() == "X" || test.getText().toString() == "O")
                count++;
        }
        if(count == 9)
            return true;
        return false;
    }

    private void resetGrid() {
        Button btnGrid[] = {a1,a2,a3,b1,b2,b3,c1,c2,c3};
        for( Button test : btnGrid){
            test.setText("");
            test.setEnabled(true);
        }


        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3;j++)
                grid[i][j] = '-';
    }

    private boolean isWin() {
        if (checkRow()) return true;
        if (checkColumn()) return true;
        if (checkDiagonal()) return true;
        return  false;
    }

    private boolean checkRow() {
        for (int i = 0; i < 3; i++){
            if (grid[i][0] == grid [i][1] && grid [i][0] == grid[i][2]){
                if (grid[i][0] != '-') return true; //because char '-' is empty
            }
        }

        return false;
    }

    private boolean checkColumn() {
        for (int i = 0; i < 3; i++){
            if (grid[0][i] == grid [1][i] && grid [0][i] == grid[2][i]){
                if (grid[0][i] != '-') return true;
            }
        }

        return false;
    }

    private boolean checkDiagonal() {
        if ((grid[1][1] == grid[0][0] && grid[1][1] == grid[2][2]) ||
                (grid[1][1] == grid[0][2] && grid [1][1] == grid[2][0])){
            if (grid[1][1] != '-') return true;
        }

        return false;
    }

}
