package es.nophalys.mapexplorer.Shaders;

import android.app.Activity;
import android.graphics.*;
import es.nophalys.mapexplorer.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowBitmap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tester for the map shader.
 */
@RunWith(RobolectricTestRunner.class)
public class MapShaderDrawableTest {

    // Canvas.
    private Canvas canvas;

    // Map shader.
    private MapShaderDrawable shader;

    // Aux activity.
    private Activity activity;

    /**
     * Constructor.
     */
    public MapShaderDrawableTest() {
        canvas = new Canvas();
    }

    /**
     * Setting up the shader.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(Activity.class).create().get();
        shader = new MapShaderDrawable(getMap());
    }

    /**
     * @return the map bitmap.
     */
    private Bitmap getMap() {
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.dungeon_map);
    }

    /**
     * Method that test de draw method.
     * @throws Exception
     */
    @Test
    public void testDraw() throws Exception {
        shader.draw(canvas);
        assertNotNull(canvas);
        Bitmap mask = getMask();
        assertNotNull(mask);
        Bitmap map = getMap();
        assertNotNull(getMap());
        assertTrue(mask.getHeight() == map.getHeight() && mask.getWidth() == map.getWidth());
    }

    /**
     * @return  The list of points of the shader.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Bitmap getMask() throws NoSuchFieldException, IllegalAccessException {
        Field pointsField = MapShaderDrawable.class.getDeclaredField("mMask");
        pointsField.setAccessible(true);
        return (Bitmap) pointsField.get(shader);
    }

    /**
     * Method that test the reveal point method.
     * @throws Exception
     */
    @Test
    public void testRevealPoint() throws Exception {
        Point point;
        List<Point> referencePoints = new ArrayList<Point>();
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j ++) {
                point = new Point(i, j);
                referencePoints.add(point);
                shader.revealPoint(point);
            }
        }
        shader.draw(canvas);
        Bitmap mask = getMask();
        for (Point referencePoint : referencePoints) {
            assertTrue(mask.getPixel(referencePoint.x, referencePoint.y) != Color.GRAY);
        }
        assertTrue(mask.getPixel(10, 10) == Color.GRAY);
    }


}
