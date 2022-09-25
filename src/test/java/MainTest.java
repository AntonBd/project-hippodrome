import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

class MainTest {

    //3.1. Метод выполняется не дольше 22 секунд
    @Disabled
    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    public void mainWorksLess22Secs() throws Exception {
        Main.main(null);
    }

}
