package com.example.shweta.test;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sample extends AppCompatActivity {
Button Next;

    EditText editTextEmail,editTextpassword;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);
        Next=(Button)findViewById(R.id.next);
              editTextEmail = (EditText) findViewById(R.id.login_name);
        editTextpassword = (EditText) findViewById(R.id.login_pass);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextEmail.getText().toString().trim();

                password=editTextpassword.getText().toString().trim();
                SQLDBHelper helper = new SQLDBHelper(sample.this);
                Cursor c = helper.authenticate( email, password);

                if(c.getCount()>0) {
                    while (c.moveToNext()) {

                        String firstname = c.getString(1);
                        String lastname = c.getString(2);
                        String dob = c.getString(5);
                        String email = c.getString(3);
                        String pass = c.getString(4);

                        Intent i = new Intent(sample.this, FINAL.class);
                        i.putExtra("key_firstname",firstname);
                        i.putExtra("key_email",email);
                        i.putExtra("key_lastname",lastname);
                        i.putExtra("key_password",pass);
                        i.putExtra("key_dob",dob);
                        startActivity(i);


                    }
                }
               else if(!helper.checkUserExist(email)){
                    Toast.makeText(sample.this, "User Not exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(sample.this, "UserName password not matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
