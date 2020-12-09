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
    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    EditText otp_textbox_five, otp_textbox_six, otp_textbox_seven, otp_textbox_eight;
    EditText otp_textbox_nine, otp_textbox_ten;
    Button verify_otp;
    EditText et_name;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        otp_textbox_five = findViewById(R.id.otp_edit_box5);
        otp_textbox_six = findViewById(R.id.otp_edit_box6);
        otp_textbox_seven = findViewById(R.id.otp_edit_box7);
        otp_textbox_eight = findViewById(R.id.otp_edit_box8);
        otp_textbox_nine = findViewById(R.id.otp_edit_box9);
        otp_textbox_ten = findViewById(R.id.otp_edit_box10);
        et_name = findViewById(R.id.et_name);

        otp_textbox_one.requestFocus();

        verify_otp = findViewById(R.id.verify_otp_btn);
        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six, otp_textbox_seven, otp_textbox_eight, otp_textbox_nine, otp_textbox_ten};

        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));

        otp_textbox_five.addTextChangedListener(new GenericTextWatcher(otp_textbox_five, edit));
        otp_textbox_six.addTextChangedListener(new GenericTextWatcher(otp_textbox_six, edit));
        otp_textbox_seven.addTextChangedListener(new GenericTextWatcher(otp_textbox_seven, edit));
        otp_textbox_eight.addTextChangedListener(new GenericTextWatcher(otp_textbox_eight, edit));
        otp_textbox_nine.addTextChangedListener(new GenericTextWatcher(otp_textbox_nine, edit));
        otp_textbox_ten.addTextChangedListener(new GenericTextWatcher(otp_textbox_ten, edit));

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
                String t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
                t1 = otp_textbox_one.getText().toString().trim();
                t2 = otp_textbox_two.getText().toString().trim();
                t3 = otp_textbox_three.getText().toString().trim();
                t4 = otp_textbox_four.getText().toString().trim();
                t5 = otp_textbox_five.getText().toString().trim();
                t6 = otp_textbox_six.getText().toString().trim();
                t7 = otp_textbox_seven.getText().toString().trim();
                t8 = otp_textbox_eight.getText().toString().trim();
                t9 = otp_textbox_nine.getText().toString().trim();
                t10 = otp_textbox_ten.getText().toString().trim();

                if (t1.equals("")) {
                    otp_textbox_one.setError("");
                } else if (t2.equals("")) {
                    otp_textbox_two.setError("");
                } else if (t3.equals("")) {
                    otp_textbox_three.setError("");
                } else if (t4.equals("")) {
                    otp_textbox_four.setError("");
                } else if (t5.equals("")) {
                    otp_textbox_five.setError("");
                } else if (t6.equals("")) {
                    otp_textbox_six.setError("");
                } else if (t7.equals("")) {
                    otp_textbox_seven.setError("");
                } else if (t8.equals("")) {
                    otp_textbox_eight.setError("");
                } else if (t9.equals("")) {
                    otp_textbox_nine.setError("");
                } else if (t10.equals("")) {
                    otp_textbox_ten.setError("");
                } else if (name.equals("") || name.length() <= 5 || !name.contains(" ")) {
                    et_name.setError("Please Enter atleast First and Last Name");
                } else {
                    mobile = t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8 + t9 + t10;
                    Toast.makeText(Signup.this, "Mobile Number is " + mobile + "\n Name :" + name, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Signup.this, "" + ss[0], Toast.LENGTH_SHORT).show();
                            //       setbtntext(response);
                            Intent intent = new Intent(Signup.this, OtpVerification.class);
                            intent.putExtra("code", ss[1]);
                            intent.putExtra("mobile", mobile);
                            intent.putExtra("name", fullname);
                            startActivity(intent);

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