package by.eventcat.jpa;

import by.eventcat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * UserTotals Dao implementation tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class UserTotalsDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserTotalsDao userTotalsDao;

    @Test
    public void getUserTotals() throws Exception {
        LOGGER.debug("test: getUserTotals()");
        List<UserTotals> totalData = userTotalsDao.getUserTotals();
        assertTrue(totalData.size() > 0);
    }

    @Test
    public void getValue() throws Exception {
        LOGGER.debug("test: getValue");
        List<UserTotals> totalData = userTotalsDao.getUserTotals();

        UserTotals superAdminData = null, cityAdminData= null, localAdminData = null;

        if (totalData.size() > 0){
            for (UserTotals userTotals: totalData){
                if (userTotals.getUserRole().equals(UserRole.SUPER_ADMIN)){
                    superAdminData = userTotals;
                } else if (userTotals.getUserRole().equals(UserRole.CITY_ADMIN)){
                    cityAdminData = userTotals;
                } else if (userTotals.getUserRole().equals(UserRole.LOCAL_ADMIN)){
                    localAdminData = userTotals;
                }
            }

            if (superAdminData != null){
                UserTotals SAD = userTotalsDao.getValue(superAdminData.getUserRole(), superAdminData.getCity());
                assertNotNull(SAD);
                assertEquals(UserRole.SUPER_ADMIN, SAD.getUserRole());
                assertEquals(null, SAD.getCity());
            }

            if (cityAdminData != null){
                UserTotals CAD = userTotalsDao.getValue(cityAdminData.getUserRole(), cityAdminData.getCity());
                assertNotNull(CAD);
                assertEquals(UserRole.CITY_ADMIN, CAD.getUserRole());
                assertEquals(cityAdminData.getCity(), CAD.getCity());
            }

            if (localAdminData != null){
                UserTotals LAD = userTotalsDao.getValue(localAdminData.getUserRole(), localAdminData.getCity());
                assertNotNull(LAD);
                assertEquals(UserRole.LOCAL_ADMIN, LAD.getUserRole());
                assertEquals(localAdminData.getCity(), LAD.getCity());
            }
        }
    }

    @Test
    public void getValueNoDataFound() throws Exception {
        LOGGER.debug("test: getValueNoDataFound()");

        UserTotals nonExistingUserTotals = userTotalsDao.getValue(UserRole.CITY_ADMIN, new Locality(999));
        assertNull(nonExistingUserTotals);
    }

    @Test
    public void setValue() throws Exception {
        LOGGER.debug("test: setValue()");
        int result;
        if (userTotalsDao.getValue(UserRole.LOCAL_ADMIN, new Locality(6)) == null){

            //"decrease" of non existing line
            result  = userTotalsDao.setValue(UserRole.LOCAL_ADMIN, new Locality(6),
                    UserTotalsSetValueOperation.DECREASE);
            assertEquals(2, result);

            //"increase" - line didn't exist before operation
            int quantityBefore = userTotalsDao.getUserTotals().size();
            result  = userTotalsDao.setValue(UserRole.LOCAL_ADMIN, new Locality(6),
                    UserTotalsSetValueOperation.INCREASE);
            assertEquals(1, result);
            int quantityAfter = userTotalsDao.getUserTotals().size();
            assertEquals(quantityBefore + 1, quantityAfter);

            //"decrease" - count after operation is 0
            result  = userTotalsDao.setValue(UserRole.LOCAL_ADMIN, new Locality(6),
                    UserTotalsSetValueOperation.DECREASE);
            assertEquals(1, result);
            assertEquals(quantityAfter - 1, userTotalsDao.getUserTotals().size());

        }
        List<UserTotals> list = userTotalsDao.getUserTotals();
        if (list.size() > 0){
            UserTotals userTotalsLine = list.get(0);
            int countBefore = userTotalsLine.getCount();

            //"increase" - line existed before operation
            result  = userTotalsDao.setValue(userTotalsLine.getUserRole(), userTotalsLine.getCity(),
                    UserTotalsSetValueOperation.INCREASE);
            assertEquals(1, result);
            UserTotals lineAfter = userTotalsDao.getValue(userTotalsLine.getUserRole(), userTotalsLine.getCity());
            assertEquals(countBefore + 1, lineAfter.getCount());

            //"decrease" - count after operation is more than 0
            result  = userTotalsDao.setValue(userTotalsLine.getUserRole(), userTotalsLine.getCity(),
                    UserTotalsSetValueOperation.DECREASE);
            assertEquals(1, result);
            assertEquals(countBefore, userTotalsDao.getValue(userTotalsLine.getUserRole(), userTotalsLine.getCity()).getCount());
        }
    }

    @Test
    public void setValueNotExistingCity() throws Exception {
        LOGGER.debug("test: setValueNotExistingCity()");

        int result  = userTotalsDao.setValue(UserRole.LOCAL_ADMIN, new Locality(999),
                UserTotalsSetValueOperation.INCREASE);
        assertEquals(3, result);
    }
}
