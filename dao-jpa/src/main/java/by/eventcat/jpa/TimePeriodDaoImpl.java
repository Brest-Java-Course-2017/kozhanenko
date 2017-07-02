package by.eventcat.jpa;

import by.eventcat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static by.eventcat.jpa.TimeConverter.*;

/**
 * TimePeriod JPA Dao Implementation
 */
@Repository
@Transactional
public class TimePeriodDaoImpl implements TimePeriodDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException {
        LOGGER.debug("getTimePeriodById where ID={}", timePeriodId);
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        TimePeriod timePeriod;
        try{
            tx = session.beginTransaction();
            timePeriod =  (TimePeriod)session.get(TimePeriod.class, timePeriodId);
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        if (timePeriod == null){
            throw new EmptyResultDataAccessException(1);
        }
        timePeriod.setLongFields();
        return timePeriod;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimePeriod> getTimePeriodListOfCertainEventByEventId(Event event) throws DataAccessException {
        LOGGER.debug("getTimePeriodListOfCertainEventByEventId() where eventId={}", event.getEventId());
        List<TimePeriod> timePeriods;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimePeriod.class, "timePeriod");
            criteria.createAlias("timePeriod.event", "event");
            criteria.add(Restrictions.eq("event.eventId", event.getEventId()));
            timePeriods = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        for (TimePeriod timePeriod: timePeriods){
            timePeriod.setLongFields();
        }
        LOGGER.debug(timePeriods);
        return timePeriods;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException {
        LOGGER.debug("getAllTimePeriods()");
        List<TimePeriod> timePeriods;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimePeriod.class);
            timePeriods = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        for (TimePeriod timePeriod: timePeriods){
            timePeriod.setLongFields();
        }
        return timePeriods;
    }

    //here - in JPA implementation - this is the same to getTimePeriodListOfCertainEventByEventId()
    //in simple DAO implementation getAllTimePeriodsByEventId() gives result with only eventId data about event
    // and getTimePeriodListOfCertainEventByEventId() gives result with full data about event
    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException {
        return getTimePeriodListOfCertainEventByEventId(event);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime)
            throws DataAccessException {
        LOGGER.debug("getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() where beginning={} and end={}",
                beginTime, endTime);
        List<TimePeriod> timePeriods;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimePeriod.class, "timePeriod");
            criteria.add(Restrictions.ge("timePeriod.endInDateFormat",
                    new Date(convertTimeFromStringToSeconds(beginTime)*1000)));
            criteria.add(Restrictions.le("timePeriod.beginningInDateFormat",
                    new Date(convertTimeFromStringToSeconds(endTime)*1000)));
            timePeriods = criteria.list();
            for(TimePeriod timePeriod: timePeriods){
                timePeriod.setLongFields();
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(timePeriods);
        return timePeriods;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TimePeriod> getAllTimePeriodsOfCertainCategoryInTimeInterval(
            Category category, long beginOfInterval, long endOfInterval) throws DataAccessException {

        LOGGER.debug("getAllTimePeriodsOfCertainCategoryInTimeInterval() where categoryId= {} beginning={} and end={}",
                category, beginOfInterval, endOfInterval);
        List<TimePeriod> timePeriods;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimePeriod.class, "timePeriod");
            criteria.createAlias("timePeriod.event", "event");
            criteria.createAlias("event.category", "category");
            criteria.add(Restrictions.eq("category.categoryId", category.getCategoryId()));
            criteria.add(Restrictions.gt("timePeriod.endInDateFormat",
                    new Date(beginOfInterval*1000)));
            criteria.add(Restrictions.le("timePeriod.beginningInDateFormat",
                    new Date(endOfInterval*1000)));
            criteria.addOrder(Order.asc("timePeriod.endInDateFormat"));
            timePeriods = criteria.list();
            for(TimePeriod timePeriod: timePeriods){
                timePeriod.setLongFields();
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(timePeriods);
        return timePeriods;
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        LOGGER.debug("addTimePeriod where event={}", timePeriod);
        Integer newTimePeriodId;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            timePeriod.setDateFields();
            newTimePeriodId = (Integer) session.save(timePeriod);
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DataIntegrityViolationException("");
            } else {
                throw ex;
            }
        } finally {
            session.close();
        }
        return newTimePeriodId;
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException {
        LOGGER.debug("addTimePeriodList timePeriods={}", timePeriods);
        int[] rowsAffectedArr = new int[timePeriods.size()];
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            int i=0;
            for ( TimePeriod timePeriod: timePeriods) {
                timePeriod.setDateFields();
                int newTimePeriodId = (Integer) session.save(timePeriod);
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                if (newTimePeriodId > 0){
                    rowsAffectedArr[i] = 1;
                } else {
                    rowsAffectedArr[i] = 0;
                }
                i++;
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof ConstraintViolationException){
                throw new DataIntegrityViolationException("");
            } else {
                throw ex;
            }
        } finally {
            session.close();
        }
        return rowsAffectedArr;
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        LOGGER.debug(" updateTimePeriod with timePeriodId={}", timePeriod.getTimePeriodId());
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            timePeriod.setDateFields();
            session.update(timePeriod);
            tx.commit();
            rowsAffected = 1;
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            if (ex instanceof StaleStateException || ex instanceof ConstraintViolationException){
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
    public int deleteTimePeriod(Integer timePeriodId) throws DataAccessException {
        LOGGER.debug("deleteTimePeriod with ID={}", timePeriodId);
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            TimePeriod timePeriod =
                    (TimePeriod) session.get(TimePeriod.class, timePeriodId);
            if (timePeriod == null){
                rowsAffected = 0;
            } else{
                session.delete(timePeriod);
                rowsAffected = 1;
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        return rowsAffected;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException {
        LOGGER.debug("deleteTimePeriodsByEventId with eventID={}", event.getEventId());
        int rowsAffected = 0;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimePeriod.class, "timePeriod");
            criteria.createAlias("timePeriod.event", "event");
            criteria.add(Restrictions.eq("event.eventId", event.getEventId()));
            List<TimePeriod> timePeriods = criteria.list();
            if (timePeriods.size() != 0){
                int i = 0;
                for ( TimePeriod timePeriod: timePeriods) {
                    timePeriod.setDateFields();
                    session.delete(timePeriod);
                    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                        //flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                    rowsAffected++;
                    i++;
                }
            }
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        return rowsAffected;
    }
}
