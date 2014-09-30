package komunikator;

import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
 
/**
 * A Drawable that draws an oval with given {@link Bitmap}
 */
public class RoundedAvatarDrawable {
  private Bitmap mBitmap;
 
  public RoundedAvatarDrawable(Bitmap bitmap) {
    mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
  
    BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
    Paint paint = new Paint();
    paint.setShader(shader);
    paint.setAntiAlias(true);

    Canvas c = new Canvas(mBitmap);
    c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
  }
 
  public Bitmap getBitmap() {
	  return mBitmap;
  }
 
  // TODO allow set and use target density, mutate, constant state, changing configurations, etc.
}