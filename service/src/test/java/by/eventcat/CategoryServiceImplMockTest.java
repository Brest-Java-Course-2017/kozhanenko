package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

/**
 * CategoryServiceImplMockTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service.xml"})
public class CategoryServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao mockCategoryDao;

    private static final Category CATEGORY = new Category(1, "Вечеринки");
    private static final Category CATEGORY1 = new Category(2, "Театр");
    private static final List<Category> CATEGORIES_LIST = new ArrayList<>();
    private static final List<Category> CATEGORIES_LIST1 = new ArrayList<>();
    private static final CategoryWithCount CATEGORY_WITH_COUNT = new CategoryWithCount(CATEGORY, 3);
    private static final CategoryWithCount CATEGORY_WITH_COUNT1 = new CategoryWithCount(CATEGORY1, 5);
    private static final List<CategoryWithCount> CATEGORIES_WITH_COUNT = new ArrayList<>();
    private static final List<CategoryWithCount> CATEGORIES_WITH_COUNT1 = new ArrayList<>();
    private static final long BEGINNING = 123412314L;
    private static final long END = 135223452345L;


    static {
        CATEGORIES_LIST.add(CATEGORY);
        CATEGORIES_LIST.add(CATEGORY1);
        CATEGORIES_WITH_COUNT.add(CATEGORY_WITH_COUNT);
        CATEGORIES_WITH_COUNT.add(CATEGORY_WITH_COUNT1);
    }

    @After
    public void clean() {
        verify(mockCategoryDao);
        reset(mockCategoryDao);
    }

    @Test
    public void getAllCategories() throws Exception {
        LOGGER.debug("mockTest: getAllCategories()");
        expect(mockCategoryDao.getAllCategories()).andReturn(CATEGORIES_LIST);
        replay(mockCategoryDao);
        List<Category> categories = categoryService.getAllCategories();
        Assert.isTrue(categories.size() > 0);
    }

    @Test(expected = ServiceException.class)
    public void getAllCategoriesWithException() throws Exception {
        LOGGER.debug("mockTest: getAllCategoriesWithException()");
        expect(mockCategoryDao.getAllCategories()).andReturn(CATEGORIES_LIST1);
        replay(mockCategoryDao);
        try{
            categoryService.getAllCategories();
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void getEventsCountForCertainTimeIntervalGroupByCategory() throws Exception {
        LOGGER.debug("mockTest: getEventsCountForCertainTimeIntervalGroupByCategory()");
        expect(mockCategoryDao.getEventsCountForCertainTimeIntervalGroupByCategory(BEGINNING, END)).
                andReturn(CATEGORIES_WITH_COUNT);
        replay(mockCategoryDao);
        List<CategoryWithCount> categoriesWithCount = categoryService
                .getEventsCountForCertainTimeIntervalGroupByCategory(BEGINNING, END);
        Assert.isTrue(categoriesWithCount.size() > 0);
    }

    @Test(expected = ServiceException.class)
    public void getEventsCountForCertainTimeIntervalGroupByCategoryNoDataFound() throws Exception {
        LOGGER.debug("mockTest: getEventsCountForCertainTimeIntervalGroupByCategoryNoDataFound()");
        expect(mockCategoryDao.getEventsCountForCertainTimeIntervalGroupByCategory(BEGINNING, END)).
                andReturn(CATEGORIES_WITH_COUNT1);
        replay(mockCategoryDao);
        try{
            categoryService.getEventsCountForCertainTimeIntervalGroupByCategory(BEGINNING, END);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void addCategory() throws Exception {
        LOGGER.debug("mockTest: addCategory()");
        expect(mockCategoryDao.addCategory(CATEGORY)).andReturn(5);
        replay(mockCategoryDao);
        int newCategoryId = categoryService.addCategory(CATEGORY);
        Assert.isTrue(newCategoryId == 5);
    }

    @Test(expected = ServiceException.class)
    public void addCategoryDuplicateKey() throws Exception{
        LOGGER.debug("mockTest: addCategoryDuplicateKey()");
        expect(mockCategoryDao.addCategory(CATEGORY1)).andThrow(new DuplicateKeyException(""));
        replay(mockCategoryDao);
        try{
            categoryService.addCategory(CATEGORY1);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED));
            throw ex;
        }
    }

    @Test
    public void updateCategory() throws Exception {
        LOGGER.debug("mockTest: updateCategory()");
        expect(mockCategoryDao.updateCategory(CATEGORY)).andReturn(1);
        replay(mockCategoryDao);
        int rowsAffected = categoryService.updateCategory(CATEGORY);
        Assert.isTrue(rowsAffected == 1);
    }

    @Test(expected = ServiceException.class)
    public void updateCategoryDuplicateData() throws Exception {
        LOGGER.debug("mockTest: updateCategoryDuplicateData()");
        expect(mockCategoryDao.updateCategory(CATEGORY)).andThrow(new DuplicateKeyException(""));
        replay(mockCategoryDao);
        try{
            categoryService.updateCategory(CATEGORY);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void updateCategoryNoActionsMade() throws Exception {
        LOGGER.debug("mockTest: updateCategoryNoActionsMade()");
        expect(mockCategoryDao.updateCategory(CATEGORY)).andReturn(0);
        replay(mockCategoryDao);
        try{
            categoryService.updateCategory(CATEGORY);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void updateCategoryProgramError() throws Exception {
        LOGGER.debug("mockTest: updateCategoryProgramError()");
        expect(mockCategoryDao.updateCategory(CATEGORY)).andReturn(5);
        replay(mockCategoryDao);
        try{
            categoryService.updateCategory(CATEGORY);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR));
            throw ex;
        }
    }

    @Test
    public void deleteCategory() throws Exception {
        LOGGER.debug("mockTest: deleteCategory()");
        expect(mockCategoryDao.deleteCategory(1)).andReturn(1);
        replay(mockCategoryDao);
        int rowsAffected = categoryService.deleteCategory(1);
        Assert.isTrue(rowsAffected == 1);
    }

    @Test(expected = ServiceException.class)
    public void deleteCategoryIntegrityViolation() throws Exception {
        LOGGER.debug("mockTest: deleteCategoryIntegrityViolation()");
        expect(mockCategoryDao.deleteCategory(1)).andThrow(new DataIntegrityViolationException(""));
        replay(mockCategoryDao);
        try{
            categoryService.deleteCategory(1);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.DELETING_DATA_IS_IN_USE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteCategoryNoActionsMade() throws Exception {
        LOGGER.debug("mockTest: deleteCategoryNoActionsMade()");
        expect(mockCategoryDao.deleteCategory(1)).andReturn(0);
        replay(mockCategoryDao);
        try{
            categoryService.deleteCategory(1);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteCategoryProgramError() throws Exception {
        LOGGER.debug("mockTest: deleteCategoryProgramError()");
        expect(mockCategoryDao.deleteCategory(1)).andReturn(4);
        replay(mockCategoryDao);
        try{
            categoryService.deleteCategory(1);
        } catch (ServiceException ex){
            Assert.isTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR));
            throw ex;
        }
    }


}
