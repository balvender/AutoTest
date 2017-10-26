import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * Created by lixuan on 2016/12/16.
 */
public class WmDbStudent {
    JdbcTemplate jdbcTemplate;
    public WmDbStudent(){
        jdbcTemplate= setDataSource("studentDataSource");
    }
    //获取JDBC的连接
    public JdbcTemplate setDataSource(String dataSource){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) ctx.getBean(dataSource));
        return jdbcTemplate;
    }
    public Integer selectfromid(String name){
        int id = 0;
        try {
            id = jdbcTemplate.queryForObject("SELECT a.id FROM test.Student as a WHERE a.name=?", new Object[]{name}, Integer.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;

    }
}
