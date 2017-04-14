package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * TimePeriod Service
 */
public interface TimePeriodService {
    /**
     * Get Time Period by its identifier
     *
     * @param timePeriodId TimePeriod identifier
     * @return TimePeriod object
     * @throws DataAccessException
     * @throws ServiceException
     */
    TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException, ServiceException;

    /**
     * Get all Time Periods
     *
     * @return list of Time Periods
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<TimePeriod> getAllTimePeriods() throws DataAccessException, ServiceException;

    /**
     * Gets all time periods of certain event
     *
     * @param event event object
     * @return list of TimePeriod objects
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException;

    /**
     * Gets all time periods that running now (beginTime) or will begin till some time period (endTime)
     *
     * @param beginTime begin of explored time (timestamp format)
     * @param endTime end of explored time (timestamp format)
     * @return list of TimePeriod objects
     * @throws DataAccessException
     * @throws ServiceException
     */
    List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime)
            throws DataAccessException, ServiceException;

    /**
     * Adds time period
     *
     * @param timePeriod TimePeriod object
     * @return new TimePeriod identifier
     * @throws DataAccessException
     * @throws ServiceException
     */
    Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException;

    /**
     *
     * @param timePeriods list of time periods
     * @return array of counts of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int [] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException, ServiceException;

    /**
     * updates time period
     *
     * @param timePeriod TimePeriod object
     * @return number of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException, ServiceException;

    /**
     * Deletes time period by identifier
     *
     * @param timePeriodId time period identifier
     * @return number of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int deleteTimePeriod(Integer timePeriodId) throws DataAccessException, ServiceException;

    /**
     * Delete time periods of certain event
     *
     * @param event event object
     * @return number of rows affected
     * @throws DataAccessException
     * @throws ServiceException
     */
    int deleteTimePeriodsByEventId(Event event) throws DataAccessException, ServiceException;
}
