package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * User Totals Dao
 */
public interface UserTotalsDao {

    /**
     * Get all aggregate data about registered users (admins)
     *
     * @return list of users types information and counts
     * @throws DataAccessException
     */
    List<UserTotals> getUserTotals() throws DataAccessException;

    /**
     * Get UserTotals object with certain aggregate data
     *
     * @param userRole user role
     * @param city user location
     * @return UserTotals object according to params condition or null if no such object
     * @throws DataAccessException
     */
    UserTotals getValue (UserRole userRole, Locality city) throws DataAccessException;

    /**
     * Set new UserTotals aggregate value
     *
     * @param userRole user role
     * @param city user location
     * @param operation what to do - "increase"/"decrease"
     * @return operation success/error status code:
     *        1- OK,
     *        2 - try to decrease non existing data
     *        3 - using not existing city (ConstraintViolationException)
     *        4 - HibernateException
     * @throws DataAccessException
     */
    int setValue(UserRole userRole, Locality city, UserTotalsSetValueOperation operation) throws DataAccessException;
}
