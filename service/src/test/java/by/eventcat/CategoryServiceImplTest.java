package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Category service implementation test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class CategoryServiceImplTest {
    @Test
    public void getAllCategories() throws Exception {

    }

    @Test
    public void getCategoryById() throws Exception {

    }

    @Test
    public void getCategoryByCategoryName() throws Exception {

    }

    @Test
    public void addCategory() throws Exception {

    }

    @Test
    public void updateCategory() throws Exception {

    }

    @Test
    public void deleteCategory() throws Exception {

    }

}