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
import static by.eventcat.TimeConverter.*;

/**
 * Tests for CategoryDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CategoryDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryDao categoryDao;

    private static final String CATEGORY_NAME_1 = "Театр";
    private static final String BEGIN_TIME = "2017-03-01 00:00:00";
    private static final String END_TIME ="2017-04-01 00:00:00";

    // sample category object for tests.
    private static final Category category = new Category("Барды");

    @Test
    public void getAllCategories() throws Exception {
        LOGGER.debug("test: getAllCategories()");
        List<Category> categories = categoryDao.getAllCategories();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getCategoryById() throws Exception {
        LOGGER.debug("test: getCategoryById()");
        Category category = categoryDao.getCategoryById(1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test
    public void getCategoryByCategoryName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryName()");
        Category category = categoryDao.getCategoryByCategoryName(CATEGORY_NAME_1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test
    public void getEventsCountForCertainTimeIntervalGroupByCategory() throws Exception{
        LOGGER.debug("test: getEventsCountForCertainTimeIntervalGroupByCategory()");
        List<CategoryWithCount> categoriesWithCount = categoryDao.getEventsCountForCertainTimeIntervalGroupByCategory(
                convertTimeFromStringToSeconds(BEGIN_TIME),
                convertTimeFromStringToSeconds(END_TIME)
        );
        assertTrue(categoriesWithCount.size() > 0);
    }

    @Test
    public void addCategory() throws Exception {
        LOGGER.debug("test: addCategory()");

        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        Integer categoryId = categoryDao.addCategory(category);
        assertNotNull(categoryId);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore + 1, categories.size());

        Category newCategory = categoryDao.getCategoryById(categoryId);
        assertNotNull(newCategory);
        assertEquals(category.getCategoryName(), newCategory.getCategoryName());
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void duplicateAddCategory() throws Exception{
        LOGGER.debug("test: duplicateAddCategory()");

        Category category = categoryDao.getCategoryById(1);
        assertNotNull(category);
        Integer categoryId = categoryDao.addCategory(category);
        assertNotNull(categoryId);
    }

    @Test
    public void updateCategory() throws Exception {
        LOGGER.debug("test: updateCategory()");

        Category category = categoryDao.getCategoryById(1);
        category.setCategoryName("Классическая музыка");

        int count = categoryDao.updateCategory(category);
        assertEquals(1, count);

        Category updatedCategory = categoryDao.getCategoryById(category.getCategoryId());
        assertTrue(category.getCategoryName().equals(updatedCategory.getCategoryName()));
    }

    @Test
    public void deleteCategory() throws Exception {
        LOGGER.debug("test: deleteCategory()");

        Integer categoryId = categoryDao.addCategory(category);
        assertNotNull(categoryId);

        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        int count = categoryDao.deleteCategory(categoryId);
        assertEquals(1, count);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore - 1, categories.size());
    }

    @Test
    public void deleteCategoryWrongIndex() throws Exception {
        LOGGER.debug("test: deleteCategoryWrongIndex()");

        List<Category> categories = categoryDao.getAllCategories();
        int quantityBefore = categories.size();

        int count = categoryDao.deleteCategory(-1);
        assertEquals(0, count);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore, categories.size());
    }
}