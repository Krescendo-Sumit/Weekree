package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

public class Flash extends AppCompatActivity {
    ImageButton btnimg;
    MyDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_flash);
        db=new MyDb(Flash.this);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.logoanim);
        btnimg=(ImageButton)findViewById(R.id.img_btn);

        btnimg.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                try{
                    finish();
                    //  Toast.makeText(Flash.this, ""+db.getUserID(), Toast.LENGTH_SHORT).show();
                    if(db.getUserID().trim().equals("0"))
                    {
                        Intent intent=new Intent(getApplicationContext(),Signup.class);
                        startActivity(intent);
                    }else
                    {
                        /*Intent intent=new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);*/
                        Intent intent=new Intent(getApplicationContext(),Signup.class);
                        startActivity(intent);
                    }

                }catch(Exception e)
                {
                    Toast.makeText(Flash.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}
