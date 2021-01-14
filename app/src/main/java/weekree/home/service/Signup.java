package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    Button verify_otp;
    EditText et_name;EditText et_mobile;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_mobile.requestFocus();

        verify_otp = findViewById(R.id.verify_otp_btn);

        progress = new ProgressDialog(Signup.this);
        progress.setMessage("Registering Your Details .........");

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                String mobile = "";
                String name = et_name.getText().toString().trim();
                mobile = et_mobile.getText().toString().trim();

                 if (name.equals("") || name.length() <= 5 || !name.contains("")) {
                    et_name.setError("Please Enter at least First and Last Name");
                 }else if(!(mobile.length()==10))
                 {
                     et_mobile.setError("Please Enter Valid Mobile Number.");
                 }else
                 {
                //    Toast.makeText(Signup.this, "Mobile Number is " + mobile + "\n Name :" + name, Toast.LENGTH_SHORT).show();
                    submitUser(name, mobile);
                }


            }
        });

    }


    private void submitUser(final String fullname, final String mobile) {

        try {

            String appURL = BaseUrl.HOSTNAME + "insertUser.php";
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
                           String ss[] = response.split("-");
                           /*
                            Toast.makeText(Signup.this, "" + ss[0], Toast.LENGTH_SHORT).show();
                            //       setbtntext(response);
                            Intent intent = new Intent(Signup.this, OtpVerification.class);
                            intent.putExtra("code", ss[1]);
                            intent.putExtra("mobile", mobile);
                            intent.putExtra("name", fullname);
                            startActivity(intent);*/
                            if(response.contains("User Registered Successfully"))
                            {
                                if(new MyDb(Signup.this).updateUser(ss[1].trim(),fullname,mobile))
                                {

                                }
                                Intent intent = new Intent(Signup.this, Home.class);
                                intent.putExtra("mobile", mobile);
                                intent.putExtra("name", fullname);
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(Signup.this, "Error While Registering Details", Toast.LENGTH_SHORT).show();
                            }

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
                    Toast.makeText(Signup.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("fullname", fullname);
                    params.put("mobile", mobile);

                    return params;
                }

                ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(Signup.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(Signup.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}