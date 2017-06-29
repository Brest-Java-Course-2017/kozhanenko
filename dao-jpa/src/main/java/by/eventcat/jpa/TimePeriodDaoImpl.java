package by.eventcat.jpa;

import by.eventcat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsOfCertainCategoryInTimeInterval(Category category, long beginOfInterval, long endOfInterval) throws DataAccessException {
        return null;
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
        return new int[0];
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteTimePeriod(Integer timePeriodId) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException {
        return 0;
    }
}
