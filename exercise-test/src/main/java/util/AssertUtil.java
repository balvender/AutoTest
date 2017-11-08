package util;

import com.jayway.jsonpath.JsonPath;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Created by LiXuan on 2017/11/6.
 */
public class AssertUtil {
    public static void isEqual(String prePath,String resPath,String resJson){
        //真实值
        Object res = JsonPath.read(resJson,resPath);
        //期待值
        Object pre = JsonPath.read(getJsonFile(),prePath);
        Assert.assertEquals(pre,res);
    }
    private static String getJsonFile(){
        StringBuffer sb = new StringBuffer();
        try {
            String path = AssertUtil.class.getClassLoader().getResource("response.json").getPath();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String tmp = null;
            while ((tmp = br.readLine())!= null){
                sb.append(tmp);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
