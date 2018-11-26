package wangfeixixi.com.car;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wangfeixixi.com.car.utils.BitmapUtils;
import wangfeixixi.com.car.utils.CarUtils;

public class CarView extends View {

    private Paint mPaintCar;
    private int mCarX;//车辆x坐标
    private int mCarY;//车辆y坐标
    private Bitmap bitmap;//原生车图
//    private Matrix matrix;
//    private Bitmap carBitmap;
//    private Rect carRectSrc;

    public CarView(Context context) {
        this(context, null, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //自身车
        mPaintCar = new Paint();
        mPaintCar.setAntiAlias(true);
        mPaintCar.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //自身车坐标,todo是以自身中心为坐标的
        mCarX = width / 2;
        mCarY = height / 3 * 2;
        //重心坐标
//        rect = new Rect(mCarX - carWidth / 2, mCarY - carLength / 2, mCarX + carWidth / 2, mCarY + carLength / 2);
        //车图标,放大车辆图标
        bitmap = BitmapUtils.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car), CarUtils.carBitmapScale);
//        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);//背景
        if (mBeans != null)
            for (int i = 0; i < mBeans.length; i++)
                drawCar(canvas, mBeans[i]);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        mPaint.setPathEffect(new DashPathEffect(new float[]{50, 20}, 0));
//        canvas.drawLine(mCarX - 100, 0,
//                mCarX - 100, mCarY, mPaint);
//        canvas.drawLine(mCarX + 100, 0,
//                mCarX + 100, mCarY, mPaint);
        canvas.drawLines(new float[]{
                mCarX - 100, 0,
                mCarX - 100, mCarY / 2 * 3,
                mCarX + 100, 0,
                mCarX + 100, mCarY / 2 * 3}, mPaint);
    }

    private void drawCar(Canvas canvas, CarBean bean) {
//        matrix.reset();
//        matrix.setRotate(bean.rotate);
//        carBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 3, bitmap.getHeight() / 3, matrix, true);
//        carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car, null);
        //自身车
//        carRectSrc = new Rect(0, 0, carBitmap.getWidth(), carBitmap.getHeight());
//        carRectSrc = new Rect(0, 0, carBitmap.getWidth(), carBitmap.getHeight());

//        canvas.drawBitmap(carBitmap, carRectSrc, rect, mPaintCar);
//        canvas.drawBitmap(carBitmap, mCarX, mCarY, mPaintCar);

        Bitmap rotateBitmap = BitmapUtils.rotateBitmap(bitmap, bean.rotate);
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        canvas.drawBitmap(rotateBitmap, mCarX, mCarY, mPaintCar);
        canvas.drawBitmap(rotateBitmap, CarUtils.x2XView(mCarX, bean), CarUtils.y2YView(mCarY, bean), mPaintCar);


        //测试版垂直
//        canvas.drawBitmap(carBitmap, carRectSrc, CarUtils.shelf2Screen(mCarX, mCarY, bean), mPaintCar);

        canvas.save();
    }

    private CarBean[] mBeans = null;

    public void updateBodys(CarBean[] beans) {
        mBeans = beans;
        invalidate();
    }

    public void switchPoint() {

    }

    public void stop() {

    }
}