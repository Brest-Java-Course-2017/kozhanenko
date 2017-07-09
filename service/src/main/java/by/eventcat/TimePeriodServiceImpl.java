package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.eventcat.jpa.TimeConverter.*;

/**
 * TimePeriodService Implementation
 */
@Service
@Transactional
public class TimePeriodServiceImpl implements TimePeriodService{

    private static final Logger LOGGER = LogManager.getLogger();


    private TimePeriodDao timePeriodDao;
//    @Autowired
    public void setTimePeriodDao(TimePeriodDao timePeriodDao) {
        this.timePeriodDao = timePeriodDao;
    }

    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException, ServiceException {
        LOGGER.debug("getTimePeriodById({})", timePeriodId);

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
    public List<TimePeriod> getTimePeriodListOfCertainEventByEventId(Event event) throws DataAccessException, ServiceException {
        LOGGER.debug("getTimePeriodListOfCertainEventByEventId({})", event.getEventId());
        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(event);
        if (timePeriods.size() == 0){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return timePeriods;
    }

    @Override
    public List<TimePeriod> getAllTimePeriods() throws DataAccessException, ServiceException {
        LOGGER.debug("getAllTimePeriods()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        if (timePeriods.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return timePeriods;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException {
        LOGGER.debug("getAllTimePeriodsByEventId({})", event.getEventId());

        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(event);
        if (timePeriods.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return timePeriods;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime)
            throws DataAccessException, ServiceException{
        LOGGER.debug("getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime({}, {})", beginTime, endTime);

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
    public List<TimePeriod> getAllTimePeriodsOfCertainCategoryInTimeInterval
            (Category category, long beginOfInterval, long endOfInterval) throws DataAccessException, ServiceException {
        LOGGER.debug("getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime()");
        if (category.getCategoryId() <= 0 || beginOfInterval <= 0 || endOfInterval <= 0){
            throw  new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsOfCertainCategoryInTimeInterval(category,
                beginOfInterval, endOfInterval);
        if (timePeriods.size() == 0){
            throw  new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return timePeriods;
    }

    @Override
    public Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException {
        LOGGER.debug("addTimePeriod({})", timePeriod);

        if (timePeriod.getEvent().getEventId() <= 0 ||
                timePeriod.getBeginning() <= 0 ||
                timePeriod.getEnd() <= 0){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int timePeriodId;
        try {
            timePeriodId = timePeriodDao.addTimePeriod(timePeriod);
        } catch (DataIntegrityViolationException ex){
            throw new ServiceException(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT);
        }
        return timePeriodId;
    }

    @Override
    public int[] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException, ServiceException {
        LOGGER.debug("addTimePeriodList()");

        for (TimePeriod timePeriod: timePeriods){

            LOGGER.debug(timePeriod.getEvent().getEventId() <= 0);
            LOGGER.debug(timePeriod.getBeginning() <= 0);
            LOGGER.debug(timePeriod.getBeginning());
            LOGGER.debug(timePeriod.getEnd() <= 0);
            LOGGER.debug(timePeriod.getEnd());

            if (timePeriod.getEvent().getEventId() <= 0 ||
                    timePeriod.getBeginning() <= 0 ||
                    timePeriod.getEnd() <= 0){
                throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
        }
        int [] rowsAffected;
        try {
            rowsAffected = timePeriodDao.addTimePeriodList(timePeriods);
        } catch (DataIntegrityViolationException ex){
            throw new ServiceException(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT);
        }
        return rowsAffected;
    }

    @Override
    public int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException {
        LOGGER.debug("updateTimePeriod({})", timePeriod);

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
    public int deleteTimePeriodById(Integer timePeriodId) throws DataAccessException, ServiceException {
        LOGGER.debug("delete TimePeriod with id = {}", timePeriodId);

        if (timePeriodId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = timePeriodDao.deleteTimePeriod(timePeriodId);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException {
        LOGGER.debug("delete TimePeriods with eventId = {}", event.getEventId());

        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = timePeriodDao.deleteTimePeriodsByEventId(event);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        return rowsAffected;
    }
}
