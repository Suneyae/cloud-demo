package com.morgan.eventListenerDemo.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: VipUserRegisterEvent
 * Package: com.morgan.eventListenerDemo.event
 *
 * @author Morgan Wei
 * @create 2026-11-14-9:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipUserRegisterEvent {
    private String userName;
    private boolean vip;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isVip() {
        return vip;
    }

    public String getUserName() {
        return userName;
    }
}
