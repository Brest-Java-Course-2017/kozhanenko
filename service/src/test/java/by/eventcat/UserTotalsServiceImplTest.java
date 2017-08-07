package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * UserTotals Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service-for-jpa-dao-impl.xml"})
@Transactional
public class UserTotalsServiceImplTest {

    private Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserTotalsService userTotalsService;

    @Test
    public void getUserTotals() throws Exception {
        LOGGER.debug("test: getUserTotals()");
        List<UserTotals> userTotals = userTotalsService.getUserTotals();
        assertTrue(userTotals.size() > 0);
    }

}
