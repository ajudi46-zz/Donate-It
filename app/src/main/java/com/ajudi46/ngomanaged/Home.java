package com.ajudi46.ngomanaged;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Iterator;


public class Home extends AppCompatActivity {

    DatabaseReference mdatabasereference;
    StorageReference myref;
    TextView userid,fid;
    TextView user_email_id;
    FirebaseUser user;
    FirebaseAuth auth;
    String usedd;
    int flag = 0;
    String fcount;
    GlideApp loaded;
    ImageView imageView,flamedialog;
    String ud;
    String user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user_email_id = findViewById(R.id.email_id_user);
        userid = findViewById(R.id.username_id);
        mdatabasereference = FirebaseDatabase.getInstance().getReference("User_id");
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        user_email_id.setText(user.getEmail());
        fid = findViewById(R.id.fire_count);
        flamedialog = findViewById(R.id.Flame);
        imageView = findViewById(R.id.image_view);
        myref = FirebaseStorage.getInstance().getReference();
        final DrawerLayout mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawerlayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mdrawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);

        flamedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        mdatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot item = items.next();
                    if(item.child("emaildid").getValue().toString().equals(user.getEmail())){
                        String username =item.getKey();
                        userid.setText(username);
                        fcount = item.child("flarecount").getValue().toString();
                        fid.setText(fcount);
                        loaded.with(getApplicationContext()).load(myref.child("images/"+username+".jpg")).into(imageView);
                        usedd = username;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ImageView event1 = findViewById(R.id.event1);
        event1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Evented.class);
                i.putExtra("Imagesrc",R.drawable.event1);
                i.putExtra("Username",""+usedd);
                startActivity(i);
            }
        });

        ImageView event2 = findViewById(R.id.event2);
        event2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Evented.class);
                i.putExtra("Imagesrc",R.drawable.event2);
                i.putExtra("Username",""+usedd);
                startActivity(i);
            }
        });

        ImageView event3 = findViewById(R.id.event3);
        event3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Evented.class);
                i.putExtra("Imagesrc",R.drawable.event3);
                i.putExtra("Username",""+usedd);
                startActivity(i);
            }
        });

        ImageView event4 = findViewById(R.id.event4);
        event4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Evented.class);
                i.putExtra("Imagesrc",R.drawable.event4);
                i.putExtra("Username",""+usedd);
                startActivity(i);
            }
        });

        ImageView event5 = findViewById(R.id.event5);
        event5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Evented.class);
                i.putExtra("Imagesrc",R.drawable.event5);
                i.putExtra("Username",""+usedd);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.donate:
                        Intent ind = new Intent(Home.this,Donate.class);
                        ind.putExtra("Username",""+usedd);
                        startActivity(ind);
                        break;
                    case R.id.leaderboard:
                        startActivity(new Intent(Home.this,LeaderBoard.class));
                        break;
                    case R.id.participatedact_nav:
                        Intent in = new Intent(Home.this,Participated_events.class);
                        in.putExtra("Username",""+usedd);
                        startActivity(in);
                        break;
                    case R.id.about:
                        startActivity(new Intent(Home.this,About.class));
                        break;
                    case R.id.logout:
                        auth.signOut();
                        finish();
                        startActivity(new Intent(Home.this,LoginActivity.class));
                        break;
                    case R.id.help:
                        startActivity(new Intent(Home.this,Help.class));

                }
                mdrawerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        loaded.get(this).clearMemory();
    }
    public void openDialog(){
        Dialog_Redeem redeem = new Dialog_Redeem();
        redeem.show(getSupportFragmentManager(),"Redeem Rate");
    }
}
