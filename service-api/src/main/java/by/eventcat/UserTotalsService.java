package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserTotals service api
 */
public interface UserTotalsService {
    /**
     * Get all aggregate data about registered users (admins)
     *
     * @return list of users types information and counts
     * @throws DataAccessException
     */
    List<UserTotals> getUserTotals() throws DataAccessException, ServiceException;
}
