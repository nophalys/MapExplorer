package es.nophalys.mapexplorer.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import es.nophalys.mapexplorer.R;
import es.nophalys.mapexplorer.Shaders.MapShaderDrawable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

import static android.os.SystemClock.uptimeMillis;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tester of MapShowerActivity.
 */
@RunWith(RobolectricTestRunner.class)
public class MapShowerActivityTest {

    // Map shower activity.
    private MapShowerActivity activity;

    /**
     * Setting up the map shower activity.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MapShowerActivity.class).create().get();
    }

    /**
     * Test the method onCreate of the map shower activity.
     * @throws Exception
     */
    @Test
    public void testOnCreate() throws Exception {
        ImageView imageView = (ImageView) activity.findViewById(R.id.mapView);
        assertNotNull(imageView);
        assertNotNull(imageView.getDrawable());
        assertThat(imageView.getVisibility(), equalTo(View.VISIBLE));
    }

    /**
     * Test the method onTouchEvent of the map shower activity.
     * @throws Exception
     */
    @Test
    public void testOnTouchEvent() throws Exception {
        MotionEvent event;
        event = MotionEvent.obtain(uptimeMillis(), uptimeMillis(), MotionEvent.ACTION_DOWN, 75, 75, 0);
        activity.onTouchEvent(event);
        ImageView imageView = (ImageView) activity.findViewById(R.id.mapView);
        assertTrue(getMask(imageView.getDrawable()).getPixel(75, 75) != Color.GRAY);
    }

    /**
     * @return  The list of points of the shader.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Bitmap getMask(Drawable shader) throws NoSuchFieldException, IllegalAccessException {
        Bitmap mask = null;

        if (shader instanceof MapShaderDrawable) {
            Field pointsField = MapShaderDrawable.class.getDeclaredField("mMask");
            pointsField.setAccessible(true);
            mask = (Bitmap) pointsField.get(shader);
        }

        return mask;
    }
}
