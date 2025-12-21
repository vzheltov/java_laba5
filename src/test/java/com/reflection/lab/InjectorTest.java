package com.reflection.lab;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InjectorTest {

    @Test
    public void testInjector() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Injector injector = new Injector();
        SomeBean sb = injector.inject(new SomeBean());
        sb.foo();

        assertEquals("AC", outContent.toString());

        System.setOut(System.out);
    }
}