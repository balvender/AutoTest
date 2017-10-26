import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.redisson.Config;
import org.redisson.Redisson;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * @author --lixuan
 * @time -- 2016/12/21
 * @description  --测试用例集
 *
 */
public class HomeTest {
     @Test(description = "测试数据库的读取")
    public void wm_101()
     {
         WmDbStudent wmDbStudent=new WmDbStudent();
         int i=wmDbStudent.selectfromid("hek");
         Assert.assertEquals(i,7);
     }
     @Test(description = "v8/home/head返回值校验")
    public void wm_102()throws Exception
     {
         Sendpost sendpost=new Sendpost();
         String response=sendpost.SendUrl();
         String s=JsonPath.read(response,"$.data.primary_filter[1].name");
         WmDataResponse wmDataResponse=new WmDataResponse();
         String jsondata=wmDataResponse.getjson();
         String n=JsonPath.read(jsondata,"$.Food_poi.primary_filter.name");
//         System.out.print(s);
         Assert.assertEquals(s,n);
        // assetThat

     }
     @Test(description = "base64转码")
    public void wm_12() throws Exception {
         String s ="bG9raTk1MTc1M0BnbWFpbC5jb20";
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] bytes=decoder.decodeBuffer(s);
         String str = new String(bytes,"utf-8");
         System.out.println(str);

     }
     @Test(description = "测试redis")
    public void wm_103()
     {
         Config config=new Config();
         config.setConnectionPoolSize(100);
         config.addAddress("127.0.0.1:6379");
         Redisson redisson=Redisson.create(config);
         System.out.print("连接成功");
         ConcurrentMap<String,Object> map=redisson.getMap("FirstMap");
         map.put("Michael","男");
         map.put("Jane","女");
         map.put("Marry","nv");
         ConcurrentMap resultmap=redisson.getMap("FirstMap");
         System.out.print("resultmap=="+resultmap.keySet());
         redisson.shutdown();

     }
     @Test(groups = "1")
    public  void wm_104()
     {
         System.out.println("This is case1");
     }
     @Test(groups = "2")
    public void wm_105()
     {
         System.out.println("This is case2");
     }
}
