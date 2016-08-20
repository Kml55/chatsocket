package com.anten.socketlibtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anten.socketconnector.Client;
import com.anten.socketconnector.SocketListener;


public class MainActivity extends AppCompatActivity {
    private EditText mRoomIdEt;
    private EditText mNickEt;
    private EditText mMessageEt;
    private Button mConnectBtn;
    private Client mSocketClient;

    private final String USERNAME = "your_username";
    private final String SECRET = "your_secret";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectBtn = (Button) findViewById(R.id.connect);
        mRoomIdEt = (EditText) findViewById(R.id.room_id);
        mNickEt = (EditText) findViewById(R.id.nick);
        mMessageEt = (EditText) findViewById(R.id.message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mMessageEt.getText().toString();
                mSocketClient.sendMessage(message);
            }
        });

        mSocketClient = new Client(USERNAME,SECRET,new SocketListener(){
            @Override
            public void connected() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateConnectBtnText("Disconnect");
                    }
                });
            }

            @Override
            public void messageRecieved(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessageToast(s);
                    }
                });

            }

            @Override
            public void socketClosed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateConnectBtnText("Connect");
                    }
                });
            }
        });
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mSocketClient.isConnected()){
                    String roomId = mRoomIdEt.getText().toString();
                    String nick = mNickEt.getText().toString();
                    mSocketClient.connect(roomId,nick);
                }else{
                    mSocketClient.close();
                }
            }
        });
    }

    public void updateConnectBtnText(String text){
        mConnectBtn.setText(text);
    }

    public void showMessageToast(String mes){
        Toast.makeText(MainActivity.this,mes,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
