package codewizards.com.ua.confidenceintervals.dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import codewizards.com.ua.confidenceintervals.DataContainer;
import codewizards.com.ua.confidenceintervals.R;
import codewizards.com.ua.confidenceintervals.logic.Interval;
import codewizards.com.ua.confidenceintervals.views.GraphView;

/**
 * Created by Интернет on 07.09.2016.
 */

public class DialogGraphic extends DialogFragment implements View.OnClickListener{
    List<Interval> intervals = DataContainer.getInstance().getLastIntervals();
    LinearLayout layout;
    Context context;
    TextView tvGraphError;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_graphic, container, false);
        view.findViewById(R.id.btn_close).setOnClickListener(this);
        layout = (LinearLayout) view.findViewById(R.id.layout_for_graphic);
        tvGraphError = (TextView) view.findViewById(R.id.tv_graph_error);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        GraphView graphView = new GraphView(context);
        try {
            graphView.setIntervals(intervals);
            Log.d("myTag", "width = " + layout.getWidth());
            layout.addView(graphView, (int)getResources().getDimension(R.dimen.graph_width),
                    (int)getResources().getDimension(R.dimen.graph_height));
        } catch (Exception e) {
            tvGraphError.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            e.printStackTrace();
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
