package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserTotals service implementation
 */
public class UserTotalsServiceImpl implements UserTotalsService {

    private static final Logger LOGGER = LogManager.getLogger();

    private UserTotalsDao userTotalsDao;

    public void setUserTotalsDao(UserTotalsDao userTotalsDao) {
        this.userTotalsDao = userTotalsDao;
    }

    @Override
    public List<UserTotals> getUserTotals() throws DataAccessException, ServiceException {
        LOGGER.debug("getUserTotals()");

        List<UserTotals> userTotals = userTotalsDao.getUserTotals();
        if (userTotals.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return userTotals;
    }
}
