package TC.booking.nutz;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    Button login,signup;
    EditText name,email,contact,password,ConfirmPassword,dob;

    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    RadioGroup gender;

    Spinner city;

    ArrayList<String> arrayList;

    Calendar calender;

    String scity,sgender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signup = findViewById(R.id.sign_button);
        login = findViewById(R.id.login_button);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        ConfirmPassword = findViewById(R.id.confirm_password);
        dob = findViewById(R.id.Sign_dob);

        calender = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener dateclick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calender.set(calender.YEAR, i);
                calender.set(calender.MONTH, i1);
                calender.set(calender.DAY_OF_MONTH, i2);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(sdf.format(calender.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, dateclick, calender.get(calender.YEAR), calender.get(calender.MONTH), calender.get(calender.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        city = findViewById(R.id.sign_city);

        arrayList = new ArrayList<>();

        arrayList.add("ahemdabad");
        arrayList.add("surat");
        arrayList.add("Anand");
        arrayList.add("xyz");
        arrayList.add("Gnagar");
        arrayList.add("ABC");
        arrayList.add(0, "Select City");

        ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, arrayList);
        city.setAdapter(adapter);


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    scity = "";
                } else {
                    scity = arrayList.get(i);
                    Toast.makeText(HomeActivity.this, arrayList.get(i), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender =findViewById(R.id.sign_gender_card);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radiobutton =findViewById(i);
                sgender=radiobutton.getText().toString();
                Toast.makeText(HomeActivity.this,sgender,Toast.LENGTH_SHORT);
            }
        });

        signup = findViewById(R.id.sign_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name Required");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().trim().matches(emailpattern)) {
                    email.setError("Invalid EMail Id");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().trim().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else if (ConfirmPassword.getText().toString().trim().equals("")) {
                    ConfirmPassword.setError("Confirm Password Required");
                } else if (ConfirmPassword.getText().toString().trim().length() < 6) {
                    ConfirmPassword.setError("Min. 6 Char Confirm Password Required");
                } else if (!ConfirmPassword.getText().toString().trim().matches(password.getText().toString().trim())) {
                    ConfirmPassword.setError("Confirm Password Does Not Match");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(HomeActivity.this,"PLease select gender", Toast.LENGTH_SHORT);
                } else if (scity.equals("")) {
                    Toast.makeText(HomeActivity.this,"PLease select city", Toast.LENGTH_SHORT);
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date Of Birth");
                } else {
                    System.out.println("Signup Successfully\nEmail:" + email.getText().toString() + "\nPassword:" + password.getText().toString());
                    Snackbar.make(view, "signup succesfully", Snackbar.LENGTH_SHORT);
                    onBackPressed();
                }
            }
        });

    }
}
