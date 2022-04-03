package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    EditText edText;
    EditText edMail;
    EditText edTitle;
    Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSend = findViewById(R.id.b_send);
        edTitle = findViewById(R.id.editTextTextEmailAddress2);
        edText = findViewById(R.id.ed_text);
        edMail = findViewById(R.id.editTextTextEmailAddress);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bSend.setClickable(false);

                String text = edMail.getText() + "\n"+ "\n" + edText.getText();
                String title = edTitle.getText().toString();


              Flowable.fromCallable(() -> {
                  try {
                      try {
                          com.example.sendmail.GMailSender sender = new com.example.sendmail.GMailSender("leshinintimur@gmail.com",
                                  "79tovuro");
                          sender.sendMail(title, text,
                                  "leshinintimur@gmail.com", "leshinintimur@gmail.com");
                      } catch (Exception e) {
                          Log.e("SendMail", e.getMessage(), e);
                      }
                  }
                  catch (Exception e)
                  {
                      return false;

                  }
                  return true;
              }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(x ->{
                            bSend.setClickable(true);
                            if(x)
                                Toast.makeText(getApplicationContext(),"Messega sended1",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

                        });



            }
        });
    }







}