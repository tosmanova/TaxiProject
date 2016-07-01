import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.constants.DaoConstants;
import ua.taxi.dao.sql.UserDaoSqlImpl;
import ua.taxi.model.Order.Address;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.Driver;
import ua.taxi.model.User.Passenger;
import ua.taxi.model.User.User;
import ua.taxi.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii on 29.06.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SQL_UserTest extends Assert {

    private UserDaoSqlImpl userDao;

    @BeforeClass
    public static void initTestSQL() {

        ProcessBuilder pb = new ProcessBuilder(DaoConstants.SQL_CREATE_TEST_SCRIPT);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Before
    public  void initUserDao() {
        userDao = new UserDaoSqlImpl();
    }

    @Test
    public void _01_createPassenger() throws SQLException {

        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        List<User> list = new ArrayList<>(userDao.createUser(pass));
        User user = list.get(0);
        assertTrue(user instanceof Passenger);
        assertTrue(((Passenger) user).equals(pass));

    }

    @Test
    public void _02_createDriver() throws SQLException {

        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        List<User> list = new ArrayList<>(userDao.createUser(driver));
        User user = list.get(0);
        assertTrue(user instanceof Driver);
        assertTrue(((Driver) user).equals(driver));
    }

    @Test
    public void _03_createPassengerNegative() throws SQLException {

        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Passenger pass2 = new Passenger("(063)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Passenger pass3 = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "29"));
        List<User> list = new ArrayList<>(userDao.createUser(pass));
        User user = list.get(0);
        assertFalse(((Passenger) user).equals(pass2));
        assertFalse(((Passenger) user).equals(pass2));
    }

    @Test
    public void _04_createDriverNegative() throws SQLException {

        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        Driver driver2 = new Driver("(093)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        Driver driver3 = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("BB2222", "Vaz", "Baklazhan"));
        List<User> list = new ArrayList<>(userDao.createUser(driver));
        User user = list.get(0);
        assertFalse(((Driver) user).equals(driver2));
        assertFalse(((Driver) user).equals(driver3));
    }

    @Test
    public void _05_getUser() throws SQLException {
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        userDao.createUser(pass);
        userDao.createUser(driver);
        User user1 = userDao.getUser(pass.getPhone());
        User user2 = userDao.getUser(driver.getPhone());
        assertTrue(((Passenger) user1).equals(pass));
        assertTrue(((Driver) user2).equals(driver));
    }

    @Test(expected = SQLException.class)
    public void _06_getUserNegative() throws SQLException {
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        userDao.createUser(pass);
        userDao.createUser(driver);
        User user1 = userDao.getUser("(085)306-01-13");
    }


    @Test
    public void _07_deleteUser() throws SQLException {

        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        userDao.createUser(pass);
        userDao.createUser(driver);
        User user = userDao.delete("(093)306-01-13");
        assertTrue((user).equals(pass));
        assertTrue(userDao.delete("(063)306-01-13").equals(driver));
    }

    @Test(expected = SQLException.class)
    public void _08_deletePassenger() throws SQLException {
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        userDao.createUser(pass);
        userDao.delete("(093)306-01-13)");
        userDao.getUser("(093)306-01-13)");
    }

    @Test(expected = SQLException.class)
    public void _09_deleteDriver() throws SQLException {
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        userDao.createUser(driver);
        userDao.delete("(063)306-01-13");
        userDao.getUser("(063)306-01-13");
    }

    @Test(expected = SQLException.class)
    public void _10_deleteUserNegative() throws SQLException {
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        userDao.createUser(driver);
        userDao.delete("(666)306-01-13");
    }

    @Test
    public void  _11_updateUser() throws SQLException{
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
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

    @Test(expected = SQLException.class)
    public void  _12_updateUserNegative() throws SQLException{
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Driver driver = new Driver("(093)306-01-13", "0633060113", "Vasia", new Car("AA2222KK", "Vaz", "Baklazhan"));

        userDao.createUser(pass);
        userDao.update(driver);
    }

    @Test
    public void  _13_Count() throws SQLException{
        Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        Passenger pass1 = new Passenger("(055)306-01-13", "0553060113", "LLdrii", new Address("Entuziastiv", "35"));
        Driver driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222KK", "Vaz", "Baklazhan"));
        Driver driver1 = new Driver("(073)306-01-13", "063060113", "Kolia", new Car("MM2222KK", "Maz", "Baklazan"));
        Driver driver2 = new Driver("(083)306-01-13", "06360113", "Kolia", new Car("M22222KK", "Maz", "Baklhan"));

        userDao.createUser(pass);
        userDao.createUser(driver);
        userDao.createUser(pass1);
        userDao.createUser(driver1);
        userDao.createUser(driver2);
        int countP = userDao.passangerRegisteredQuantity();
        int countD = userDao.driverRegisteredQuantity();
        assertEquals(countP,2);
        assertEquals(countD,3);
    }

    @After
    public void clearBase(){

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

        ProcessBuilder pb = new ProcessBuilder(DaoConstants.SQL_REMOVE_TEST_SCRIPT);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
