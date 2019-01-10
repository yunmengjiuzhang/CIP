package wangfeixixi.cip.widget.carview;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wangfeixixi.cip.utils.ScreenUtils;

/**
 * 转换坐标类，将接收到的信息转换成屏幕坐标点
 * <p>
 * 人和车的坐标都应该是左上角
 */
public class CarUtils {

    private CarUtils() {
        //本车
//        BitmapFactory.Options carSelfOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        carSelfOpts.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(UIUtils.getResources(), R.mipmap.car_self, carSelfOpts);//此时返回bm为空
//        carSelfOpts.inJustDecodeBounds = false;
//        carWidth = carSelfOpts.outWidth;
//        carLength = carSelfOpts.outHeight;

        carWidth = 3f * carBitmapScale;
        carLength = 3f * carBitmapScale;
//        //远车
//        BitmapFactory.Options carOtherOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        carOtherOpts.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(UIUtils.getResources(), R.mipmap.car_other, carOtherOpts);//此时返回bm为空
//        carOtherOpts.inJustDecodeBounds = false;
//        carOtherWidth = carOtherOpts.outWidth;
//        carOtherLength = carOtherOpts.outHeight;


        carDiagonal = (float) Math.sqrt(Math.abs(carWidth) * Math.abs(carWidth) + Math.abs(carLength) * Math.abs(carLength));

        roadWidth = 2 * carBitmapScale * scale;
        carViewHeight = ScreenUtils.getScreenHeight() / 3 * 2;
        carViewWidth = ScreenUtils.getScreenWidth();
        x0 = carViewWidth / 2;
        y0 = carViewHeight / 3 * 2;
    }

    private static class Inner {
        private static CarUtils instance = new CarUtils();
    }

    public static CarUtils getInstance() {
        return Inner.instance;
    }

    /**
     * 转化比例：1米scale个像素
     */
    public float scale = 20;

    /**
     * 车辆图片的缩放
     */
    public float carBitmapScale = 2f;

    public float carWidth;//车宽
    public float carLength;//车长
    public float carDiagonal;//车对角线
    public float roadWidth;//旁边道路的宽度
    public int x0;//原地x
    public int y0;//原点y
    public int carViewHeight;//车辆显示界面height
    public int carViewWidth;//车辆显示界面width

    /**
     * @param x     横向距离，单位m
     * @param width 车宽，单位m
     * @return x像素点，单位像素
     */
    public int getLeftMargin(float x, float width) {
        return (int) (x0 + x * scale - width * scale / 2);
    }

    /**
     * @param y      纵向距离，单位m
     * @param height 车长，单位m
     * @return y像素点，单位像素
     */
    public int getTopMargin(float y, float height) {
        return (int) (y0 - y * scale - height * scale / 2);
    }

    public CarBean filterOver(CarBean carBean) {
        float carLengthTemp = carLength;
        float carWidthTemp = carWidth;
        float diagonal = (float) Math.sqrt(carBean.x * carBean.x + carBean.y * carBean.y);
        if (carLengthTemp / carWidthTemp >= Math.abs(carBean.y / carBean.x)) {  //x方向
            float diagonalMix = Math.abs(carWidthTemp * diagonal / carBean.x);
            if (diagonal < diagonalMix) {//需要修正数据
                carBean.x = carBean.x > 0 ? carWidthTemp : -carWidthTemp;
                float yAbs = (float) Math.sqrt(diagonalMix * diagonalMix - carWidthTemp * carWidthTemp);
                carBean.y = carBean.y > 0 ? yAbs : -yAbs;
            }
        } else {//y方向
            float diagonalMix = Math.abs(carLengthTemp * diagonal / carBean.y);
            if (diagonal < diagonalMix) {//需要修正数据
                carBean.y = carBean.y > 0 ? carLengthTemp : -carLengthTemp;
                float xAbs = (float) Math.sqrt(diagonalMix * diagonalMix - carLengthTemp * carLengthTemp);
                carBean.x = (carBean.x > 0 ? xAbs : -xAbs);
            }
        }
        return carBean;
    }

//    public CarBean filterOver(CarBean carBean) {
//        float diagonal = (float) Math.sqrt(carBean.x * carBean.x + carBean.y * carBean.y);
//        if (CarUtils.getInstance().carLength / CarUtils.getInstance().carWidth >= Math.abs(carBean.y / carBean.x)) {  //x方向
//            float diagonalMix = Math.abs(carWidth * diagonal / carBean.x);
//            if (diagonal < diagonalMix) {//需要修正数据
//                carBean.x = carBean.x > 0 ? carWidth : -carWidth;
//                float yAbs = (float) Math.sqrt(diagonalMix * diagonalMix - carWidth * carWidth);
//                carBean.y = carBean.y > 0 ? yAbs : -yAbs;
//            }
//        } else {//y方向
//            float diagonalMix = Math.abs(carLength * diagonal / carBean.y);
//            if (diagonal < diagonalMix) {//需要修正数据
//                carBean.y = carBean.y > 0 ? carLength : -carLength;
//                float xAbs = (float) Math.sqrt(diagonalMix * diagonalMix - carLength * carLength);
//                carBean.x = (carBean.x > 0 ? xAbs : -xAbs);
//            }
//        }
//        return carBean;
//    }

