package damian.stopwatch.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import damian.stopwatch.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    TextView textView ;

    Button start, stop_clean, lap;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds;

    int laps_number=0;

    public static int laps_max_number=5;

    ListView listView ;

    boolean parametr=false;

    String[] ListElements = new String[] {  };

    String[] ListElements2 = new String[] {  };

    List<String> ListElementsArrayList ;

    List<String> ListElementsArrayList2 ;

    ArrayAdapter<String> adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOverflowMenu();

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        stop_clean = (Button)findViewById(R.id.button2);
        lap = (Button)findViewById(R.id.button3) ;
        listView = (ListView)findViewById(R.id.listView);

        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        ListElementsArrayList2 = new ArrayList<String>(Arrays.asList(ListElements2));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                parametr=true;
                stop_clean.setText("Stop");

            }
        });

        stop_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parametr==true) {
                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);

                    stop_clean.setText("Clear");

                    parametr=false;
                }
                else {
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    MilliSeconds = 0 ;
                    laps_number = 0;

                    textView.setText("0:00:000");

                    ListElementsArrayList2.clear();
                    ListElementsArrayList.clear();

                    adapter.notifyDataSetChanged();
                }

            }
        });

        /*
        clear_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListElementsArrayList.isEmpty()) {}
                else {
                    ListElementsArrayList.remove(0);
                    adapter.notifyDataSetChanged();
                }
            }

        });
        */

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               while (laps_max_number<=laps_number) {
                   if (ListElementsArrayList.isEmpty()) {
                   } else {
                       ListElementsArrayList.remove(0);
                       adapter.notifyDataSetChanged();
                   }
                   laps_number-=1;
               }

                if (ListElementsArrayList.isEmpty())
                {

                    ListElementsArrayList2.add(textView.getText().toString());
                    ListElementsArrayList.add(textView.getText().toString());
                    laps_number+=1;

                    adapter.notifyDataSetChanged();
                }
                else {
                    int x=0, y=0, z=0, count;
                    int size = ListElementsArrayList2.size()-1;
                    String a = ListElementsArrayList2.get(size);
                    Pattern pattern2 = Pattern.compile("(\\d+):(\\d+):(\\d+)");
                    Matcher matcher2 = pattern2.matcher(a);
                    if(matcher2.find()) {
                        x = Integer.parseInt(matcher2.group(1));
                        y = Integer.parseInt(matcher2.group(2));
                        z = Integer.parseInt(matcher2.group(3));
                    }
                    count=(x*60000)+(y*1000)+(z);

                    int x2=0, y2=0, z2=0, count2;
                    String b = textView.getText().toString();
                    Pattern pattern = Pattern.compile("(\\d+):(\\d+):(\\d+)");
                    Matcher matcher = pattern.matcher(b);
                    if(matcher.find()) {
                        x2 = Integer.parseInt(matcher.group(1));
                        y2 = Integer.parseInt(matcher.group(2));
                        z2 = Integer.parseInt(matcher.group(3));
                    }
                    count2=(x2*60000)+(y2*1000)+(z2);
                    int milliseconds = count2-count;

                    int miliseconds = (int) (milliseconds%1000);
                    int seconds = (int) (milliseconds / 1000) % 60 ;
                    int minutes = (int) ((milliseconds / (1000*60)) % 60);

                    String millisecondz = Integer.toString(miliseconds);
                    String secondz = Integer.toString(seconds);
                    String minutez = Integer.toString(minutes);

                    String c = minutez+":"+secondz+":"+millisecondz;
                    Log.e("cont", Integer.toString(count));
                    Log.e("cont2", Integer.toString(count2));
                    Log.e("czas",c);

                    ListElementsArrayList2.add(textView.getText().toString());
                    ListElementsArrayList.add(c);
                    laps_number+=1;

                    adapter.notifyDataSetChanged();


                }
            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}