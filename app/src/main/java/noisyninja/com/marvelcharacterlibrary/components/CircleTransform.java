package noisyninja.com.marvelcharacterlibrary.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import noisyninja.com.marvelcharacterlibrary.R;


/**
 * Transformation class to return circular bitmaps of the passed image(unused)
 * Created by ir2pi on 5/1/2015.
 */

public class CircleTransform extends BitmapTransformation {

    static Context context;

    public CircleTransform(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    /**
     * applies circle transformtion on the bitmap provided
     * @param pool bitmap pool to be used for transformation
     * @param source bitmap to be transformed
     * @return circle transformed bitmap
     */
    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        float r = size / 2f;

        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.button_off_white));
        paint.setStrokeWidth(32);

        Canvas canvas = new Canvas(result);
        canvas.drawCircle(r, r, r, paint);

        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        canvas.drawCircle(r, r, r - 16, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}