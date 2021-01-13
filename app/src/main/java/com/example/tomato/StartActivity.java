package com.example.tomato;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StartActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Button btnSiram, btnStop;
    TextView txtSuhu, txtHumidity, txtMoistureTotal, txtMoisture1, txtMoisture2, txtMoisture3, txtPH;
    String API_KEY = "BBFF-JCKkapS6WlPhjFVDBPs0KLVtYlCOX3";
    String varIdSuhu ="5ff93d500ff4c37a420012f9";
    String varIdHumidity ="5ff93d4e0ff4c37cf639af2e";
    String varIdMoistureTotal ="5ff975f14763e768092e259b";
    String varIdMoisture1 ="5ff93d4e4763e7191cfc6cad";
    String varIdMoisture2 ="5ff975f04763e7687e665ada";
    String varIdMoisture3 ="5ff975f00ff4c3096c28baaa";
    String varIdPH ="5ff93d4f73efc33556de3394";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSuhu = (TextView) findViewById(R.id.txt_nilai_suhu);
        txtHumidity = (TextView) findViewById(R.id.txt_nilai_humidity);
        txtMoisture1 = (TextView) findViewById(R.id.txt_nilai_kelembapan1);
        txtMoisture2 = (TextView) findViewById(R.id.txt_nilai_kelembapan2);
        txtMoisture3 = (TextView) findViewById(R.id.txt_nilai_kelembapan3);
        txtMoistureTotal = (TextView) findViewById(R.id.txt_nilai_kelembapantotal);
        txtPH = (TextView) findViewById(R.id.txt_nilai_ph);
        btnSiram = (Button) findViewById(R.id.siram);
        btnStop = (Button) findViewById(R.id.stop);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UsersData usersData = snapshot.getValue(UsersData.class);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Toast.makeText(StartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
        Thread thread = new Thread(){
            @Override
            public void run() {
//                try {
//                    synchronized (this) {
//                        wait(10000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new UbidotsClient().handleUbidots(varIdSuhu, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {
                                        for (int i=0; i<result.size(); i++){
                                            Log.i("suhu", "onDataReady: "+result.get(0).value);
                                            String dtSuhu = String.valueOf(result.get(0).value);
                                            txtSuhu.setText(dtSuhu+"\u2103");

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdHumidity, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("hum", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtHumidity.setText(dt+"%");

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdPH, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("ph", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtPH.setText(dt);

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdMoistureTotal, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("moistt", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtMoistureTotal.setText(dt+"%");

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdMoisture1, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("moist1", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtMoisture1.setText(dt+"%");

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdMoisture2, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("moist2", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtMoisture2.setText(dt+"%");

                                        }
                                    }
                                });
                                new UbidotsClient().handleUbidots(varIdMoisture3, API_KEY, new UbidotsClient.UbiListener() {
                                    @Override
                                    public void onDataReady(List<UbidotsClient.Value>result) {

                                        for (int i=0; i<result.size(); i++){
                                            Log.i("moist3", "onDataReady: "+result.get(0).value);
                                            String dt = String.valueOf(result.get(0).value);
                                            txtMoisture3.setText(dt+"%");

                                        }
                                    }
                                });
                            }
                        });

                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Intent i = new Intent(getApplicationContext(),StartActivity.class);
//                startActivity(i);
//            };
        };
        thread.start();

//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                // Stuff that updates the UI

//                new UbidotsClient().handleUbidots(varIdSuhu, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("suhu", "onDataReady: "+result.get(0).value);
//                            String dtSuhu = String.valueOf(result.get(0).value);
//                            txtSuhu.setText(dtSuhu+"\u2103");
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdHumidity, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("hum", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtHumidity.setText(dt+"%");
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdPH, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("ph", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtPH.setText(dt);
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdMoistureTotal, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("moistt", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtMoistureTotal.setText(dt+"%");
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdMoisture1, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("moist1", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtMoisture1.setText(dt+"%");
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdMoisture2, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("moist2", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtMoisture2.setText(dt+"%");
//
//                        }
//                    }
//                });
//                new UbidotsClient().handleUbidots(varIdMoisture3, API_KEY, new UbidotsClient.UbiListener() {
//                    @Override
//                    public void onDataReady(List<UbidotsClient.Value>result) {
//
//                        for (int i=0; i<result.size(); i++){
//                            Log.i("moist3", "onDataReady: "+result.get(0).value);
//                            String dt = String.valueOf(result.get(0).value);
//                            txtMoisture3.setText(dt+"%");
//
//                        }
//                    }
//                });
            }
//        });
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }
}