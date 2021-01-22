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


public class HumidityFragment extends Fragment {

    public String API_KEY = "BBFF-sFOAioOKr7PpSK7Ikq7qs7whA0MzYy"; //default token
    public String varIdSuhu ="60092d991d84727b5be9698d";
    public String varIdHumidity ="60092d721d84727b6e3bfab9";
    public String varIdMoistureTotal ="60092d411d84727e4ca25ccc";
    public String varIdMoisture1 ="60092c651d84727aafcbd9e5";
    public String varIdMoisture2 ="60092cd91d84727dc34b37ac";
    public String varIdMoisture3 ="60092d031d84727dc34b37ad";
    public String varIdPH ="60092aec1d84727683c242db";
    public String varIdRelay = "60092dc91d84727fcd096c36";


    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // UI reference
    private LineChart tempChart;
    private LineChart humChart;

    public HumidityFragment() {
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
        View v = inflater.inflate(R.layout.fragment_humidity, container, false);
        humChart = (LineChart) v.findViewById(R.id.chartHumdity);
        initChartLine(humChart);

        ( new UbidotsClient() ).handleUbidots(varIdHumidity, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i=0; i < result.size(); i++) {

                    Entry ln = new Entry(result.get(i).value, i);
                    entries.add(ln);
                    Log.d("Chart", ln.toString());
                    // Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }

                LineDataSet lse = new LineDataSet(entries, "Humidity");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.BLUE);
                lse.setCircleColor(Color.BLUE);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.BLUE);

                LineData bd = new LineData(labels, lse);

                humChart.setData(bd);
                Handler handler = new Handler(HumidityFragment.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        humChart.invalidate();
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


    private void initChartLine(LineChart chart) {
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

//    private void initChartBar(BarChart chart) {
//        chart.setTouchEnabled(true);
//        chart.setDrawGridBackground(true);
//        chart.getAxisRight().setEnabled(false);
//        chart.setDrawGridBackground(true);
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setAxisMaxValue(100F);
//        leftAxis.setAxisMinValue(10F);
//        leftAxis.setStartAtZero(false);
//        leftAxis.setAxisLineWidth(2);
//        leftAxis.setDrawGridLines(true);
//
//
//        // X-Axis
//        XAxis xAxis = chart.getXAxis();
//        xAxis.resetLabelsToSkip();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(true);
//    }

}
