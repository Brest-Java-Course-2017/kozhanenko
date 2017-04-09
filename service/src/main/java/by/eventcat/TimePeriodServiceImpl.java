package by.eventcat;

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

    //@Autowired
    private TimePeriodDao timePeriodDao;

    public void setTimePeriodDao(TimePeriodDao timePeriodDao) {
        this.timePeriodDao = timePeriodDao;
    }

    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException {
        return timePeriodDao.getTimePeriodById(timePeriodId);
    }

    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException {
        return timePeriodDao.getAllTimePeriods();
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException {
        return timePeriodDao.getAllTimePeriodsByEventId(event);
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime)
            throws DataAccessException{
        return timePeriodDao.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(beginTime, endTime);
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        return timePeriodDao.addTimePeriod(timePeriod);
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException {
        return timePeriodDao.addTimePeriodList(timePeriods);
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException {
        return timePeriodDao.updateTimePeriod(timePeriod);
    }

    @Override
    public int deleteTimePeriod(Integer timePeriodId) throws DataAccessException {
        return timePeriodDao.deleteTimePeriod(timePeriodId);
    }

    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException {
        return timePeriodDao.deleteTimePeriodsByEventId(event);
    }
}
