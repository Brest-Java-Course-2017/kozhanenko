package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for CategoryDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CategoryDaoImplTest {

    @Autowired
    private CategoryDao categoryDao;

    private static final String CATEGORY_NAME_1 = "Театр";

    // sample category object for tests.
    private static final Category category = new Category("Барды");

    @Test
    public void getAllCategories() throws Exception {
        List<Category> categories = categoryDao.getAllCategories();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getCategoryById() throws Exception {
        Category category = categoryDao.getCategoryById(1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test
    public void getCategoryByCategoryName() throws Exception {
        Category category = categoryDao.getCategoryByCategoryName(CATEGORY_NAME_1);
        assertNotNull(category);
        assertEquals(CATEGORY_NAME_1, category.getCategoryName());
    }

    @Test
    public void addCategory() throws Exception {
        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        Integer categoryId = categoryDao.addCategory(category);
        assertNotNull(categoryId);

        Category newCategory = categoryDao.getCategoryById(categoryId);
        assertNotNull(newCategory);
        assertTrue(category.getCategoryName().equals(newCategory.getCategoryName()));

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore + 1, categories.size());
    }

    //TODO: test adding dublicate category

    @Test
    public void updateCategory() throws Exception {
        Category category = categoryDao.getCategoryById(1);
        category.setCategoryName("Классическая музыка");

        int count = categoryDao.updateCategory(category);
        assertEquals(1, count);

        Category updatedCategory = categoryDao.getCategoryById(category.getCategoryId());
        assertTrue(category.getCategoryName().equals(updatedCategory.getCategoryName()));
    }

    @Test
    public void deleteCategory() throws Exception {
        Integer categoryId = categoryDao.addCategory(category);
        assertNotNull(categoryId);

        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        int count = categoryDao.deleteCategory(categoryId);
        assertEquals(1, count);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore - 1, categories.size());
    }
}