package by.eventcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Locale;

/**
 * Category Service Implementation
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() throws DataAccessException {
        return categoryDao.getAllCategories();
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
