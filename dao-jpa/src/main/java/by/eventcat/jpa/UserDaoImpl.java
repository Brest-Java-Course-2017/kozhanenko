package by.eventcat.jpa;

import by.eventcat.User;
import by.eventcat.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

/**
 * User Dao implementation
 */
public class UserDaoImpl implements UserDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() throws DataAccessException {
        LOGGER.debug("getAllUsers()");
        List<User> users;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            users = criteria.list();
            for(User user: users){
                Hibernate.initialize(user.getLocalities());
                Hibernate.initialize(user.getPlacesAvailable());
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(users);
        return users;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsersByLocationPermission(String cityName) throws DataAccessException {
        LOGGER.debug("getAllUsersByLocationPermission cityName={}", cityName);
        List<User> users;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.createAlias("localities", "localitiesAlias");
            criteria.add(Restrictions.eq("localitiesAlias.cityName", cityName));
            users = criteria.list();
            for(User user: users){
                Hibernate.initialize(user.getLocalities());
                Hibernate.initialize(user.getPlacesAvailable());
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public User getUserById(long userId) throws DataAccessException {
        LOGGER.debug("getUserById where ID={}", userId);
        User user;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            user = (User) session.get(User.class, userId);
            if (user != null){
                Hibernate.initialize(user.getLocalities());
                Hibernate.initialize(user.getPlacesAvailable());
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        if (user == null){
            throw new EmptyResultDataAccessException(1);
        }
        LOGGER.debug("user={}", user);
        return user;
    }

    @Override
    public User getUserByUserEmail(String userEmail) throws DataAccessException {
        LOGGER.debug("getUserByUserEmail={}", userEmail);
        User user;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            user = (User) criteria.add(Restrictions.eq("userEmail", userEmail))
                    .uniqueResult();
            if (user != null){
                Hibernate.initialize(user.getLocalities());
                Hibernate.initialize(user.getPlacesAvailable());
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        if (user == null){
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }

    @Override
    public Long addNewUser(User user) throws DataAccessException {
        LOGGER.debug("addNewUser={}", user.getUserEmail());
        Long newUserId;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            newUserId = (Long)session.save(user);
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
        return newUserId;
    }

    @Override
    public int updateUser(User user) throws DataAccessException {
        LOGGER.debug("updateUser with userId={}", user.getUserId());
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(user);
            rowsAffected = 1;
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DuplicateKeyException("");
            }
            if (ex instanceof StaleStateException){
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
    public int deleteUserById(Long userId) throws DataAccessException {
        LOGGER.debug("deleteUserById with ID={}", userId);
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user =
                    (User)session.get(User.class, userId);
            if (user == null){
                rowsAffected = 0;
            } else{
                session.delete(user);
                rowsAffected = 1;
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                //TODO: implement when User is in use in users_event_place_correlation table
                throw new DataIntegrityViolationException("");
            } else {
                throw ex;
            }
        } finally {
            session.close();
        }
        return rowsAffected;
    }
}
