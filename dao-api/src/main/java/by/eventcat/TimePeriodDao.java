package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public class TimePeriodDao
 */
public interface TimePeriodDao {
    /**
     * Get Time Period by its identifier
     *
     * @param timePeriodId TimePeriod identifier
     * @return TimePeriod object
     * @throws DataAccessException
     */
    TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException;

    /**
     * Get all Time Periods
     *
     * @return list of Time Periods
     * @throws DataAccessException
     */
    List<TimePeriod> getAllTimePeriods() throws DataAccessException;

    /**
     * Gets all time periods of certain event
     *
     * @param event event object
     * @return list of TimePeriod objects
     * @throws DataAccessException
     */
    List<TimePeriod> getAllTimePeriodsByEventId(Event event) throws DataAccessException;

    /**
     * Gets all time periods that running now (beginTime) or will begin till some time period (endTime)
     *
     * @param beginTime begin of explored time (timestamp format)
     * @param endTime end of explored time (timestamp format)
     * @return list of TimePeriod objects
     * @throws DataAccessException
     */
    List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime) throws DataAccessException;

    /**
     * Adds time period
     *
     * @param timePeriod TimePeriod object
     * @return new TimePeriod identifier
     * @throws DataAccessException
     */
    Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException;

    /**
     *
     * @param timePeriods list of time periods
     * @return array of counts of rows affected
     * @throws DataAccessException
     */
    int [] addTimePeriodList(List<TimePeriod> timePeriods) throws DataAccessException;

    /**
     * updates time period
     *
     * @param timePeriod TimePeriod object
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateTimePeriod(TimePeriod timePeriod) throws DataAccessException;

    /**
     * Deletes time period by identifier
     *
     * @param timePeriodId time period identifier
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteTimePeriod(Integer timePeriodId) throws DataAccessException;

    /**
     * Delete time periods of certain event
     *
     * @param event event object
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteTimePeriodsByEventId(Event event) throws DataAccessException;
}
