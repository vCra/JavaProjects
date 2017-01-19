import static org.junit.Assert.*;

/**
 * Created by awalker on 16/01/2017.
 */
public class ExampleClass1Test {


    @Test
    public final void AddTest() {
        ExampleClass1 testClass = new AnExampleClass();
        int add = testClass.additionOfInts(200, 300);
        assertEquals(500, add);
    }

}