package codewizards.com.ua.confidenceintervals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import codewizards.com.ua.confidenceintervals.dialogs.DialogAddInterval;
import codewizards.com.ua.confidenceintervals.dialogs.DialogGraphic;

public class MainActivity extends AppCompatActivity {
    Button btnInterval;
    Button btnOperation;
    Button btnNumber;
    Button btnRemoveLast;
    TextView tvResultArea;
    TextView tvExpression;
    boolean enableIntervalInsert = true;
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInterval = (Button) findViewById(R.id.btn_interval);
        btnOperation = (Button) findViewById(R.id.btn_operation);
        btnNumber = (Button) findViewById(R.id.btn_number);
        btnRemoveLast = (Button) findViewById(R.id.btn_remove_last);
        tvResultArea = (TextView) findViewById(R.id.result_area);
        tvExpression = (TextView) findViewById(R.id.tv_expression);

        presenter = new MainActivityPresenter(this);

        btnInterval.setOnClickListener(presenter);
        btnOperation.setOnClickListener(presenter);
        btnNumber.setOnClickListener(presenter);
        btnRemoveLast.setOnClickListener(presenter);

        toggleButtons();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogGraphic dialogGraphic = new DialogGraphic();
                dialogGraphic.setContext(MainActivity.this);
                dialogGraphic.show(getFragmentManager(), "");
            }
        });
    }

    public void toggleButtons() {
        btnInterval.setEnabled(enableIntervalInsert);
        btnOperation.setEnabled(!enableIntervalInsert);
        btnNumber.setEnabled(enableIntervalInsert);
        enableIntervalInsert = !enableIntervalInsert;
    }


    public void updateExpression(String exp) {
        tvExpression.setText(exp);
    }

    public void updateResults(String result) {
        if(result.equals("")) {
            tvResultArea.setText(getResources().getString(R.string.input_expr));
        } else {
            tvResultArea.setText(result);
        }

    }
}
