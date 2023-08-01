package com.ratatouille.Views.Schermate.Stats;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fragment_Stats extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_Stats";

    //LAYOUT
    android.view.View view_fragment;
    PieChart    Pie_Chart_Productivity;

    ArrayList<PieEntry> pieEntries;
    TextView            Text_View_Title_productivity;
    LinearLayout        Linear_Layout_Date_produttivita;
    CardView            Card_view_from_Data;
    CardView            Card_view_to_Data;
    TextView            Text_View_From_Data;
    TextView            Text_View_to_Data;
    CardView            Card_view_Pie_Chart;

    //FUNCTIONAL
    Manager manager;
    //DATA
    ArrayList<String> OperatoriSala;
    ArrayList<Integer> OperatoriSala_Value ;

    String dataFrom;
    String dataTo;
    //OTHER..

    public Fragment_Stats(Manager manager,int a) {
        this.manager = manager;
        pieEntries = new ArrayList<>();
        OperatoriSala = new ArrayList<>();
        OperatoriSala_Value = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view_fragment =  inflater.inflate(R.layout.fragment__stats, container, false);

        PrepareData();
        PrepareLayout();

        return view_fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        setOperatoriSala();
    }
    private void setOperatoriSala(){
        OperatoriSala.clear();
        OperatoriSala_Value.clear();

        OperatoriSala.add("Marco Mengoni");
        OperatoriSala.add("Francesco Figa");
        OperatoriSala.add("Eminem");

        OperatoriSala_Value.add(25);
        OperatoriSala_Value.add(30);
        OperatoriSala_Value.add(10);

    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();

        StartAnimations();
    }

    @Override
    public void LinkLayout() {
        Pie_Chart_Productivity          = view_fragment.findViewById(R.id.pie_chart_productivity);
        Text_View_Title_productivity    = view_fragment.findViewById(R.id.text_view_title_productivity);
        Linear_Layout_Date_produttivita = view_fragment.findViewById(R.id.linear_layout_date_produttivita);
        Card_view_from_Data             = view_fragment.findViewById(R.id.Card_from_data);
        Card_view_to_Data               = view_fragment.findViewById(R.id.Card_to_data);
        Text_View_From_Data             = view_fragment.findViewById(R.id.edit_text_from_data);
        Text_View_to_Data               = view_fragment.findViewById(R.id.edit_text_to_data);
        Card_view_Pie_Chart             = view_fragment.findViewById(R.id.card_view_element_pie_chart);
    }
    @Override
    public void SetActionsOfLayout() {
        Card_view_from_Data .setOnClickListener(view -> getDataDialog(Text_View_From_Data));
        Card_view_to_Data   .setOnClickListener(view -> getDataDialog(Text_View_to_Data));
    }
    @Override
    public void SetDataOnLayout() {
        setPieData();
        setDataPickers();
    }
    public void setDataPickers()  {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1 ;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dataTo = day+"/"+month+"/"+year;

            Date myDateTo = dateFormat.parse(dataTo);
            calendar.setTime(myDateTo);
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date newDate = calendar.getTime();
            dataFrom = dateFormat.format(newDate);

            Log.d(TAG, "setDataPickers: daraFrom->"+dataFrom);
            Log.d(TAG, "setDataPickers: dataTo->"+dataTo);
            Text_View_From_Data .setText(dataFrom);
            Text_View_to_Data   .setText(dataTo);

        }catch (Exception e){
            Log.e(TAG, "setDataPickers: ",e);
        }

    }
    //FUNCTIONAL
    private void setPieData(){
        pieEntries.clear();
        pieEntries.add(new PieEntry(OperatoriSala_Value.get(0),OperatoriSala.get(0)));
        pieEntries.add(new PieEntry(OperatoriSala_Value.get(1),OperatoriSala.get(1)));
        pieEntries.add(new PieEntry(OperatoriSala_Value.get(2),OperatoriSala.get(2)));


        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Ordini Registrati");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(10.0f);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        ValueFormatter vf = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""+(int)value;
            }
        };

        pieData.setValueFormatter(vf);

        Pie_Chart_Productivity.setData(pieData);
        Pie_Chart_Productivity.setEntryLabelColor(Color.BLACK);
        Pie_Chart_Productivity.getDescription().setEnabled(false);
        Pie_Chart_Productivity.setCenterText("Addetto alla Sala");
        Pie_Chart_Productivity.getLegend().setEnabled(false);
        Pie_Chart_Productivity.animate();
        Pie_Chart_Productivity.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = Pie_Chart_Productivity.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);

                Log.d(TAG, "onValueSelected: nome:"+OperatoriSala.get(index));
                Log.d(TAG, "onValueSelected: value->"+OperatoriSala_Value.get(index));
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    public void getDataDialog(TextView dataPicker){
        Log.d(TAG, "getDataDialog: ");
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), (datePicker, i, i1, i2) -> {
                    String data = i2+"/"+(i1+1)+"/"+i;
                    dataPicker.setText(data);

                },year,month,day);

        datePickerDialog.show();
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        final Handler handler = new Handler();
        Pie_Chart_Productivity.setVisibility(android.view.View.GONE);
        Pie_Chart_Productivity.animate().rotation(240).start();
        handler.postDelayed(()->{
                Pie_Chart_Productivity.setVisibility(android.view.View.VISIBLE);
                Pie_Chart_Productivity.startAnimation(Manager_Animation.getFadeIn(400));
                Pie_Chart_Productivity.animate().rotationBy(120).setDuration(600).start();
            },400
        );
        Text_View_Title_productivity    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        Linear_Layout_Date_produttivita .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        Card_view_Pie_Chart             .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
    }

    @Override
    public void EndAnimations() {
        Text_View_Title_productivity    .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Linear_Layout_Date_produttivita .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Card_view_Pie_Chart             .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Pie_Chart_Productivity          .startAnimation(Manager_Animation.getFadeOut(400));
        Pie_Chart_Productivity          .animate().rotationBy(120).setDuration(400).start();
    }
}