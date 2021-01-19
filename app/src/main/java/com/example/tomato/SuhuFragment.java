package com.example.tomato;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SuhuFragment extends Fragment {

    private String API_KEY = "BBFF-JCKkapS6WlPhjFVDBPs0KLVtYlCOX3";
    private String varIdSuhu ="5ff93d500ff4c37a420012f9";
    private String varIdHumidity ="5ff93d4e0ff4c37cf639af2e";
    String varIdMoistureTotal ="5ff975f14763e768092e259b";
    String varIdMoisture1 ="5ff93d4e4763e7191cfc6cad";
    String varIdMoisture2 ="5ff975f04763e7687e665ada";
    String varIdMoisture3 ="5ff975f00ff4c3096c28baaa";
    String varIdPH ="5ff93d4f73efc33556de3394";
    String varIdRelay = "60000b4e1d84724cb1c75e92";


    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // UI reference
    private LineChart tempChart;
    private BarChart humChart;

    public SuhuFragment() {
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
        View v = inflater.inflate(R.layout.fragment_suhu, container, false);
        tempChart = (LineChart) v.findViewById(R.id.chartTemp);
        humChart = (BarChart) v.findViewById(R.id.chartPress);
        initChartTemp(tempChart);
        //initChartBar(humChart);

        new UbidotsClient().handleUbidots(varIdSuhu, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i=0; i < result.size(); i++) {

                    Entry be = new Entry(result.get(i).value, i);
                    entries.add(be);
                    Log.d("Chart", be.toString());
                    // Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }
                LineDataSet lse = new LineDataSet(entries, "Tempearature");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.BLUE);
                lse.setCircleColor(Color.BLUE);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.BLUE);

                LineData ld = new LineData(labels, lse);

                tempChart.setData(ld);
                Handler handler = new Handler(SuhuFragment.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tempChart.invalidate();
                    }
                });
            }

        });

        return v;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initChartTemp(LineChart chart) {
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawGridBackground(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(100);
        leftAxis.setAxisMinValue(0);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(1);
        leftAxis.setDrawGridLines(true);


        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.resetLabelsToSkip();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
    }

    private void initChartBar(BarChart chart) {
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawGridBackground(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(100F);
        leftAxis.setAxisMinValue(10F);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(2);
        leftAxis.setDrawGridLines(true);


        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.resetLabelsToSkip();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
    }

}