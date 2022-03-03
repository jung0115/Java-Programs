package com.example.tcptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button connect_btn;                 // word 받아오는 버튼

    EditText word_edit;               // word 에디트
    TextView show_text;             // 서버에서 온 거 보여주는 에디트

    String[] words = new String[100]; // 연관어 저장할 배열

    // 소켓통신에 필요한것
    private String html = "";
    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    //private String ip = "192.168.0.5";            // 노트북 IP 번호
    private String ip = "203.255.3.164";            // 연구실컴 IP 번호
    private int port = 9999;                          // port 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect_btn = (Button)findViewById(R.id.connect_btn);
        connect_btn.setOnClickListener(this);

        word_edit = (EditText)findViewById(R.id.word_edit);
        show_text = (TextView)findViewById(R.id.show_text);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect_btn:     // ip 받아오는 버튼
                connect();
        }
    }

    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // ip받기
                String newip = String.valueOf(ip);

                // 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    String search_word = String.valueOf(word_edit.getText()); //검색할 단어 입력 받기
                    dos.writeUTF(search_word); //Python 서버로 문자열 보내기

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                // Python 서버에서 받아옴
                try {
                    String line = "";

                    int count = 0;
                    while(true) {
                        if(count == 100){ //100개의 데이터를 전송 받으면 종료
                            break;
                        }
                        line = (String)dis.readUTF(); //python server에서 전송한 값을 받아옴
                        words[count] = line;
                        count++;
                    }
                    show_text.setText(words[0]);
                }catch (Exception e){

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
}