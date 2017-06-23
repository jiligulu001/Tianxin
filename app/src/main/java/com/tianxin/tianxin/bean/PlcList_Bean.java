package com.tianxin.tianxin.bean;

/**
 * Created by PC大佬 on 2017/4/21.
 */
public class PlcList_Bean {

    /**
     * device_name : 20140010
     * temp : 38.0
     * dO : 80.0
     * pH : 7.5
     * stir : 100.0
     * feed : 80.0
     * acid : 100.0
     * tempSwitch : 0
     * doSwitch : 1
     * phSwitch : 0
     * stirSwitch : 0
     * feedSwitch : 1
     * acidSwitch : 0
     */

    private String device_name;
    private float temp;
    private float dO;
    private float pH;
    private float stir;
    private float feed;
    private float acid;
    private String tempSwitch;
    private String doSwitch;
    private String phSwitch;
    private String stirSwitch;
    private String feedSwitch;
    private String acidSwitch;

    public String getDevice_name() {
        return device_name;
    }
    public void setDevice_name(String deviceName) {
        this.device_name = deviceName;
    }

    public float getTemp() { return  temp; }
    public void  setTemp(float temp) { this.temp = temp; }

    public float getdO() { return  dO; }
    public void  setdO(float dO) { this.dO = dO; }

    public float getpH() { return  pH; }
    public void  setpH(float pH) { this.pH = pH; }

    public float getStir() { return  stir; }
    public void  setStir(float stir) { this.stir = stir; }

    public float getFeed() { return  feed; }
    public void  setFeed(float feed) { this.feed = feed; }

    public float getAcid() { return  acid; }
    public void setAcid(float acid) { this.acid = acid; }

    public String getTempSwitch() { return  doSwitch; }
    public void setTempSwitch( String tempSwitch) { this.tempSwitch = tempSwitch; }

    public String getDoSwitch() { return doSwitch; }
    public void setDoSwitch(String doSwitch) { this.doSwitch = doSwitch; }

    public String getPhSwitch() { return phSwitch; }
    public void setPhSwitch(String phSwitch) { this.phSwitch = phSwitch; }

    public String getStirSwitch() { return stirSwitch; }
    public void setStirSwitch(String stirSwitch) { this.stirSwitch = stirSwitch; }

    public String getFeedSwitch() { return feedSwitch; }
    public void setFeedSwitch(String feedSwitch) { this.feedSwitch = feedSwitch; }

    public String getAcidSwitch() { return  acidSwitch; }
    public void setAcidSwitch(String acidSwitch) { this.acidSwitch = acidSwitch; }
}
