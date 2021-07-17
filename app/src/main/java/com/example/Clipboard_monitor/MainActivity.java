package com.example.Clipboard_monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.Clipboard_monitor.adapters.ClipAdapter;
import com.example.Clipboard_monitor.databinding.ActivityMainBinding;
import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    static ArrayList<Clip> clips = new ArrayList<>();
    EditText editText;
    Button button;
    WifiManager wifi;
    WifiManager.MulticastLock multicastLock;

    static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            clips.add((Clip)msg.obj);
        }
    };

    private ActivityMainBinding binding;
    private ClipAdapter clipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
     //   editText = findViewById(R.id.editText);

        wifi  = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        multicastLock = wifi.createMulticastLock("multicastLock");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        clipAdapter = new ClipAdapter(clips, this);
        binding.recyclerView.setAdapter(clipAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread("ReceiveThread") {
            @Override
            public void run() {
                try {
                   RcvPacket rcv = new RcvPacket(256,handler);
                   rcv.joinGroup();
                   rcv.receive();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    public void sendOnClick(View view) {

        editText = findViewById(R.id.editText);
        String sendText =  editText.getText().toString();

        new Thread("SendThread"){
            @Override
            public void run() {
                try {
                    new SendPacket().sendPacket(sendText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (multicastLock.isHeld()) {
            multicastLock.release();
        }
    }
}
