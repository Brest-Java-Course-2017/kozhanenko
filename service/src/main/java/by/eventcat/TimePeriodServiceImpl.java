package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * TimePeriodService Implementation
 */
public class TimePeriodServiceImpl implements TimePeriodService{

    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime) {
        return null;
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        return null;
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException {
        return new int[0];
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
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException {
        return 0;
    }
}
