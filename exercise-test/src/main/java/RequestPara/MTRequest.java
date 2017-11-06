package RequestPara;

import util.RequestAnnotation;

/**
 * Created by LiXuan on 2017/11/6.
 */
@RequestAnnotation(uri="/ptapi/getScenesList")
public class MTRequest extends BaseRequest {
    private String theme = "quality";
    private String tab = "all";
    private String ci = "57";
    private String limit = "12";

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
