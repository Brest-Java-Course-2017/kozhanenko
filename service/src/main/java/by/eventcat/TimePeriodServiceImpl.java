package by.eventcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TimePeriodService Implementation
 */
@Service
@Transactional
public class TimePeriodServiceImpl implements TimePeriodService{

    @Autowired
    private TimePeriodDao timePeriodDao;

    public void setTimePeriodDao(TimePeriodDao timePeriodDao) {
        this.timePeriodDao = timePeriodDao;
    }

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
