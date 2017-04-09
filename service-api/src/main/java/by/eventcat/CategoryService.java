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
