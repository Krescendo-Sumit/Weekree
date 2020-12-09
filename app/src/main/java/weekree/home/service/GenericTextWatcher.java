package weekree.home.service;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private View view;
    public GenericTextWatcher(View view, EditText editText[])
    {
        this.editText = editText;
        this.view = view;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();


        switch (view.getId()) {

            case R.id.otp_edit_box1:
                editText[1].requestFocus();
                if (text.length() == 1)
                    editText[1].requestFocus();
                break;
            case R.id.otp_edit_box2:
                editText[2].requestFocus();
                if (text.length() == 1)
                    editText[2].requestFocus();
                else if (text.length() == 0)
                    editText[0].requestFocus();
                break;
            case R.id.otp_edit_box3:
                editText[3].requestFocus();
                if (text.length() == 1)
                    editText[3].requestFocus();
                else if (text.length() == 0)
                    editText[1].requestFocus();
                break;
            case R.id.otp_edit_box4:
                editText[4].requestFocus();
                if (text.length() == 1)
                    editText[4].requestFocus();
                else if (text.length() == 0)
                    editText[2].requestFocus();
                break;
            case R.id.otp_edit_box5:
                editText[5].requestFocus();
                if (text.length() == 1)
                    editText[5].requestFocus();
                else if (text.length() == 0)
                    editText[3].requestFocus();
                break;
            case R.id.otp_edit_box6:
                editText[6].requestFocus();
                if (text.length() == 1)
                    editText[6].requestFocus();
                else if (text.length() == 0)
                    editText[4].requestFocus();
                break;
            case R.id.otp_edit_box7:
                editText[7].requestFocus();
                if (text.length() == 1)
                    editText[7].requestFocus();
                else if (text.length() == 0)
                    editText[5].requestFocus();
                break;
            case R.id.otp_edit_box8:
                editText[8].requestFocus();
                if (text.length() == 1)
                    editText[8].requestFocus();
                else if (text.length() == 0)
                    editText[6].requestFocus();
                break;
            case R.id.otp_edit_box9:
                editText[9].requestFocus();
                if (text.length() == 1)
                    editText[9].requestFocus();
                else if (text.length() == 0)
                    editText[7].requestFocus();
                break;
            case R.id.otp_edit_box10:
                //editText[10].requestFocus();
                if (text.length() == 0)
                    editText[8].requestFocus();

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