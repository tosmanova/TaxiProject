package ua.taxi.test;

import ua.taxi.model.Car;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Andrii on 5/12/2016.
 */
public class TestXML {

    public static void main(String[] args) {

        Car car = new Car("AA3225AI","White Lanos", "Gray");
        String path = "C:\\Java\\TaxiProject\\DataXML\\orderData.xml";
        try{
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Car.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(car, file);
            jaxbMarshaller.marshal(car, System.out);
            System.out.println();

            System.out.println(loadXML(path));


        }catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static Car loadXML(String path){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Car.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Car car = (Car) jaxbUnmarshaller.unmarshal(new File(path));
            return car;
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
