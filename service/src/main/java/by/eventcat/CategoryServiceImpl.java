package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

    private static final Logger LOGGER = LogManager.getLogger();

    //@Autowired
    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() throws DataAccessException, ServiceException {
        LOGGER.debug("getAllCategories()");

        List<Category> categories = categoryDao.getAllCategories();
        if (categories.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return categories;
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws DataAccessException, ServiceException {
        LOGGER.debug("getCategoryById({})", categoryId);

        Category category;
        if (categoryId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        try{
            category = categoryDao.getCategoryById(categoryId);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return category;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws DataAccessException, ServiceException {
        LOGGER.debug("getCategoryByCategoryName({})", categoryName);

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
        LOGGER.debug("addCategory(category): name = {}", category.getCategoryName());

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
        LOGGER.debug("update category {}", category);

        if (category.getCategoryId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        if(category.getCategoryName() == null || category.getCategoryName().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int rowsAffected;
        try{
            rowsAffected = categoryDao.updateCategory(category);
        } catch (DuplicateKeyException ex){
            throw new ServiceException(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
        }
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteCategory(Integer categoryId) throws DataAccessException, ServiceException {
        LOGGER.debug("delete category with categoryId = {}", categoryId);

        if (categoryId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = 0;
        try {
            rowsAffected = categoryDao.deleteCategory(categoryId);
        } catch (DataIntegrityViolationException ex){
            throw new ServiceException(CustomErrorCodes.DELETING_DATA_IS_USED);
        }
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }
}
