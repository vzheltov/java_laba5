package com.reflection.lab;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    public void foo() {
        if (field1 != null)
            field1.doSomething();
        if (field2 != null)
            field2.doSomeOther();
    }
}