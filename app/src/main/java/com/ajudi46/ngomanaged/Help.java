package com.ajudi46.ngomanaged;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Help extends AppCompatActivity {
    TableLayout act_1,act_2,act_3,act_4,act_5;
    LinearLayout root_1,root_2;
    DatabaseReference databaseReference;
    int a=0,b=0,c=0,d=0,e=0;
    RadioGroup gr1;
    TextView charity_shop,edu_disp,charity_golf,run_displayed,sch_supply;
    TextView nochtext,noedutext,nogolftext,noruntext,noschtext;
    Button submit_feed;
    EditText feedback_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        act_1 = findViewById(R.id.charity_shop_table);
        act_2 = findViewById(R.id.edu_table);
        act_3 = findViewById(R.id.charity_golf_table);
        act_4 = findViewById(R.id.run_table);
        act_5 = findViewById(R.id.Sch_table);
        charity_shop = findViewById(R.id.charity_shop_display);
        edu_disp = findViewById(R.id.edu_display);
        charity_golf = findViewById(R.id.charity_golf_display);
        run_displayed = findViewById(R.id.run_display);
        sch_supply = findViewById(R.id.sch_display);
        nochtext = findViewById(R.id.nocharityText);
        noedutext = findViewById(R.id.noedutext);
        nogolftext = findViewById(R.id.nogolftext);
        noruntext = findViewById(R.id.noruntext);
        noschtext = findViewById(R.id.noschooltext);
        feedback_body = findViewById(R.id.feedback_text);
        charity_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a!=0){
                    if(act_1.getVisibility() == View.GONE)
                        act_1.setVisibility(View.VISIBLE);
                    else
                        act_1.setVisibility(View.GONE);
                }
                else {
                    if(nochtext.getVisibility()==View.GONE)
                        nochtext.setVisibility(View.VISIBLE);
                    else
                        nochtext.setVisibility(View.GONE);
                }

            }
        });

        edu_disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b!=0){
                    if(act_2.getVisibility() == View.GONE)
                        act_2.setVisibility(View.VISIBLE);
                    else
                        act_2.setVisibility(View.GONE);
                }
                else {
                    if(noedutext.getVisibility()==View.GONE)
                        noedutext.setVisibility(View.VISIBLE);
                    else
                        noedutext.setVisibility(View.GONE);
                }

            }
        });
        charity_golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c!=0){
                    if(act_3.getVisibility() == View.GONE)
                        act_3.setVisibility(View.VISIBLE);
                    else
                        act_3.setVisibility(View.GONE);
                }
                else {
                    if(nogolftext.getVisibility()==View.GONE)
                        nogolftext.setVisibility(View.VISIBLE);
                    else
                        nogolftext.setVisibility(View.GONE);
                }
            }
        });
        run_displayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d!=0){
                    if(act_4.getVisibility() == View.GONE)
                        act_4.setVisibility(View.VISIBLE);
                    else
                        act_4.setVisibility(View.GONE);
                }
                else {
                    if(noruntext.getVisibility()==View.GONE)
                        noruntext.setVisibility(View.VISIBLE);
                    else
                        noruntext.setVisibility(View.GONE);
                }

            }
        });
        sch_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e!=0){
                    if(act_5.getVisibility() == View.GONE)
                        act_5.setVisibility(View.VISIBLE);
                    else
                        act_5.setVisibility(View.GONE);
                }
                else {
                    if(noschtext.getVisibility()==View.GONE)
                        noschtext.setVisibility(View.VISIBLE);
                    else
                        noschtext.setVisibility(View.GONE);
                }
            }
        });

        submit_feed = findViewById(R.id.submit_feedback);
        submit_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "ajudi46@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack Report");
                intent.putExtra(Intent.EXTRA_TEXT, feedback_body.getText().toString());
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Intrest_group");

        root_1 = findViewById(R.id.root_help_activity);
        root_2 = findViewById(R.id.feedback_root);
        gr1 = findViewById(R.id.radio_group_1);
        gr1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_rd:
                        root_2.setVisibility(View.GONE);
                        root_1.setVisibility(View.VISIBLE);
                        init();
                        break;
                    case R.id.feedback:
                        root_1.setVisibility(View.GONE);
                        root_2.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    public void init(){
        final TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=24;
        int topMargin=10;
        int rightMargin=24;
        int bottomMargin=10;

        tableRowParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);

        final TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f
        );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String arr[][] = new String[5][15];
                String name[][] = new String[5][15];

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot itemed = items.next();
                    if(itemed.child("event_name").getValue().toString().equals("Charity Shop")){
                        arr[0][a] = itemed.child("username").getValue().toString();
                        name[0][a] = itemed.child("email_id").getValue().toString();
                        Log.i("ARRRRRR Value", "onDataChange: " + arr[0][a]);
                        a++;
                    }
                    if(itemed.child("event_name").getValue().toString().equals("Education For all")){
                        arr[1][b] = itemed.child("username").getValue().toString();
                        name[1][b] = itemed.child("email_id").getValue().toString();
                        b++;
                    }
                    if(itemed.child("event_name").getValue().toString().equals("Charity Golf")){
                        arr[2][c] = itemed.child("username").getValue().toString();
                        name[2][c] = itemed.child("email_id").getValue().toString();
                        c++;
                    }
                    if(itemed.child("event_name").getValue().toString().equals("Run For Peace")){
                        arr[3][d] = itemed.child("username").getValue().toString();
                        name[3][d] = itemed.child("email_id").getValue().toString();
                        d++;
                    }
                    if(itemed.child("event_name").getValue().toString().equals("School Supply")){
                        arr[4][e] = itemed.child("username").getValue().toString();
                        name[4][e] = itemed.child("email_id").getValue().toString();
                        e++;
                    }
                }

                    for(int k=0;k<a;k++){

                        TableRow db_row = new TableRow(Help.this);
                        TextView db_name = new TextView(Help.this);
                        db_name.setText(arr[0][k]);
                        db_name.setTextColor(Color.BLACK);
                        db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                        db_name.setGravity(Gravity.CENTER);
                        db_row.addView(db_name);
                        db_name.setLayoutParams(param);
                        final TextView db_amount = new TextView(Help.this);
                        db_amount.setText(name[0][k]);
                        db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                        db_amount.setTextColor(Color.BLACK);
                        db_amount.setGravity(Gravity.CENTER);
                        db_row.addView(db_amount);
                        db_amount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setClipboard(Help.this,db_amount.getText().toString());
                            }
                        });
                        db_amount.setLayoutParams(param);


                        db_row.setLayoutParams(tableRowParams);
                        act_1.addView(db_row);
                    }

                    for(int k=0;k<b;k++){

                        TableRow db_row = new TableRow(Help.this);
                        TextView db_name = new TextView(Help.this);
                        db_name.setText(arr[1][k]);
                        db_name.setTextColor(Color.BLACK);
                        db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                        db_name.setGravity(Gravity.CENTER);
                        db_row.addView(db_name,80,80);
                        db_name.setLayoutParams(param);

                        final TextView db_amount = new TextView(Help.this);
                        db_amount.setText(name[1][k]);
                        db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                        db_amount.setTextColor(Color.BLACK);
                        db_amount.setGravity(Gravity.CENTER);
                        db_row.addView(db_amount,80,80);
                        db_amount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setClipboard(Help.this,db_amount.getText().toString());
                            }
                        });
                        db_amount.setLayoutParams(param);


                        db_row.setLayoutParams(tableRowParams);
                        act_2.addView(db_row);
                    }

                  for(int k=0;k<c;k++){

                      TableRow db_row = new TableRow(Help.this);
                      TextView db_name = new TextView(Help.this);
                      db_name.setText(arr[2][k]);
                      db_name.setTextColor(Color.BLACK);
                      db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                      db_name.setGravity(Gravity.CENTER);
                      db_row.addView(db_name);
                      db_name.setLayoutParams(param);

                      final TextView db_amount = new TextView(Help.this);
                      db_amount.setText(name[2][k]);
                      db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                      db_amount.setTextColor(Color.BLACK);
                      db_amount.setGravity(Gravity.CENTER);
                      db_row.addView(db_amount);
                      db_amount.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              setClipboard(Help.this,db_amount.getText().toString());
                          }
                      });
                      db_amount.setLayoutParams(param);


                      db_row.setLayoutParams(tableRowParams);
                      act_3.addView(db_row);
                  }

                  for(int k=0;k<d;k++){

                      TableRow db_row = new TableRow(Help.this);
                      TextView db_name = new TextView(Help.this);
                      db_name.setText(arr[3][k]);
                      db_name.setTextColor(Color.BLACK);
                      db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                      db_name.setGravity(Gravity.CENTER);
                      db_row.addView(db_name,80,80);
                      db_name.setLayoutParams(param);

                      final TextView db_amount = new TextView(Help.this);
                      db_amount.setText(name[3][k]);
                      db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                      db_amount.setTextColor(Color.BLACK);
                      db_amount.setGravity(Gravity.CENTER);
                      db_row.addView(db_amount,80,80);
                      db_amount.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              setClipboard(Help.this,db_amount.getText().toString());
                          }
                      });
                      db_amount.setLayoutParams(param);


                      db_row.setLayoutParams(tableRowParams);
                      act_4.addView(db_row);
                  }

                  for(int k=0;k<e;k++){

                      TableRow db_row = new TableRow(Help.this);
                      TextView db_name = new TextView(Help.this);
                      db_name.setText(arr[4][k]);
                      db_name.setTextColor(Color.BLACK);
                      db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                      db_name.setGravity(Gravity.CENTER);
                      db_row.addView(db_name,80,80);
                      db_name.setLayoutParams(param);

                      final TextView db_amount = new TextView(Help.this);
                      db_amount.setText(name[4][k]);
                      db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                      db_amount.setTextColor(Color.BLACK);
                      db_amount.setGravity(Gravity.CENTER);
                      db_row.addView(db_amount,80,80);
                      db_amount.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              setClipboard(Help.this,db_amount.getText().toString());
                          }
                      });
                      db_amount.setLayoutParams(param);


                      db_row.setLayoutParams(tableRowParams);
                      act_5.addView(db_row);
                  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setClipboard(Context context, String text) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            Toast.makeText(context,"Copied To Clipboard",Toast.LENGTH_SHORT).show();
            clipboard.setPrimaryClip(clip);

    }
}
