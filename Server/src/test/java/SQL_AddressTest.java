import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.constants.DaoConstants;
import ua.taxi.dao.sql.AddressDao;
import ua.taxi.model.Order.Address;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii on 29.06.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SQL_AddressTest extends Assert{

    private AddressDao addressDao;

    @Before
    public void initTestSQL() {

        ProcessBuilder pb = new ProcessBuilder(DaoConstants.SQL_CREATE_TEST_SCRIPT);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addressDao = new AddressDao();
    }

    @Test
    public void _1_findById() throws SQLException {

        Address address = new Address("Address1", "1N");
        addressDao.create(address);
        addressDao.create(new Address("Address2", "2N"));

        Address findedAddress = addressDao.findById(1);
        assertEquals(findedAddress, address);
    }

    @Test(expected = SQLException.class)
    public void _2_findByIdNegative() throws SQLException {
        Address address = new Address("Address1", "1N");
        addressDao.create(address);
        addressDao.create(new Address("Address2", "2N"));
        addressDao.findById(3);
    }

    @Test
    public void _3_createAddress() throws SQLException {

        Address address = new Address("Address1", "1N");
        int id = addressDao.create(address);
        assertEquals(address, addressDao.findById(id));

        Address address1 = new Address("Address2", "2N");
        int id1 = addressDao.create(address1);
        assertEquals(address1, addressDao.findById(id1));
    }

    @Test(expected = SQLException.class)
    public void _4_createAddressNegative() throws SQLException {
        Address address = new Address("Address1", "111111111");
        addressDao.create(address);

    }

    @Test
    public void deleteAddress() throws SQLException {

        int id = addressDao.create(new Address("Address2", "2N"));
        assertTrue(addressDao.delete(id));
    }

    @Test
    public void deleteAddressNegative() throws SQLException {

        Address address = new Address("Address2", "2N");
        addressDao.create(address);
        assertFalse(addressDao.delete(5));
    }

    @Test
    public void getAll() throws SQLException {
        List<Address> list = new ArrayList<>();

        addressDao.create(new Address("Address", "N"));
        list.add(new Address("Address1", "1N"));
        list.add(new Address("Address2", "2N"));
        list.add(new Address("Address3", "3N"));
        addressDao.create(list.get(0));
        addressDao.create(list.get(1));
        addressDao.create(list.get(2));
        addressDao.create(new Address("Address4", "24"));

        assertEquals(list, addressDao.getAll(1, 3));
    }

    @Test
    public void getAllNegative() {
        List<Address> list = new ArrayList<>();

        try {
            addressDao.create(new Address("Address1", "1N"));
            list.add(new Address("Address2", "2N"));
            list.add(new Address("Address3", "3N"));
            list.add(new Address("Address4", "4N"));
            addressDao.create(list.get(0));
            addressDao.create(list.get(1));
            addressDao.create(list.get(2));
            addressDao.create(new Address("Address5", "5N"));
            assertEquals(addressDao.getAll(10, 5).size(), 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() throws SQLException {

        Address address = new Address("Address2", "2N");
        addressDao.create(new Address("Address3", "3N"));
        addressDao.create(new Address("Address4", "4N"));

        Address updatedAddress = addressDao.update(2, address);
        assertEquals(updatedAddress, address);
    }

    @Test
    public void updateNegative() throws SQLException {

        Address address = new Address("Address1", "1N");
        Address addressUpdated = new Address("Address2", "2N");
        addressDao.create(addressUpdated);
        addressDao.create(new Address("Address2", "2N"));
        assertNotEquals(addressDao.update(1, address), addressUpdated);
    }

    @After
    public void removeTestSQL() {

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
