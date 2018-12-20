package wangfeixixi.cip.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

import wangfeixixi.cip.R;
import wangfeixixi.cip.beans.JsonRootBean;
import wangfeixixi.cip.fram.BaseActivity;
import wangfeixixi.cip.widget.carview.CarBean;
import wangfeixixi.cip.widget.carview.CarView;
import wangfeixixi.cip.widget.udp.UDPUtils;
import wangfeixixi.cip.widget.udp.server.UDPResultListener;
import wangfeixixi.com.base.UIUtils;
import wangfeixixi.com.base.data.DateUtils;
import wangfeixixi.com.base.crash.SpLogUtil;
import wangfeixixi.com.bdvoice.VoiceUtil;
import wangfeixixi.share.circle.DialProgress;

public class CarViewActivity extends BaseActivity {
    private CarView carview;
    //    private LikeButton btn_start;
    private TextView tv_warning;
    private ImageView iv_hand_rv;
    private ImageView iv_hand_hv;
    private DialProgress dial_progress_hv;
    private DialProgress dial_progress_rv;
    private TextView tv_tips;
    private UDPResultListener listener;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.carview_activity);
        carview = findViewById(R.id.carview);
//        btn_start = findViewById(R.id.btn_start);
        tv_tips = findViewById(R.id.tv_tips);
        tv_warning = findViewById(R.id.tv_warning);
        iv_hand_hv = findViewById(R.id.iv_hand_hv);
        iv_hand_rv = findViewById(R.id.iv_hand_rv);
        dial_progress_hv = findViewById(R.id.dial_progress_hv);
        dial_progress_rv = findViewById(R.id.dial_progress_rv);
    }

    @Override
    protected void initData() {
        VoiceUtil.getInstance().initKey(UIUtils.getContext(), "14678940", "F7aZGFVk9cOQdb9X6nPw2Aog", "2wkI4xprZ8sMmxICY9iZYim704j1qy65");
        listener = new UDPResultListener() {
            @Override
            public void onResultListener(final JsonRootBean jsonRootBean) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            receiveCars(jsonRootBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        dial_progress_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_warning.setVisibility(tv_warning.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

//        carview.updateBodys(new CarBean[0]);
//        UDPUtils.udpServer(listener);
//        dial_progress_hv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                carview.updateBodys(new CarBean[0]);
//                UDPUtils.udpServer(listener);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carview.updateBodys(new CarBean[0]);
        UDPUtils.startServer(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UDPUtils.stopServer();
    }

    public long lastTime = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveCars(JsonRootBean bean) {
        ArrayList<CarBean> list = new ArrayList<>();
        if (bean.hvDatas != null)
            list.add(bean.hvDatas);
        if (bean.rvDatas != null)
            list.addAll(bean.rvDatas);
        carview.updateBodys(list.toArray(new CarBean[list.size()]));

        SpLogUtil.putString(String.valueOf(bean.sn), bean.toString());


        long nowTime = System.currentTimeMillis();
        long timeTemp = nowTime - lastTime;
        StringBuffer sb = new StringBuffer();
        sb.append("\n" + String.valueOf(DateUtils.getCurrentDate(DateUtils.dateFormatYMDHMS)));

        if (bean.hvDatas == null) {
            sb.append(bean.toString());
            tv_warning.setText(sb.toString());
            return;
        }

        if (bean.rvDatas == null) {
            sb.append(bean.toString());
            tv_warning.setText(sb.toString());
            return;
        }
        double sqrt = Math.sqrt(Math.abs(list.get(1).x) * Math.abs(list.get(1).x) + Math.abs(list.get(1).y) * Math.abs(list.get(1).y));
        double jvli = Double.parseDouble(new DecimalFormat("#.##").format(sqrt));
        if (bean.rvDatas.get(0).fcwAlarm != 0) {
            tv_tips.setText("附近车辆:" + jvli + "米");
        } else {
            tv_tips.setText("");
        }
        sb.append("\n距离：" + jvli);
        sb.append(bean.toString());
        tv_warning.setText(sb.toString());

        iv_hand_rv.setRotation(bean.rvDatas.get(0).heading);
        iv_hand_hv.setRotation(bean.hvDatas.heading);
        dial_progress_hv.setValue(bean.hvDatas.speed * 3.6f);
        dial_progress_rv.setValue(bean.rvDatas.get(0).speed * 3.6f);

        if ((timeTemp) > 2000) {
            carview.switchSpeed((int) bean.hvDatas.speed);
            if (bean.rvDatas.get(0).fcwAlarm != 0)
                VoiceUtil.getInstance().speek("注意安全距离");
            lastTime = nowTime;
        }
    }
}
