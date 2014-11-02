package au.com.exercise.earthquakeapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import au.com.exercise.earthquakeapp.R;
import au.com.exercise.earthquakeapp.adapter.QuakeListAdapter;
import au.com.exercise.earthquakeapp.network.QuakeService;
import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById
    ListView quakeListView;

    @Bean
    QuakeService quakeService;

    QuakeListAdapter listAdapter;
    private boolean isLoading;

    @AfterInject
    void init() {
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    @AfterViews
    void setupViews() {
        quakeListView.setAdapter(listAdapter = new QuakeListAdapter(this));
        quakeListView.addHeaderView(getLayoutInflater().inflate(R.layout.header_quake, null));
        reloadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(QuakeService.QuakeDataEvent event) {
        setBusy(false);
        listAdapter.setData(event.data.getEarthquakes());
        listAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(QuakeService.FailureEvent event) {
        setBusy(false);
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("There were some problems in loading quake data")
                .setNegativeButton("OK", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_reload) {
            reloadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // disable menu item if loading
        MenuItem reload = menu.findItem(R.id.action_reload);
        if(reload != null) {
            reload.setEnabled(!isLoading);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void reloadData() {
        setBusy(true);
        quakeService.getQuakeData();
    }

    private void setBusy(boolean busy) {
        isLoading = busy;
        invalidateOptionsMenu();
        setProgressBarIndeterminateVisibility(busy);
    }
}
