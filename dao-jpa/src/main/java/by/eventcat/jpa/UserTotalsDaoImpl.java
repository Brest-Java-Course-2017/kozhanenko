package by.eventcat.jpa;

import by.eventcat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * UserTotals Dao implementation
 */
public class UserTotalsDaoImpl implements UserTotalsDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserTotals> getUserTotals() throws DataAccessException {
        LOGGER.debug("getUserTotals()");
        List<UserTotals> totalData;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserTotals.class);
            totalData = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(totalData);
        return totalData;
    }

    @Override
    public UserTotals getValue(UserRole userRole, Locality city) throws DataAccessException {
        LOGGER.debug("getValue userRole={} city={}", userRole, city);
        UserTotals singleUserTotal;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserTotals.class);
            criteria.add(Restrictions.eq("userRole", userRole));
            if ( ! userRole.equals(UserRole.SUPER_ADMIN)){
                criteria.add(Restrictions.eq("city", city));
            }
            singleUserTotal = (UserTotals) criteria.uniqueResult();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(singleUserTotal);
        return singleUserTotal;
    }

    @Override
    public int setValue(UserRole userRole, Locality city, String operation) throws DataAccessException {
        UserTotals editingLine = getValue(userRole, city);

        if (operation.equals("increase")){
            //no such data before - add new UserTotals object
            if (editingLine == null){
                UserTotals addedData = new UserTotals(userRole, city, 1);

                Session session = sessionFactory.openSession();
                Transaction tx = null;
                try{
                    tx = session.beginTransaction();
                    session.save(addedData);
                    tx.commit();
                } catch(HibernateException ex){
                    if (tx!=null) tx.rollback();
                    if (ex instanceof ConstraintViolationException){
                        return 3;
                    }
                    return 5;
                } finally {
                    session.close();
                }
            } else {//such data existed before - +1 to count
                editingLine.setCount(editingLine.getCount() + 1);
                return updateUserTotalsLine(editingLine);
            }
        } else if (operation.equals("decrease")){
            if (editingLine == null){
                return 2;//error code
            } else {
                if (editingLine.getCount() - 1 == 0){//we do not hold in DB 0 counts
                    Session session = sessionFactory.openSession();
                    Transaction tx = null;
                    try{
                        tx = session.beginTransaction();
                        session.delete(editingLine);
                        tx.commit();
                    } catch(HibernateException ex){
                        if (tx!=null) tx.rollback();
                        return 5;
                        }
                } else {
                    editingLine.setCount(editingLine.getCount() - 1);
                    return updateUserTotalsLine(editingLine);
                }
            }
        }
        //TODO: in service layer check if operation value is "increase" or "decrease"
        return 1;
    }

    private int updateUserTotalsLine(UserTotals updatingObject){
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.update(updatingObject);
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
                return 5;
        } finally {
            session.close();
        }

        return 1;
    }
}
