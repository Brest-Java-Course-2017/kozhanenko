package by.eventcat.jpa;

import by.eventcat.User;
import by.eventcat.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.springframework.dao.DataAccessException;

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

    @Override
    public User getUserById(long userId) throws DataAccessException {
        return null;
    }

    @Override
    public User getUserByUserEmail(long userEmail) throws DataAccessException {
        return null;
    }

    @Override
    public Integer addNewUser(User user) throws DataAccessException {
        return null;
    }

    @Override
    public int updateUser(User user) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteUserById(Integer userId) throws DataAccessException {
        return 0;
    }
}
