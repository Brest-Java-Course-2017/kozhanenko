package by.eventcat.rest;

import by.eventcat.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    @Value("${category_added}")
    private String categoryAdded;

    @Value("${category_updated}")
    private String categoryUpdated;

    @Value("${category_deleted}")
    private String categoryDeleted;

    private static final Category CATEGORY = new Category(1, "Theater");
    private static final Category CATEGORY1 = new Category(2, "Cinema");
    private static final List<Category> CATEGORIES = new ArrayList<>();
    private static final CategoryWithCount CATEGORY_WITH_COUNT = new CategoryWithCount(CATEGORY, 2);
    private static final CategoryWithCount CATEGORY_WITH_COUNT1 = new CategoryWithCount(CATEGORY1, 5);
    private static final List<CategoryWithCount> CATEGORIES_WITH_COUNT = new ArrayList<>();

    static {
        CATEGORIES.add(CATEGORY);
        CATEGORIES.add(CATEGORY1);
        CATEGORIES_WITH_COUNT.add(CATEGORY_WITH_COUNT);
        CATEGORIES_WITH_COUNT.add(CATEGORY_WITH_COUNT1);
    }

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
        expect(mockCategoryService.getAllCategories()).andReturn(CATEGORIES);
        replay(mockCategoryService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", CATEGORIES);

        mockMvc.perform(
                get("/categories")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
    }

    @Test
    public void getCategoriesCount() throws Exception{
        expect(mockCategoryService.getEventsCountForCertainTimeIntervalGroupByCategory(
                eq(2345324523L), eq(23412341234L)))
                .andReturn(CATEGORIES_WITH_COUNT);
        replay(mockCategoryService);

        Map<String, List<CategoryWithCount>> responseBody = new HashMap<>();
        responseBody.put("data", CATEGORIES_WITH_COUNT);

        mockMvc.perform(
                get("/categories/2345324523/23412341234")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
    }

    @Test
    public void addCategory() throws Exception{
        expect(mockCategoryService.addCategory(anyObject(Category.class))).andReturn(5);
        replay(mockCategoryService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", categoryAdded);
        responseBody.put("data", 5);

        mockMvc.perform(
                post("/category")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(CATEGORY))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
    }

    @Test
    public void updateCategory() throws Exception{
        expect(mockCategoryService.updateCategory(anyObject(Category.class))).andReturn(1);
        replay(mockCategoryService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", categoryUpdated);

        mockMvc.perform(
                put("/category/1/OpenAir")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
    }

    @Test
    public void deleteCategoryById() throws Exception{
        expect(mockCategoryService.deleteCategory(anyObject(Integer.class))).andReturn(1);
        replay(mockCategoryService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", categoryDeleted);

        mockMvc.perform(
                delete("/category/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
    }

}
