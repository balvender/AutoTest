import RequestPara.MTRequest;
import org.testng.annotations.Test;
import util.HttpUtil;

/**
 * @author --lixuan
 * @time -- 2016/12/21
 * @description  --测试用例集
 *
 */
public class HomeTest {
     @Test(groups = "1")
    public  void wm_104()
     {
         HttpUtil util = new HttpUtil();
         MTRequest mtRequest = new MTRequest();
         System.out.println(util.doGet(mtRequest));
     }
     @Test(groups = "2")
    public void wm_105()
     {
         System.out.println("This is case2");
     }
     @Test(description = "beta环境美团官网校验")
    public void wm_meituan(){

     }
}
