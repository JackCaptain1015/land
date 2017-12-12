package adbaitai;

import com.adbaitai.config.MysqlDataSourceConfig;
import com.adbaitai.config.OdpsDataSourceConfig;
import com.adbaitai.land.biz.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {MysqlDataSourceConfig.class, OdpsDataSourceConfig.class})
public class LandBizApplicationTests {


	@Test
	public void contextLoads() {
	}

}
