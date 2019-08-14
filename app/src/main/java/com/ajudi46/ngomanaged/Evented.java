package com.ajudi46.ngomanaged;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Evented extends AppCompatActivity {

    DatabaseReference databaseReference,newref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evented);
        ImageButton addtocal1 = findViewById(R.id.calender1);
        TextView event_title = findViewById(R.id.evented_title);
        TextView event_deescrp = findViewById(R.id.evented_descrp);
        TextView event_date = findViewById(R.id.event_date);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("participated");
        newref = FirebaseDatabase.getInstance().getReference("Intrest_group");
        ImageView evented_img = findViewById(R.id.evnted_image);
        Button particpate = findViewById(R.id.participate);
        Bundle b = getIntent().getExtras();
        if(b!= null){
            int flag = b.getInt("Imagesrc");
            final String user_name = b.getString("Username");
            Log.i("BBBBBBBBBBBBBBBBBBBB", "onCreatedddd: "+user_name);

            switch (flag){
                case R.drawable.event1:
                    evented_img.setImageResource(R.drawable.event1);
                    event_title.setText("Charity Shop");
                    event_deescrp.setText(R.string.longevent1);
                    event_date.setText("November 13, 2018");
                    addtocal1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Evented.this,
                                    new String[]{Manifest.permission.WRITE_CALENDAR},
                                    421);
                            String startDate = "2018-11-13";
                            long startTime;
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                startTime = date.getTime();
                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime",startTime);
                                intent.putExtra("allDay", true);
                                intent.putExtra("title", "Charity Shop Event");
                                if(ActivityCompat.checkSelfPermission(Evented.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)
                                    startActivity(intent);
                            }catch (Exception e){}

                        }
                    });

                    particpate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Evented.this,"Successfully Registered For The Event",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user_name).child("Charity Shop").setValue("November 13, 2018");
                            String key_id = databaseReference.push().getKey();
                            EventInfo paranormal= new EventInfo("Charity Shop",currentUser.getEmail(),user_name);
                            newref.child(key_id).setValue(paranormal);                        }
                    });
                    break;


                case R.drawable.event2:
                    evented_img.setImageResource(R.drawable.event2);
                    event_title.setText("Education For All");
                    event_deescrp.setText(R.string.longevent2);
                    event_date.setText("September 21, 2018");
                    addtocal1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Evented.this,
                                    new String[]{Manifest.permission.WRITE_CALENDAR},
                                    421);
                            String startDate = "2018-09-21";
                            long startTime;
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                startTime = date.getTime();
                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime",startTime);
                                intent.putExtra("allDay", true);
                                intent.putExtra("title", "Education For all");
                                if(ActivityCompat.checkSelfPermission(Evented.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)
                                    startActivity(intent);
                            }catch (Exception e){}

                        }
                    });


                    particpate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Evented.this,"Successfully Registered For The Event",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user_name).child("Education For all").setValue("September 21, 2018");
                            String key_id = databaseReference.push().getKey();
                            EventInfo paranormal= new EventInfo("Education For all",currentUser.getEmail(),user_name);
                            newref.child(key_id).setValue(paranormal);
                        }
                    });
                    break;
                case R.drawable.event3:
                    evented_img.setImageResource(R.drawable.event3);
                    event_title.setText("Charity Golf");
                    event_deescrp.setText(R.string.longevent3);
                    event_date.setText("July 24, 2019");
                    addtocal1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Evented.this,
                                    new String[]{Manifest.permission.WRITE_CALENDAR},
                                    421);
                            String startDate = "2019-07-24";
                            long startTime;
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                startTime = date.getTime();
                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime",startTime);
                                intent.putExtra("allDay", true);
                                intent.putExtra("title", "Charity Golf");
                                if(ActivityCompat.checkSelfPermission(Evented.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)
                                    startActivity(intent);
                            }catch (Exception e){}

                        }
                    });

                    particpate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Evented.this,"Successfully Registered For The Event",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user_name).child("Charity Golf").setValue("July 24, 2019");
                            String key_id = databaseReference.push().getKey();
                            EventInfo paranormal= new EventInfo("Charity Golf",currentUser.getEmail(),user_name);
                            newref.child(key_id).setValue(paranormal);
                        }
                    });
                    break;
                case R.drawable.event4:
                    evented_img.setImageResource(R.drawable.event4);
                    event_title.setText("Run for Peace");
                    event_deescrp.setText(R.string.longevent4);
                    event_date.setText("October 27, 2018");
                    addtocal1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Evented.this,
                                    new String[]{Manifest.permission.WRITE_CALENDAR},
                                    421);
                            String startDate = "2018-10-27";
                            long startTime;
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                startTime = date.getTime();
                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime",startTime);
                                intent.putExtra("allDay", true);
                                intent.putExtra("title", "Run For Peace");
                                if(ActivityCompat.checkSelfPermission(Evented.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)
                                    startActivity(intent);
                            }catch (Exception e){}

                        }
                    });

                    particpate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Evented.this,"Successfully Registered For The Event",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user_name).child("Run for Peace").setValue("October 27, 2018");
                            String key_id = databaseReference.push().getKey();
                            EventInfo paranormal= new EventInfo("Run For Peace",currentUser.getEmail(),user_name);
                            newref.child(key_id).setValue(paranormal);
                        }
                    });
                    break;
                case R.drawable.event5:
                    evented_img.setImageResource(R.drawable.event5);
                    event_title.setText("School Supply Drive");
                    event_deescrp.setText(R.string.longevent5);
                    event_date.setText("July 15, 2020");
                    addtocal1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Evented.this,
                                    new String[]{Manifest.permission.WRITE_CALENDAR},
                                    421);
                            String startDate = "2020-07-15";
                            long startTime;
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                                startTime = date.getTime();
                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime",startTime);
                                intent.putExtra("allDay", true);
                                intent.putExtra("title", "School Supply");
                                if(ActivityCompat.checkSelfPermission(Evented.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)
                                startActivity(intent);

                            }catch (Exception e){}

                        }
                    });

                    particpate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Evented.this,"Successfully Registered For The Event",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user_name).child("School Supply").setValue("July 15, 2020");
                            String key_id = databaseReference.push().getKey();
                            EventInfo paranormal= new EventInfo("School Supply",currentUser.getEmail(),user_name);
                            newref.child(key_id).setValue(paranormal);
                        }
                    });
                    break;

            }

        }



    }

}
