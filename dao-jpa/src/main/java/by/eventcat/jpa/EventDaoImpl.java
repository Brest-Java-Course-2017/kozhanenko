package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.Event;
import by.eventcat.EventDao;
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
 * Event JPA Dao Implementation
 */
@Repository
@Transactional
public class EventDaoImpl implements EventDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() throws DataAccessException {
        LOGGER.debug("getAllEvents()");
        List<Event> events;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Event.class);
            events = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(events);
        return events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException {
        LOGGER.debug("getAllEventsByEventPlaceName() where eventPlaceName={}", eventPlaceName);
        List<Event> events;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Event.class);
            criteria.add(Restrictions.eq("eventPlace", eventPlaceName));
            events = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(events);
        return events;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException {
        LOGGER.debug("getAllEventsByCategoryId() where categoryId={}", category.getCategoryId());
        List<Event> events;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Event.class, "event");
            criteria.createAlias("event.category", "category");
            criteria.add(Restrictions.eq("category.categoryId", category.getCategoryId()));
            events = criteria.list();
            tx.commit();
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(events);
        return events;
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException {
        LOGGER.debug("getEventById() where eventId={}", eventId);
        Event event;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            event = (Event)session.get(Event.class, eventId);
            tx.commit();
            if (event == null){
                throw new EmptyResultDataAccessException(1);
            }
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
        LOGGER.debug(event);
        return event;
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException {
        LOGGER.debug("addEvent() where event={}", event);
        Integer newEventId;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            newEventId = (Integer) session.save(event);
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
        return newEventId;
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException {
        LOGGER.debug("updateEvent with eventId={}", event.getEventId());
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(event);
            tx.commit();
            rowsAffected = 1;
        } catch(HibernateException ex){
            if (tx!=null) tx.rollback();
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
    public int deleteEvent(Integer eventId) throws DataAccessException {
        LOGGER.debug("deleteEvent with ID={}", eventId);
        int rowsAffected;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Event event =
                    (Event) session.get(Event.class, eventId);
            if (event == null){
                rowsAffected = 0;
            } else{
                session.delete(event);
                rowsAffected = 1;
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
        return rowsAffected;
    }
}
