package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExTests {

    @ParameterizedTest
    @ValueSource(strings = {"text is short", "this text is not longer"})
    void shortNameTest(String text){
        int length = 15;
        int textLength = text.length();
        if(textLength > 15){
            assertTrue(textLength > length, "the text is no longer than the length: " +  length);
        } else {
            assertTrue(textLength < length, "the text is shorter than the specified length: " +  length);
        }
    }
}
