package com.example.notificacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edt1,edt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt1=findViewById(R.id.ed1);
        edt2=findViewById(R.id.edt2);

        Button btn2=(Button)findViewById(R.id.btntodos);

        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"Registrado",Toast.LENGTH_SHORT).show();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamartopico();
            }
        });
    }

    private void llamartopico() {

        RequestQueue myrequest= Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {

            String url_foto="https://cuponassets.cuponatic-latam.com/backendPe/uploads/imagenes_descuentos/93242/ccbb931b0087775d930c6eb8e8d7fa728b043cdb.XL2.jpg";

            // String token="cIb2ajMbQ7mtXBSV-rsHHW:APA91bEmqMrRYqHNFwWTTjrODwfkQLf4Kg0-5Pnf2A7OrLgQqn2yM7zdED2dc2Q7tSnQhhxslc0lqQOx8yDQl05QaCgy1lcuhv-kl-YOScfmmsD_0rg1j6kimDqkMSydGaBvqEval-1P";
            // "cIb2ajMbQ7mtXBSV-rsHHW:APA91bEmqMrRYqHNFwWTTjrODwfkQLf4Kg0-5Pnf2A7OrLgQqn2yM7zdED2dc2Q7tSnQhhxslc0lqQOx8yDQl05QaCgy1lcuhv-kl-YOScfmmsD_0rg1j6kimDqkMSydGaBvqEval-1P"
            json.put("to","/topics/"+"enviaratodos");
            JSONObject notificacion=new JSONObject();
            notificacion.put("titulo",edt1.getText().toString());
            notificacion.put("detalle",edt2.getText().toString());
            notificacion.put("foto",url_foto);

            json.put("data",notificacion);
            String URL="https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String>header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAATkAG9B0:APA91bEfCRWnqMuyVZjaKVtLaU-JapLgjKyPQuueJtpEfVhXeyUrNgoQi6QI7hOyrblJtjN6222ckNzp9mLwl9el6iXpwZP76ZDymklpUARrLJsx5Ecrd_piUXgrdC3VBT2sTwG7s2ZI");
                    return  header;

                }
            };
            myrequest.add(request);


        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}