    /**
     * 两图相接两个中心最小距离：首先判断是两图片的长相接还是宽相接
     *
     * @param x 中心点x
     * @param y 中心点y
     * @return 两个中心最小距离
     */
    public float getMixDiagonal(float x, float y) {
        float diagonal = (float) Math.sqrt(x * x + y * y);
        if (CarUtils.getInstance().carLength / CarUtils.getInstance().carWidth >= Math.abs(y / x)) {  //x方向
            return Math.abs(carWidth * diagonal / x);
        } else {//y方向
            return Math.abs(carLength * diagonal / y);
        }
    }

    ////////////////////////////////////动态代码/////////////////////////////////////////////////////

//    public  CarBean carBean2Screen(CarBean bean) {
//        int y = ScreenUtils.getScreenHeight() / 3 * 2;
//        int x = ScreenUtils.getScreenWidth() / 2;
////        float width = bean.width;
////        float height = bean.height;
////        float x0 = x - width / 2;
////        float y0 = y - height / 2;
//        bean.leftMargin = (int) (x + bean.x * scale - bean.width * scale / 2);
//        bean.topMargin = (int) (y - bean.y * scale - bean.height * scale / 2);
//        return bean;
//    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

//    /**
//     * 将自身坐标系转换为屏幕坐标系
//     *
//     * @param carX     车辆的x像素值,坐标原点，车辆中心
//     * @param carY     车辆的y像素值，坐标原点，车辆中心
//     * @param bodyBean 扫描的物体信息
//     * @return 屏幕坐标系物体区域
//     */
//    public Rect shelf2Screen(float carX, float carY, CarBean bodyBean) {
//        //1.车辆自身中心坐标（x1,y1）
//
//        //2.
//
//
//        float x = (carX + bodyBean.x * scale);
//        float y = (carY - bodyBean.y * scale);
//        float width = bodyBean.width * scale;
//        float length = bodyBean.height * scale;
//        return new Rect((int) (x - width), (int) (y - length), (int) x, (int) y);
//    }

    public float x2XView(float carX, CarBean bodyBean) {
        return carX + bodyBean.x * scale - bodyBean.width * scale;
    }

    public float y2YView(float carY, CarBean bodyBean) {
        return carY - bodyBean.y * scale - bodyBean.height * scale;
    }

    //    public  void main(String[] args) {
//        speed2Arrays(10);
//        for (int j = 0; j < 100; j++) {
//
//
//            float[][] floats = speed2Arrays(j);
//            String temp = "";
//            for (int i = 0; i < floats.height; i++) {
//                temp += "  " + String.valueOf(floats[i]);
//                if (floats[i] != null) temp += "长度" + floats.height;
//            }
//            System.out.println(temp);
//
//        }
//
//
//    }
    private float[] floatsSrc = {80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40};

    public float[][] speed2Arrays(int speed) {
        List<float[]> strList = null;
        if (speed == 0) {
            return new float[][]{floatsSrc};
        } else if (speed <= 20) {
            strList = Arrays.asList(getLiensArrays(80, -40, 2));
        } else if (speed <= 40) {
            strList = Arrays.asList(getLiensArrays(80, -40, 4));
        } else if (speed <= 60) {
            strList = Arrays.asList(getLiensArrays(80, -40, 6));
        } else if (speed <= 80) {
            strList = Arrays.asList(getLiensArrays(80, -40, 8));
        } else if (speed <= 100) {
            strList = Arrays.asList(getLiensArrays(80, -40, 10));
        } else if (speed <= 120) {
            strList = Arrays.asList(getLiensArrays(80, -40, 12));
        } else {
            strList = Arrays.asList(getLiensArrays(80, -40, 20));
        }
        List<float[]> strListNew = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i) != null) {
                strListNew.add(strList.get(i));
            }
        }
        return strListNew.toArray(new float[strListNew.size()][]);
    }

    /**
     * @param start 开始的数字 如80
     * @param end   结束的数字 如-40
     * @param cha   中间的插值 如10
     * @return 返回float[][]双重数组
     */
    public float[][] getLiensArrays(int start, int end, int cha) {
        int size = (start - end) / cha;
        float[][] floatsReturn = new float[size][];
//        float[] floatsSrc = {80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40};
        float[] floatsTemp;
        while (true) {
            if (start != end && (start -= cha) > end) {
                floatsTemp = new float[floatsSrc.length];
                System.arraycopy(floatsSrc, 1, floatsTemp, 1, floatsSrc.length - 1);
                floatsTemp[0] = start;
                floatsReturn[--size] = floatsTemp;
            } else {
                break;
            }
        }
        return floatsReturn;
    }

//    public  float[][] linesOne = new float[][]{
//            new float[]{80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{70, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{60, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{50, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{40, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{30, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{20, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{10, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{0, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//
//
//            new float[]{-10, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{-20, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{-30, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//            new float[]{-40, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40, 80, 40},
//    };
}
