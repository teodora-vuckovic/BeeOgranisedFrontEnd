package com.cvetici.beeorganised;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Kalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener  {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    //istrazi sta je ovo Requires API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendar);
        initWigets();
        selectDate = LocalDate.now();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        monthYearText.setText(monthYearFromDate(selectDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i=0; i<=42; i++){
                if(i<=dayOfWeek|| i>daysInMonth+dayOfWeek){
                    daysInMonthArray.add(" ");
                }else{
                    daysInMonthArray.add(String.valueOf(i-dayOfWeek));
                }
        }
        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);

    }

    private void initWigets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextClick(View view) {
        selectDate = selectDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousClick(View view) {
        selectDate = selectDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals(" ")){
            String message ="Selektovali ste datum"+dayText+" "+ monthYearFromDate(selectDate);
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();

        }
    }
}