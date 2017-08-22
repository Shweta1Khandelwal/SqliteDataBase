package com.example.shweta.test;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class FINAL extends AppCompatActivity {

    SQLDBHelper db;

    EditText EdittextLastName, EditTextfirstName, EditTextPassword;
    TextView textViewCamera, textViewGallery, textViewCancel;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 188;
    CircleImageView circleImageView;
    FloatingActionButton floatingActionButton;
    String img_Decodable_Str;
    Button buttonSave;
    String email, firstname, lastname, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        EditTextPassword = (EditText) findViewById(R.id.pass_final);
        circleImageView = (CircleImageView) findViewById(R.id.circularImage);
        //get and set text from database START
        EditTextfirstName = (EditText) findViewById(R.id.firstname_final);
        EdittextLastName = (EditText) findViewById(R.id.lastname_final);

        Intent in = getIntent();
        email = in.getStringExtra("key_email");
        firstname = in.getStringExtra("key_firstname");
        lastname = in.getStringExtra("key_lastname");
        EditTextfirstName.setText(firstname);
        EdittextLastName.setText(lastname);
//update or edit details start
        password = in.getStringExtra("key_password");
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLDBHelper helper = new SQLDBHelper(FINAL.this);
                String fname = EditTextfirstName.getText().toString().trim();
                String lname = EdittextLastName.getText().toString().trim();
                Toast.makeText(FINAL.this, "EMAIL" + email, Toast.LENGTH_SHORT).show();
                helper.update_byEMAIL(email, fname, lname);
                Toast.makeText(FINAL.this, "Sucessful saved", Toast.LENGTH_SHORT).show();

            }
        });


        //image picker from camera and gallery comp here
        //connect fab to Gallery
        floatingActionButton = (FloatingActionButton) findViewById(R.id.FAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create intent to Open Image applications like Gallery, Google Photos
                AlertDialog.Builder builder = new AlertDialog.Builder(FINAL.this);
                LayoutInflater inflater = LayoutInflater.from(FINAL.this);
                View v = inflater.inflate(R.layout.uploadphoto, null);

                builder.setView(v);
                final AlertDialog dialog = builder.create();

                dialog.show();
                dialog.setCancelable(true);

                textViewCamera = (TextView) dialog.findViewById(R.id.camera_text);
                textViewGallery = (TextView) dialog.findViewById(R.id.gallery_text);
                textViewCancel = (TextView) dialog.findViewById(R.id.Cancel);
                textViewCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                        startActivityForResult(CameraIntent, CAMERA_REQUEST);
                        dialog.dismiss();
                    }
                });
                textViewGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // Start the Intent
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        dialog.dismiss();
                    }
                });
                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //camera
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            circleImageView.setImageBitmap(photo);

        }
        // When an Image is picked
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            img_Decodable_Str = cursor.getString(columnIndex);
            cursor.close();
            // Set the Image in ImageView after decoding the String
            circleImageView.setImageBitmap(BitmapFactory
                    .decodeFile(img_Decodable_Str));

        } else {
            Toast.makeText(this, "Hey pick your image first",
                    Toast.LENGTH_LONG).show();
        }
    }
    // image picker from camera and gallery comp here




}







