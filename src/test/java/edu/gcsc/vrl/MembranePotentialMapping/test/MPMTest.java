/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.test;

/// imports
import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.I_VDCC_BG_VM2UG_NEURON3d;
import edu.gcsc.vrl.ug.api.NeuronMPM;
import edu.gcsc.vrl.ug.api.Transformator;
import edu.gcsc.vrl.ug.api.VDCC_BG_VM2UG_NEURON3d;
import eu.mihosoft.vrl.system.PluginConfigurator;
import eu.mihosoft.vrl.system.VRL;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	
	private static final String hoc_geom 
		= "geom.hoc";
	
	private static final String hoc_stim 
		= "stim.hoc";


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
		
		
		ClassLoader classLoader = getClass().getClassLoader();
		File hoc_geom_file = new File(classLoader.getResource(hoc_geom).getFile());
		File hoc_stim_file = new File(classLoader.getResource(hoc_stim).getFile());
		
		t.setup_hoc(0d, 0d, 0d, 0d);
		
		
		/**
		 * @brief provide correct input file
		 */
		t.load_geom(hoc_geom_file.toString());
		t.load_stim(hoc_stim_file.toString());
	
		t.print_setup(true);
		
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
