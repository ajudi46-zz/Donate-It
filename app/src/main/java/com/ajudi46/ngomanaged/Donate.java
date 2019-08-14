package com.ajudi46.ngomanaged;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.jar.Manifest;

public class Donate extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    RadioButton radioButton;
    BillingProcessor bp;
    LinearLayout money_layout,mt_layout;
    DatabaseReference databaseReference,getMdatabasereference,getref;
    FirebaseUser user;
    boolean flag;
    RadioButton chh,flarebutton;
    EditText amount_text,behalf_text;
    String user_name;
    int updateflare;
    String flarecount,fccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        money_layout = (LinearLayout)findViewById(R.id.money_linear);
        mt_layout = (LinearLayout)findViewById(R.id.mt_layout);
        user = FirebaseAuth.getInstance().getCurrentUser();
        flarebutton = findViewById(R.id.use_flare);
        final ImageButton phone = findViewById(R.id.phone);
        final ImageButton maps = findViewById(R.id.maps);
        final Button payButton = (Button)findViewById(R.id.pay_button);
        amount_text = (EditText) findViewById(R.id.amount_text);
        databaseReference = FirebaseDatabase.getInstance().getReference("purchases");
        getMdatabasereference =  FirebaseDatabase.getInstance().getReference("User_id");
        getref = FirebaseDatabase.getInstance().getReference("User_id");
        bp = new BillingProcessor(Donate.this, null, this);
        bp.initialize();
        Bundle b = getIntent().getExtras();
        if(b!=null)
            user_name = b.getString("Username");
        getMdatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot item = items.next();
                    if(item.getKey().equals(user_name)){
                        String fcount =item.child("flarecount").getValue().toString();
                        flarecount = fcount;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.money_rd:
                        mt_layout.setVisibility(View.GONE);
                        money_layout.setVisibility(View.VISIBLE);
                        Double fck = Double.parseDouble(flarecount) * 0.1;
                        flarebutton.setText("Flares Redeem: Rs "+ fck);
                        payButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateflare = Integer.parseInt(amount_text.getText().toString());
                                money_layout.setVisibility(View.GONE);
                                flag = true;
                                chh = (RadioButton) findViewById(R.id.display_check);
                                while (flag == true){
                                    getMdatabasereference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                            while (items.hasNext()){
                                                DataSnapshot item = items.next();
                                                if(item.getKey().equals(user_name)){
                                                    String fcount =item.child("flarecount").getValue().toString();
                                                    if(updateflare>50 && updateflare<100){
                                                        int upf = Integer.parseInt(fcount) + 5;
                                                        getref.child(user_name).child("flarecount").setValue("" + upf);
                                                        flag = false;
                                                    }
                                                    if(updateflare>101 && updateflare<500){
                                                        int upf = Integer.parseInt(fcount) + 10;
                                                        getref.child(user_name).child("flarecount").setValue("" + upf);
                                                        flag = false;
                                                    }
                                                    if(updateflare>501 ){
                                                        int upf = Integer.parseInt(fcount) + 20;
                                                        getref.child(user_name).child("flarecount").setValue("" + upf);
                                                        flag = false;
                                                    }
                                                    flarecount = fcount;
                                                    break;
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    break;
                                }

                                    if(bp.isInitialized()){
                                    bp.purchase(Donate.this,"android.test.canceled");
                                    boolean haschecked = chh.isChecked();
                                    boolean flare_check = flarebutton.isChecked();
                                    if(haschecked && flare_check){
                                        String key_id = databaseReference.push().getKey();
                                        purchased paranormal= new purchased(user_name,amount_text.getText().toString());
                                        databaseReference.child(key_id).setValue(paranormal);
                                        getref.child(user_name).child("flarecount").setValue(0);
                                    }
                                    if(haschecked && !flare_check){
                                        String key_id = databaseReference.push().getKey();
                                        purchased paranormal= new purchased(user_name,amount_text.getText().toString());
                                        databaseReference.child(key_id).setValue(paranormal);
                                    }
                                    if(!haschecked && flare_check) {
                                        String key_id = databaseReference.push().getKey();
                                        purchased para= new purchased("Anonymous",amount_text.getText().toString());
                                        databaseReference.child(key_id).setValue(para);
                                        getref.child(user_name).child("flarecount").setValue(0);
                                    }
                                    if(!haschecked && !flare_check) {
                                        String key_id = databaseReference.push().getKey();
                                        purchased para= new purchased("Anonymous",amount_text.getText().toString());
                                        databaseReference.child(key_id).setValue(para);
                                    }
                                    Toast.makeText(Donate.this,"Purchased Thanks",Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                        break;
                    case R.id.mt_rd:
                        money_layout.setVisibility(View.GONE);

                        mt_layout.setVisibility(View.VISIBLE);

                        phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(Donate.this,
                                        new String[]{android.Manifest.permission.CALL_PHONE},
                                        421);
                                String number = "8275679482";
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:"+number));
                                if(ActivityCompat.checkSelfPermission(Donate.this, android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) startActivity(intent);
                                mt_layout.setVisibility(View.GONE);
                            }
                        });

                        maps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri gmmIntentUri = Uri.parse("geo:19.8762,75.3433");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(mapIntent);
                                }
                                mt_layout.setVisibility(View.GONE);
                            }
                        });

                            break;
                }
            }
        });
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {


    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
