package by.eventcat.rest;

import by.eventcat.CategoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Category Rest Controller Mock Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-rest-mock.xml"})
public class CategoryRestControllerMockTest {

    @Resource
    private CategoryRestController categoryController;

    private MockMvc mockMvc;

    @Autowired
    private CategoryService mockCategoryService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(categoryController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(mockCategoryService);
        reset(mockCategoryService);
    }

    @Test
    public void getCategories() throws Exception{

    }

    @Test
    public void getCategoriesCount() throws Exception{

    }

    @Test
    public void addCategory() throws Exception{

    }

    @Test
    public void updateCategory() throws Exception{

    }

    @Test
    public void deleteCategoryById() throws Exception{

    }

}
