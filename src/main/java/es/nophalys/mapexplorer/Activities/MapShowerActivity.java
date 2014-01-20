package es.nophalys.mapexplorer.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import es.nophalys.mapexplorer.R;
import es.nophalys.mapexplorer.Shaders.MapShaderDrawable;

/**
 * Activity that shows a map.
 * @see Activity
 */
public class MapShowerActivity extends Activity {

    // Range of revelation.
    private final static int REVEAL_RANGE = 25;

    // Image view.
    private ImageView view;

    // The map shader.
    private MapShaderDrawable shader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event != null) {
            revealMap(new Point((int) event.getRawX(), (int) event.getY() - 245));
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        view = (ImageView) findViewById(R.id.mapView);
        if (view != null) {
            Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.dungeon_map_2);
            shader = new MapShaderDrawable(map);
            view.setImageDrawable(shader);
            view.invalidate();
        }
    }

    /**
     * Inititalize the activity.
     */
    private void initialize() {
        shader.initialize();
        view.invalidate();
    }

    /**
     * Called when the user clicks the new map button
     */
    public void loadNewMap(View view) {
        initialize();
    }

    /**
     * Reveal a section of the map.
     * @param point center of the section to reveal.
     */
    private void revealMap(Point point) {
        if (view != null) {
            int xUpperOffset, xLowOffset, yUpperOffset, yLowOffset;
            xUpperOffset = getUpperOffset(point.x, view.getWidth());
            xLowOffset = getLowOffset(point.x, 0);
            yUpperOffset = getUpperOffset(point.y, view.getHeight());
            yLowOffset = getLowOffset(point.y, 0);

            for (int i = xLowOffset; i < xUpperOffset; i ++) {
                for (int j = yLowOffset; j < yUpperOffset; j ++) {
                    shader.revealPoint(new Point(i, j));
                }
            }
            view.invalidate();
        }
    }

    /**
     * @param n     Initial number.
     * @param max   Max range of upperOffset
     * @return      the upperOffset.
     */
    private int getUpperOffset(int n, int max) {
        int upperOffset = n + REVEAL_RANGE;
        if (upperOffset >= max) {
            upperOffset = max - 1;
        }

        return upperOffset;
    }

    /**
     * @param n     Initial number.
     * @param min   Min range of lowOffset
     * @return      the lowOffset.
     */
    private int getLowOffset(int n, int min) {
        int lowOffset = n - REVEAL_RANGE;
        if (lowOffset < min) {
            lowOffset = min;
        }

        return lowOffset;
    }
}