package au.com.exercise.earthquakeapp.network;

import au.com.exercise.earthquakeapp.data.QuakeData;
import retrofit.http.GET;

/**
 * Created by Anita on 30/10/2014.
 */
public interface QuakeInterface {

    @GET("/eqs")
    QuakeData getQuakeData();
}
