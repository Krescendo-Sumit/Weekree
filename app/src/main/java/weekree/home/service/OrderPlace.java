package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OrderPlace extends AppCompatActivity {
    EditText et_fullname, et_mobile, et_address, et_eamil, et_landmark;
    String fullname, mobile, address, email, landmark;
    ProgressDialog progress;
    String uid="";
    RadioGroup radioGroup;
    int pmode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place);
        et_fullname = findViewById(R.id.et_fullname);
        et_mobile = findViewById(R.id.et_mobile);
        et_address = findViewById(R.id.et_address);
        et_eamil = findViewById(R.id.et_email);
        et_landmark = findViewById(R.id.et_landmark);
        radioGroup=findViewById(R.id.rg);
        uid=new MyDb(OrderPlace.this).getUserID().trim();;

        progress = new ProgressDialog(OrderPlace.this);
        progress.setMessage("Loading .........");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.cod)
                {
                    pmode=1;
                }
                if(i==R.id.ol)
                {
                    pmode=2;
                }
            }
        });

    }

    public void place(View v) {
        try {

            fullname = et_fullname.getText().toString().trim();
            mobile = et_mobile.getText().toString().trim();
            address = et_address.getText().toString().trim();
            email = et_eamil.getText().toString().trim();
            landmark = et_landmark.getText().toString().trim();

            if(fullname.equals("")|| fullname.length()<5 || !fullname.contains(" "))
            {
                et_fullname.setError("Please Enter Your Full Name");
            }
            else if(mobile.equals("")||mobile.length()<10||mobile.length()>10)
            {
                et_mobile.setError("Please Enter Valid Mobile Number");
            }
            else if(address.equals("")||address.length()<10)
            {
                et_address.setError("Please Enter Proper Address");
            }
            else if(landmark.equals("") || landmark.length()<4)
            {
                et_landmark.setError("Please Enter Proper Landmark");
            }
            else if(pmode==0)
            {
                Toast.makeText(OrderPlace.this, "Please Choose Payment Option", Toast.LENGTH_SHORT).show();
            }
            else {
                placeOrder(fullname, mobile, address, email, landmark);
            }
        } catch (Exception e) {

        }
    }

    private void placeOrder(final String fullname,final  String mobile,final  String address,final  String email,final  String landmark) {

        try {

            String appURL = BaseUrl.HOSTNAME + "insertOrder.php";
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

                            //       setbtntext(response);
                            String ss[]=response.split("-");
                            Toast.makeText(OrderPlace.this, "" + ss[0].trim(), Toast.LENGTH_SHORT).show();
                            if(pmode==1)
                            {
                                Intent intent=new Intent(OrderPlace.this,CashOnDeliveryVerify.class);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("code",ss[1].trim());
                                startActivity(intent);


                            }else if(pmode==2)
                            {
                                Intent intent=new Intent(OrderPlace.this,OnlinePaymentGatewayActivity.class);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("oid",ss[2].trim());
                                startActivity(intent);
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
                    Toast.makeText(OrderPlace.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("uid", uid);
                    params.put("fullname", fullname);
                    params.put("mobile", mobile);
                    params.put("address", address);
                    params.put("email", email);
                    params.put("landmark", landmark);
                    params.put("pmode", ""+pmode);

                    return params;
                }

                ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(OrderPlace.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(OrderPlace.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}