package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Category Service Implementation
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    //@Autowired
    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() throws DataAccessException, ServiceException {
        List<Category> categories = categoryDao.getAllCategories();
        if (categories.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return categories;
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws DataAccessException, ServiceException {
        if (categoryId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        Category category = categoryDao.getCategoryById(categoryId);
        if (category == null)  throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return category;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws DataAccessException, ServiceException {
        if(categoryName == null || categoryName.length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        }
        Category category;
        try{
            category = categoryDao.getCategoryByCategoryName(categoryName);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return category;
    }

    @Override
    public Integer addCategory(Category category) throws DataAccessException, ServiceException {
        if(category.getCategoryName() == null || category.getCategoryName().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int categoryId;
        try {
            categoryId = categoryDao.addCategory(category);
        } catch (DuplicateKeyException ex){
            throw new ServiceException(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
        }
        return categoryId;
    }

    @Override
    public int updateCategory(Category category) throws DataAccessException, ServiceException {
        if (category.getCategoryId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        if(category.getCategoryName() == null || category.getCategoryName().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int rowsAffected = categoryDao.updateCategory(category);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteCategory(Integer categoryId) throws DataAccessException, ServiceException {
        if (categoryId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = categoryDao.deleteCategory(categoryId);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }
}
