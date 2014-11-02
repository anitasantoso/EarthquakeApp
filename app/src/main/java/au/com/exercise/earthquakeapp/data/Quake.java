package au.com.exercise.earthquakeapp.data;

/**
 * Created by Anita on 30/10/2014.
 */
public class Quake {

    private String src;
    private String eqid;
    private String timedate;
    private double lat;
    private double lon;
    private float magnitude;
    private float depth;
    private String region;

    public Quake() {}

    public String getSrc() {
        return src;
    }

    public String getEqid() {
        return eqid;
    }

    public String getTimedate() {
        return timedate;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public float getDepth() {
        return depth;
    }

    public String getRegion() {
        return region;
    }
}
