package au.com.exercise.earthquakeapp.data;

import java.util.List;

/**
 * Created by Anita on 30/10/2014.
 */
public class QuakeData {

    private int count;
    private List<Quake> earthquakes;

    public QuakeData() {}

    public int getCount() {
        return count;
    }

    public List<Quake> getEarthquakes() {
        return earthquakes;
    }
}
