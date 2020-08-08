package com.example.phploginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    private EditText eamil,password;
    private Button btn_login;
    private ProgressBar loading;
    private TextView link_register;
    //private static String URL_LOGIN="http://192.168.43.67/Android_Register_Login/login.php";
    private static String URL_LOGIN="https://apk.samiulcse.info/Android_Register_Login/login.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        sessionManager=new SessionManager(this);

        eamil=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        btn_login=findViewById(R.id.login_button);

        loading=findViewById(R.id.login_loading);
        link_register=findViewById(R.id.link_register);
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=eamil.getText().toString();
                String mPassword=password.getText().toString();

                if (!mEmail.isEmpty() && !mPassword.isEmpty()){
                    login(mEmail,mPassword);
                }else{
                    eamil.setError("Please insert email");
                    password.setError("Please insert password");
                }
            }
        });
    }

    private void login(final String email, final String password) {
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.INVISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");

                    JSONArray jsonArray=jsonObject.getJSONArray("login");

                    if (success.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            String name=object.getString("name").trim();
                            String email=object.getString("email").trim();
                            loading.setVisibility(View.GONE);

                            sessionManager.createSession(name,email);

                            //Toast.makeText(getApplicationContext(),"Success Login"+"Name "+name+" email"+email,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Home.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            startActivity(intent);


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error    "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
