package com.ajudi46.ngomanaged;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LeaderBoard extends AppCompatActivity {
    private DatabaseReference databaseReference;
    TableLayout mostdonation_table,recentdonation_table;
    int i =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        mostdonation_table = findViewById(R.id.most_donation_table);
        recentdonation_table = findViewById(R.id.recent_donations_table);
        databaseReference = FirebaseDatabase.getInstance().getReference("purchases");
        TextView rel1 = findViewById(R.id.most_donation);
        rel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostdonation_table.getVisibility() == View.GONE)
                    mostdonation_table.setVisibility(View.VISIBLE);
                else
                    mostdonation_table.setVisibility(View.GONE);

            }
        });

        TextView rel2 = findViewById(R.id.recent_donations);
        rel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recentdonation_table.getVisibility() == View.GONE)
                    recentdonation_table.setVisibility(View.VISIBLE);
                else
                    recentdonation_table.setVisibility(View.GONE);

            }
        });

        init();

    }
    public void init(){

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=24;
        int topMargin=10;
        int rightMargin=24;
        int bottomMargin=10;

        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        final LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f
        );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ////////////////////////////////////////////////////////////////////////////////////////
                int arr[] = new int[50];
                int brr[] = new int[50];
                int drr[] = new int[50];
                String name[] = new String[50];
                String rname[] = new String[50];
                int rsd;
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                int i=0;
                while (items.hasNext()){
                    DataSnapshot itemed = items.next();
                    String nsed = new String(itemed.child("amount").getValue().toString());
                    rsd=Integer.parseInt(nsed);
                    arr[i] = rsd;
                    name[i] = itemed.child("name").getValue().toString();
                    i++;
                }
                for(int o=0;o<arr.length;o++)
                    brr[o]=o;
                int n = i;
                for (i = 0; i < n-1; i++)
                    for (int j = 0; j < n-i-1; j++)
                        if (arr[j] > arr[j+1])
                        {
                            // swap temp and arr[i]
                            int temp = arr[j];
                            arr[j] = arr[j+1];
                            arr[j+1] = temp;

                            int temp1 = brr[j];
                            brr[j] = brr[j+1];
                            brr[j+1] = temp1;
                        }

                for(int k=n-1;k>=0;k--){

                    TableRow db_row = new TableRow(LeaderBoard.this);
                    TextView db_name = new TextView(LeaderBoard.this);
                    db_name.setText(name[brr[k]]);
                    db_name.setTextColor(Color.BLACK);
                    db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                    db_name.setGravity(Gravity.CENTER);
                    db_row.addView(db_name,80,80);
                    db_name.setLayoutParams(param);

                    TextView db_amount = new TextView(LeaderBoard.this);
                    db_amount.setText(""+arr[k]);
                    db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                    db_amount.setTextColor(Color.BLACK);
                    db_amount.setGravity(Gravity.CENTER);
                    db_row.addView(db_amount,80,80);
                    db_amount.setLayoutParams(param);

                    db_row.setLayoutParams(tableRowParams);
                    mostdonation_table.addView(db_row);
                }

                ////////////////////////////////////////////////////////////////////////////////

                i=0;
                items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot itemed = items.next();
                    String nsed = new String(itemed.child("amount").getValue().toString());
                    rsd=Integer.parseInt(nsed);
                    drr[i] = rsd;
                    rname[i] = itemed.child("name").getValue().toString();
                    i++;
                }

                for(int k=n-1;k>n-11&&k>=0;k--){

                    TableRow db_row = new TableRow(LeaderBoard.this);
                    TextView db_name = new TextView(LeaderBoard.this);
                    db_name.setText(rname[k]);
                    db_name.setTextColor(Color.BLACK);
                    db_name.setTypeface(db_name.getTypeface(),Typeface.ITALIC);
                    db_name.setGravity(Gravity.CENTER);
                    db_row.addView(db_name,80,80);
                    db_name.setLayoutParams(param);

                    TextView db_amount = new TextView(LeaderBoard.this);
                    db_amount.setText(""+drr[k]);
                    db_amount.setTypeface(db_amount.getTypeface(),Typeface.ITALIC);
                    db_amount.setTextColor(Color.BLACK);
                    db_amount.setGravity(Gravity.CENTER);
                    db_row.addView(db_amount,80,80);
                    db_amount.setLayoutParams(param);

                    db_row.setLayoutParams(tableRowParams);
                    recentdonation_table.addView(db_row);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
