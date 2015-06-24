/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.test;

/// imports
import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.NeuronMPM;
import edu.gcsc.vrl.ug.api.Transformator;
import eu.mihosoft.vrl.system.VRL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @brief unit tests
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class MPMTest {
	/// private static members
	private final static String VRL_PROPERTY_FOLDER
		= "/Users/stephan/Temp/vrl2/";
	private final static int dim 
		= 3;


	/// static initializer for all required VRL plugins
	static {
		VRL.initAll(new String[]{"-install-plugin-help", "no", "-property-folder", VRL_PROPERTY_FOLDER});

	}

	/**
	 * @brief simple test of transformator and mapper
	 */
	@Test
	public void test() {
		F_InitUG.invoke(dim, new AlgebraType("CPU", 1));
		NeuronMPM mpm = new NeuronMPM();
		Transformator t = new Transformator();
		mpm.set_transformator(t);
		
		/**
		 * @todo test mapped values for given loaded geometry and 
		 *       stimulation protocol as well
		 */
	}

	/**
	 * @brief def ctor
	 */
	public MPMTest() {
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
}
