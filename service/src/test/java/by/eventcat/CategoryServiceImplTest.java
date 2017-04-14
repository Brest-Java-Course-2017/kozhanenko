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

import static org.junit.Assert.*;

/**
 * Category service implementation test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class CategoryServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryServiceImpl categoryService;

    private static final String CATEGORY_NAME_1 = "Театр";
    private static final String INCORRECT_CATEGORY_NAME_1 = "Т";
    private static final String INCORRECT_CATEGORY_NAME_2 = "Некорректное имя";
    private static final Category CATEGORY = new Category("Барды");
    private static final Category DUPLICATE_CATEGORY = new Category(CATEGORY_NAME_1);

    @Test
    public void getAllCategories() throws Exception {
        LOGGER.debug("test: getAllCategories()");
        List<Category> categories = categoryService.getAllCategories();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getCategoryById() throws Exception {
        LOGGER.debug("test: getCategoryById()");
        Category category = categoryService.getCategoryById(1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByIdWrongIndex() throws Exception {
        LOGGER.debug("test: getCategoryByIdWrongIndex()");
        categoryService.getCategoryById(0);
    }

    @Test
    public void getCategoryByCategoryName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryName()");
        Category category = categoryService.getCategoryByCategoryName(CATEGORY_NAME_1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByCategoryNameIncorrectName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryNameIncorrectName()");
        categoryService.getCategoryByCategoryName(INCORRECT_CATEGORY_NAME_1);
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByCategoryNameNoDataFound() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryNameIncorrectName()");
        categoryService.getCategoryByCategoryName(INCORRECT_CATEGORY_NAME_2);
    }

    @Test
    public void addCategory() throws Exception {
        LOGGER.debug("test: addCategory()");

        List<Category> categories = categoryService.getAllCategories();
        Integer quantityBefore = categories.size();

        Integer categoryId = categoryService.addCategory(CATEGORY);
        assertNotNull(categoryId);
        categories = categoryService.getAllCategories();
        assertEquals(quantityBefore + 1, categories.size());

        Category newCategory = categoryService.getCategoryById(categoryId);
        assertNotNull(newCategory);
        assertEquals(CATEGORY.getCategoryName(), newCategory.getCategoryName());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addDuplicateCategory() throws Exception {
        LOGGER.debug("test: addDuplicateCategory()");
        categoryService.addCategory(DUPLICATE_CATEGORY);
    }

    @Test
    public void updateCategory() throws Exception {
        LOGGER.debug("test: updateCategory()");

        Category category = categoryService.getCategoryById(1);
        category.setCategoryName("НЕКлассическая музыка");

        int count = categoryService.updateCategory(category);
        assertEquals(1, count);

        Category updatedCategory = categoryService.getCategoryById(category.getCategoryId());
        assertTrue(category.getCategoryName().equals(updatedCategory.getCategoryName()));
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateCategoryWrongNewData() throws Exception {
        LOGGER.debug("test: updateCategoryWrongNewData()");

        Category category = categoryService.getCategoryById(1);
        category.setCategoryName("");
        categoryService.updateCategory(category);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateCategoryWithNonExistingIndex() throws Exception {
        LOGGER.debug("test: updateCategoryWrongNewData()");

        Category category = new Category(999, "some name");
        categoryService.updateCategory(category);
    }

    @Test
    public void deleteCategory() throws Exception {
        LOGGER.debug("test: deleteCategory() for service");

        Integer addedCategoryId = categoryService.addCategory(CATEGORY);
        assertNotNull(addedCategoryId);
        List<Category> categories = categoryService.getAllCategories();
        Integer quantityBefore = categories.size();
        int count = categoryService.deleteCategory(addedCategoryId);
        assertEquals(1, count);
        categories = categoryService.getAllCategories();
        assertEquals(quantityBefore - 1, categories.size());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteCategoryWithNonExistingIndex() throws Exception {
        LOGGER.debug("test: deleteCategory() for service");
        categoryService.deleteCategory(100);
    }
}