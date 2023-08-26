package TC.booking.nutz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login,signup;
    EditText email,password;

    TextView fp;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SQLiteDatabase db;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp= getSharedPreferences(Constantsp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("KIRA",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INTEGER(10),PASSWORD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);

        login= findViewById(R.id.login_button);
        signup= findViewById(R.id.sign_button);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        fp=findViewById(R.id.fp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,fpActivity.class);
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
                } else if (password.getText().toString().trim().length()<6) {
                    password.setError("Minimum 6 charecters required");
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0) {

                        while (cursor.moveToNext()){
                            String sUserId = cursor.getString(0);
                            String sName = cursor.getString(1);
                            String sEmail = cursor.getString(2);
                            String sContact = cursor.getString(3);
                            String sPassword = cursor.getString(4);
                            String sGender = cursor.getString(5);
                            String sCity = cursor.getString(6);
                            String sDob = cursor.getString(7);

                            sp.edit().putString(Constantsp.ID,sUserId).commit();
                            sp.edit().putString(Constantsp.NAME,sName).commit();
                            sp.edit().putString(Constantsp.EMAIL,sEmail).commit();
                            sp.edit().putString(Constantsp.CONTACT,sContact).commit();
                            sp.edit().putString(Constantsp.PASSWORD,sPassword).commit();
                            sp.edit().putString(Constantsp.GENDER,sGender).commit();
                            sp.edit().putString(Constantsp.CITY,sCity).commit();
                            sp.edit().putString(Constantsp.DOB,sDob).commit();

                            Log.d("RESPONSE_USER_DETAIL",sUserId+"\n"+sName+"\n"+sEmail+"\n"+sContact+"\n"+sPassword+"\n"+sGender+"\n"+sCity+"\n"+sDob);
                        }
                    Toast.makeText(MainActivity.this,"Login Succesful",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view,"Login Succesfully",Snackbar.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this,DashBoardActivity.class);
                    startActivity(intent);
                    }
                    else{
                        new CommonMethod(MainActivity.this, "Login Unsuccessfully");
                    }
                }
            }
        });
    }
}