import java.io.*;
import java.util.Properties;

/**
 * @author ----lixuan
 * @time  -----2016/12/26
 * @description  ---从data.json中读取数据
 */
public class WmDataResponse {
    public String getjson() throws  Exception{
        StringBuffer stringBuffer=new StringBuffer();
        String line=null;
        String url=WmDataResponse.class.getClassLoader().getResource("data.json").getPath();
        BufferedReader bufferedReader=new BufferedReader(new FileReader(url));
        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }


}
