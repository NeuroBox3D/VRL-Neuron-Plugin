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
	private final static String CONSOLE_APP_EXE = 
		"/Users/stephan/Temp/console_apps/export_console_app/run.sh";
	
	/**
	 * @brief ctor
	 */
	public MPMIntegration() {
	}
	
	/**
	 * 
	 */
	@BeforeClass
	public static void setUpClass() {
	}
	
	/**
	 * 
	 */
	@AfterClass
	public static void tearDownClass() {
	}
	
	/**
	 * 
	 */
	@Before
	public void setUp() {
	}
	
	/**
	 * 
	 */
	@After
	public void tearDown() {
	}
	
	@Test
	public void test() {
		
	}

	/**
	 * @brief integration test
	 * @author stephan <stephan@syntaktischer-zucker.de>
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
