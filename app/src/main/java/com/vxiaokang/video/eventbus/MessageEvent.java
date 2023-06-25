package com.vxiaokang.video.eventbus;

/**
 * Created with Android.
 *
 * @CLASS:
 * @Date: 2022/09/20/17:50
 * @Description:
 */
public class MessageEvent {
    private float speed;
    private int payFlag;

    public MessageEvent(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public int getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(int payFlag) {
        this.payFlag = payFlag;
    }
}
