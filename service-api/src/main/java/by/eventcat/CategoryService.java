package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Category service
 */
public interface CategoryService {
    /**
     * Get all categories
     *
     * @return all categories list
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<Category> getAllCategories() throws DataAccessException, ServiceException;

    /**
     * Gets category by identifier
     *
     * @param categoryId category identifier
     * @return category object
     * @throws DataAccessException
     *
     */
    Category getCategoryById(Integer categoryId) throws DataAccessException, ServiceException;

    /**
     * Get category by its name
     * @param categoryName category name
     * @return category object
     * @throws DataAccessException
     */
    Category getCategoryByCategoryName(String categoryName) throws DataAccessException, ServiceException;

    /**
     * Get Categories list with number of events of those categories of certain time period
     *
     * @param beginOfInterval beginning of watching interval
     * @param endOfInterval end of watching interval
     * @return Categories list with number of events of those categories of certain time period
     * @throws DataAccessException
     */
    List<CategoryWithCount> getEventsCountForCertainTimeIntervalGroupByCategory
    (long beginOfInterval, long endOfInterval) throws DataAccessException, ServiceException;

    /**
     * Add new category
     *
     * @param category category object
     * @return new category identifier
     * @throws DataAccessException
     */
    Integer addCategory (Category category) throws DataAccessException, ServiceException;

    /**
     * Update category data
     *
     * @param category object of changing category
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateCategory (Category category) throws DataAccessException, ServiceException;

    /**
     * Delete category
     *
     * @param categoryId category identifier
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteCategory (Integer categoryId) throws DataAccessException, ServiceException;
}
