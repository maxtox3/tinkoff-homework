package gusev.max.tinkoff_homework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Map;

/**
 * Created by v on 30/10/2017.
 */

public class GraphView extends View {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint, bitmapPaint;
    private int canvasSize;
    private float indent;
    private Path path;

    //Public methods

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initCanvas(context);
    }

    public void addPoint(float x, float y, float scaleX, float scaleY, Context context, int color) {
        paint.setColor(color);
        canvas.drawCircle(indent + resizeDpAxis(x, scaleX, context), -indent + canvasSize - resizeDpAxis(y, scaleY, context), 3, paint);
    }

    public void refresh() {
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5f);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawLine(indent, canvasSize - indent, indent, 0, paint);
        canvas.drawLine(indent, canvasSize - indent, canvasSize, canvasSize - indent, paint);
    }

    public void chart(Map<Float, Float> points, float mScaleX, float mScaleY, Context context, int color) {
        path.reset();
        paint.setColor(color);

        boolean first = true;

        for (Map.Entry point : points.entrySet()) {
            if (first) {
                path.moveTo(indent + resizeDpAxis((Float) point.getKey(), mScaleX, context), -indent + canvasSize - resizeDpAxis((Float) point.getValue(), mScaleY, context));
                first = false;
            } else {
                path.lineTo(indent + resizeDpAxis((Float) point.getKey(), mScaleX, context), -indent + canvasSize - resizeDpAxis((Float) point.getValue(), mScaleY, context));
            }
        }
        canvas.drawPath(path, paint);
    }

    public void drawXScale(float maxX, int steps) {
        resetPaintForScaleDrawing();

        for (int i = 0; i <= steps; i++) {
            path.reset();
            path.moveTo(indent + i * (canvasSize - indent) / steps, canvasSize - indent);
            path.lineTo(indent + i * (canvasSize - indent) / steps, canvasSize - indent - indent / 10);
            canvas.drawPath(path, paint);
            canvas.drawText(Float.toString(i * maxX / steps), indent + i * (canvasSize - indent) / steps, canvasSize - 3 * indent / 4, paint);
        }
    }

    public void drawYScale(float maxY, int steps) {
        resetPaintForScaleDrawing();

        for (int i = 1; i <= steps; i++) {
            path.reset();
            path.moveTo(indent, canvasSize - indent - i * (canvasSize - indent) / steps);
            path.lineTo(indent + indent / 10, canvasSize - indent - i * (canvasSize - indent) / steps);
            canvas.drawPath(path, paint);
            canvas.drawText(Float.toString(i * maxY / steps), 3 * indent / 4, canvasSize - 3 * indent / 4 - i * (canvasSize - indent) / steps, paint);
        }
    }

    //Private methods

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initCanvas(Context context){
        int viewSize = (int) convertDpToPixel(300, context);
        float scale = 1f;
        canvasSize = (int) (viewSize * scale);
        indent = canvasSize / 6;
        bitmap = Bitmap.createBitmap(canvasSize, canvasSize, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        path = new Path();

        canvas.drawLine(indent, canvasSize - indent, indent, 0, paint);
        canvas.drawLine(indent, canvasSize - indent, canvasSize, canvasSize - indent, paint);
    }

    private float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    private float resizeDpAxis(float dp, float scaleAxis, Context context) {
        return convertDpToPixel(dp, context) * scaleAxis;
    }

    private void resetPaintForScaleDrawing(){
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setStrokeWidth(3);
        paint.setTextAlign(Paint.Align.RIGHT);
    }

    //Protected methods

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.restore();
    }
}
