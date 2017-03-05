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
    List<Category> getAllCaterories() throws DataAccessException;

    /**
     *
     * @param categoryId category identifier
     * @return category object
     * @throws DataAccessException
     */
    Category getCategoryById(Integer categoryId) throws DataAccessException;

    /**
     *
     * @param categoryName category name
     * @return category object
     * @throws DataAccessException
     */
    Category getCategoryByCategoryName(String categoryName) throws DataAccessException;

    /**
     *
     * @param category category object
     * @return new category identifier
     * @throws DataAccessException
     */
    Integer addCategory (Category category) throws DataAccessException;

    /**
     *
     * @param category object of changing category
     * @return
     * @throws DataAccessException
     */
    int updateCategory (Category category) throws DataAccessException;
    //TODO: what returns???

    /**
     *
     * @param categoryId category identifier
     * @return
     * @throws DataAccessException
     */
    int deleteCategory (Integer categoryId) throws DataAccessException;
    //TODO: maybe it returns void???

}
