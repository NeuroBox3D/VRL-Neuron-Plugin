/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.test;

/// imports
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @brief Testsuite for MembranePotentialMapping (unit and integration tests)
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {MPMTest.class, MPMIntegration.class})
public class MembranePotentialMappingTestSuite {

	/**
	 * @brief static setup classes
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	/**
	 * @brief static teardown classes
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * @brief non-static setup classes
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @brief non-static teardown classes
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
