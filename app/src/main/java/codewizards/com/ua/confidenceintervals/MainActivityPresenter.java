package codewizards.com.ua.confidenceintervals;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import codewizards.com.ua.confidenceintervals.dialogs.AbstractDialogFragment;
import codewizards.com.ua.confidenceintervals.dialogs.DialogAddInterval;
import codewizards.com.ua.confidenceintervals.dialogs.DialogAddNumber;
import codewizards.com.ua.confidenceintervals.dialogs.DialogAddOperation;
import codewizards.com.ua.confidenceintervals.logic.Element;
import codewizards.com.ua.confidenceintervals.logic.Interval;
import codewizards.com.ua.confidenceintervals.logic.Operation;

/**
 * Created by Интернет on 03.09.2016.
 */

public class MainActivityPresenter implements View.OnClickListener, OnDialogEventListener {
    private MainActivity mainActivity;
    private DataContainer dataContainer = DataContainer.getInstance();
    private ExpressionCalculator calculator = ExpressionCalculator.getInstance();

    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void onClick(View view) {
        AbstractDialogFragment dialogFragment = null;
        switch (view.getId()) {
            case R.id.btn_interval:
                dialogFragment = new DialogAddInterval();
                break;
            case R.id.btn_operation:
                dialogFragment = new DialogAddOperation();
                break;
            case R.id.btn_number:
                dialogFragment = new DialogAddNumber();
                break;
            case R.id.btn_remove_last:
                if(!dataContainer.isEmpty()) {
                    makeRemove();
                }
                return;
        }
        dialogFragment.attachListener(this);
        dialogFragment.show(mainActivity.getFragmentManager(), "");
    }

    private void makeRemove() {
        dataContainer.removeLast();
        mainActivity.toggleButtons();
        mainActivity.updateExpression(buildExpression());
        String expResult = calculator.calculateExpression();
        mainActivity.updateResults(expResult);
    }


    @Override
    public void onSuccess() {
        mainActivity.toggleButtons();
        mainActivity.updateExpression(buildExpression());
        String expResult = calculator.calculateExpression();
        if(expResult.equals("Ділення на нуль!")) {
            makeRemove();
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            builder.setTitle("Помилка!")
                    .setMessage("Ділення на нуль!")
                    .setNeutralButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            mainActivity.updateResults(calculator.calculateExpression());
        }

    }

    private String buildExpression() {
        StringBuilder expression = new StringBuilder();
        for(Element element : DataContainer.getInstance().getElementList()) {
            if(element instanceof Interval) {
                Interval lastInterval = (Interval) element;
                expression.append(lastInterval);
            } else {
                Operation lastOperation = (Operation) element;
                expression.append(lastOperation);
            }
        }
        return expression.toString();
    }

}
