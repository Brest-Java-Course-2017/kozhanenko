package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.eventcat.jpa.TimeConverter.convertTimeFromStringToSeconds;
import static org.junit.Assert.*;

/**
 * Category service implementation test
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@ContextConfiguration(locations = {"classpath*:test-spring-service-for-jpa-dao-impl.xml"})
@Transactional
public class CategoryServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    private static final String CATEGORY_NAME_1 = "Театр";
    private static final String INCORRECT_CATEGORY_NAME_1 = "Т";
    private static final String INCORRECT_CATEGORY_NAME_2 = "Некорректное имя";
    private static final Category CATEGORY = new Category("Барды");
    private static final String BEGIN_TIME = "2017-03-01 00:00:00";
    private static final String END_TIME ="2017-04-01 00:00:00";
    private static final String BEGIN_TIME1 = "2099-03-01 00:00:00";
    private static final String END_TIME1 ="2099-04-01 00:00:00";


    @Test
    public void getAllCategories() throws Exception {
        LOGGER.debug("test: getAllCategories()");
        List<Category> categories = categoryService.getAllCategories();
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getCategoryById() throws Exception {
        LOGGER.debug("test: getCategoryById()");
        List<Category> categories = categoryService.getAllCategories();
        if (categories.size() > 0){
            int categoryId = categories.get(0).getCategoryId();
            String categoryName = categories.get(0).getCategoryName();
            Category category = categoryService.getCategoryById(categoryId);
            assertNotNull(category);
            assertEquals(categoryName, category.getCategoryName());
        }
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByIdWrongIndex() throws Exception {
        LOGGER.debug("test: getCategoryByIdWrongIndex()");
        try{
            categoryService.getCategoryById(0);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByIdNoCallingDataFound() throws Exception {
        LOGGER.debug("test: getCategoryByIdNoCallingDataFound()");
        try{
            categoryService.getCategoryById(999);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void getCategoryByCategoryName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryName()");
        List<Category> categories = categoryService.getAllCategories();
        if (categories.size() > 0){
            String categoryName = categories.get(0).getCategoryName();
            Category category = categoryService.getCategoryByCategoryName(categoryName);
            assertNotNull(category);
            assertEquals(categoryName, category.getCategoryName());
        }
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByCategoryNameIncorrectName() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryNameIncorrectName()");
        try{
            categoryService.getCategoryByCategoryName(INCORRECT_CATEGORY_NAME_1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test (expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getCategoryByCategoryNameNoDataFound() throws Exception {
        LOGGER.debug("test: getCategoryByCategoryNameIncorrectName()");

        try{
            categoryService.getCategoryByCategoryName(INCORRECT_CATEGORY_NAME_2);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void getEventsCountForCertainTimeIntervalGroupByCategory() throws Exception {
        LOGGER.debug("test: getEventsCountForCertainTimeIntervalGroupByCategory()");

        List<CategoryWithCount> categoriesWithCount = categoryService.getEventsCountForCertainTimeIntervalGroupByCategory(
                convertTimeFromStringToSeconds(BEGIN_TIME),
                convertTimeFromStringToSeconds(END_TIME)
        );
        assertTrue(categoriesWithCount.size() > 0);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getEventsCountForCertainTimeIntervalGroupByCategoryNoDataFound() throws Exception {
        LOGGER.debug("test: getEventsCountForCertainTimeIntervalGroupByCategoryNoDataFound()");

        try{
            categoryService.getEventsCountForCertainTimeIntervalGroupByCategory(
                    convertTimeFromStringToSeconds(BEGIN_TIME1),
                    convertTimeFromStringToSeconds(END_TIME1)
            );
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getEventsCountForCertainTimeIntervalGroupByCategoryIncorrectInputData() throws Exception {
        LOGGER.debug("test: getEventsCountForCertainTimeIntervalGroupByCategoryIncorrectInputData()");

        try{
            categoryService.getEventsCountForCertainTimeIntervalGroupByCategory(0, -4532452343563456L);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
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
    public void addCategoryIncorrectData() throws Exception {
        LOGGER.debug("test: addCategoryIncorrectData()");

        Category category = categoryService.getCategoryById(1);
        category.setCategoryName("G");
        try{
            categoryService.addCategory(category);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addDuplicateCategory() throws Exception {
        LOGGER.debug("test: addDuplicateCategory()");
        List<Category> categories = categoryService.getAllCategories();
        if (categories.size() > 0){
            Category duplicateCategory = new Category(categories.get(0).getCategoryName());
            try{
                categoryService.addCategory(duplicateCategory);
            } catch (ServiceException ex){
                assertEquals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED, ex.getCustomErrorCode());
                throw ex;
            }
        }
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
    public void updateCategoryDuplicateIndex() throws Exception {
        LOGGER.debug("test: updateCategoryDuplicateIndex()");

        Category category = categoryService.getCategoryById(2);
        category.setCategoryName(CATEGORY_NAME_1);
        try{
            categoryService.updateCategory(category);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateCategoryIncorrectIndex() throws Exception {
        LOGGER.debug("test: updateCategoryIncorrectIndex()");

        Category category = categoryService.getCategoryById(1);
        category.setCategoryId(-5);
        try{
            categoryService.updateCategory(category);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateCategoryWrongNewData() throws Exception {
        LOGGER.debug("test: updateCategoryWrongNewData()");

        Category category = categoryService.getCategoryById(1);
        category.setCategoryName("");
        try{
            categoryService.updateCategory(category);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateCategoryWithNonExistingIndex() throws Exception {
        LOGGER.debug("test: updateCategoryWrongNewData()");

        Category category = new Category(999, "some name");
        try{
            categoryService.updateCategory(category);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
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
    public void deleteCategoryIncorrectIndex() throws Exception {
        LOGGER.debug("test: deleteCategory() for service");
        try{
            categoryService.deleteCategory(-1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteCategoryWithNonExistingIndex() throws Exception {
        LOGGER.debug("test: deleteCategory() for service");

        try{
            categoryService.deleteCategory(100);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteUsedCategory() throws Exception {
        LOGGER.debug("test: deleteUsedCategory() for service");
        try{
            categoryService.deleteCategory(1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.DELETING_DATA_IS_IN_USE, ex.getCustomErrorCode());
            throw ex;
        }
    }

}