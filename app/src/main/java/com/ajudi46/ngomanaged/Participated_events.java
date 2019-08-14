package com.ajudi46.ngomanaged;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Participated_events extends AppCompatActivity {

    private DatabaseReference databaseReference,newref;
    TableLayout participate_layout,same_part;
    TextView tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participated_events);
        databaseReference = FirebaseDatabase.getInstance().getReference("participated");
        newref = FirebaseDatabase.getInstance().getReference("Intrest_group");
        participate_layout = findViewById(R.id.participated_table);
        tr= findViewById(R.id.noacttext);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            String username = b.getString("Username");
            init(username);
        }

    }


    public void init(final String username){
        final TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=24;
        int topMargin=10;
        int rightMargin=24;
        int bottomMargin=10;

        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        final TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f
        );
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).getChildrenCount()!=0){
                    participate_layout.setVisibility(View.VISIBLE);
                    Iterator<DataSnapshot> items = dataSnapshot.child(username).getChildren().iterator();
                    final Iterator<DataSnapshot> iterator = dataSnapshot.child(username).getChildren().iterator();
                    while (items.hasNext()){
                        DataSnapshot item = items.next();

                        TableRow db_row = new TableRow(Participated_events.this);
                        final TextView db_name = new TextView(Participated_events.this);
                        db_name.setText(item.getKey());
                        db_name.setTextColor(Color.BLACK);
                        db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                        db_name.setGravity(Gravity.CENTER);
                        db_row.addView(db_name,80,80);
                        db_name.setLayoutParams(param);

                        TextView db_amount = new TextView(Participated_events.this);
                        db_amount.setText(item.getValue(String.class));
                        db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                        db_amount.setTextColor(Color.BLACK);
                        db_amount.setGravity(Gravity.CENTER);
                        db_row.addView(db_amount,80,80);
                        db_amount.setLayoutParams(param);


                        db_row.setLayoutParams(tableRowParams);
                        participate_layout.addView(db_row);

                    }
                }
                else
                    tr.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
