package test;

import com.captain.config.MysqlDataSourceConfig;
import com.captain.entity.EventType;
import com.captain.land.mapper.EventTypeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {MysqlDataSourceConfig.class})
public class LandDalApplicationTests {

	@Resource
	private EventTypeMapper eventTypeMapper;

	@Test
	public void contextLoads() {
		EventType eventType = eventTypeMapper.selectByPrimaryKey(1);
		System.out.println(eventType.toString());
	}

}
