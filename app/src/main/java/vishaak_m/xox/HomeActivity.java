package vishaak_m.xox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button singlePlayer,twoPlayer,howToPlay,about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        singlePlayer = findViewById(R.id.sp);
        twoPlayer = findViewById(R.id.tp);
        howToPlay = findViewById(R.id.htp);
        about = findViewById(R.id.about);

        //Single Player
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,SinglePlayerActivity.class);
                startActivity(intent);
            }
        });

        //Two Player
        twoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TwoPlayerActivity.class);
                startActivity(intent);
            }
        });


        //How to Play
        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,HowToPlayActivity.class);
                startActivity(intent);
            }
        });

        //About
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

    }
}
