package test;

import com.captain.config.MysqlDataSourceConfig;
import com.captain.config.OdpsDataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {MysqlDataSourceConfig.class, OdpsDataSourceConfig.class})
public class LandBizApplicationTests {


	@Test
	public void contextLoads() {
	}

}
