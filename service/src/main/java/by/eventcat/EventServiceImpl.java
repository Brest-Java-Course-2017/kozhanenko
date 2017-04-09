package by.eventcat;


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

    //@Autowired
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> getAllEvents() throws DataAccessException {
        return eventDao.getAllEvents();
    }

    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException {
        return eventDao.getAllEventsByEventPlaceName(eventPlaceName);
    }

    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException {
        return eventDao.getAllEventsByCategoryId(category);
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException {
        return eventDao.getEventById(eventId);
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException {
        return eventDao.addEvent(event);
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException {
        return eventDao.updateEvent(event);
    }

    @Override
    public int deleteEvent(Integer eventId) throws DataAccessException {
        return eventDao.deleteEvent(eventId);
    }
}
