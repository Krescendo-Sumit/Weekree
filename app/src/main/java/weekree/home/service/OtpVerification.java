package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;

import java.util.HashMap;
import java.util.Map;

public class OtpVerification extends AppCompatActivity {
    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    Button verify_otp;
    String mobile,code,name;
    ProgressDialog progress;
    String t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        verify_otp = findViewById(R.id.verify_otp_btn);
        progress = new ProgressDialog(OtpVerification.this);
        progress.setMessage("Registering Your Details .........");

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");
        code=b.getString("code");
        name=b.getString("name");
        Toast.makeText(OtpVerification.this,"Details are otp :"+code+" \n Name :"+name+"\n mobile :"+mobile,Toast.LENGTH_LONG).show();
        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four};
        otp_textbox_one.addTextChangedListener(new GenericTextWatcher1(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher1(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher1(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher1(otp_textbox_four, edit));

         try{
             sendOtp(mobile,code);
         }catch(Exception e)
         {

         }
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                t1=otp_textbox_one.getText().toString().trim();
                        t2=otp_textbox_two.getText().toString().trim();
                t3=otp_textbox_three.getText().toString().trim();
                        t4=otp_textbox_four.getText().toString().trim();

                 if(t1.trim().equals(""))
                 {
                     otp_textbox_one.setError("");
                 }else if(t2.trim().equals(""))
                 {
                     otp_textbox_two.setError("");
                 }else if(t3.trim().equals(""))
                 {
                     otp_textbox_three.setError("");
                 }else if(t4.trim().equals(""))
                 {
                     otp_textbox_four.setError("");
                 }else {

                     String code = t1 + t2 + t3 + t4;
                     sendVerify(mobile, code);
                 }

            }
        });

        requestSMSPermission();


        new Otp_Receiver().setEditText(otp_textbox_one,otp_textbox_two,otp_textbox_three,otp_textbox_four);
    }
    private void requestSMSPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list,1);
        }
    }

    private void sendOtp(final String mobile,final String code) {


        try {

            String appURL = BaseUrl.HOSTNAME + "OtpGetter.php";
            //   Toast.makeText(this, ""+appURL, Toast.LENGTH_SHORT).show();

            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(Home.this, "" + response, Toast.LENGTH_SHORT).show();
                    try {
                        progress.dismiss();

                        Log.i("Details", response);
                        try {

                       //     Toast.makeText(OtpVerification.this, "" +response, Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            Log.i("Details Inner", e.getMessage());
                        }


                        //loadData(response);

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(OtpVerification.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("mobile", mobile);
                    params.put("code", code);

                    return params;
                }

                ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(OtpVerification.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(OtpVerification.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void sendVerify(final String mobile,final String code) {


        try {

            String appURL = BaseUrl.HOSTNAME + "OtpVerify.php";
            //   Toast.makeText(this, ""+appURL, Toast.LENGTH_SHORT).show();

            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(Home.this, "" + response, Toast.LENGTH_SHORT).show();
                    try {
                        progress.dismiss();

                        Log.i("Details", response);
                        try {
                              String ss[]=response.split("-");
                              if(ss[0].trim().equals("Success"))
                              {
                                  Toast.makeText(OtpVerification.this, "OTP Verified.", Toast.LENGTH_SHORT).show();
                                 if(new MyDb(OtpVerification.this).updateUser(ss[1],name,mobile))
                                 {
                                     Intent intent=new Intent(OtpVerification.this,Home.class);
                                     startActivity(intent);
                                 }

                              }else
                              {
                                  Toast.makeText(OtpVerification.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                              }
                         //   Toast.makeText(OtpVerification.this, "" +response, Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            Log.i("Details Inner", e.getMessage());
                        }


                        //loadData(response);

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(OtpVerification.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("mobile", mobile);
                    params.put("code", code);

                    return params;
                }

                ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(OtpVerification.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(OtpVerification.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class GenericTextWatcher1 implements TextWatcher {
        private final EditText[] editText;
        private View view;
        public GenericTextWatcher1(View view, EditText editText[])
        {
            this.editText = editText;
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();


            switch (view.getId()) {

                case R.id.otp_edit_box1:
                    if (text.length() == 1)
                        editText[1].requestFocus();
                    break;
                case R.id.otp_edit_box2:

                    if (text.length() == 1)
                        editText[2].requestFocus();
                    else if (text.length() == 0)
                        editText[0].requestFocus();
                    break;
                case R.id.otp_edit_box3:
                    if (text.length() == 1)
                        editText[3].requestFocus();
                    else if (text.length() == 0)
                        editText[1].requestFocus();
                    break;
                case R.id.otp_edit_box4:
                    if (text.length() == 0)
                        editText[2].requestFocus();
                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }

}

