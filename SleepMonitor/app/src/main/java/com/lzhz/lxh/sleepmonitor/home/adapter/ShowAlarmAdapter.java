package com.lzhz.lxh.sleepmonitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.allen.library.SuperTextView;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.CommonAdapter;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.AlarmBean;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * 作者：lxh on 2018-01-06:10:03
 * 邮箱：15911638454@163.com
 */

public class ShowAlarmAdapter  extends CommonAdapter<RecyclerView.ViewHolder> {
    private List<AlarmBean> mDatas;

    public ShowAlarmAdapter(Context context, int layoutId,List<AlarmBean> mDatas) {
        super(context, layoutId, mDatas.size());
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, mLayoutId, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final  AlarmBean alarmBean = mDatas.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.smrv.setLeftBottomString(mDatas.get(position).getAabTime());
        myViewHolder.smrv.setLeftString(alarmBean.getHour() + ":" + mDatas.get(position).getMinute());
        myViewHolder.smrv.setLeftBottomString(alarmBean.getWeeks() +"");
        myViewHolder.smrv.setSwitchIsChecked(alarmBean.isState());

        myViewHolder.smrv.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //添加闹钟
                    addAlart(alarmBean);
                }else{
                    //删除
                    AlarmManagerUtil.cancelAlarm(mContext,AlarmManagerUtil.ALARM_ACTION,mDatas.get(position).getAlarmId());
                }
                AlarmBean alarmBean1= alarmBean;
                alarmBean1.setState(b);
                int i = alarmBean1.updateAll("alarmId = ?",alarmBean1.getAlarmId()+"");
                LogUtils.i("check" + b + "---" +i);
            }
        });
    }

    private void addAlart(AlarmBean bean) {
        if (bean.getCycle() == 0 || bean.getCycle() == -1) {//是每天的闹钟
            AlarmManagerUtil.setAlarm(mContext, bean.getFlag(),bean.getHour(),
                    bean.getMinute(),bean.getAlarmId(), 0, bean.getTips(),bean.getSoundOrVibrator());

        } else {//多选，周几的闹钟
            String[] weeks = bean.getWeeks().split(",");
            for (int i = 0; i < weeks.length; i++) {
                AlarmManagerUtil.setAlarm(mContext, bean.getFlag(),bean.getHour(),
                        bean.getMinute(),bean.getAlarmId(), Integer.parseInt(weeks[i]), bean.getTips(),bean.getSoundOrVibrator());
            }
        }
    }
     class MyViewHolder extends RecyclerView.ViewHolder{
       private SuperTextView smrv;


        public MyViewHolder(View itemView) {
            super(itemView);
            smrv = itemView.findViewById(R.id.alipay_stv);
        }
    }

    private String getDay(String str){
        return null;
    }


}
