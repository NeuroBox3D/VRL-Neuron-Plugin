/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.test;

/// imports
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @brief integration tests
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class MPMIntegration {
	/// private static members
	private final static String CONSOLE_APP_EXE
		= "/Users/stephan/Temp/console_apps/export_console_app/run.sh";

	/**
	 * @brief def ctor
	 */
	public MPMIntegration() {
	}

	/**
	 * @brief setup static classes
	 */
	@BeforeClass
	public static void setUpClass() {
	}

	/**
	 * @brief teardown static classes
	 */
	@AfterClass
	public static void tearDownClass() {
	}

	/**
	 * @brief setup non-static classes
	 */
	@Before
	public void setUp() {
	}

	/**
	 * @brief teardown non-static classes
	 */
	@After
	public void tearDown() {
	}

	/**
	 * @brief simple integration test
	 */
	@Test
	@SuppressWarnings("CallToPrintStackTrace")
	public void integration() {
		try {
			Process ps = Runtime.getRuntime().exec(CONSOLE_APP_EXE);
			try {
				ps.waitFor();
			} catch (InterruptedException ex) {
				Logger.getLogger(MPMTest.class.getName()).log(Level.SEVERE, null, ex);
				Logger.getLogger(MPMTest.class.getName()).log(Level.SEVERE, null, ex);
			}
			assertEquals(ps.exitValue(), 0);
		} catch (IOException ioe) {
			Logger.getLogger(MPMTest.class.getName()).log(Level.SEVERE, null, ioe);
			Logger.getLogger(MPMTest.class.getName()).log(Level.SEVERE, null, ioe.getStackTrace());
		}
	}
}
