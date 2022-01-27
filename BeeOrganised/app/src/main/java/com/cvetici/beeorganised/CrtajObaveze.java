package com.cvetici.beeorganised;


import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrtajObaveze extends View {
    private int height,width=0;
    private int radius=0;
    private Paint paint, paint1, bela, paintest;
    private boolean isInit=false;
    private RectF oval;
    private List<Task> listaTaskova;
    Random rand = new Random();
    private boolean dan=true;
    private RectF osnovaKruga;
    private boolean IsTouched;
    private float touchAngle;
    private TextView taskName,startingTime,endingTime;
    private RelativeLayout taskChangerLayout;
    private Animation animacijaOtvaranja,animacijaZatvaranja;
    Context context;
    private ImageButton bin;
    private List<Task> mainList;
    private SmartToDo std;
    private Task tasknow;
    private int[] boje = {
            getResources().getColor(R.color.redpick),
            getResources().getColor(R.color.orangepick),
            getResources().getColor(R.color.yellowpick),
            getResources().getColor(R.color.greenpick),
            getResources().getColor(R.color.bluepick),
            getResources().getColor(R.color.dbluepick),
            getResources().getColor(R.color.purplepick)
    };
    private int[] bojeb = {
            getResources().getColor(R.color.redpickb),
            getResources().getColor(R.color.orangepickb),
            getResources().getColor(R.color.yellowpickb),
            getResources().getColor(R.color.greenpickb),
            getResources().getColor(R.color.bluepickb),
            getResources().getColor(R.color.dbluepickb),
            getResources().getColor(R.color.purplepickb)
    };

    private View WorkerActivity;
    private ImageButton checkbx, color, red, orange, yellow, green, blue, dblue, purple;
    private ImageView check;
    private Button cancel, del;
    Dialog dialogdel, dialogcol;


    public CrtajObaveze(Context context) {
        super(context);
        this.context=context;
    }
    public CrtajObaveze(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public CrtajObaveze(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;


    }
    private void initClock(){
        std = new SmartToDo(5);
        paintest = new Paint();
        paintest.setColor(getResources().getColor(R.color.yellowpick));
        taskName = ((Activity)context).findViewById(R.id.imeTaska);
        startingTime=((Activity)context).findViewById(R.id.startingTime);
        endingTime = ((Activity)context).findViewById(R.id.endingTime);
        height = getHeight();
        width = getWidth();
        dialogcol = new Dialog((Activity)context);
        int min = Math.min(height,width);
        radius = min/2;
        paint = new Paint();
        paint1 = new Paint();
        bela = new Paint();
        bela.setColor(getResources().getColor(R.color.yellowpickb));
        taskChangerLayout = ((Activity)context).findViewById(R.id.changeTask);
        isInit=true;
        osnovaKruga = new RectF(80, 80, width - 80, height - 80);
        IsTouched = false;
        animacijaOtvaranja = AnimationUtils.loadAnimation(context,R.anim.polako_pojavi);
        animacijaZatvaranja = AnimationUtils.loadAnimation(context,R.anim.polako_zatvori);

        load();
        postaviboje();
        std.setTasks((ArrayList<Task>) mainList);

        bin = (ImageButton) ((Activity)context).findViewById(R.id.delete);
        check = ((Activity)context).findViewById(R.id.check);
        color = (ImageButton) ((Activity) context).findViewById(R.id.color);
        opencolor();

    }


    private void opencolor() {
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcol.setContentView(R.layout.colorpicker);
                dialogcol.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                red = (ImageButton) dialogcol.findViewById(R.id.red);
                orange = (ImageButton) dialogcol.findViewById(R.id.orange);
                yellow = (ImageButton) dialogcol.findViewById(R.id.yellow);
                green = (ImageButton) dialogcol.findViewById(R.id.green);
                blue = (ImageButton) dialogcol.findViewById(R.id.blue);
                dblue = (ImageButton) dialogcol.findViewById(R.id.dblue);
                purple = (ImageButton) dialogcol.findViewById(R.id.purple);
                itsred();
                itsorange();
                itsyellow();
                itsgreen();
                itsblue();
                itsdblue();
                itspurple();
                dialogcol.show();

            }
        });

    }

    private void itsred() {
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintest.setColor(boje[0]);
                bela.setColor(bojeb[0]);
                sacuvajboje(0);
                tasknow.SetNewColor(paintest);
                Refreshuj();
                dialogcol.dismiss();
            }
        });

    }

    private void itsorange() {
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintest.setColor(boje[1]);
                bela.setColor(bojeb[1]);
                sacuvajboje(1);
                tasknow.SetNewColor(paintest);
                Refreshuj();
                dialogcol.dismiss();
            }
        });

    }

    private void itsyellow() {
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintest.setColor(boje[2]);
                bela.setColor(bojeb[2]);
                sacuvajboje(2);
                tasknow.SetNewColor(paintest);
                Refreshuj();
                dialogcol.dismiss();
            }
        });

    }

    private void itsgreen() {
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintest.setColor(boje[3]);
                bela.setColor(bojeb[3]);
                sacuvajboje(3);
                tasknow.SetNewColor(paintest);
                Refreshuj();
                dialogcol.dismiss();
            }
        });

    }

    private void itsblue() {
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcol.dismiss();
                paintest.setColor(boje[4]);
                bela.setColor(bojeb[4]);
                sacuvajboje(4);
                tasknow.SetNewColor(paintest);
                Refreshuj();
            }
        });

    }

    private void itsdblue() {
        dblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcol.dismiss();
                paintest.setColor(boje[5]);
                bela.setColor(bojeb[5]);
                sacuvajboje(5);
                tasknow.SetNewColor(paintest);
                Refreshuj();
            }
        });

    }

    private void itspurple() {
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintest.setColor(boje[6]);
                bela.setColor(bojeb[6]);
                sacuvajboje(6);
                tasknow.SetNewColor(paintest);
                Refreshuj();
                dialogcol.dismiss();
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            initClock();
        }
        if(listaTaskova!=null) {

            for(Task item:listaTaskova) {
                paint.setColor(paintest.getColor());
                paint1.setColor(getResources().getColor(R.color.BeeText));
                float hour1 =item.GetTime().GetStartTime().GetHour();
                float minute1 = item.GetTime().GetStartTime().GetMinute();
                float hour2 =item.GetTime().GetEndTime().GetHour();
                float minute2 =item.GetTime().GetEndTime().GetMinute();
                float loc1,loc2;
                loc1 = (hour1+minute1/60);
                loc2 = (hour2+minute2/60);
                if (dan == true && (hour1 >= 12 && hour2 >= 12)) {
                    hour1 = hour1 -12;
                    hour2 = hour2 -12;
                    loc1 = (hour1+minute1/60);
                    loc2 = (hour2+minute2/60);
                    float angle1 = (float) ((Math.PI / 6) * loc1 - Math.PI / 2);
                    float angle2 = (float) ((Math.PI / 6) * loc2 - Math.PI / 2) - angle1;
                    angle2 = (float) (180 * angle2 / Math.PI);
                    angle1 = (float) (180 * angle1 / Math.PI);
                    canvas.drawArc(osnovaKruga, angle1, angle2 , true,paint);
                    canvas.drawArc(osnovaKruga,angle1,2,true,bela);
                    canvas.drawArc(osnovaKruga,angle2+angle1-1,2,true,bela);
                    clickListener(angle1,angle2,canvas,item);



                }if(loc1 < 12 && loc2 >= 12){
                    proveriPrelom(loc1,hour2-12,minute2,canvas,item);

                }
                if (dan == false && loc1 < 12 && loc2 < 12) {
                    float angle1 = (float) ((Math.PI / 6) * loc1 - Math.PI / 2);
                    float angle2 = (float) ((Math.PI / 6) * loc2 - Math.PI / 2) - angle1;
                    angle2 = (float) (180 * angle2 / Math.PI);
                    angle1 = (float) (180 * angle1 / Math.PI);
                    canvas.drawArc(osnovaKruga, angle1 , angle2 , true, paint);
                    canvas.drawArc(osnovaKruga,angle1,2,true,bela);
                    canvas.drawArc(osnovaKruga,angle2+angle1-1,2,true,bela);
                    clickListener(angle1,angle2,canvas,item);

                }
                if(IsTouched&&taskChangerLayout.getVisibility()==VISIBLE){
                    taskChangerLayout.startAnimation(animacijaZatvaranja);
                    taskChangerLayout.setVisibility(INVISIBLE);

                }

            }

        }

    }

    private void proveriPrelom(float loc1,float hour3,float minute3,Canvas canvas,Task item) {
        if(dan==true){
            float angle1 = (float) (-Math.PI / 2);
            float loc3 = (hour3+minute3/60);
            float angle3 = (float) ((Math.PI / 6) * loc3 - Math.PI / 2)-angle1;
            canvas.drawArc(osnovaKruga, (float) (180 * angle1 / Math.PI), (float) (180 * angle3 / Math.PI), true, paint);
            canvas.drawArc(osnovaKruga, (float) (180 * (angle3+angle1) / Math.PI)-1, 1f, true, bela);
            clickListener((float)(180 * angle1 / Math.PI),(float)(180 * angle3 / Math.PI),canvas,item);
        }else {
            float angle1 = (float) ((Math.PI / 6) * loc1 - Math.PI / 2);
            float angle3 = (float) ((Math.PI / 6) * 11.99f - Math.PI / 2)-angle1;
            canvas.drawArc(osnovaKruga, (float) (180 * angle1 / Math.PI), (float) (180 * angle3 / Math.PI), true, paint);
            canvas.drawArc(osnovaKruga, (float) (180 * angle1 / Math.PI), (float) 2, true, bela);
            clickListener((float)(180 * angle1 / Math.PI),(float)(180 * angle3 / Math.PI),canvas,item);
        }
    }

    public void drawLists(List<Task> listaTaskova){
        this.listaTaskova = listaTaskova;
    }
    public void Refreshuj(){
        invalidate();

    }
    public void CrtajDan(boolean dan){
        this.dan = dan;
        Refreshuj();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX,touchY;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
               IsTouched = true;
                touchX = event.getX()-width/2;
                touchY = event.getY()-height/2;
                 touchAngle= (float) Math.toDegrees(Math.atan2(touchY,touchX)+Math.PI/2);
                 touchAngle-=90;
                if (touchAngle < 0) {
                    touchAngle += 360;
                }

                invalidate();
        }
        return super.onTouchEvent(event);
    }


    public void clickListener(float angle1, float angle2,Canvas canvas,Task current){

        if(angle1+angle2<0){
            angle1+=360;
        }

        if(IsTouched&&angle1<=touchAngle&&touchAngle<=angle2+angle1 ){
            tasknow = current;
            IsTouched = false;
            canvas.drawArc(osnovaKruga,(float) angle1, (float) angle2,true,paint1);
            taskName.setText(current.GetTitle());
            startingTime.setText(current.GetTime().GetStartTime().ToStringTime());
            endingTime.setText(current.GetTime().GetEndTime().ToStringTime());
            changeTaskColor();
            if(tasknow.GetDone()){
                check.setVisibility(View.VISIBLE);
            }else{
                check.setVisibility(View.INVISIBLE);
            }
        }


    }
    public void sacuvajboje(int boja){
        SharedPreferences.Editor editor = context.getSharedPreferences("BOJE", Context.MODE_PRIVATE).edit();
        editor.putInt("boja", boja);
        editor.apply();
    }
    public void postaviboje(){
        SharedPreferences prefs = context.getSharedPreferences("BOJE", Context.MODE_PRIVATE);
        int boja = prefs.getInt("boja",3);
        sacuvajboje(boja);
    }


    public void load(){
        String FILE_NAME="taskLists";
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FILE_NAME,null);
        Type type = new TypeToken<List<Task>>() {}.getType();
        mainList = gson.fromJson(json,type);
        if(mainList==null){
            mainList = new ArrayList<>();
        }

    }
    public void changeTaskColor(){
        if(taskChangerLayout.getVisibility()==INVISIBLE) {
            taskChangerLayout.setVisibility(VISIBLE);
            taskChangerLayout.startAnimation(animacijaOtvaranja);
        }
    }
    public Task getClickedTask(){
        return tasknow;

    }

}
