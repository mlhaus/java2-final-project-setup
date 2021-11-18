package com.hauschildt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private Car car;
    @BeforeEach
    void setUp() {
        car = new Car();
    }

    @Test
    void getLicensePlate() {
        String expected = Car.DEFAULT_LICENSE_PLATE;
        String actual = car.getLicensePlate();
        assertEquals(expected, actual);
    }

    @Test
    void getMake() {
        String expected = Car.DEFAULT_MAKE;
        String actual = car.getMake();
        assertEquals(expected, actual);
    }

    @Test
    void getModel() {
        String expected = Car.DEFAULT_MODEL;
        String actual = car.getModel();
        assertEquals(expected, actual);
    }

    @Test
    void getModelYear() {
        int expected = Car.DEFAULT_MODEL_YEAR;
        int actual = car.getModelYear();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        String expected = "Car{" +
                "licensePlate='" + Car.DEFAULT_LICENSE_PLATE + '\'' +
                ", make='" + Car.DEFAULT_MAKE + '\'' +
                ", model='" + Car.DEFAULT_MODEL + '\'' +
                ", modelYear=" + Car.DEFAULT_MODEL_YEAR +
                '}';
        String actual = car.toString();
        assertEquals(expected, actual);
    }

    @Test
    void setLicensePlate() {
        String expected = "AAA AAA";
        car.setLicensePlate(expected);
        String actual = car.getLicensePlate();
        assertEquals(expected, actual);
    }

    @Test
    void setMake() {
        String expected = "Chevrolet";
        car.setMake(expected);
        String actual = car.getMake();
        assertEquals(expected, actual);
    }

    @Test
    void setModel() {
        String expected = "Silverado";
        car.setModel(expected);
        String actual = car.getModel();
        assertEquals(expected, actual);
    }

    @Test
    void setModelYear() {
        int expected = LocalDate.now().minusYears(1).getYear();
        car.setModelYear(expected);
        int actual = car.getModelYear();
        assertEquals(expected, actual);
    }

    @Test
    void setLicensePlateLengthTooLong() {
        String badValue = "AAAAAAAAA";
        Exception exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setLicensePlate(badValue);
            }
        });
        String expectedMessage = "License Plate cannot have more than "
                + Car.MAXIMUM_LICENSE_PLATE_LENGTH + " characters.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setLicensePlateNull() {
        String badValue = null;
        Exception exception = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setLicensePlate(badValue);
            }
        });
        String expectedMessage = "License Plate cannot be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setMakeNull() {
        String badValue = null;
        Exception exception = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setMake(badValue);
            }
        });
        String expectedMessage = "Make cannot be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setModelNull() {
        String badValue = null;
        Exception exception = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setModel(badValue);
            }
        });
        String expectedMessage = "Model cannot be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setModelYearBelowMinimumBad() {
        int badValue = Car.MINIMUM_MODEL_YEAR - 1;
        Exception exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setModelYear(badValue);
            }
        });
        String expectedMessage = "Model Year cannot be earlier than "
                + Car.MINIMUM_MODEL_YEAR + ".";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setModelYearAboveMaximumBad() {
        int badValue = Car.MAXIMUM_MODEL_YEAR + 1;
        Exception exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                car.setModelYear(badValue);
            }
        });
        String expectedMessage = "Model year cannot be later than "
                + Car.MAXIMUM_MODEL_YEAR + ".";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void compareTo_Same() {
        Car car2 = new Car();
        assertTrue(car.compareTo(car2) == 0);
    }

    @Test
    void compareTo_ModelYearA_Before_ModelYearB() {
        Car car2 = new Car();
        car.setModelYear(1952);
        car2.setModelYear(1953);
        assertTrue(car.compareTo(car2) > 0);
    }

    @Test
    void compareTo_ModelYearA_After_ModelYearB() {
        Car car2 = new Car();
        car.setModelYear(1953);
        car2.setModelYear(1952);
        assertTrue(car.compareTo(car2) < 0);
    }

    @Test
    void compareTo_MakeA_Before_MakeB() {
        Car car2 = new Car();
        car.setMake("Chevrolet");
        car2.setMake("Ford");
        assertTrue(car.compareTo(car2) < 0);
    }

    @Test
    void compareTo_MakeA_After_MakeB() {
        Car car2 = new Car();
        car.setMake("Ford");
        car2.setMake("Chevrolet");
        assertTrue(car.compareTo(car2) > 0);
    }

    @Test
    void compareTo_ModelA_Before_ModelB() {
        Car car2 = new Car();
        car.setModel("Escape");
        car2.setModel("Explorer");
        assertTrue(car.compareTo(car2) < 0);
    }

    @Test
    void compareTo_ModelA_After_ModelB() {
        Car car2 = new Car();
        car.setModel("Explorer");
        car2.setModel("Escape");
        assertTrue(car.compareTo(car2) > 0);
    }

    @Test
    void compareTo_LicensePlateA_Before_LicensePlateB() {
        Car car2 = new Car();
        car.setLicensePlate("AAA AAA");
        car2.setLicensePlate("BBB BBB");
        assertTrue(car.compareTo(car2) < 0);
    }

    @Test
    void compareTo_LicensePlateA_After_LicensePlateB() {
        Car car2 = new Car();
        car.setLicensePlate("BBB BBB");
        car2.setLicensePlate("AAA AAA");
        assertTrue(car.compareTo(car2) > 0);
    }
}