package TC.booking.nutz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login,signup;
    EditText email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login= findViewById(R.id.login_button);
        signup= findViewById(R.id.sign_button);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError("Email ID required");
                }
                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password required");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Invalid Email");
                } else if (password.getText().toString().trim().length()<8) {
                    password.setError("Minimum 8 charecters required");
                } else {
                    Toast.makeText(MainActivity.this,"Login Succesful",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view,"Login Succesfully",Snackbar.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this,ExeActivity2.class);
                }
            }
        });
    }
}