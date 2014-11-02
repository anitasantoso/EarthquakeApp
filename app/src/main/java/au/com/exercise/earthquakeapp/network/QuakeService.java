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


/**
 * Singleton class that handles network communication between the app and Seismi API.
 */
@EBean
public class QuakeService {

    public static final String BASE_URL = "http://www.seismi.org/api";
    QuakeInterface client;

    @AfterInject
    void init() {
        client = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        if(cause.getResponse() == null) {
                            // no internet connection
                            // specify error type in failure event
                        }
                        EventBus.getDefault().post(new FailureEvent(cause.getMessage()));
                        return null;
                    }
                })
                .build()
                .create(QuakeInterface.class);
    }

    /**
     * Fetch most recent earthquake data from the API.
     */
    @Background
    public void getQuakeData() {
        QuakeData data = client.getQuakeData();
        EventBus.getDefault().post(new QuakeDataEvent(data));
    }

    /**
     * Broadcast when network request encountered any error.
     */
    public class FailureEvent {
        public String errorMessage;
        FailureEvent(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    /**
     * Broadcast if data was fetched successfully.
     */
    public class QuakeDataEvent {
        public QuakeData data;
        public QuakeDataEvent(QuakeData data) {
            this.data = data;
        }
    }
}
