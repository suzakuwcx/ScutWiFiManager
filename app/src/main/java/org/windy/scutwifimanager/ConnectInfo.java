package org.windy.scutwifimanager;

//存储连接时的关键信息
public class ConnectInfo {
    //表明在访问第一个链接的时候是否发生了强制门户
    private boolean isRedirect = false;
    private String wlanuserip = null;
    private String wlanacip = null;
    //最终学校服务器返回的url
    private String lastMsg = null;
    //设备是否联网
    private boolean success = false;

    public ConnectInfo() {

    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public String getWlanuserip() {
        return wlanuserip;
    }

    public void setWlanuserip(String wlanuserip) {
        this.wlanuserip = wlanuserip;
    }

    public String getWlanacip() {
        return wlanacip;
    }

    public void setWlanacip(String wlanacip) {
        this.wlanacip = wlanacip;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setIsSuccess(boolean success) {
        this.success = success;
    }
}
