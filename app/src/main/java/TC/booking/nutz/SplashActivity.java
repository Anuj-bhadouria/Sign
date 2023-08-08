package TC.booking.nutz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent i= new Intent(SplashActivity.this,MainActivity.class);
            startActivity(i);
            finish();
            }
        },5000);
    }
}