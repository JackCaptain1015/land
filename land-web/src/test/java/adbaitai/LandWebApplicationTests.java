package adbaitai;

import com.adbaitai.LandWebApplication;
import com.adbaitai.land.biz.DemoService;
import com.adbaitai.land.controller.DemoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LandWebApplication.class)
public class LandWebApplicationTests {

	@Resource
	private DemoService demoService;
	@Resource
	private DemoController demoController;

	@Autowired
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate mysqlJdbcTemplate;

	@Autowired
	@Qualifier("odpsJdbcTemplate")
	private JdbcTemplate odpsJdbcTemplate;

	@Test
	public void testJdbcTemplate(){
		Integer getCount = odpsJdbcTemplate.queryForObject("select count(*) from action_log_real where action_date = 20171205", Integer.class);
		System.out.println(getCount);
	}

	@Test
	public void demoServiceTest(){
		System.out.println(demoService.getCount());
	}

	@Test
	public void demoControllerTest(){
		System.out.println(demoController.getCount());
	}

}
