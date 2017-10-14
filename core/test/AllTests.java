<<<<<<< HEAD
//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
//
///**
// * If you run this file as a JUnit test, it automatically runs all the tests.
// */
//
//@RunWith(Suite.class)
//@Suite.SuiteClasses(
//        {CollectableTests.class, GameTest.class, GUIElementTest.class,
//        LevelTest.class, PersistenceTest.class, PlayerTest.class,
//        SlimeTest.class, SpikeBlockTest.class, ViewTest.class
//        }
//)
//public class AllTests {
//
//}//commented out by jerm cause cbf
=======
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * If you run this file as a JUnit test, it automatically runs all the tests.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {CollectableTests.class, GameTest.class, GUIElementTest.class,
        LevelTest.class, PersistenceTest.class, PlayerTest.class,
        SlimeTest.class, SpikeBlockTest.class, //ViewTest.class
        }
)
public class AllTests {

}
>>>>>>> d19cb31d5dd42684da742c4f82ca4a73be89896d
