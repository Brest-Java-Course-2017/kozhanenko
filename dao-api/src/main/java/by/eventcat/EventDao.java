package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public interface EventDao
 */
public interface EventDao {
    /**
     * Get all events
     *
     * @return List of events
     * @throws DataAccessException
     */
    List<Event> getAllEvents() throws DataAccessException;

    /**
     * Get all events of certain event place
     *
     * @param eventPlaceId event place identifier
     * @return List of events
     * @throws DataAccessException
     */
    List<Event> getAllEventsByEventPlace(Integer eventPlaceId) throws DataAccessException;

    /**
     * Get all events of certain category
     *
     * @param categoryId category identifier
     * @return List of events
     * @throws DataAccessException
     */
    List<Event> getAllEventsByCategoryId(Integer categoryId) throws DataAccessException;

    /**
     * Get event by identifier
     *
     * @param eventId event identifier
     * @return event
     * @throws DataAccessException
     */
    Event getEventById(Integer eventId) throws DataAccessException;

    /**
     * Add event
     *
     * @param event event object
     * @return new event identifier
     * @throws DataAccessException
     */
    Integer addEvent(Event event) throws DataAccessException;

    /**
     *  Update event
     *
     * @param event event object
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateEvent(Event event) throws DataAccessException;

    /**
     * Delete event
     *
     * @param eventId event identifier
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteEvent(Integer eventId) throws DataAccessException;

}
