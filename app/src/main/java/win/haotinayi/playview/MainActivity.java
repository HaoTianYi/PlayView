package win.haotinayi.playview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import win.haotinayi.playviewlib.PlayView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        PlayView playView = (PlayView) findViewById(R.id.pv);
        playView.startViewAnim();
    }
}
