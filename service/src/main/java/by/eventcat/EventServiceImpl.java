package by.eventcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EventService Implementation
 */
@Service
@Transactional
public class EventServiceImpl implements EventService{

    @Autowired
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> getAllEvents() throws DataAccessException {
        return null;
    }

    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException {
        return null;
    }

    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException {
        return null;
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException {
        return null;
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException {
        return null;
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteEvent(Integer eventId) throws DataAccessException {
        return 0;
    }
}
