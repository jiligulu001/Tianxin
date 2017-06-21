package com.tianxin.tianxin.bean;

/**
 * Created by PC大佬 on 2017/4/24.
 */
public class INfo_Bean {

    /**
     * deviceName : br20140009
     * currentDate : 2017-04-14T10:49:18Z
     * temp : 18
     * do : 19
     * pH : 13
     * stir : 80.0
     * feed : 85.0
     * acid : 70.0
     * base : 100.0
     * ca : 0.0
     * o2 : 100
     * n2 : 0.0
     * co2 : 0.0
     */
    private  String deviceName;
    private  String currentDate;
    private  String temp;
    private  String stir;
    private  String feed;
    private  String acid;
    private  String ca;
    private  String o2;
    private  String n2;
    private  String co2;
    private String dO;
    private String pH;
    private String base;

    public  String getDeviceName(){ return  deviceName; }
    public  void setDeviceName(String deviceName){ this.deviceName = deviceName; }

    public  String getCurrentDate(){ return  currentDate;}
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStir() {
        return stir;
    }
    public void  setStir(String stir){ this.stir = stir; }

    public  String getFeed() { return  feed;}
    public  void setFeed(String feed) { this.feed = feed; }

    public String getAcid() { return  acid; }
    public void setAcid(String acid) { this.acid = acid; }

    public String getCa() { return  ca; }
    public void setCa(String ca) { this.ca = ca; }


    public String getDO() {
        return dO;
    }
    public void setDO(String dO) { this.dO = dO; }

    public String getPH() {
        return pH;
    }
    public void setPH(String pH) {
        this.pH = pH;
    }

    public String getO2() {
        return o2;
    }
    public void setO2(String O2) {
        this.o2 = O2;
    }

    public String getN2() { return  n2; }
    public void  setN2(String n2) { this.n2 = n2; }

    public String getCo2() { return  co2; }
    public void  setCo2(String co2) { this.co2 = co2; }

    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }

}
