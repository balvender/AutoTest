package RequestPara;

import util.RequestAnnotation;

/**
 * Created by LiXuan on 2017/11/6.
 */
@RequestAnnotation(uri="/bar/search")
public class DPRequest extends BaseRequest {
    private String cityId = "1";

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
