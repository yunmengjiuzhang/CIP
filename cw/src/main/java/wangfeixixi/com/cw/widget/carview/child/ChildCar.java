package wangfeixixi.com.cw.widget.carview.child;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import wangfeixixi.com.commen.utils.UIUtils;
import wangfeixixi.com.cw.R;
import wangfeixixi.com.cw.widget.carview.CarBean;
import wangfeixixi.com.cw.widget.carview.CarUtils;
import wangfeixixi.com.cw.widget.carview.anim.BlinkAnim;

public class ChildCar {
    ImageView iv_ben;
    private final ImageView iv_other;
    private final View view_alarm;

    private ChildCar() {
        iv_ben = new ImageView(UIUtils.getContext());
        iv_ben.setBackgroundResource(R.mipmap.car_self);

        iv_other = new ImageView(UIUtils.getContext());
        iv_other.setBackgroundResource(R.mipmap.car_other);

        view_alarm = new View(UIUtils.getContext());
    }

    public static class Inner {
        private static ChildCar instance = new ChildCar();
    }

    public static ChildCar getInstance() {
        return Inner.instance;
    }

    public boolean isBenCarInit = false;

    public float benHeading;

    public void addUpdateBenCar(ViewGroup viewGroup, CarBean bean) {
        if (!isBenCarInit) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
            layoutParams.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
            layoutParams.width = (int) (bean.width * CarUtils.getInstance().scale);
            layoutParams.height = (int) (bean.height * CarUtils.getInstance().scale);
            viewGroup.addView(iv_ben, layoutParams);
            isBenCarInit = true;
        } else {
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) iv_ben.getLayoutParams();
            layoutParams1.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
            layoutParams1.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
            layoutParams1.width = (int) (bean.width * CarUtils.getInstance().scale);
            layoutParams1.height = (int) (bean.height * CarUtils.getInstance().scale);
            iv_ben.setLayoutParams(layoutParams1);
        }
        benHeading = bean.heading;
    }

    public void addBenCar(ViewGroup viewGroup, CarBean bean) {
        viewGroup.removeView(iv_ben);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
        layoutParams.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
        layoutParams.width = (int) (bean.width * CarUtils.getInstance().scale);
        layoutParams.height = (int) (bean.height * CarUtils.getInstance().scale);
        viewGroup.addView(iv_ben, layoutParams);
    }

    public void addOtherCar(ViewGroup viewGroup, CarBean bean) {
        viewGroup.removeView(iv_other);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
        layoutParams.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
        layoutParams.width = (int) (bean.width * CarUtils.getInstance().scale);
        layoutParams.height = (int) (bean.height * CarUtils.getInstance().scale);
        viewGroup.addView(iv_other, layoutParams);
        BlinkAnim.blink(iv_other, bean.cw != 0);
    }

    public boolean isOtherCarInit = false;

    public void addUpdateOtherCar(ViewGroup viewGroup, CarBean bean) {
//        bean = CarUtils.getInstance().filterOver(bean);

        if (!isOtherCarInit) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
            layoutParams.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
            layoutParams.width = (int) (bean.width * CarUtils.getInstance().scale);
            layoutParams.height = (int) (bean.height * CarUtils.getInstance().scale);
            viewGroup.addView(iv_other, layoutParams);
            isOtherCarInit = true;
        } else {
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) iv_other.getLayoutParams();
            layoutParams1.leftMargin = CarUtils.getInstance().getLeftMargin(bean.averagex, bean.width);
            layoutParams1.topMargin = CarUtils.getInstance().getTopMargin(bean.averagey, bean.height);
            layoutParams1.width = (int) (bean.width * CarUtils.getInstance().scale);
            layoutParams1.height = (int) (bean.height * CarUtils.getInstance().scale);
            iv_other.setLayoutParams(layoutParams1);
            BlinkAnim.blink(iv_other, bean.fcwAlarm != 0);
        }
        iv_other.setRotation(bean.heading - benHeading);
    }
//    public void addUpdateOtherCarTest(ViewGroup viewGroup, CarBean bean) {
////        bean = CarUtils.getInstance().filterOver(bean);
//        if (!isOtherCarInit) {
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.leftMargin = CarUtils.getInstance().getLeftMargin(bean.x, bean.width);
//            layoutParams.topMargin = CarUtils.getInstance().getTopMargin(bean.y, bean.height);
//            layoutParams.width = (int) (bean.width * CarUtils.getInstance().scale);
//            layoutParams.height = (int) (bean.height * CarUtils.getInstance().scale);
//            viewGroup.addView(iv_other, layoutParams);
//            isOtherCarInit = true;
//        } else {
//            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) iv_other.getLayoutParams();
//            layoutParams1.leftMargin = CarUtils.getInstance().getLeftMargin(bean.x, bean.width);
//            layoutParams1.topMargin = CarUtils.getInstance().getTopMargin(bean.y, bean.height);
//            layoutParams1.width = (int) (bean.width * CarUtils.getInstance().scale);
//            layoutParams1.height = (int) (bean.height * CarUtils.getInstance().scale);
//            iv_other.setLayoutParams(layoutParams1);
//            BlinkAnim.blink(iv_other, bean.fcwAlarm != 0);
//        }
//        iv_other.setRotation(bean.heading - benHeading);
//    }

//    private boolean isAlarmViewInit = false;

//    private void addAlarm(ViewGroup viewGroup, CarBean bean) {
//        if (bean.fcwAlarm != 0) {
//            view_alarm.setVisibility(View.VISIBLE);
//            if (!isAlarmViewInit) {
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                int alarmWidth = ((int) CarUtils.getInstance().carWidth - 20);
//                layoutParams.width = alarmWidth;
//                layoutParams.height = alarmWidth;
//                layoutParams.leftMargin = bean.getAlarmLeftMargin();
//                layoutParams.topMargin = bean.getAlarmTopMargin();
//                viewGroup.addView(view_alarm, layoutParams);
//                isAlarmViewInit = true;
//                BlinkAnim.blink(view_alarm, bean.getAlarmOritation());
//            } else {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_alarm.getLayoutParams();
//                int alarmWidth = ((int) CarUtils.getInstance().carWidth - 20);
//                layoutParams.width = alarmWidth;
//                layoutParams.height = alarmWidth;
//                layoutParams.leftMargin = bean.getAlarmLeftMargin();
//                layoutParams.topMargin = bean.getAlarmTopMargin();
//                view_alarm.setLayoutParams(layoutParams);
//            }
//            if (bean.getAlarmOritation() != alarmOrientation) {
//                BlinkAnim.blink(view_alarm, bean.getAlarmOritation());
//            }
//        } else {
//            view_alarm.setVisibility(View.GONE);
//            alarmOrientation = 0;
//        }
//    }

}