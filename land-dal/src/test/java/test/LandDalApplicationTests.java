package test;

import com.captain.config.MysqlDataSourceConfig;
import com.captain.entity.EventType;
import com.captain.land.mapper.EventTypeMapper;
import com.captain.template.TableSpider;
import com.captain.template.segment.FieldSegment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = {MysqlDataSourceConfig.class})
public class LandDalApplicationTests {

	@Resource
	private EventTypeMapper eventTypeMapper;
	@Resource
	private TableSpider tableSpider;
	@Resource
	private FieldSegment fieldSegment;
	@Test
	public void contextLoads() {
		EventType eventType = eventTypeMapper.selectByPrimaryKey(1);
		System.out.println(eventType.toString());
	}

	@Test
	public void templateTest() throws SQLException {

	}

}
