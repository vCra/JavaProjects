import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by awalker on 16/01/2017.
 */
class ExampleClass2Test {
    @org.junit.jupiter.api.Test
    void concatOfStrings() {
        ExampleClass1 testClass = new ExampleClass1();
        int add = testClass.additionOfInts(200, 300);
        assertEquals(500, add);
    }

    @org.junit.jupiter.api.Test
    void additionOfInts() {

    }

    @org.junit.jupiter.api.Test
    void isXOR() {

    }

    @org.junit.jupiter.api.Test
    void manualArray() {

    }

}