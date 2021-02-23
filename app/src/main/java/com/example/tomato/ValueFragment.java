package com.example.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class ValueFragment extends Fragment {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Hours ;
    ListView listView;
    LinearLayout timerCard;
    Fragment hum,temp,ph,moistA, moist1, moist2, moist3;
    TextView txtTimer;
    Button btnSiram, btnStop;
    TextView txtSuhu, txtHumidity, txtMoistureTotal, txtMoisture1, txtMoisture2, txtMoisture3, txtPH;
    String API_KEY = VarId.API_KEY;
    String varIdSuhu = VarId.varIdSuhu;
    String varIdHumidity = VarId.varIdHumidity;
    String varIdMoistureTotal =VarId.varIdMoistureTotal;
    String varIdMoisture1 = VarId.varIdMoisture1;
    String varIdMoisture2 = VarId.varIdMoisture2;
    String varIdMoisture3 = VarId.varIdMoisture3;
    String varIdPH = VarId.varIdPH;
    String varIdRelay = VarId.varIdRelay;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ValueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_value, container, false);

        txtSuhu = v.findViewById(R.id.txt_nilai_suhu);
        txtHumidity = v.findViewById(R.id.txt_nilai_humidity);
        txtMoisture1 =  v.findViewById(R.id.txt_nilai_kelembapan1);
        txtMoisture2 =  v.findViewById(R.id.txt_nilai_kelembapan2);
        txtMoisture3 =  v.findViewById(R.id.txt_nilai_kelembapan3);
        txtMoistureTotal =  v.findViewById(R.id.txt_nilai_kelembapantotal);
        txtPH =  v.findViewById(R.id.txt_nilai_ph);
        btnSiram =  v.findViewById(R.id.siram);
        btnStop =  v.findViewById(R.id.stop);
        txtTimer = v.findViewById(R.id.timertext);
        timerCard = v.findViewById(R.id.timer);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        new UbidotsClient().handleUbidots(varIdSuhu, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                for (int i=0; i<result.size(); i++){
                    Log.i("suhu", "onDataReady: "+result.get(0).value);
                    final String dtSuhu = String.valueOf(result.get(0).value);

                    //txtSuhu.setText(dtSuhu+"\u2103");
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtSuhu.setText(dtSuhu+"\u2103");
                            txtSuhu.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdHumidity, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("hum", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    //txtHumidity.setText(dt+"%");
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtHumidity.setText(dt+"%");
                            txtHumidity.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdPH, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("ph", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    //txtPH.setText(dt);
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtPH.setText(dt);
                            txtPH.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdMoistureTotal, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("moistt", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    //txtMoistureTotal.setText(dt+"%");
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtMoistureTotal.setText(dt+"%");
                            txtMoistureTotal.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdMoisture1, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("moist1", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    //txtMoisture1.setText(dt+"%");
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtMoisture1.setText(dt+"%");
                            txtMoisture1.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdMoisture2, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("moist2", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    //txtMoisture2.setText(dt+"%");
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtMoisture2.setText(dt+"%");
                            txtMoisture2.invalidate();
                        }
                    }, 5000);
                }
            }
        });
        new UbidotsClient().handleUbidots(varIdMoisture3, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(final List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("moist3", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtMoisture3.setText(dt+"%");
                            txtMoisture3.invalidate();
                        }
                    }, 5000);
                }
            }
        });

        new UbidotsClient().handleUbidots(varIdRelay, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(final List<UbidotsClient.Value>result) {

                for (int i=0; i<result.size(); i++){
                    Log.i("relay", "onDataReady: "+result.get(0).value);
                    final String dt = String.valueOf(result.get(0).value);
                    final Handler[] handler = {new Handler(ValueFragment.this.getActivity().getMainLooper())};
                    handler[0].postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dt.equals("1.00"))
                            {
                                handler[0] = new Handler();
                                StartTime = SystemClock.uptimeMillis();
                                handler[0].postDelayed(runnable, 0);
                                timerCard.setVisibility(VISIBLE);
                                //databaseReference.child("Pompa").setValue("1");
                                btnSiram.setEnabled(false);
                                btnStop.setEnabled(true);
                            }
                            else
                            {
                                MillisecondTime = 0L ;
                                StartTime = 0L ;
                                TimeBuff = 0L ;
                                UpdateTime = 0L ;
                                Seconds = 0 ;
                                Minutes = 0 ;
                                MilliSeconds = 0 ;

                                txtTimer.setText("00:00:00");
                                timerCard.setVisibility(INVISIBLE);
                                btnSiram.setEnabled(true);
                                btnStop.setEnabled(false);
                                //databaseReference.child("Pompa").setValue("0");
                            }

                        }
                    }, 5000);
                }
            }
        });
        handler = new Handler();
        btnSiram =  v.findViewById(R.id.siram);
        btnStop =  v.findViewById(R.id.stop);
        btnSiram.setEnabled(true);
        btnSiram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                timerCard.setVisibility(VISIBLE);
                new UbidotsClient().handleUbidots(varIdRelay, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(final List<UbidotsClient.Value>result) {

                        for (int i=0; i<result.size(); i++){
                            Log.i("moist3", "onDataReady: "+result.get(0).value);
                            final String dt = String.valueOf(result.get(0).value);
                            Handler handler = new Handler(ValueFragment.this.getActivity().getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    txtMoisture3.setText(dt+"%");
                                    txtMoisture3.invalidate();
                                }
                            }, 5000);
                        }
                    }
                });
                //databaseReference.child("Pompa").setValue("1");
                btnSiram.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });
        btnStop.setEnabled(false);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                txtTimer.setText("00:00:00");
                timerCard.setVisibility(INVISIBLE);
                btnSiram.setEnabled(true);
                btnStop.setEnabled(false);
                //databaseReference.child("Pompa").setValue("0");
            }
        });

        txtSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment temp = new SuhuFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, temp);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        txtHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment hum = new HumidityFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, hum);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment ph = new PhFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, ph);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtMoistureTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment moistt = new MoistureAverageFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, moistt);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtMoisture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment moist1 = new MoistureOneFragment();
                //Fragment hum = new Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, moist1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtMoisture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment moist2 = new MoistureTwoFragment();
                //Fragment hum = new Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, moist2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        txtMoisture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Fragment moist3 = new MoistureThreeFragment();
                //Fragment hum = new Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmnt, moist3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff+MillisecondTime;
            Seconds = (int) (UpdateTime/1000);
            Minutes = Seconds/60;
            Seconds = Seconds % 60;
            Hours = Minutes/60;

            MilliSeconds = (int) (UpdateTime % 1000);

            txtTimer.setText("" + String.format("%02d", Hours) + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}