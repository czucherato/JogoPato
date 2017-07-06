package carloszucherato.com.gamepato;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        this.game= new Game(this);
        container.addView(this.game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.game.cancela();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.game.inicia();
        new Thread(this.game).start();
    }
}
