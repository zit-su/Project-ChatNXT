package chatme.nxt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class WaveformView extends View {
    private Paint paint;
    private int barCount = 20;
    private int[] heights;
    private Random random = new Random();

    public WaveformView(Context context) {
        super(context);
        init();
    }

    public WaveformView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#3F51B5"));
        paint.setStrokeWidth(8);
        heights = new int[barCount];
        for (int i = 0; i < barCount; i++) {
            heights[i] = random.nextInt(100);
        }
        post(updateWaveform);
    }

    private Runnable updateWaveform = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < barCount; i++) {
                heights[i] = random.nextInt(getHeight());
            }
            invalidate();
            postDelayed(this, 100);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float space = (float) getWidth() / (float) barCount;
        for (int i = 0; i < barCount; i++) {
            float left = i * space;
            float top = getHeight() - heights[i];
            float right = left + space / 1.5f;
            canvas.drawRect(left, top, right, getHeight(), paint);
        }
    }
}
