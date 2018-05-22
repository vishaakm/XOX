package vishaak_m.xox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class SinglePlayerActivity extends AppCompatActivity {

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

        for(int i=0;i<2;i++) {
            if (player == 1) {
                object = "X";
                grid[x][y] = object.charAt(0);
                btn.setText(object);
                btn.setEnabled(false);
            } else {
                object = "O";
                aiTurn(object);
            }

            if (player == 1) winner = 'X';
            else winner = 'O';

            if (isWin()) {
                resetGrid();
                if (winner == 'X') {
                    score = Integer.parseInt("" + playerX.getText());
                    score++;
                    playerX.setText("" + score);
                } else {
                    score = Integer.parseInt("" + playerO.getText());
                    score++;
                    playerO.setText("" + score);
                }
                Toast.makeText(getApplicationContext(), winner + " Won the Round :)", Toast.LENGTH_SHORT).show();
            }

            if (draw()) {
                resetGrid();
                Toast.makeText(getApplicationContext(), "Draw !!!", Toast.LENGTH_SHORT).show();
            }

            player++;
        }
        if (player == 3) player = 1;
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
            for(int j = 0; j < 3;j++){
                grid[i][j] = '-';
            }
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

    private void aiTurn(String object) {
        int x = -1,y = -1,bestVal = -1000;

        Button btnGrid[][] = {{a1,a2,a3},{b1,b2,b3},{c1,c2,c3}};

        for (int i = 0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                // Check if celll is empty
                if (grid[i][j]=='-')
                {
                    // Make the move
                    grid[i][j] = object.charAt(0);

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(grid, 0, false);

                    // Undo the move
                    grid[i][j] = '-';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        x = i;
                        y = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        grid[x][y] = object.charAt(0);
        btnGrid[x][y].setText(object);
        btnGrid[x][y].setEnabled(false);
    }

    //Source : https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
    boolean isMovesLeft(char board[][]) {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (board[i][j]=='_')
                    return true;
        return false;
    }

    private int evaluate(char b[][]) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row<3; row++)
        {
            if (b[row][0]==b[row][1] &&
                    b[row][1]==b[row][2])
            {
                if (b[row][0]=='O')
                    return +10;
                else if (b[row][0]=='X')
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (b[0][col]==b[1][col] &&
                    b[1][col]==b[2][col])
            {
                if (b[0][col]=='O')
                    return +10;

                else if (b[0][col]=='X')
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2])
        {
            if (b[0][0]=='O')
                return +10;
            else if (b[0][0]=='X')
                return -10;
        }

        if (b[0][2]==b[1][1] && b[1][1]==b[2][0])
        {
            if (b[0][2]=='O')
                return +10;
            else if (b[0][2]=='X')
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
     private int minimax(char board[][], int depth, boolean isMax) {
        int score = evaluate(board);

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft(board)==false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='-')
                    {
                        // Make the move
                        board[i][j] = 'O';

                        // Call minimax recursively and choose
                        // the maximum value
                        best = max( best,
                                minimax(board, depth+1, !isMax) );

                        // Undo the move
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='-')
                    {
                        // Make the move
                        board[i][j] = 'O';

                        // Call minimax recursively and choose
                        // the minimum value
                        best = min(best,
                                minimax(board, depth+1, !isMax));

                        // Undo the move
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        }
     }

}
