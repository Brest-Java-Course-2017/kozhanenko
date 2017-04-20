package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CategoryServiceImplMockTest
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service.xml"})
public class CategoryServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;

}
