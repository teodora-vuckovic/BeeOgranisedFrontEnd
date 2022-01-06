package com.cvetici.beeorganised;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ActivityOptions;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WorkerActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Calendar calendar;
    private TextView d1,d2,d3;
    private TextView w1,w2,w3;

    private boolean clicked=false;
    private FloatingActionButton main,routine,task;

    private Animation rotateOpen,rotateClose,fromButton,toButton ;
    private Animation openTaskView,closeTaskView;

    private LinearLayout LinearViewHolder,ManualTimeLayout,AiLayout,AfterCalculateBtn;
    private boolean TaskClicked =false,FromTimeClicked=false;

    private RadioGroup RG;
    private View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;

    private Button FromTime,ToTime,CaluculateBtn;
    private String Vreme="";

    private Switch daynightSwitch;
    private ImageView sat;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        calendar = Calendar.getInstance();
        bottomSheetDialog = new BottomSheetDialog(
                WorkerActivity.this, R.style.BottomSheetDialogTheme
        );
        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer)
        );
        bottomSheetDialog.setContentView(bottomSheetView);

        FindViews();
        RadioGroupClicked();
        FromToTimeSetter();
        SwitchListener();
        PostaviDatume();



    }
    private void SwitchListener(){

        daynightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sat.setImageResource(R.drawable.ic_amclock);
                }else{
                    sat.setImageResource(R.drawable.ic_pmclock);
                }
            }
        });



    }

    private void FindViews(){

        daynightSwitch = (Switch) findViewById(R.id.daynightSwitch);
        d1 = (TextView) findViewById(R.id.firstDate);
        d2 = (TextView) findViewById(R.id.secondDate);
        d3 = (TextView) findViewById(R.id.thirdDate);
        w1 = (TextView) findViewById(R.id.firstWeek);
        w2 = (TextView) findViewById(R.id.secondWeek);
        w3 = (TextView) findViewById(R.id.thirdWeek);

        LinearViewHolder =  (LinearLayout) findViewById(R.id.taskLinearHolderId);

        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromButton = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toButton = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        openTaskView = AnimationUtils.loadAnimation(this,R.anim.from_empty_holder);
        closeTaskView = AnimationUtils.loadAnimation(this,R.anim.from_holder_empty);

        main = (FloatingActionButton) findViewById(R.id.MainButton);
        routine = (FloatingActionButton) findViewById(R.id.RoutineButton);
        task = (FloatingActionButton) findViewById(R.id.SimpleButton);

        sat = (ImageView) findViewById(R.id.sat);

        RG = bottomSheetView.findViewById(R.id.RadioGroup);
        ManualTimeLayout = bottomSheetView.findViewById(R.id.ManualTimeLayout);
        AiLayout = bottomSheetView.findViewById(R.id.AiTimeLayout);
        FromTime = bottomSheetView.findViewById(R.id.fromTime);
        ToTime = bottomSheetView.findViewById(R.id.toTime);
        AfterCalculateBtn = bottomSheetView.findViewById(R.id.AfterBtnClicked);
        CaluculateBtn = bottomSheetView.findViewById(R.id.CalculateBtn);

        CaluculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AfterCalculateBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void RadioGroupClicked(){
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.ManualTime:
                        ManualTimeLayout.setVisibility(View.VISIBLE);
                        AiLayout.setVisibility(View.GONE);
                        AfterCalculateBtn.setVisibility(View.GONE);
                        CaluculateBtn.setVisibility(View.GONE);

                        break;
                    case R.id.AiTime:
                        if(ManualTimeLayout.getVisibility()==View.VISIBLE){
                            ManualTimeLayout.setVisibility(View.GONE);
                        }
                        AiLayout.setVisibility(View.VISIBLE);

                        CaluculateBtn.setVisibility(View.VISIBLE);


                }
            }
        });

    }
    private void FromToTimeSetter(){
        FromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker  = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");


            }
        });
        ToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker  = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

    }






    @RequiresApi(api = Build.VERSION_CODES.N)
    public void PostaviDatume(){
        Date currentTime  = calendar.getTime();

        String Danas = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);
        String DanasNedelja = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        d1.setText(Danas.substring(0,2));
        w1.setText(DanasNedelja.substring(0,3).toUpperCase(Locale.ROOT));

        //////////////Sutrasnji Dan
        calendar.add(Calendar.DATE,1);
        currentTime = calendar.getTime();
        String Sutra = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);
        String SutraNedelja = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        d2.setText(Sutra.substring(0,2));
        w2.setText(SutraNedelja.substring(0,3).toUpperCase(Locale.ROOT));

        //////////////Prekosutrasnji Dan
        calendar.add(Calendar.DATE,1);
        currentTime = calendar.getTime();
        String PSutra = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);
        String PSutraNedelja = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        d3.setText(PSutra.substring(0,2));
        w3.setText(PSutraNedelja.substring(0,3).toUpperCase(Locale.ROOT));

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public void otvoriKalendar(View view) {
        Intent intent = new Intent(WorkerActivity.this , Kalendar.class);
        ImageButton button = (ImageButton) findViewById(R.id.datumCetvrti);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,button,"transition_calendar");
        startActivity(intent,options.toBundle());

    }
    public void expandButtons(View view) {
        setVisibility(clicked);
        setAnimation(clicked);
        if(!clicked){
            clicked = true;
        }else{
            clicked = false;
        }

    }
    private void setVisibility(boolean clicked) {
        if(!clicked){
            task.setVisibility(View.VISIBLE) ;
            routine.setVisibility(View.VISIBLE);
        }else{
            task.setVisibility(View.INVISIBLE) ;
            routine.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(boolean clicked){
        if(!clicked){
            task.startAnimation(fromButton);
            routine.startAnimation(fromButton);
            main.startAnimation(rotateOpen);
        }else{
            task.startAnimation(toButton);
            routine.startAnimation(toButton);
            main.startAnimation(rotateClose);

        }

    }
    private void setClickable(boolean clicked){
        if(!clicked){
            task.setClickable(true);
            routine.setClickable(true);
        }else{
            task.setClickable(false);
            routine.setClickable(false);

        }


    }

    public void simpleTaskCard(View view) {
            bottomSheetDialog.show();
    }




    @Override
    public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
        if(!FromTimeClicked) {
            FromTime.setText("Starting time: " + Hour+ ":" + Minute);
            FromTimeClicked=true;
        }else{
            ToTime.setText("Ending Time: "+ Hour+ ":" + Minute);
        }

    }
}