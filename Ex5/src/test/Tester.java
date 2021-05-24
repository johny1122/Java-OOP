package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        test.DirectoryProcessorTest.class,
        SecondaryTester.class
})

public class Tester {
}
