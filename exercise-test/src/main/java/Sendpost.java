import netscape.javascript.JSObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.json.JSONObject;
import org.json.Property;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import java.lang.reflect.Method;

/**
 * Created by lixuan on 2016/12/1.
 */
public class Sendpost {
    private static TreeMap<String, String> props = new TreeMap();

    /**
    * 拼接UrL
    * **/
    public static String doPost()  {
        SendRequest request = new SendRequest();
        HashMap map = new HashMap();
        StringBuffer stringBuffer=new StringBuffer();
        String str;
        //通过反射获取Request类中的属性
        try {
            Class c = Class.forName("SendRequest");
            //得到类中的所有属性集合
            Field[] fs = c.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field field = fs[i];
                String value = request.getFieldValue(c, field.getName()).toString();

                map.put(field.getName(), value);
            }
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                stringBuffer.append(key + "=" + value +"&");
            }
            if(stringBuffer.length()>0)
                stringBuffer.deleteCharAt(stringBuffer.length()-1);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         str=new String(stringBuffer);
         return str;
    }
    /**
     *
     * 将json进行格式化
     * */
    public static String formatJson(String jsonStr)
    {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
    //读取不同环境下的config.properties
    public static String getPropertyValue(String key)
    {
        Properties properties=new Properties();
        FileInputStream fileInputStream;
        // 获取位于工程根目录下的config.properties配置文件绝对路径
        //String url = new File("").getAbsolutePath() + File.separator+  "src/main/profile/beta/config.properties";
        String url=Sendpost.class.getClassLoader().getResource("config.properties").getPath();
       try{

           fileInputStream=new FileInputStream(url);
           properties.load(fileInputStream);
           fileInputStream.close();
       }catch (Exception e)
       {
           System.out.println("problem with properties");
       }
       return properties.getProperty(key);
    }

    public  String  SendUrl () throws IOException {
        String host= getPropertyValue("host");
               // "http://api.c.waimai.beta.sankuai.com";
        String uri="/api/v8/home/head?";
        String url;
        String string;
        StringBuffer result=new StringBuffer();
        BufferedReader bufferedReader=null;
        PrintWriter out=null;
        string=doPost();
        url=host+uri+string;
       // System.out.println(url);
        try {
            URL httpurl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) httpurl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //connection.connect();
            out=new PrintWriter(connection.getOutputStream());
            out.print(string);
            out.flush();
            bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                result.append(line);
            }
            System.out.println(formatJson(result.toString()));
            //System.out.println(result);
        }catch (Exception e){
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }

        }
        return (formatJson(result.toString()));
    }

}
