package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public interface CategoryDao
 */
public interface CategoryDao {
    /**
     * Get all categories list
     *
     * @return list of existing categories
     * @throws DataAccessException
     */
    List<Category> getAllCategories() throws DataAccessException;

    /**
     * Gets category by identifier
     *
     * @param categoryId category identifier
     * @return category object
     * @throws DataAccessException
     */
    Category getCategoryById(Integer categoryId) throws DataAccessException;

    /**
     * Get category by its name
     * @param categoryName category name
     * @return category object
     * @throws DataAccessException
     */
    Category getCategoryByCategoryName(String categoryName) throws DataAccessException;

    /**
     * Get Categories list with number of events of those categories of certain time period
     *
     * @param beginOfInterval beginning of watching interval
     * @param endOfInterval end of watching interval
     * @return Categories list with number of events of those categories of certain time period
     * @throws DataAccessException
     */
    List<CategoryWithCount> getEventsCountForCertainTimeIntervalGroupByCategory
            (long beginOfInterval, long endOfInterval) throws DataAccessException;

    /**
     * Add new category
     *
     * @param category category object
     * @return new category identifier
     * @throws DataAccessException
     */

    Integer addCategory (Category category) throws DataAccessException;

    /**
     * Update category data
     *
     * @param category object of changing category
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateCategory (Category category) throws DataAccessException;

    /**
     * Delete category
     *
     * @param categoryId category identifier
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteCategory (Integer categoryId) throws DataAccessException;

}
