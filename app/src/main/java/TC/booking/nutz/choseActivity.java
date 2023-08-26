package TC.booking.nutz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choseActivity extends AppCompatActivity {

    Button sign,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose);

        sign= findViewById(R.id.img_sign_button);
        login=findViewById(R.id.img_login_button);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(choseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(choseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}