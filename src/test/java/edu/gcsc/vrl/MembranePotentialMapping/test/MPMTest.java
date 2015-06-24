/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.test;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.MembranePotentialMapping;
import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.Mapper;
import edu.gcsc.vrl.ug.api.NeuronMPM;
import edu.gcsc.vrl.ug.api.Transformator;
import eu.mihosoft.vrl.system.VRL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @brief unit tests 
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class MPMTest {
	private final static String VRL_PROPERTY_FOLDER =
		"/Users/stephan/Temp/vrl2/";
	private final static int dim = 3;
	
	static {
		VRL.initAll(new String[]{"-install-plugin-help", "no", "-property-folder", VRL_PROPERTY_FOLDER});

	}

	/**
	 * @brief
	 */
	@Test
	public void test() {
		F_InitUG.invoke(dim, new AlgebraType("CPU", 1));
		NeuronMPM mpm = new NeuronMPM();
		Transformator t = new Transformator();
		mpm.set_transformator(t);
	}

	/**
	 * @brief ctor
	 */
	public MPMTest() {
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
}
