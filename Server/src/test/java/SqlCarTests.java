
import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.constants.Constants;
import ua.taxi.dao.jdbcsql.CarDao;
import ua.taxi.model.user.Car;
import ua.taxi.utils.ConnectionFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrii on 27.06.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlCarTests extends Assert {

    private CarDao carDao;

    @BeforeClass
    public static void initTestSQL() {
        TestUtils.sriptRun(Constants.SQL_CREATE_TEST_SCRIPT);
    }

    @Before
    public void initDao() {
        carDao = TestUtils.getCarDao();
    }

    @Test
    public void _1_findById() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(car);
        carDao.create(new Car("BB1234AA", "Toyota", "Blue"));

        Car findedCar = carDao.findById(1);
        assertEquals(findedCar, car);
    }


    @Test(expected = SQLException.class)
    public void _2_findByIdNegative() throws SQLException {
        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(car);
        carDao.create(new Car("BB1234AA", "Toyota", "Blue"));
        carDao.findById(3);
    }

    @Test
    public void _3_createCar() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        int id = carDao.create(car);
        assertEquals(car, carDao.findById(id));

        Car car1 = new Car("BB1234AA", "Bently", "Blue");
        int id1 = carDao.create(car1);
        assertEquals(car1, carDao.findById(id1));
    }

    @Test(expected = SQLException.class)
    public void _4_createCarNegative() throws SQLException {
        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(car);
        carDao.create(car);
    }

    @Test
    public void deleteCar() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(car);
        assertTrue(carDao.delete(car));
        int id = carDao.create(new Car("BB1234AA", "Bently", "Blue"));
        assertTrue(carDao.delete(id));
    }

    @Test
    public void deleteCarNegative() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(car);

        assertFalse(carDao.delete(new Car("BB1234AA", "Bently", "Blue")));
        assertFalse(carDao.delete(5));
    }

    @Test
    public void getAll() throws SQLException {
        List<Car> list = new ArrayList<>();

        carDao.create(new Car("1B1234AA", "Toyota", "Blue"));
        list.add(new Car("2B2234AA", "Toyota", "Blue"));
        list.add(new Car("3B3234AA", "Toyota", "Blue"));
        list.add(new Car("4B4234AA", "Toyota", "Blue"));
        carDao.create(list.get(0));
        carDao.create(list.get(1));
        carDao.create(list.get(2));
        carDao.create(new Car("5B5234AA", "Toyota", "Blue"));

        assertEquals(list, carDao.getAll(1, 3));
    }

    @Test
    public void getAllNegative() {
        List<Car> list = new ArrayList<>();

        try {
            carDao.create(new Car("1B1234AA", "Toyota", "Blue"));
            list.add(new Car("2B2234AA", "Toyota", "Blue"));
            list.add(new Car("3B3234AA", "Toyota", "Blue"));
            list.add(new Car("4B4234AA", "Toyota", "Blue"));
            carDao.create(list.get(0));
            carDao.create(list.get(1));
            carDao.create(list.get(2));
            carDao.create(new Car("5B5234AA", "Toyota", "Blue"));
            assertEquals(carDao.getAll(10, 5).size(), 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        carDao.create(new Car("BB1234AA", "Toyota", "Blue"));
        carDao.create(new Car("1B1234AA", "Toyota", "Blue"));

        Car updatedCar = carDao.update(2, car);
        assertEquals(updatedCar, car);
    }

    @Test
    public void updateNegative() throws SQLException {

        Car car = new Car("AA1234AA", "Bently", "Blue");
        Car carUpdated = new Car("BB1234AA", "Toyota", "Blue");
        carDao.create(carUpdated);
        carDao.create(new Car("1B1234AA", "Toyota", "Blue"));
        assertNotEquals(carDao.update(1, car), carUpdated);
    }

    @After
    public void clearBase(){

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            statement.executeUpdate("TRUNCATE Cars");
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void removeTestSQL() {
        TestUtils.sriptRun(Constants.SQL_REMOVE_TEST_SCRIPT);
    }


}
