package com.example.tcptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button connect_btn;                 // word 받아오는 버튼

    EditText word_edit;               // word 에디트
    ListView show_text;             // 서버에서 온 거 보여주는 에디트

    ArrayList<String> words = new ArrayList<String>(); // 빈 데이터 리스트 생성
    ArrayAdapter<String> adapter;
    //String[] words = new String[100]; // 연관어 저장할 배열

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
        
        // listview 생성 및 adapter 지정
        show_text = (ListView)findViewById(R.id.show_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words); // ArrayAdapter 생성
        show_text.setAdapter(adapter);

        //words.add("Test");
        //adapter.notifyDataSetChanged();

        //show_text.setText(words[0]);
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
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 socket server로 보낼 거 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 socket server에서 받은 거 들어감
                    String search_word = String.valueOf(word_edit.getText()); //검색할 단어 입력 받기
                    dos.writeUTF(search_word); //Python 서버로 문자열 보내기

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                // Python 서버에서 받아옴
                try {
                    String len = "";
                    String line = "";

                    int count = 0;
                    while(true) {
                        if (count == 50) { //50개의 데이터를 전송 받으면 종료
                            break;
                        }
                        len = (String)dis.readUTF();
                        line = (String)dis.readUTF(); //python server에서 전송한 값을 받아옴
                        words.add(line);
                        //Log.w("서버에서 받아온 값 ", ""+count+": "+words.get(count));
                        count++;
                    }
                }catch (Exception e){

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
}