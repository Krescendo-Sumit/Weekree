package weekree.home.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

public class Otp_Receiver extends BroadcastReceiver {
private static EditText editText;
    public void setEditText(EditText editText1,EditText editText2,EditText editText3,EditText editText4)
    {
        this.editText = editText1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {


        SmsMessage smsMessage[]= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms:smsMessage)
        {
            String message = sms.getMessageBody();
      //      String otp = message.split("otp ")[1];
            Log.i("OTP IS ",message);
            editText.setText(message);

        }
    }


}
