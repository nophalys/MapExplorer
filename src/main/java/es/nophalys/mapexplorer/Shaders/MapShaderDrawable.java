package es.nophalys.mapexplorer.Shaders;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/**
 * Class for the map shader.
 * @see Drawable
 */
public class MapShaderDrawable extends Drawable {

    // Mask to draw.
    private Bitmap mMask;

    // Paint.
    private Paint mPaint = new Paint();

    // Hidden image.
    private Bitmap mImage;

    /**
     * Constructor.
     * @param image Image which will be masked.
     */
    public MapShaderDrawable(Bitmap image) {
        mImage = image;
        settingUpPoints();
    }

    /**
     * Reveal the point in the next draw iteration.
     * @param point    Point to be revealed.
     */
    public void revealPoint(Point point) {
        if (point.x >= 0 && point.y >= 0
                && point.x < mMask.getWidth()
                && point.y < mMask.getHeight()) {
            mMask.setPixel(point.x, point.y, mImage.getPixel(point.x, point.y));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Point point = new Point(0, 0);
        canvas.drawBitmap(mMask, point.x, point.y, mPaint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    /**
     * Initialize the map.
     */
    public void initialize() {
        mMask.recycle();
        settingUpPoints();
    }

    /**
     * Setting up the mask.
     */
    private void settingUpPoints() {
        if (mImage != null && mImage.getHeight() > 0 && mImage.getWidth() > 0) {
            Bitmap tinyMask = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            tinyMask.setPixel(0, 0, Color.GRAY);
            mMask = Bitmap.createScaledBitmap(tinyMask, mImage.getWidth(), mImage.getHeight(), false);
        }
    }
}
