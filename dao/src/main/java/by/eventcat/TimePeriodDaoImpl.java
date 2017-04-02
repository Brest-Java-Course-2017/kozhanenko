package by.eventcat;

import static by.eventcat.TimeConverter.*;

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
import java.util.*;

/**
 * Time Period Dao Implementation
 */
public class TimePeriodDaoImpl implements TimePeriodDao{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TIME_PERIOD_ID = "time_period_id";
    private static final String EVENT_ID = "event_id";
    private static final String BEGINNING = "beginning";
    private static final String END = "end";

    TimePeriodDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Value("${period.getTimePeriodById}")
    private String getTimePeriodByIdSql;

    @Value("${period.getAllTimePeriods}")
    private String getAllTimePeriodsSql;

    @Value("${period.getAllTimePeriodsByEventId}")
    private String getAllTimePeriodsByEventSql;

    @Value("${period.getAllTimePeriodsThatLastCertainTime}")
    private String getAllTimePeriodsThatLastCertainTimeSql;

    @Value("${period.addTimePeriod}")
    private String addTimePeriodSql;

    @Value("${period.addTimePeriodsAsBatch}")
    private String addTimePeriodsAsBatchSql;

    @Value("${period.updateTimePeriod}")
    private String updateTimePeriodSql;

    @Value("${period.deleteTimePeriodById}")
    private String deleteTimePeriodSql;

    @Value("${period.deleteTimePeriodsByEventId}")
    private String deleteTimePeriodsByEventSql;


    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_time_period_id", timePeriodId);
        return namedParameterJdbcTemplate.queryForObject(
                getTimePeriodByIdSql, namedParameters, new TimePeriodRowMapper());
    }

    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException {
        return jdbcTemplate.query(getAllTimePeriodsSql, new TimePeriodRowMapper());
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException {
        return jdbcTemplate.query(getAllTimePeriodsByEventSql, new String[]{Integer.toString(event.getEventId())},
                new TimePeriodRowMapper());
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime) {
        return jdbcTemplate.query(getAllTimePeriodsThatLastCertainTimeSql, new String[]{beginTime, endTime},
                new TimePeriodRowMapper());
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(EVENT_ID, timePeriod.getEvent().getEventId());
        parameterSource.addValue(BEGINNING, convertTimeFromSecondsToString(timePeriod.getBeginning()));
        parameterSource.addValue(END, convertTimeFromSecondsToString(timePeriod.getEnd()));
        namedParameterJdbcTemplate.update(addTimePeriodSql, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException {
        List<Object[]> batch = new ArrayList<>();
        for (TimePeriod timePeriod: timePeriods){
            Object[] values = new Object[] {
                    timePeriod.getEvent().getEventId(),
                    convertTimeFromSecondsToString(timePeriod.getBeginning()),
                    convertTimeFromSecondsToString(timePeriod.getEnd())
            };
            batch.add(values);
        }
        return jdbcTemplate.batchUpdate(addTimePeriodsAsBatchSql, batch);
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        params.put(TIME_PERIOD_ID, timePeriod.getTimePeriodId());
        params.put(EVENT_ID, timePeriod.getEvent().getEventId());
        params.put(BEGINNING, convertTimeFromSecondsToString(timePeriod.getBeginning()));
        params.put(END, convertTimeFromSecondsToString(timePeriod.getEnd()));
        return namedParameterJdbcTemplate.update(updateTimePeriodSql, params);
    }

    @Override
    public int deleteTimePeriod(Integer timePeriodId) throws DataAccessException {
        Map<String, Integer> params = new HashMap<>();
        params.put(TIME_PERIOD_ID, timePeriodId);
        return namedParameterJdbcTemplate.update(deleteTimePeriodSql, params);
    }

    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException {
        Map<String, Integer> params = new HashMap<>();
        params.put(EVENT_ID, event.getEventId());
        return namedParameterJdbcTemplate.update(deleteTimePeriodsByEventSql, params);
    }

    private class TimePeriodRowMapper implements RowMapper<TimePeriod> {

        @Override
        public TimePeriod mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TimePeriod(
                    resultSet.getInt(TIME_PERIOD_ID),
                    new Event(resultSet.getInt(EVENT_ID)),
                    convertTimeFromStringToSeconds(resultSet.getString(BEGINNING)),
                    convertTimeFromStringToSeconds(resultSet.getString(END))
            );
        }
    }

}
