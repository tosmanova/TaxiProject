import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.server.constants.Constants;
import ua.taxi.server.dao.UserDao;
import ua.taxi.base.exception.TaxiAppException;
import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.user.Car;
import ua.taxi.base.model.user.Driver;
import ua.taxi.base.model.user.Passenger;
import ua.taxi.base.model.user.User;
import ua.taxi.server.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.PersistenceException;

/**
 * Created by andrii on 29.06.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlUserTest extends Assert {

    private UserDao userDao;

    private Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
    private Passenger pass1 = new Passenger("(055)306-01-13", "0553060113", "LLdrii", new Address("Entuziastiv", "35"));
    private Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
    private Driver driver1 = new Driver("(073)306-01-13", "063060113", "Kolia", new Car("MM2222KK", "Maz", "Baklazan"));
    private Driver driver2 = new Driver("(083)306-01-13", "06360113", "Kolia", new Car("M22222KK", "Maz", "Baklhan"));

    @BeforeClass
    public static void initTestSQL() {

        TestUtils.scriptRun(Constants.SQL_CREATE_TEST_SCRIPT);
        TestUtils.init();
    }

    @Before
    public void initUserDao() {
        userDao = TestUtils.getUserDao();
    }


    @Test
    public void _01_createPassenger()  {

        User user = userDao.createUser(pass);
        assertTrue(user instanceof Passenger);
        assertEquals(user, pass);
    }

    @Test(expected = PersistenceException.class)
    public void _02_createUserNegative() throws TaxiAppException {

        userDao.createUser(driver);
        userDao.createUser(driver);
    }

    @Test
    public void _05_getUser() throws TaxiAppException {
        userDao.createUser(pass);
        userDao.createUser(driver);
        User user1 = userDao.getUser(pass.getPhone());
        User user2 = userDao.getUser(driver.getPhone());
        assertEquals(user1 , pass);
        assertEquals(user2 , driver);
    }

    @Test(expected = PersistenceException.class)
    public void _06_getUserNegative() throws TaxiAppException {

        userDao.createUser(pass);
        userDao.createUser(driver);
        User user1 = userDao.getUser("(085)306-01-13");
    }

    @Test
    public void _07_deleteUser() throws TaxiAppException {

        userDao.createUser(pass);
        userDao.createUser(driver);
        User user = userDao.delete("(093)306-01-13");
        assertTrue((user).equals(pass));
        assertTrue(userDao.delete("(063)306-01-13").equals(driver));
    }

    @Test(expected = PersistenceException.class)
    public void _08_deletePassenger() throws TaxiAppException {
        userDao.createUser(pass);
        userDao.delete("(093)306-01-13)");
        userDao.getUser("(093)306-01-13)");
    }


    @Test(expected = PersistenceException.class)
    public void _09_deleteDriver() throws TaxiAppException {
        userDao.createUser(driver);
        userDao.delete("(063)306-01-13");
        userDao.getUser("(063)306-01-13");
    }

    @Test(expected = PersistenceException.class)
    public void _10_deleteUserNegative() throws TaxiAppException {
        userDao.createUser(driver);
        userDao.delete("(666)306-01-13");
    }


    @Test
    public void _11_updateUser() throws TaxiAppException {
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "35"));
        Passenger pass1 = new Passenger("(093)306-01-13", "0553060113", "LLdrii", new Address("Entuziastiv", "35"));
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222KK", "Vaz", "Baklazhan"));
        Driver driver1 = new Driver("(063)306-01-13", "0633060113", "Kolia", new Car("MM2222KK", "Maz", "Baklazhan"));

        userDao.createUser(pass);
        userDao.createUser(driver);
        userDao.update(pass1);
        userDao.update(driver1);
        assertTrue(userDao.getUser("(093)306-01-13").equals(pass1));
        assertFalse(userDao.getUser("(093)306-01-13").equals(pass));
        assertTrue(userDao.getUser("(063)306-01-13").equals(driver1));
        assertFalse(userDao.getUser("(063)306-01-13").equals(driver));
    }


    @Test(expected = PersistenceException.class)
    public void _12_updateUserNegative() throws TaxiAppException {
        userDao.update(driver);
    }


    @Test
    public void _13_Count() throws TaxiAppException {

        userDao.createUser(pass);
        userDao.createUser(driver);
        userDao.createUser(pass1);
        userDao.createUser(driver1);
        userDao.createUser(driver2);
        int countP = userDao.passengerRegisteredQuantity();
        int countD = userDao.driverRegisteredQuantity();
        assertEquals(countP, 2);
        assertEquals(countD, 3);
    }

    @Test
    public void _13_CountNull() throws TaxiAppException {

        int countP = userDao.passengerRegisteredQuantity();
        int countD = userDao.driverRegisteredQuantity();
        assertEquals(countP, 0);
        assertEquals(countD, 0);
    }

    @After
    public void clearBase() {

       try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            statement.executeUpdate("TRUNCATE Drivers");
            statement.executeUpdate("TRUNCATE Passengers");
            statement.executeUpdate("TRUNCATE Cars");
            statement.executeUpdate("TRUNCATE Addresses");
            statement.executeUpdate("TRUNCATE Users");
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void removeTestSQL() {

        TestUtils.scriptRun(Constants.SQL_REMOVE_TEST_SCRIPT);
    }

}
