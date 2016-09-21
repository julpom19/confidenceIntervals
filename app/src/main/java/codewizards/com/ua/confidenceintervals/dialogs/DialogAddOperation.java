package codewizards.com.ua.confidenceintervals.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import codewizards.com.ua.confidenceintervals.DataContainer;
import codewizards.com.ua.confidenceintervals.OnDialogEventListener;
import codewizards.com.ua.confidenceintervals.R;
import codewizards.com.ua.confidenceintervals.logic.Operation;

/**
 * Created by Интернет on 03.09.2016.
 */

public class DialogAddOperation extends AbstractDialogFragment implements View.OnClickListener{
    private RadioGroup radioGroup;
    private TextView tvAddOperation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_operation, container, false);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio);
        tvAddOperation = (TextView) view.findViewById(R.id.tv_add_operation);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                performAdding();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    void performAdding() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        Operation operation = null;
        switch (checkedId) {
            case R.id.sum:
                operation = new Operation("SUM");
                break;
            case R.id.sub:
                operation = new Operation("SUB");
                break;
            case R.id.mult:
                operation = new Operation("MULT");
                break;
            case R.id.div:
                operation = new Operation("DIV");
                break;
            case R.id.div_hip:
                operation = new Operation("DIV_HIP");
                break;
            case R.id.max:
                operation = new Operation("MAX");
                break;
            case R.id.min:
                operation = new Operation("MIN");
                break;
            default:
                tvAddOperation.setError("Виберіть операцію");
                return;
        }
        DataContainer.getInstance().addElement(operation);
        listener.onSuccess();
        dismiss();
    }
}
