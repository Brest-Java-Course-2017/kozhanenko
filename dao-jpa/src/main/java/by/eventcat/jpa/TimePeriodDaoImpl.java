package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.Event;
import by.eventcat.TimePeriod;
import by.eventcat.TimePeriodDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TimePeriod JPA Dao Implementation
 */
@Repository
@Transactional
public class TimePeriodDaoImpl implements TimePeriodDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public TimePeriod getTimePeriodById(Integer timePeriodId) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getTimePeriodListOfCertainEventByEventId(Event event) throws DataAccessException {
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
    public List<TimePeriod> getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(String beginTime, String endTime) throws DataAccessException {
        return null;
    }

    @Override
    public List<TimePeriod> getAllTimePeriodsOfCertainCategoryInTimeInterval(Category category, long beginOfInterval, long endOfInterval) throws DataAccessException {
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
