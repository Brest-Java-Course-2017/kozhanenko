package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.CategoryDao;
import by.eventcat.CategoryWithCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CategoryDao JPA Implementation
 */
@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public List<Category> getAllCategories() throws DataAccessException {
        LOGGER.debug("getAllCategories()");
        List<Category> categories;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            categories = session.createCriteria(Category.class).list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        return categories;
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws DataAccessException {
        LOGGER.debug("getCategoryById where ID={}", categoryId);
        Category category;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            category = (Category) session.get(Category.class, categoryId);
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        if (category == null){
            throw new EmptyResultDataAccessException(1);
        }
        return category;
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws DataAccessException {
        LOGGER.debug("getCategoryByCategoryName where categoryName={}", categoryName);
        Category category;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Category.class);
            category = (Category) criteria.add(Restrictions.eq("categoryName", categoryName))
                    .uniqueResult();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        if (category == null){
            throw new EmptyResultDataAccessException(1);
        }
        return category;
    }

    @Override
    public List<CategoryWithCount> getEventsCountForCertainTimeIntervalGroupByCategory(long beginOfInterval, long endOfInterval)
            throws DataAccessException {
        return null;
    }

    @Override
    public Integer addCategory(Category category) throws DataAccessException {
        LOGGER.debug("addCategory with categoryName={}", category.getCategoryName());
        Integer newCategoryId;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            newCategoryId = (Integer) session.save(category);
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DuplicateKeyException("");
            }
            throw ex;
        } finally {
            session.close();
        }
        return newCategoryId;
    }

    @Override
    public int updateCategory(Category category) throws DataAccessException {
        LOGGER.debug("updateCategory with categoryId={}", category.getCategoryId());
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(category);
            tx.commit();
            rowsAffected = 1;
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DuplicateKeyException("");
            } else if (ex instanceof StaleStateException){
                rowsAffected = 0;
            } else {
                throw ex;
            }
        } finally {
            session.close();
        }
        return rowsAffected;
    }

    @Override
    public int deleteCategory(Integer categoryId) throws DataAccessException {
        LOGGER.debug("deleteCategory with ID={}", categoryId);
        int rowsAffected = 0;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Category category =
                    (Category)session.get(Category.class, categoryId);
            if (category == null){
                rowsAffected = 0;
            } else{
                session.delete(category);
                rowsAffected = 1;
            }
            tx.commit();

        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DataIntegrityViolationException("");
            }
            throw ex;
        } finally {
            session.close();
        }
        return rowsAffected;
    }
}
