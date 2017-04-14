package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Event Service
 */
public interface EventService {
    /**
     * Get all events
     *
     * @return List of events
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<Event> getAllEvents() throws DataAccessException, ServiceException;

    /**
     * Get all events of certain event place
     *
     * @param eventPlaceName event place name
     * @return List of events
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException, ServiceException;

    /**
     * Get all events of certain category
     *
     * @param category category object
     * @return List of events
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException, ServiceException;

    /**
     * Get event by identifier
     *
     * @param eventId event identifier
     * @return event
     * @throws DataAccessException
     * @throws ServiceException
     */
    Event getEventById(Integer eventId) throws DataAccessException, ServiceException;

    /**
     * Add event
     *
     * @param event event object
     * @return new event identifier
     * @throws DataAccessException
     * @throws ServiceException
     */
    Integer addEvent(Event event) throws DataAccessException, ServiceException;

    /**
     *  Update event
     *
     * @param event event object
     * @return number of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int updateEvent(Event event) throws DataAccessException, ServiceException;

    /**
     * Delete event
     *
     * @param eventId event identifier
     * @return number of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int deleteEvent(Integer eventId) throws DataAccessException, ServiceException;
}
