package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * CategoryService Implementation
 */
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<Category> getAllCategories() throws DataAccessException {
        return null;
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws DataAccessException {
        return null;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws DataAccessException {
        return null;
    }

    @Override
    public Integer addCategory(Category category) throws DataAccessException {
        return null;
    }

    @Override
    public int updateCategory(Category category) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteCategory(Integer categoryId) throws DataAccessException {
        return 0;
    }
}
