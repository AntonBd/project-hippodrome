import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {
    Throwable exception;
    Horse horse;

    //1.1. Первый параметр конструктора - null => IllegalArgumentException
    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class,
                    () -> new Horse(null, 2.5, 1));
    }

    //1.2. Первый параметр конструктора - null => сообщение "Name cannot be null."
    @Test
    public void nullMessageException() {
        exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse(null, 2.5, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    //1.3. Первый параметр конструктора - пустая строка/пробельные символы  => IllegalArgumentException
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t", "\t\t\t\t", "\n", "\n\n", "\r", " \n   \r\r \t\n"})
    public void spaceCharacterException(String name) {
        assertThrows(IllegalArgumentException.class,
                    () -> new Horse(name, 2.5, 1));
    }

    //1.4. Первый параметр конструктора - пустая строка/пробельные символы => сообщение "Name cannot be blank."
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t", "\t\t\t\t", "\n", "\n\n", "\r", " \n   \r\r \t\n"})
    public void spaceCharacterMessageException(String name) {
        exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse(name, 2.5, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    //1.5. Второй параметр конструктора - отрицательное число => IllegalArgumentException
    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Мурка", -5, 1));
    }

    //1.6. Второй параметр конструктора - отрицательное число => сообщение "Speed cannot be negative."
    @Test
    public void negativeSpeedMessage() {
        exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse("Зорька", -12, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    //1.7. Третий параметр конструктора - отрицательное число => IllegalArgumentException
    @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Милка", 2.5, -1));
    }


    //1.8. Третий параметр конструктора - отрицательное число => сообщение "Distance cannot be negative."
    @Test
    public void negativeDistanceMessage() {
        exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Horse("Пряник", 2.7, -3));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    //1.9. Метод getName возвращает строку, переданную первым параметром в конструктор
    @Test
    public void getNameIsFirstParam() throws NoSuchFieldException, IllegalAccessException {
        horse = new Horse("Крендель", 2.8, 3);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals(nameValue, horse.getName());
    }

    //1.10. Метод getSpeed возвращает число, переданное вторым параметром в конструктор
    @Test
    public void getSpeedIsSecondParam() throws IllegalAccessException, NoSuchFieldException {
        horse = new Horse("Неспешный", 2.0, 5);
        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        double speedValue = (double) speed.get(horse);
        assertEquals(speedValue, horse.getSpeed());
    }

    //1.11. Метод getDistance возвращает число, переданное третьим параметром в конструктор
    @Test
    public void getDistanceIsThirdParam() throws IllegalAccessException, NoSuchFieldException {
        horse = new Horse("Пятнистый", 2.6, 3);
        Field dist = Horse.class.getDeclaredField("distance");
        dist.setAccessible(true);
        double distValue = (double) dist.get(horse);
        assertEquals(distValue, horse.getDistance());
    }

    //1.12. Метод getDistance возвращает 0, если в конструкторе - 2 параметра
    @Test
    public void getDistanceIsZero() {
        horse = new Horse("Простоконь", 2.6);
        assertEquals(0, horse.getDistance());
    }

    //1.13. Метод move() вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9
    @Test
    @ExtendWith(MockitoExtension.class)
    public void moveCallGetRandomDouble() {
        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Пятнистый", 2.6, 3).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

}
