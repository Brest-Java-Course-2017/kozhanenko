package by.eventcat.jpa;

import by.eventcat.TimePeriodDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * TimePeriod JPA Dao Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class TimePeriodDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TimePeriodDao timePeriodDao;



}
