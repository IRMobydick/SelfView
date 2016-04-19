package keyboard3.com.selfviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import keyboard3.com.selfviewdemo.View.CircleView;

public class MainActivity extends AppCompatActivity {

    private CircleView circleView;
    private TextView tv_num;
    View view_line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView = (CircleView) findViewById(R.id.circle);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleView.startCustomAnimation();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circleView.startCustomAnimation();
            }
        },1000);
        tv_num= (TextView) findViewById(R.id.tv_num);
        final int process=13140;
        circleView.setProcessChangeLinstener(new CircleView.processChangeLinstener() {
            @Override
            public void onProcessChange(float interpolatedTime) {
                tv_num.setText((int)(process*interpolatedTime)+"");
            }
        });
        view_line = findViewById(R.id.view_line);
        view_line.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }
}
