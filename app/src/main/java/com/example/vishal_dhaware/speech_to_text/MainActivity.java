package com.example.vishal_dhaware.speech_to_text;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.os.Environment;
import android.support.annotation.Nullable;
  import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.speech.RecognizerIntent;
        import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_SWITCH_INPUT = 10;
    private static final int STORAGE_PERMISSION_CODE=100;
    TextView tv;
    Button m;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        m = findViewById(R.id.bspeech);

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool1();


            }
        });

    }

    private void bool1() {
        //intent=message/data passing 1 activity to another activity
        //action_recognize_speech=perform action for speech recognization

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //putextra =is used to pass multiple parameter or msg in a single intent...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                //free_form= support multiple lang. and maintain free form...
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //locale.getdefault=using system languages...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //extra_promt=passing additional values to  recognizer...
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hie speak something");

        try {
            //  we pass Intent object and request code in startactivityforresult
            // startactivityforresult is method its followed by onactivity result method..
            startActivityForResult(intent, REQ_CODE_SWITCH_INPUT);
        } catch (ActivityNotFoundException a) {


        }

    }

    @Override
    //@ = it is annotation we called as directive which pts something values
    //annotation =  annotation used for instrustion
    //we used here because it give some value
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_SWITCH_INPUT:{
                if(resultCode == RESULT_OK && null != data){
                    ArrayList<String>result=data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv.setText(result.get(0));//save speech to text file...
                    writedatainfile(tv.getText().toString());


                }
                break;
            }
        }
    }

    private void writedatainfile(String text) {
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-YYY HH:mm:ss");
        s="ExternalDta"+s+".txt";
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myfile=new File(folder,s);
        writeData(myfile, text);
    }
    private  void writeData(File myfile,String result){
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(myfile);
            fileOutputStream.write(result.getBytes());
            Toast.makeText(this,"Done"+myfile.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
