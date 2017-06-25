package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.CategoryDao;
import by.eventcat.CategoryWithCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static by.eventcat.jpa.TimeConverter.*;


/**
 * Category Dao JPA Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class CategoryDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryDao categoryDao;

    private static final String CATEGORY_NAME =  "Театр";
    private static final String CATEGORY_NAME1 =  "Дискотеки";
    private static final String WRONG_CATEGORY_NAME =  "Ярмарка";
    private static final Category CATEGORY =  new Category("Барды");
    private static final Category DUPLICATE_CATEGORY =  new Category(2, "Театр");
    private static final Category NON_EXISTING_CATEGORY =  new Category(99,"Барды");
    private static final Category CATEGORY_TO_DELETE =  new Category("Детские");
    private static final Category EXISTING_CATEGORY =  new Category(CATEGORY_NAME);
    private static final String BEGIN_TIME = "2017-03-01 00:00:00";
    private static final String END_TIME ="2017-04-01 00:00:00";

    @Test
    public void getAllCategories() throws Exception{
        LOGGER.debug("test: getAllCategories()");
        List<Category> categories = categoryDao.getAllCategories();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getCategoryById() throws Exception {
        LOGGER.debug("test: getCategoryById()");
        Category category = categoryDao.getCategoryById(1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME, category.getCategoryName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getCategoryByIdDataNotFound() throws Exception {
        LOGGER.debug("test: getCategoryByIdDataNotFound()");
         categoryDao.getCategoryById(999);
    }

    @Test
    public void getCategoryByCategoryName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryName()");
        Category category = categoryDao.getCategoryByCategoryName(CATEGORY_NAME);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME, category.getCategoryName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getCategoryByCategoryNameNoSuchCategory() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryName()");
        categoryDao.getCategoryByCategoryName(WRONG_CATEGORY_NAME);
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

        Integer categoryId = categoryDao.addCategory(CATEGORY);
        assertNotNull(categoryId);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore + 1, categories.size());

        Category newCategory = categoryDao.getCategoryById(categoryId);
        assertEquals(CATEGORY.getCategoryName(), newCategory.getCategoryName());
        assertNotNull(newCategory);
    }

    @Test(expected = DuplicateKeyException.class)
    public void addCategoryDuplicateCategoryName() throws Exception {
        LOGGER.debug("test: addCategoryDuplicateCategoryName()");
        categoryDao.addCategory(EXISTING_CATEGORY);
    }

    @Test
    public void updateCategory() throws Exception {
        LOGGER.debug("test: updateCategory()");

        Category category = categoryDao.getCategoryById(2);
        category.setCategoryName(CATEGORY_NAME1);

        int count = categoryDao.updateCategory(category);
        assertEquals(1, count);

        Category updatedCategory = categoryDao.getCategoryById(category.getCategoryId());
        assertTrue(category.getCategoryName().equals(updatedCategory.getCategoryName()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void updateCategoryDuplicateCategoryName() throws Exception {
        LOGGER.debug("test: updateCategoryDuplicateCategoryName()");
        categoryDao.updateCategory(DUPLICATE_CATEGORY);
    }

    @Test
    public void updateNonExistingCategory() throws Exception {
        LOGGER.debug("test: updateCategoryDuplicateCategoryName()");
        int rowsAffected = categoryDao.updateCategory(NON_EXISTING_CATEGORY);
        assertEquals(0, rowsAffected);
    }

    @Test
    public void deleteCategory() throws Exception {
        LOGGER.debug("test: deleteCategory()");

        Integer categoryId = categoryDao.addCategory(CATEGORY_TO_DELETE);
        assertTrue(categoryId > 0);

        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        int count = categoryDao.deleteCategory(categoryId);
        assertEquals(1, count);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore - 1, categories.size());
    }

    @Test
    public void deleteNonExistingCategory() throws Exception {
        LOGGER.debug("test: deleteNonExistingCategory()");
        int rowsAffected = categoryDao.deleteCategory(99);
        assertEquals(0, rowsAffected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteCategoryDataIntegrityViolationException() throws Exception {
        LOGGER.debug("test: deleteCategoryDataIntegrityViolationException()");
        categoryDao.deleteCategory(1);
    }

}
