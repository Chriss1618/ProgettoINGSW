package com.ratatouille.Views.Schermate.Stats;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Stats;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_Stats extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_Stats";

    //LAYOUT
    android.view.View view_fragment;
    PieChart    Pie_Chart_Productivity;

    ArrayList<PieEntry> pieEntries;
    TextView            Text_View_Title_productivity;
    LinearLayout        Linear_Layout_Date_produttivita;
    LinearLayout        Linear_Layout_HorizontalBar;
    CardView            Card_view_BarChart;
    CardView            Card_view_from_Data;
    CardView            Card_view_to_Data;
    TextView            Text_View_From_Data;
    TextView            Text_View_to_Data;
    CardView            Card_view_Pie_Chart;
    private TextView        Text_View_Empty;
    private ProgressBar     ProgressBar;

    LinearLayout    LinearLayout_Dialog;
    LinearLayout    LinearLayout_DarkL;
    //FUNCTIONAL
    Manager manager;

    //DATA
    Stats stats;
    long timestampFrom;
    long timestampTo;
    String dataFrom;
    String dataTo;
    private int yearTo = 0;
    private int monthTo;
    private int dayTo;


    private int yearFrom = 0;
    private int monthFrom;
    private int dayFrom;

    private boolean isLoaded = false;

    //OTHER..

    public Fragment_Stats(Manager manager,int a) {
        this.manager = manager;
        pieEntries = new ArrayList<>();
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

        PrepareLayout();
        PrepareData();

        return view_fragment;
    }

    //DATA
    private void SendRequest(){
        ProgressBar.setVisibility(View.VISIBLE);
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_STATS,
                (stats)-> setOperatoriSala((Stats) stats));
        manager.HandleRequest(request);

    }

    @Override
    public void PrepareData() {
        SendRequest();
    }
    private void setOperatoriSala(Stats Newstats){

        requireActivity().runOnUiThread(() -> {
            ProgressBar.setVisibility(View.GONE);
            this.stats = Newstats;
            stats.getListStaffChef().removeIf(utente -> !utente.getType_user().equals("Chef"));
            PrepareStats();

        });

    }

    private void PrepareStats(){

        for(Utente utente : stats.getListStaffChef()){
            Log.d(TAG, "setOperatoriSala: Score ->"+utente.getScore() + " Resetted " );
            utente.setScore(0);
        }
        for(Product prodotto : stats.getListOrdiniCompletati()){
            Log.d(TAG, "------------------------");
            Log.d(TAG, "setOperatoriSala: Timestamp From    ->"+timestampFrom);
            Log.d(TAG, "setOperatoriSala: Timestamp Ordine  ->"+prodotto.getTimestampCompletamento());
            Log.d(TAG, "setOperatoriSala: Timestamp To      ->"+timestampTo);
            Log.d(TAG, "------------------------");
            if(  Long.parseLong( prodotto.getTimestampCompletamento())  > timestampFrom  && Long.parseLong( prodotto.getTimestampCompletamento() ) < timestampTo)
                for(Utente utente : stats.getListStaffChef()){
                    if(prodotto.getId_User().equals(utente.getId_utente()+"")) utente.setScore( utente.getScore() + 1 );
                }

        }
        Log.d(TAG, "PrepareStats: size " + stats.getListStaffChef().size());
        for(Utente utente : stats.getListStaffChef()){
            Log.d(TAG, "setOperatoriSala: Score ->"+utente.getScore() + " Name ->"+utente.getNome() + " "+utente.getCognome());
        }
        setPieData();
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
        LinearLayout_Dialog             = view_fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL              = view_fragment.findViewById(R.id.darkRL);
        Linear_Layout_HorizontalBar     = view_fragment.findViewById(R.id.linear_layout_horizontal_bar);
        Pie_Chart_Productivity          = view_fragment.findViewById(R.id.pie_chart_productivity);
        Text_View_Title_productivity    = view_fragment.findViewById(R.id.text_view_title_productivity);
        Linear_Layout_Date_produttivita = view_fragment.findViewById(R.id.linear_layout_date_produttivita);
        Card_view_from_Data             = view_fragment.findViewById(R.id.Card_from_data);
        Card_view_to_Data               = view_fragment.findViewById(R.id.Card_to_data);
        Text_View_From_Data             = view_fragment.findViewById(R.id.edit_text_from_data);
        Text_View_to_Data               = view_fragment.findViewById(R.id.edit_text_to_data);
        Card_view_Pie_Chart             = view_fragment.findViewById(R.id.card_view_element_pie_chart);
        Card_view_BarChart             = view_fragment.findViewById(R.id.card_view_element_bar_chart);
        ProgressBar                     = view_fragment.findViewById(R.id.progressbar);
        Text_View_Empty                 = view_fragment.findViewById(R.id.text_view_empty);
    }
    @Override
    public void SetActionsOfLayout() {
        Card_view_from_Data .setOnClickListener(view -> getDataDialogFrom(Text_View_From_Data));
        Card_view_to_Data   .setOnClickListener(view -> getDataDialogTo(Text_View_to_Data));
    }
    @Override
    public void SetDataOnLayout() {
        setDataPickers();
    }
    public void setDataPickers() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            final Calendar calendar = Calendar.getInstance();
            yearTo = calendar.get(Calendar.YEAR);
            monthTo = calendar.get(Calendar.MONTH)+1;
            dayTo= calendar.get(Calendar.DAY_OF_MONTH);

            // Calculate "to" date and timestamp
            dataTo = dayTo + "/" + monthTo + "/" + yearTo;
            Date myDateTo = dateFormat.parse(dataTo);
            calendar.setTime(myDateTo);
            timestampTo = calendar.getTimeInMillis() / 1000; // Convert to seconds
            dataTo = dateFormat.format(myDateTo);

            // Calculate "from" date and timestamp
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            yearFrom = calendar.get(Calendar.YEAR);
            monthFrom = calendar.get(Calendar.MONTH);
            dayFrom = calendar.get(Calendar.DAY_OF_MONTH);
            Date newDate = calendar.getTime();
            dataFrom = dateFormat.format(newDate);
            timestampFrom = newDate.getTime() / 1000; // Convert to seconds

            Log.d(TAG, "setDataPickers: dataFrom->" + dataFrom);
            Log.d(TAG, "setDataPickers: timestampFrom->" + timestampFrom);
            Log.d(TAG, "setDataPickers: dataTo->" + dataTo);
            Log.d(TAG, "setDataPickers: timestampTo->" + timestampTo);

            Text_View_From_Data.setText(dataFrom);
            Text_View_to_Data.setText(dataTo);
            monthTo = monthTo -1;
            // You can now use timestampFrom and timestampTo as needed in your code.
        } catch (Exception e) {
            Log.e(TAG, "setDataPickers: ", e);
        }
    }
    //FUNCTIONAL
    private void setPieData(){
        pieEntries.clear();
        stats.getShownStaffChef().clear();
        int max = 0;
        for(Utente user : stats.getListStaffChef()){
            if(user.getScore() > 0){
                pieEntries.add(new PieEntry(user.getScore(), user.getNome() + " " +user.getCognome()));
                stats.getShownStaffChef().add(user);
                //if(max < user.getScore()) max = user.getScore();
                max += user.getScore();
            }
        }
        PieData pieData;
        PieDataSet pieDataSet;
        ValueFormatter vf;
        if(max == 0){
            pieEntries.add(new PieEntry(1, ""));

            pieDataSet = new PieDataSet(pieEntries,"Ordini Registrati");
            pieDataSet.setColors(Color.GRAY);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setSliceSpace(10.0f);
            pieDataSet.setValueTextSize(16f);

            pieData = new PieData(pieDataSet);
            vf = new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "";
                }
            };

            Pie_Chart_Productivity.setCenterText("Nessuno");
        }else{
            pieDataSet = new PieDataSet(pieEntries,"Ordini Registrati");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setSliceSpace(10.0f);
            pieDataSet.setValueTextSize(16f);

            pieData = new PieData(pieDataSet);
            vf = new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return ""+(int)value;
                }
            };

            Pie_Chart_Productivity.setCenterText("Totale: " + max);
        }



        pieData.setValueFormatter(vf);
        Pie_Chart_Productivity.setData(pieData);
        Pie_Chart_Productivity.setEntryLabelColor(Color.BLACK);
        Pie_Chart_Productivity.getDescription().setEnabled(false);
        Pie_Chart_Productivity.getLegend().setEnabled(false);
        Pie_Chart_Productivity.animate();
        Pie_Chart_Productivity.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = Pie_Chart_Productivity.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);

                //Log.d(TAG, "onValueSelected: nome:"+stats.getShownStaffChef().get(index).getNome());
                //Log.d(TAG, "onValueSelected: value->"+stats.getShownStaffChef().get(index).getScore());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Pie_Chart_Productivity.invalidate();
        int[] androidColors = {
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_purple,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_purple
        };

        Linear_Layout_HorizontalBar.removeAllViews();
        TextView textView = new TextView(getContext()); // Replace 'context' with your current context

        textView.setText("Lista Chef");

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.Black)); // Replace 'context' with your current context

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.sf_compact_rounded_bold); // Replace 'context' with your current context
        textView.setTypeface(typeface);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(layoutParams);
        Linear_Layout_HorizontalBar.addView(textView);
        int indexColor = 0;


        for(Utente user : stats.getListStaffChef()){
            createName(user.getNome() + " " + user.getCognome(), user.getScore());
            createHorizontalBar(user.getScore(),max,androidColors[indexColor]);
            indexColor++;
        }
        if(!isLoaded){
            isLoaded = true;
            Card_view_Pie_Chart.setVisibility(View.VISIBLE);
            Card_view_Pie_Chart.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
            Pie_Chart_Productivity.startAnimation(Manager_Animation.getFadeIn(1000));

            Pie_Chart_Productivity.animate().rotationBy(-180).setDuration(1500).start();

            new Handler(Looper.getMainLooper()).postDelayed(()->{
                Card_view_BarChart.setVisibility(View.VISIBLE);
                Card_view_BarChart.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
                Linear_Layout_HorizontalBar.startAnimation(Manager_Animation.getFadeIn(1000));
            },250);
        }else{
            Card_view_Pie_Chart     .startAnimation(Manager_Animation.getFadeIn(500));
            Card_view_BarChart      .startAnimation(Manager_Animation.getFadeIn(1000));

        }

    }


    private void createName(String name, int value){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create the first EditText on the left
        TextView editTextLeft = new TextView(getContext());
        editTextLeft.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f // Weight to make it take up half of the width
        ));
        editTextLeft.setText(name);
        linearLayout.addView(editTextLeft);

        // Create the second EditText on the right
        TextView editTextRight = new TextView(getContext());
        editTextRight.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f // Weight to make it take up half of the width
        ));
        editTextRight.setGravity(Gravity.END);
        editTextRight.setText(value+"");
        linearLayout.addView(editTextRight);
        Linear_Layout_HorizontalBar.addView(linearLayout);

    }
    private void createHorizontalBar(int value,int maxValue,int color){
        ProgressBar progressBar = new ProgressBar(getContext(),null, android.R.attr.progressBarStyleHorizontal); // 'this' refers to the current context (e.g., Activity)

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Set the width to match parent
                ViewGroup.LayoutParams.WRAP_CONTENT   // Set the height to wrap content
        );
        progressBar.setLayoutParams(layoutParams);
        progressBar.setMax(maxValue);
        progressBar.setProgress(value);

        int colorID = ContextCompat.getColor(getContext(), color);
        progressBar.getProgressDrawable().setColorFilter(colorID, PorterDuff.Mode.SRC_IN);

        Linear_Layout_HorizontalBar.addView(progressBar);
    }
    public void getDataDialogFrom(TextView dataPicker){
        Log.d(TAG, "getDataDialog: ");

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), (datePicker, i, i1, i2) -> {
                    String data = i2+"/"+(i1+1)+"/"+i;

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = dateFormat.parse(data);
                        if(timestampTo > date.getTime() / 1000){
                            timestampFrom = date.getTime() / 1000; // Get the timestamp in milliseconds
                            yearFrom = i;
                            monthFrom = i1;
                            dayFrom = i2;
                            dataPicker.setText(data);
                            PrepareStats();
                        }else{
                            Log.d(TAG, "getDataDialogFrom: WrongFrom");
                            new DialogMessage().showDialogError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },yearFrom,monthFrom,dayFrom);

        datePickerDialog.show();
    }

    public void getDataDialogTo(TextView dataPicker){
        Log.d(TAG, "getDataDialog: ");
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), (datePicker, i, i1, i2) -> {
                    String data = i2+"/"+(i1+1)+"/"+i;

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = dateFormat.parse(data);
                        if(timestampFrom < date.getTime() / 1000){
                            timestampTo = date.getTime() / 1000;
                            yearTo = i;
                            monthTo = i1;
                            dayTo = i2;
                            dataPicker.setText(data);
                            PrepareStats();
                        }else{
                            Log.d(TAG, "getDataDialogFrom: WrongTo");
                            new DialogMessage().showDialogError();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PrepareStats();
                },yearTo,monthTo,dayTo);

        datePickerDialog.show();
    }
    //DIALOG

    private class DialogMessage{
        LinearLayout LinearLayout_Error;
        LinearLayout LinearLayout_Success;
        LinearLayout LinearLayout_Loading;

        CardView CardView_Dialog_Cancel;
        private int numGiri = 0;

        public DialogMessage() {
            LinearLayout_Error      = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_error);
            LinearLayout_Success    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_accepted);
            LinearLayout_Loading    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_loading);

            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_Error      .setVisibility(View.GONE);
            LinearLayout_Success    .setVisibility(View.GONE);
            LinearLayout_Loading    .setVisibility(View.GONE);
        }

        private void showLoading(){
            LinearLayout_Loading    .setVisibility(View.VISIBLE);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);
            LinearLayout_Loading    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL       .startAnimation(Manager_Animation.getFadeIn(500));
            new Thread(()->rotation(1500)).start();
        }
        private void rotation(int speed){
            ImageView ImageView_Logo = LinearLayout_Loading.findViewById(R.id.image_view_logo);

            ImageView_Logo.animate()
                    .rotationBy(speed) // Use rotationBy instead of setting absolute rotation value
                    .setDuration(5000)
                    .withEndAction(() -> {
                        // This will be executed when the animation ends
                        int nextSpeed = (numGiri++ % 2 == 0) ? -1500 : 1500;
                        rotation(nextSpeed);
                    });

        }

        private void showDialogError(){
            CardView_Dialog_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_dialog_confirm);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);
            CardView_Dialog_Cancel .setOnClickListener(view -> dismissDialogError());
            LinearLayout_DarkL       .startAnimation(Manager_Animation.getFadeIn(500));
            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));

            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading           .setVisibility(View.GONE);
                LinearLayout_Error            .setVisibility(View.VISIBLE);
                LinearLayout_Error            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);
            hideKeyboardFrom();
        }
        private void dismissDialogError(){
            LinearLayout_Dialog.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_DarkL.startAnimation(Manager_Animation.getFadeOut(500));

            Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));

            LinearLayout_Error  .setVisibility(View.GONE);
            LinearLayout_Dialog .setVisibility(View.GONE);
            LinearLayout_DarkL  .setVisibility(View.GONE);
        }
        private void showDialogSuccess(){
            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));
            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading            .setVisibility(View.GONE);
                LinearLayout_Success            .setVisibility(View.VISIBLE);
                LinearLayout_Success            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);

            hideKeyboardFrom();
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }

    //ANIMATIONS
    @Override
    public void onPause() {
        super.onPause();
        isLoaded = false;
    }

    @Override
    public void StartAnimations(){
        Card_view_Pie_Chart.setVisibility(View.GONE);
        Card_view_BarChart.setVisibility(View.GONE);
        Text_View_Title_productivity    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        Linear_Layout_Date_produttivita .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
    }

    @Override
    public void EndAnimations() {
        Text_View_Title_productivity    .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Linear_Layout_Date_produttivita .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Card_view_Pie_Chart             .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Pie_Chart_Productivity          .startAnimation(Manager_Animation.getFadeOut(400));
        Pie_Chart_Productivity          .animate().rotationBy(120).setDuration(400).start();
        Card_view_BarChart              .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
    }
}