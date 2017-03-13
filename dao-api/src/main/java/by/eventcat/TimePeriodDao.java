package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public class TimePeriodDao
 */
public interface TimePeriodDao {

    /**
     * Gets all time periods of certain event
     *
     * @param eventId event identifier
     * @return list of TimePeriod objects
     * @throws DataAccessException
     */
    List<TimePeriod> getAllTimePeriodsByEventId(Integer eventId) throws DataAccessException;

    /**
     * Gets all time periods that running now (beginTime) or will begin till some time period (endTime)
     *
     * @param beginTime begin of explored time
     * @param endTime end of explored time
     * @return list of TimePeriod objects
     */
    List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(long beginTime, long endTime);

    /**
     * Adds time period
     *
     * @param timePeriod TimePeriod object
     * @return new TimePeriod identifier
     * @throws DataAccessException
     */
    Integer addTimePeriod(TimePeriod timePeriod) throws DataAccessException;

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
     * @param eventId event identifier
     * @return number of rows affected
     * @throws DataAccessException
     */
    int deleteTimePeriodsByEventId(Integer eventId) throws DataAccessException;

}
