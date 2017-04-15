package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.eventcat.TimeConverter.*;

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
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException, ServiceException {
        if (timePeriodId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        TimePeriod timePeriod;
        try{
            timePeriod = timePeriodDao.getTimePeriodById(timePeriodId);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return timePeriod;
    }

    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException, ServiceException {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        if (timePeriods.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return timePeriods;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException {
        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(event);
        if (timePeriods.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return timePeriods;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime)
            throws DataAccessException, ServiceException{
        if ( ! isValidDateInStringFormat(beginTime)) throw new ServiceException(CustomErrorCodes.INCORRECT_DATE_FORMAT);
        if ( ! isValidDateInStringFormat(endTime)) throw new ServiceException(CustomErrorCodes.INCORRECT_DATE_FORMAT);
        if ( ! isValidDateInString(beginTime)) throw new ServiceException(CustomErrorCodes.INCORRECT_DATE);
        if ( ! isValidDateInString(endTime)) throw new ServiceException(CustomErrorCodes.INCORRECT_DATE);
        if (convertTimeFromStringToSeconds(beginTime) > convertTimeFromStringToSeconds(endTime))
            throw  new ServiceException(CustomErrorCodes.INCORRECT_REQUEST_PARAMETERS);
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(beginTime, endTime);
        if (timePeriods.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return timePeriods;
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException {
        if (timePeriod.getEvent().getEventId() <= 0 ||
                timePeriod.getBeginning() <= 0 ||
                timePeriod.getEnd() <= 0){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        //TODO: check what data returns if error
        return timePeriodDao.addTimePeriod(timePeriod);
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException, ServiceException {
        //TODO: check what Exception butch update throws
        return timePeriodDao.addTimePeriodList(timePeriods);
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException {
        if (timePeriod.getTimePeriodId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        if (timePeriod.getEvent().getEventId() <= 0 ||
                timePeriod.getBeginning() <= 0 ||
                timePeriod.getEnd() <= 0){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int rowsAffected = timePeriodDao.updateTimePeriod(timePeriod);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteTimePeriod(Integer timePeriodId) throws DataAccessException, ServiceException {
        if (timePeriodId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = timePeriodDao.deleteTimePeriod(timePeriodId);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException {
        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = timePeriodDao.deleteTimePeriodsByEventId(event);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }
}
