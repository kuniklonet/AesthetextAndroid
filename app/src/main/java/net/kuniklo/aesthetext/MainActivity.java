package net.kuniklo.aesthetext;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, SeekBar.OnSeekBarChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button button;
    private TextView input;
    private TextView output;
    private SeekBar spacingBar;
    private TextView spacingText;
    private Switch capsSwitch;
    private Button shareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.copyButton);
        button.setOnClickListener(MainActivity.this);

        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = output.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        input = (TextView) findViewById(R.id.inputText);

        capsSwitch = (Switch) findViewById(R.id.capsSwitch);

        output = (TextView) findViewById(R.id.output);

        spacingBar = (SeekBar) findViewById(R.id.spacingBar);
        spacingBar.setOnSeekBarChangeListener(MainActivity.this);

        spacingText = (TextView) findViewById(R.id.spacingText);

        capsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "Switch Changed");
                memeify();
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Hey, a key press!");
                memeify();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spacingText.setText(Integer.toString(spacingBar.getProgress()));
    }

    @Override
    public void onClick(View v) {
        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(output.getText().toString());
        Log.d(TAG, "First Button Clicked");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "Key Pressed");
        memeify();
        return false;
    }

    private void memeify(){
        String string = input.getText().toString();
        int spacing = spacingBar.getProgress();
        boolean caps = capsSwitch.isChecked();
        String meme = aesthetic(string, spacing, caps);
        output.setText(meme);
    }

    private String aesthetic(String string, int spacing, boolean caps){
        String outString = "";
        String spaces = repeatString(" ", spacing);
        for (int i=0; i<string.length(); i++){
            outString += Character.toString(string.toCharArray()[i]) + spaces;
        }
        if (caps){
            outString = outString.toUpperCase();
        }
        outString = outString.trim();
        return outString;
    }

    private String repeatString(String s, int c){
        String outString = "";
        for (int i=0;i<c;i++){
            outString += s;
        }
        return outString;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "Slider Changed");
        Log.d(TAG, (Integer.toString(spacingBar.getProgress())));
        memeify();
        spacingText.setText(Integer.toString(spacingBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
