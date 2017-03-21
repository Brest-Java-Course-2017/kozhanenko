package by.eventcat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Time Period Dao Implementation
 */
public class TimePeriodDaoImpl implements TimePeriodDao{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    TimePeriodDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Value("${event.getAllTimePeriodsByEventId}")
    private String getAllTimePeriodsByEventSql;

    @Value("${event.getAllTimePeriodsThatLastCertainTime}")
    private String getAllTimePeriodsThatLastCertainTimeSql;

    @Value("${event.addTimePeriod}")
    private String addTimePeriodSql;

    @Value("${event.updateTimePeriod}")
    private String updateTimePeriodSql;

    @Value("${event.deleteTimePeriodById}")
    private String deleteTimePeriodSql;

    @Value("${event.deleteTimePeriodsByEventId}")
    private String deleteTimePeriodsByEventSql;

    @Override
    public List<TimePeriod> getAllTimePeriodsByEvent(Event event) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(long beginTime, long endTime) {
        return null;
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        return null;
    }

    @Override
    public int addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException {
        return 0;
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
    public int deleteTimePeriodsByEvent(Event event) throws DataAccessException {
        return 0;
    }
}
