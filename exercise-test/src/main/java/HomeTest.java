import RequestPara.MTRequest;
import org.testng.annotations.Test;
import util.AssertUtil;
import util.HttpUtil;

/**
 * @author --lixuan
 * @time -- 2016/12/21
 * @description  --测试用例集
 *
 */
public class HomeTest {
     @Test
    public void wm_104()
     {
         HttpUtil util = new HttpUtil();
         MTRequest mtRequest = new MTRequest();
         AssertUtil.isEqual("$.code","$.code",util.doGet(mtRequest));
     }

}
