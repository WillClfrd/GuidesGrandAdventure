package edu.utsa.cs3443.theguidesgrandadventure;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import edu.utsa.cs3443.theguidesgrandadventure.Controller.GameController;
import edu.utsa.cs3443.theguidesgrandadventure.Model.CollectibleThread;
import edu.utsa.cs3443.theguidesgrandadventure.Model.GameCanvas;
import edu.utsa.cs3443.theguidesgrandadventure.Model.CharacterThread;
import edu.utsa.cs3443.theguidesgrandadventure.Model.MediaPlayerManager;

//GameView activity
//William
public class GameActivity extends AppCompatActivity {
    private GameCanvas gameCanvas;
    private GameController controller;
    private CharacterThread characterThread;
    private CollectibleThread collectibleThread;

    @SuppressLint("AppCompatMethod")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_game);

        LinearLayout ll = findViewById(R.id.game_view_ll);
        gameCanvas = new GameCanvas(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        ll.addView(gameCanvas, lp);

        controller = new GameController(this);

        if(MediaPlayerManager.isPlaying) {
            MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance(this);
            mediaPlayerManager.playMusic(R.raw.gamemusic);
        }

        ImageButton upButton = findViewById(R.id.up_arrow);
        ImageButton downButton = findViewById(R.id.down_arrow);
        ImageButton rightButton = findViewById(R.id.right_arrow);
        ImageButton leftButton = findViewById(R.id.left_arrow);
        ImageButton inGameMenuButton = findViewById(R.id.in_game_menu_button);

        setupButton(upButton);
        setupButton(downButton);
        setupButton(rightButton);
        setupButton(leftButton);
        setupButton(inGameMenuButton);

        collectibleThread = new CollectibleThread(this);
        collectibleThread.setRunning(true);
        collectibleThread.start();

        characterThread = new CharacterThread(this);
        characterThread.setRunning(true);
        characterThread.start();
    }

    private void setupButton(View view){
        view.setOnClickListener(controller);
    }

    public GameCanvas getGameCanvas(){
        return this.gameCanvas;
    }

    public CharacterThread getCharacterThread(){
        return this.characterThread;
    }

    public CollectibleThread getCollectibleThread(){
        return this.collectibleThread;
    }
}
