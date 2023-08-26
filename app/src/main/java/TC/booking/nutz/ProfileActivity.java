package TC.booking.nutz;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sp;
    Button edit,update,logout;
    EditText name,email,contact,dob;

    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    RadioGroup gender;

    Spinner city;

    ArrayList<String> arrayList;

    Calendar calender;

    RadioButton female,male;
    String scity,sgender;

    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db= openOrCreateDatabase("KIRA",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INTEGER(10),GENDER VARCHAR(6),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);

        edit = findViewById(R.id.pf_edit_button);
        sp =getSharedPreferences(Constantsp.PREF,MODE_PRIVATE);
        
        name = findViewById(R.id.pf_name);
        contact = findViewById(R.id.pf_contact);
        email = findViewById(R.id.pf_email);
        dob = findViewById(R.id.pf_dob);

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, dateclick, calender.get(calender.YEAR), calender.get(calender.MONTH), calender.get(calender.DAY_OF_MONTH));

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

        ArrayAdapter adapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1, arrayList);
        city.setAdapter(adapter);


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    scity = "";
                } else {
                    scity = arrayList.get(i);
                    Toast.makeText(ProfileActivity.this, arrayList.get(i), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender =findViewById(R.id.pf_gender_card);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radiobutton =findViewById(i);
                sgender=radiobutton.getText().toString();
                Toast.makeText(ProfileActivity.this,sgender,Toast.LENGTH_SHORT);
            }
        });
        male = findViewById(R.id.pf_gender_male);
        female = findViewById(R.id.pf_gender_female);

        update = findViewById(R.id.pf_update_profile);

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        update.setOnClickListener(new View.OnClickListener() {
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
                }
                else if(gender.getCheckedRadioButtonId() == -1){
                    new CommonMethod(ProfileActivity.this,"Please Select Gender");
                }
                else if(scity.equals("")){
                    new CommonMethod(ProfileActivity.this,"Please Select City");
                }
                else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date Of Birth");
                }
                else {
                    String selectQuery = "SELECT * FROM USERS WHERE USERID='"+sp.getString(Constantsp.ID,"")+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        String updateQuery = "UPDATE USERS SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"',CONTACT='"+contact.getText().toString()+"',GENDER='"+sgender+"',CITY='"+scity+"',DOB='"+dob.getText().toString()+"' WHERE USERID='"+sp.getString(Constantsp.ID,"")+"'";
                        db.execSQL(updateQuery);
                        new CommonMethod(ProfileActivity.this,"Profile Update Success");

                        sp.edit().putString(Constantsp.NAME,name.getText().toString()).commit();
                        sp.edit().putString(Constantsp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(Constantsp.CONTACT,contact.getText().toString()).commit();
                        sp.edit().putString(Constantsp.GENDER,sgender).commit();
                        sp.edit().putString(Constantsp.CITY,scity).commit();
                        sp.edit().putString(Constantsp.DOB,dob.getText().toString()).commit();

                        setData(false);
                    }
                    else {
                        new CommonMethod(ProfileActivity.this,"Invalid User Id");
                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setData(true);
            }
    });

        setData(false);
    }


    private void setData(boolean isEnable) {
        name.setEnabled(false);
        email.setEnabled(false);
        contact.setEnabled(false);
        dob.setEnabled(false);

        male.setEnabled(false);
        female.setEnabled(false);

        city.setEnabled(false);

        if(isEnable){
            edit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
        }
        else{
            edit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }

        name.setText(sp.getString(Constantsp.NAME,""));
        email.setText(sp.getString(Constantsp.EMAIL,""));
        contact.setText(sp.getString(Constantsp.CONTACT,""));
        dob.setText(sp.getString(Constantsp.DOB,""));

        sgender = sp.getString(Constantsp.GENDER,"");
        if(sgender.equalsIgnoreCase("Male")) {
            male.setChecked(true);
        }
        else if(sgender.equalsIgnoreCase("Female")) {
            female.setChecked(true);
        }
        else{

        }

        //city.setSelection(2);
        scity = sp.getString(Constantsp.CITY,"");
        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(scity.equalsIgnoreCase(arrayList.get(i))){
                iCityPosition = i;
                break;
            }
        }
        city.setSelection(iCityPosition);
    }
}
