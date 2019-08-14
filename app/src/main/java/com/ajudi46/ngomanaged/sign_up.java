package com.ajudi46.ngomanaged;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

import static android.Manifest.permission.*;

public class sign_up extends AppCompatActivity {
    private EditText semail,spassword,susername;
    private Button sregisterbutton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ImageView img;
    private static final int RESULT_LOAD_IMAGE = 0;
    String imagePath;
    File imageFile;
    boolean state  = false;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        semail = (EditText) findViewById(R.id.sign_up_email);
        spassword = (EditText) findViewById(R.id.sign_up_password);
        susername = (EditText) findViewById(R.id.sign_up_name);
        sregisterbutton = (Button) findViewById(R.id.sign_up_register);
        mAuth = FirebaseAuth.getInstance();
        sregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==true){
                    uploadimg();
                }
                else{
                    Toast.makeText(sign_up.this,"SELECT A PICTURE",Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("User_id");
        img = findViewById(R.id.user_icon);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFilePermissions();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
    }

    public EditText getSusername() {
        return susername;
    }

    @Override
    protected void onStart(){
        super.onStart();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null !=data){
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
            cursor.moveToFirst();

            int column_index = cursor.getColumnIndex(projection[0]);
            imagePath = cursor.getString(column_index);
            cursor.close();
            imageFile = new File(imagePath);
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getPath());
            img.setImageBitmap(imageBitmap);
            state = true;
        } else {
            Toast.makeText(sign_up.this, "You have not selected and image", Toast.LENGTH_SHORT).show();
            state = false;
        }
    }

    public void registersignup(){

        final String semailed = semail.getText().toString();
        final String spassed = spassword.getText().toString();
        final String username = susername.getText().toString();


        if(TextUtils.isEmpty(semailed) || TextUtils.isEmpty(spassed)){
            Toast.makeText(sign_up.this,"Enter Valid Entries",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i("sjdfhlsad", "registersignup: "+mAuth.getCurrentUser());
            if(mAuth.getCurrentUser()==null){
                mAuth.createUserWithEmailAndPassword(semailed,spassed).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserInfo ufo = new UserInfo(semailed,"0");
                            databaseReference.child(username).setValue(ufo);
                            Toast.makeText(sign_up.this,"Registration Successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(sign_up.this,"Registration Unsuccessful Try Again !",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
            else
            {
                Toast.makeText(sign_up.this,"SIGNOUT PROBLEM",Toast.LENGTH_LONG).show();

            }

        }
    }


    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = sign_up.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += sign_up.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d("", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    public void uploadimg(){
        if (imageFile.exists()){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            Log.i("PATHHH", "onActivityResult: "+imagePath);
            Uri file = Uri.fromFile(new File(imagePath));
            StorageReference riversRef = FirebaseStorage.getInstance().getReference("images/"+susername.getText().toString()+".jpg");
            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Toast.makeText(sign_up.this,"Successful",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            registersignup();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            progressDialog.cancel();

                            // ...
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (int) (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                }
            });
        }


    }


}
