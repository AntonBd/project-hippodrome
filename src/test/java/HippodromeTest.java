import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class  HippodromeTest {

    Throwable exception;
    Hippodrome hippodrome;

    //2.1. При передаче в конструктор null => IllegalArgumentException
    @Test
    public void constructorNullException() {
        assertThrows(IllegalArgumentException.class,
                    () -> hippodrome = new Hippodrome(null));
    }

    //2.2. При передаче в конструктор null => сообщение исключения "Horses cannot be null."
    @Test
    public void constructorNullMessage() {
        exception = assertThrows(IllegalArgumentException.class,
                () -> hippodrome = new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }


    //2.3. При передаче в конструктор пустого списка => IllegalArgumentException
    @Test
    public void emptyListToConstructorException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    //2.4. При передаче в конструктор пустого списка => сообщение исключения "Horses cannot be empty."
    @Test
    public void emptyListToConstructorMessage() {
        exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    //2.5. getHorses() возвращает список, идентичный переданному в конструктор
    @Test
    public void getHorsesReturnsListEqualOneInConstructor() {

        double speed = 2.0;
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            horses.add(new Horse("Лошадка-"+i, speed));
            speed += 0.1;
        }

        hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    //2.6. move() вызывает метод move у всех лошадей
    @Test
    @ExtendWith(MockitoExtension.class)
    public void moveCallsMoveAllHorses() {

        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse: horses) {
            Mockito.verify(horse).move();
        }
    }

    //2.7. getWinner() возвращает лошадь с самым большим значением distance
    @Test
    public void getWinnerReturnsTheFastestHorse() throws Exception {
        Horse horse1 = new Horse("Ромашка", 2.5, 1);
        Horse horse2 = new Horse("Ромашка", 2.5, 2);
        Horse horse3 = new Horse("Ромашка", 2.5, 3);
        Horse horse4 = new Horse("Ромашка", 2.5, 4);

        hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse4, hippodrome.getWinner());
    }

}