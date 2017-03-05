package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public interface EventPlaceDao
 */
public interface EventPlaceDao {

    /**
     * Get all event places list.
     *
     * @return all event places list
     * @throws DataAccessException
     */
    List<EventPlace> getAllEventPlaces() throws DataAccessException;

    /**
     * Get event place by identifier
     *
     * @param eventPlaceId event place identifier
     * @return EventPlace object
     * @throws DataAccessException
     */
    EventPlace getEventPlaceById(Integer eventPlaceId) throws DataAccessException;

    /**
     * get all event places of certain category by category identifier
     *
     * @param categoryId event place category identifier
     * @return list of event places of certain category
     * @throws DataAccessException
     */
    List<EventPlace> getAllIventPlacesByCategoryId(Integer categoryId) throws DataAccessException;

    /**
     * add new event place
     *
     * @param eventPlace new event place object
     * @return new event place identifier
     * @throws DataAccessException
     */
    Integer addEventPlace(EventPlace eventPlace) throws DataAccessException;

    /**
     * update event place
     *
     * @param eventPlace event place object with updates
     * @return number of updated rows
     * @throws DataAccessException
     */
    int updateEventPlace(EventPlace eventPlace) throws DataAccessException;

    /**
     *
     * @param eventPlaceId event place identifier
     * @return
     * @throws DataAccessException
     */
    int deleteEventPlace(Integer eventPlaceId) throws DataAccessException;
    //TODO: realise if there is needless to return something - REST do not permit any return when delete
    //TODO: DataAccessException description in javadoc???
}
