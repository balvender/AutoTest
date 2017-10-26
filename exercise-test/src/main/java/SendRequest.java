import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by lixuan on 2016/12/1.
 */
public class SendRequest {
    private String wm_ctype="iphone";
    private int    poilist_wm_cityid=110100;
    private String wm_did="55E9D2FC-21FC-47E0-A0E9-C52C934CF62B";
    private String uuid="9FA8B3A4A5D5883C100F42F2FB4031E7366AF58A0750B1A1D053617323631E3F";
    private int    wm_latitude=40007752;
    private String utm_medium="iphone";
    private String wm_uuid="9FA8B3A4A5D5883C100F42F2FB4031E7366AF58A0750B1A1D053617323631E3F";
    private String wm_appversion="5.2.0";
    private int    wm_longitude=116486984;
    private String wm_dtype="iPhone6S";
    private int    need_regions=0;

    public String getWm_ctype() {
        return wm_ctype;
    }

    public void setWm_ctype(String wm_ctype) {
        this.wm_ctype = wm_ctype;
    }

    public int getPoilist_wm_cityid() {
        return poilist_wm_cityid;
    }

    public void setPoilist_wm_cityid(int poilist_wm_cityid) {
        this.poilist_wm_cityid = poilist_wm_cityid;
    }

    public String getWm_did() {
        return wm_did;
    }

    public void setWm_did(String wm_did) {
        this.wm_did = wm_did;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getWm_latitude() {
        return wm_latitude;
    }

    public void setWm_latitude(int wm_latitude) {
        this.wm_latitude = wm_latitude;
    }

    public String getUtm_medium() {
        return utm_medium;
    }

    public void setUtm_medium(String utm_medium) {
        this.utm_medium = utm_medium;
    }

    public String getWm_uuid() {
        return wm_uuid;
    }

    public void setWm_uuid(String wm_uuid) {
        this.wm_uuid = wm_uuid;
    }

    public String getWm_appversion() {
        return wm_appversion;
    }

    public void setWm_appversion(String wm_appversion) {
        this.wm_appversion = wm_appversion;
    }

    public int getWm_longitude() {
        return wm_longitude;
    }

    public void setWm_longitude(int wm_longitude) {
        this.wm_longitude = wm_longitude;
    }

    public String getWm_dtype() {
        return wm_dtype;
    }

    public void setWm_dtype(String wm_dtype) {
        this.wm_dtype = wm_dtype;
    }

    public int getNeed_regions() {
        return need_regions;
    }

    public void setNeed_regions(int need_regions) {
        this.need_regions = need_regions;
    }
    /**
     * 通过反射，用属性名称获得属性值
     * @param thisClass 需要获取属性值的类
     * @param fieldName 该类的属性名称
     * @return
     */
    public  Object getFieldValue(Class thisClass, String fieldName)
    {

        Object value = new Object();
        Method method = null;
        try {

            String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);

            method = thisClass.getMethod("get"+methodName);

            value = method.invoke(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return value;
    }
}
