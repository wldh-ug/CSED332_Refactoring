package postech.sample;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddTest {

    @Test
    public void testAdd() {
        assertTrue(3 + 5 == Add.add(3, 5));
        assertTrue(10 + 9 == Add.add(10, 9));
    }
}

