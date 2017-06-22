package com.tianxin.tianxin.bean;

/**
 * Created by PC大佬 on 2017/4/24.
 */
public class INfo_Bean {

    /**
     * deviceName : br20140010
     * currentDate : 2017-06-20T05:19:00Z
     * temp : 36.7
     * dO : 200
     * pH : 7.9
     * stir : 100
     * feed : 80
     * acid : 80
     * base : 100
     * ca : 0
     * o2 : 100
     * n2 : 0
     * co2 : 0
     */

    private String deviceName;
    private String currentDate;
    private float  temp;
    private float  dO;
    private float  pH;
    private float  stir;
    private float  feed;
    private float  acid;
    private float  base;
    private float  ca;
    private float  o2;
    private float  n2;
    private float  co2;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getDO() {
        return dO;
    }

    public void setDO(float dO) {
        this.dO = dO;
    }

    public float getPH() {
        return pH;
    }

    public void setPH(float pH) {
        this.pH = pH;
    }

    public float getStir() {
        return stir;
    }

    public void setStir(float stir) {
        this.stir = stir;
    }

    public float getFeed() {
        return feed;
    }

    public void setFeed(float feed) {
        this.feed = feed;
    }

    public float getAcid() {
        return acid;
    }

    public void setAcid(float acid) {
        this.acid = acid;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public float getCa() {
        return ca;
    }

    public void setCa(float ca) {
        this.ca = ca;
    }

    public float getO2() {
        return o2;
    }

    public void setO2(float o2) {
        this.o2 = o2;
    }

    public float getN2() {
        return n2;
    }

    public void setN2(float n2) {
        this.n2 = n2;
    }

    public float getCo2() {
        return co2;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }
}
