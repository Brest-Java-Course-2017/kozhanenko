package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event Dao Implementation
 */
public class EventDaoImpl implements EventDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String EVENT_ID = "event_id";
    private static final String CATEGORY_ID = "category_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_PLACE_NAME = "event_place_name";

    @Value("${event.getAllEvents}")
    private String getAllEventsSql;

    @Value("${event.getAllEventsByEventPlaceName}")
    private String getAllEventsByEventPlaceNameSql;

    @Value("${event.getAllEventsByCategoryId}")
    private String getAllEventsByCategoryIdSql;

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
        LOGGER.debug("getAllEvents()");
        return jdbcTemplate.query(getAllEventsSql, new EventRowMapper());
    }

    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException {
        LOGGER.debug("getAllEventsByEventPlaceName({})", eventPlaceName);
        return jdbcTemplate.query(getAllEventsByEventPlaceNameSql, new String[]{eventPlaceName}, new EventRowMapper());
    }

    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException {
        LOGGER.debug("getAllEventsByCategoryId({})", category.getCategoryId());
        return jdbcTemplate.query(getAllEventsByCategoryIdSql, new String[]{Integer.toString(category.getCategoryId())},
                    new EventRowMapper());
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException {
        LOGGER.debug("getEventById({})", eventId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_event_id", eventId);
        return namedParameterJdbcTemplate.queryForObject(
                getEventByIdSql, namedParameters, new EventRowMapper());
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException {
        LOGGER.debug("addEvent(event): name = {}", event.getEventName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(CATEGORY_ID, event.getCategory().getCategoryId());
        parameterSource.addValue(EVENT_NAME, event.getEventName());
        parameterSource.addValue(EVENT_PLACE_NAME, event.getEventPlace());
        namedParameterJdbcTemplate.update(addEventSql, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException {
        LOGGER.debug("updateEvent {}", event);
        Map<String, Object> params = new HashMap<>();
        params.put(EVENT_ID, event.getEventId());
        params.put(CATEGORY_ID, event.getCategory().getCategoryId());
        params.put(EVENT_NAME, event.getEventName());
        params.put(EVENT_PLACE_NAME, event.getEventPlace());
        return namedParameterJdbcTemplate.update(updateEventSql, params);
    }

    @Override
    public int deleteEvent(Integer eventId) throws DataAccessException {
        LOGGER.debug("delete event with eventId = {}", eventId);
        Map<String, Object> params = new HashMap<>();
        params.put(EVENT_ID, eventId);
        return namedParameterJdbcTemplate.update(deleteEventSql, params);
    }

    private class EventRowMapper implements RowMapper<Event> {

        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Event(
                    resultSet.getInt(EVENT_ID),
                    new Category(resultSet.getInt(CATEGORY_ID)),
                    resultSet.getString(EVENT_NAME),
                    resultSet.getString(EVENT_PLACE_NAME)
            );
        }
    }
}
