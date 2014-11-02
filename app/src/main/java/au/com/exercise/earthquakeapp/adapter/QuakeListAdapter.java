package au.com.exercise.earthquakeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import au.com.exercise.earthquakeapp.R;
import au.com.exercise.earthquakeapp.data.Quake;

/**
 * Created by Anita on 3/11/2014.
 */
public class QuakeListAdapter extends BaseAdapter {

    List<Quake> data;
    Context context;

    public QuakeListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Quake> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_quake, null);
        }
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        TextView regionTextView = (TextView) view.findViewById(R.id.regionTextView);
        TextView infoTextView = (TextView) view.findViewById(R.id.infoTextView);

        Quake q = data.get(i);
        dateTextView.setText(q.getTimedate());
        regionTextView.setText(q.getRegion());
        infoTextView.setText("Magnitude: " + q.getMagnitude() + " - Depth: " + q.getDepth());
        return view;
    }
}
