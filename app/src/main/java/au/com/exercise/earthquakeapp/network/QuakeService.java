package au.com.exercise.earthquakeapp.network;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;

import au.com.exercise.earthquakeapp.data.QuakeData;
import de.greenrobot.event.EventBus;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Anita on 30/10/2014.
 */
@EBean
public class QuakeService {

    QuakeInterface client;

    @AfterInject
    void init() {
        client = new RestAdapter.Builder()
                .setEndpoint("http://www.seismi.org/api")
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        EventBus.getDefault().post(new FailureEvent(cause.getMessage()));
                        return null;
                    }
                })
                .build()
                .create(QuakeInterface.class);
    }

    @Background
    public void getQuakeData() {
        QuakeData data = client.getQuakeData();
        EventBus.getDefault().post(new QuakeDataEvent(data));
    }

    public class FailureEvent {
        public String errorMessage;
        FailureEvent(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public class QuakeDataEvent {
        public QuakeData data;
        public QuakeDataEvent(QuakeData data) {
            this.data = data;
        }
    }
}
