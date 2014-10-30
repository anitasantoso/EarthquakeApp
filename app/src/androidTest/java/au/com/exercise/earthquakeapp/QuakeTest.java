package au.com.exercise.earthquakeapp;

import android.content.Context;
import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import au.com.exercise.earthquakeapp.data.QuakeData;
import au.com.exercise.earthquakeapp.network.QuakeService;
import au.com.exercise.earthquakeapp.network.QuakeService_;
import de.greenrobot.event.EventBus;

/**
 * Created by Anita on 30/10/2014.
 */
public class QuakeTest extends InstrumentationTestCase {

    public static final int DELAY_IN_MILLIS = 10;
    QuakeService quakeService;
    CountDownLatch latch;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context context = getInstrumentation().getTargetContext().getApplicationContext();
        quakeService = QuakeService_.getInstance_(context);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        EventBus.getDefault().unregister(this);
    }

    public void testGetQuakeData() {
        latch = new CountDownLatch(1);
        quakeService.getQuakeData();

        try {
            latch.await(DELAY_IN_MILLIS, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onEventMainThread(QuakeService.QuakeDataEvent event) {
        latch.countDown();
        QuakeData data = event.data;
        assertNotNull(data);
    }

    public void onEventMainThread(QuakeService.FailureEvent event) {
        latch.countDown();
        fail();
    }
}
