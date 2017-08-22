package com.example.shweta.test;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class signup extends AppCompatActivity {
    SQLDBHelper sql;
    String firstname, lastname, email, password, dob;
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextDOB;
    Button buttonSubmit;
      DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sql = new SQLDBHelper(this);
        buttonSubmit = (Button) findViewById(R.id.Submit);
        editTextFirstName = (EditText) findViewById(R.id.signp_first_name);
        editTextLastName = (EditText) findViewById(R.id.signup_last_name);
        editTextEmail = (EditText) findViewById(R.id.signup_email);
        editTextPassword = (EditText) findViewById(R.id.signup_password);
//Calender strt
        editTextDOB = (EditText) findViewById(R.id.signup_DOB);
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mCurrentDate = Calendar.getInstance();
                int Year = mCurrentDate.get(Calendar.YEAR);
                int Month = mCurrentDate.get(Calendar.MONTH);
                int Day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(signup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        datePickerDialog.getDatePicker().setMaxDate(mCurrentDate.getTimeInMillis());
                        editTextDOB.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, Year, Month, Day);
                datePickerDialog.setTitle("Select date");
                datePickerDialog.show();
            }
        });


                //Claender end
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dob = editTextDOB.getText().toString().trim();
                        firstname = editTextFirstName.getText().toString().trim();
                        lastname = editTextLastName.getText().toString().trim();
                        email = editTextEmail.getText().toString().trim();
                        password = editTextPassword.getText().toString().trim();

                        if (firstname.isEmpty()) {
                            Toast.makeText(signup.this, "FirstName cant be empty", Toast.LENGTH_SHORT).show();
                        } else if (email.isEmpty()) {
                            Toast.makeText(signup.this, "email cant be empty", Toast.LENGTH_SHORT).show();
                        } else if (password.isEmpty()) {
                            Toast.makeText(signup.this, "Password cant be empty", Toast.LENGTH_SHORT).show();
                        } else if (lastname.isEmpty()) {
                            Toast.makeText(signup.this, "Lastname cant be empty", Toast.LENGTH_SHORT).show();
                        } else {

                                                                                                                                                                                                                                                                                                                                                                            if (sql.insertUserDetails(firstname, lastname, email, password,dob)) {
                                Intent i = new Intent(signup.this, sample.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(signup.this, "User Already exists!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


            }

    }
