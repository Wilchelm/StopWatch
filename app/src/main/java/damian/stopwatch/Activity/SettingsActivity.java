package damian.stopwatch.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import damian.stopwatch.R;

public class SettingsActivity extends AppCompatActivity {

    EditText editText;
    Button set_laps_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editText = (EditText)findViewById(R.id.editText);
        set_laps_number = (Button)findViewById(R.id.button4);

        set_laps_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLaps_number();
            }
        });

    }

    public void setLaps_number() {
        String text = editText.getText().toString();
        Boolean bool = false;
        try {
            MainActivity.laps_max_number = Integer.parseInt(text);
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            bool = true;
        } catch (NumberFormatException e) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "This is not number", Toast.LENGTH_LONG);
            toast.show();
        }
        if (bool==true) {
            Intent intent2 = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent2);
        }
    }
}
