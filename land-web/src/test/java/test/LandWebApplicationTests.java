package test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.captain.LandWebApplication;
import com.captain.land.biz.DemoService;
import com.captain.land.controller.DemoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static java.nio.charset.StandardCharsets.UTF_8;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LandWebApplication.class)
public class LandWebApplicationTests {

	@Resource
	private DemoService demoService;
	@Resource
	private DemoController demoController;

//	@Autowired
//	@Qualifier("mysqlJdbcTemplate")
//	private JdbcTemplate mysqlJdbcTemplate;
//
//	@Autowired
//	@Qualifier("odpsJdbcTemplate")
//	private JdbcTemplate odpsJdbcTemplate;

	/***
	 * 通过api方式创建logger，这样可以得到特定的logger来记录日志
	 */
//	@Test
//	public void getLog(){
//		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//		Logger actionLogger = loggerContext.getLogger("actionLogger");
////		actionLogger.detachAndStopAllAppenders();
//
//		String logPath = "../logs/land-logs/action.log";
//		//appender
//		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
//		rollingFileAppender.setContext(loggerContext);
//		rollingFileAppender.setName("actionLog");
//		rollingFileAppender.setFile(logPath);
//		rollingFileAppender.setAppend(true);
//
//		//policy
//		TimeBasedRollingPolicy<ILoggingEvent> timePolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
//		timePolicy.setContext(loggerContext);
//		timePolicy.setMaxHistory(2);
//		timePolicy.setFileNamePattern(logPath+"/action.%d{yyyy-MM-dd}.log");
//		timePolicy.setParent(rollingFileAppender);
//		timePolicy.start();
//		rollingFileAppender.setRollingPolicy(timePolicy);
//
//		//encoder
//		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//		encoder.setContext(loggerContext);
//		encoder.setPattern("[%d{'MM-dd HH:mm:ss,SSS',GMT+8:00}] %level [%thread] - [%c.%M][%line] - %msg%n");
//		encoder.setCharset(UTF_8);
//		encoder.start();
//		rollingFileAppender.setEncoder(encoder);
//
//		rollingFileAppender.start();
//
//		actionLogger.addAppender(rollingFileAppender);
//		actionLogger.setLevel(Level.toLevel("INFO"));
//		actionLogger.setAdditive(false);
//
//		actionLogger.info("actionLogger init");
//	}
//
//	@Test
//	public void testJdbcTemplate(){
//		Integer getCount = odpsJdbcTemplate.queryForObject("select count(*) from action_log_real where action_date = 20171205", Integer.class);
//		System.out.println(getCount);
//	}
//
//	@Test
//	public void demoServiceTest(){
////		System.out.println(demoService.getCount());
//	}
//
//	@Test
//	public void demoControllerTest(){
////		System.out.println(demoController.getCount());
//	}

}
