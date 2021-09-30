package com.example.practica6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button Act;
    private Button UpButton;
    private Button DownButton;
    private Button RightButton;
    private Button LeftButton;

    private BufferedWriter bWriter;
    private int x=250;
    private int y=250;
    private int r=255;
    private int g=0;
    private int b=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Act = findViewById(R.id.Act);
        UpButton = findViewById(R.id.UpButton);
        DownButton = findViewById(R.id.DownButton);
        RightButton = findViewById(R.id.RightButton);
        LeftButton = findViewById(R.id.LeftButton);

        Act.setOnClickListener(
                (v)->{
                    r=(int) Math.floor(Math.random() * 256);
                    g=(int) Math.floor(Math.random() * 256);
                    b=(int) Math.floor(Math.random() * 256);
                    //enviar color
                    new Thread(
                            ()->{
                                enviarJson(x,y,r,g,b);
                            }
                    ).start();

                }
        );

        UpButton.setOnClickListener(
                (v)->{
                    //enviar arriba
                    y-=25;
                    new Thread(
                            ()->{
                                enviarJson(x, y, r, g,b);
                            }
                    ).start();

                }
        );

        DownButton.setOnClickListener(
                (v)->{
                    y+=25;
                    //enviar abajo
                    new Thread(
                            ()->{
                                enviarJson(x, y, r, g,b);
                            }
                    ).start();

                }
        );

        RightButton.setOnClickListener(
                (v)->{
                    x+=25;
                    //enviar derecha
                    new Thread(
                            ()->{
                                enviarJson(x, y, r, g,b);
                            }
                    ).start();

                }
        );

        LeftButton.setOnClickListener(
                (v)->{
                    x-=25;
                    //enviar izquierda
                    new Thread(
                            ()->{
                                enviarJson(x, y, r, g,b);
                            }
                    ).start();

                }
        );


        new Thread(
                ()->{
                    try {
                        Socket socket = new Socket("10.0.2.2",5000);

                        OutputStream os = socket.getOutputStream(); //para saber el flujo de datos
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bWriter = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();


    }

    private void enviarJson(int x, int y, int r, int g, int b) {

        Gson gson = new Gson();
        Informacion info = new Informacion(x,y,r,g,b);

        //Serializacion
        String coordStr = gson.toJson(info);
        new Thread(
                ()-> {

                    try {
                        bWriter.write(coordStr + "\n");
                        bWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }


}