package by.eventcat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Event Dao Implementation
 */
public class EventDaoImpl implements EventDao{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${event.getAllEvents}")
    private String getAllEventsSql;

    @Value("${event.getAllEventsByEventPlaceName}")
    private String getAllEventsByEventPlaceNameSql;

    @Value("${event.getAllEventsByCategory}")
    private String getAllEventsByCategorySql;

    @Value("${event.getEventById}")
    private String getEventByIdSql;

    @Value("${event.addEvent}")
    private String addEventSql;

    @Value("${event.updateEvent}")
    private String updateEventSql;

    @Value("${event.deleteEvent}")
    private String deleteEventSql;

    EventDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
    public List<Event> getAllEventsByCategory(Category category) throws DataAccessException {
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
