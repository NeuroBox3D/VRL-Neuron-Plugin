// package name
package edu.gcsc.vrl.MembranePotentialMapping;

// imports
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCCleanup;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCExecuteStatement;
import edu.gcsc.vrl.MembranePotentialMapping.types.ClampArrayType;
import edu.gcsc.vrl.MembranePotentialMapping.types.ClampType;
import edu.gcsc.vrl.MembranePotentialMapping.types.LoadHOCFileStringType;
import edu.gcsc.vrl.MembranePotentialMapping.types.LoadHOCFileType;
import edu.gcsc.vrl.MembranePotentialMapping.types.SectionArrayType;
import edu.gcsc.vrl.MembranePotentialMapping.types.SectionType;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Clamp;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCGeometryLoader;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCInterpreter;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCMembraneMechanismInserter;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCStimulationLoader;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCTimeStepper;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.IClamp;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.SEClamp;
import edu.gcsc.vrl.MembranePotentialMapping.util.PlotFile;
import edu.gcsc.vrl.MembranePotentialMapping.hoc.VClamp;
import edu.gcsc.vrl.MembranePotentialMapping.types.DoubleSliderType;
import eu.mihosoft.vrl.io.IOUtil;
import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.ProjectTemplate;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import eu.mihosoft.vrl.system.VRLPlugin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sgrein adapted by mbreit
 */
public class MembranePotentialMappingPluginConfigurator extends VPluginConfigurator {

	private File templateProjectSrc;
	private File templateProjectSrc2;

	public MembranePotentialMappingPluginConfigurator() {
		//specify the plugin name and version
		setIdentifier(new PluginIdentifier("VRL-MembranePotentialMapping-Plugin", "0.5"));

		// describe the plugin
		setDescription("Plugin for membrane potential mapping simulations and using the NEURON interpreter within UG in VRL-Studio.");

		// copyright info
		setCopyrightInfo("VRL-MembranePotentialMapping-Plugin",
			"(c) Stephan Grein",
			"www.g-csc.de", "Proprietary", "Proprietary");

		// export the package
		exportPackage("edu.gcsc.vrl.MembranePotentialMapping");

		// specify dependencies
		addDependency(new PluginDependency("VRL", "0.4.2", "0.4.2"));
		addDependency(new PluginDependency("VRL-UG", "0.2", "0.2"));
		addDependency(new PluginDependency("VRL-UG-API", "0.2", "0.2"));
		addDependency(new PluginDependency("VRL-UserData", "0.2", "0.2"));
	}

	@Override
	/**
	 * @brief register components
	 * @param api
	 */
	public void register(PluginAPI api) {

		// register plugin with canvas
		if (api instanceof VPluginAPI) {
			VPluginAPI vapi = (VPluginAPI) api;

			/// defaults
			vapi.addComponent(MembranePotentialMapping.class);
			vapi.addComponent(MembranePotentialMappingSolver.class);

			/// types
			vapi.addTypeRepresentation(ClampType.class);
			vapi.addTypeRepresentation(ClampArrayType.class);
			vapi.addTypeRepresentation(LoadHOCFileStringType.class);
			vapi.addTypeRepresentation(LoadHOCFileType.class);
			vapi.addTypeRepresentation(SectionType.class);
			vapi.addTypeRepresentation(SectionArrayType.class);
			vapi.addTypeRepresentation(DoubleSliderType.class);

			/// userdata
			vapi.addComponent(Clamp.class);

			/// util
			vapi.addComponent(PlotFile.class);

			/// hoc
			vapi.addComponent(HOCExecuteStatement.class);
			vapi.addComponent(HOCInterpreter.class);
			vapi.addComponent(HOCGeometryLoader.class);
			vapi.addComponent(HOCStimulationLoader.class);
			vapi.addComponent(HOCTimeStepper.class);
			vapi.addComponent(HOCCleanup.class);
			vapi.addComponent(HOCMembraneMechanismInserter.class);
			vapi.addComponent(IClamp.class);
			vapi.addComponent(VClamp.class);
			vapi.addComponent(SEClamp.class);

		}
	}

	@Override
	/**
	 * @brief unregister components
	 * @param iApi
	 */
	public void unregister(PluginAPI api) {
		// nothing to unregister
	}

	@Override
	/**
	 * @brief install plugins
	 * @param iApi
	 */
	public void install(InitPluginAPI iApi) {
		// ensure template projects are updated
		new File(iApi.getResourceFolder(), "template-01.vrlp").delete();
		new File(iApi.getResourceFolder(), "template-02.vrlp").delete();
	}

	/**
	 * @brief inits the plugin
	 * @param iApi
	 */
	@Override
	public void init(InitPluginAPI iApi) {

		CompletionUtil.registerClassesFromJar(
			VJarUtil.getClassLocation(MembranePotentialMappingPluginConfigurator.class));

		initTemplateProject(iApi);
	}

	/**
	 * @brief inits the template projects
	 * @param iApi
	 */
	private void initTemplateProject(InitPluginAPI iApi) {
		templateProjectSrc = new File(iApi.getResourceFolder(), "template-01.vrlp");
		templateProjectSrc2 = new File(iApi.getResourceFolder(), "template-02.vrlp");

		if (!templateProjectSrc.exists()) {
			saveProjectTemplate();
		}

		if (!templateProjectSrc2.exists()) {
			saveProjectTemplate();
		}

		iApi.addProjectTemplate(new ProjectTemplate() {

			@Override
			public String getName() {
				return "MPM Plugin - Template 1";
			}

			@Override
			public File getSource() {
				return templateProjectSrc;
			}

			@Override
			public String getDescription() {
				return "MPM Plugin Template Project 1 - Basic usage of the 1D/3D Hybrid Modeling Framework";
			}

			@Override
			public BufferedImage getIcon() {
				return null;
			}
		});

		iApi.addProjectTemplate(new ProjectTemplate() {

			@Override
			public String getName() {
				return "MPM Plugin - Template 2";
			}

			@Override
			public File getSource() {
				return templateProjectSrc2;
			}

			@Override
			public String getDescription() {
				return "MPM Plugin Template Project 2 - Basic usage of the NEURON interpreter within VRL-Studio";
			}

			@Override
			public BufferedImage getIcon() {
				return null;
			}
		});

	}

	/**
	 * @brief saves the project templates
	 */
	private void saveProjectTemplate() {
		InputStream in = MembranePotentialMappingPluginConfigurator.class.getResourceAsStream(
			"/edu/gcsc/vrl/MembranePotentialMapping/resources/projects/template-01.vrlp");
		try {
			IOUtil.saveStreamToFile(in, templateProjectSrc);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}

		InputStream in2 = MembranePotentialMappingPluginConfigurator.class.getResourceAsStream(
			"/edu/gcsc/vrl/MembranePotentialMapping/resources/projects/template-02.vrlp");
		try {
			IOUtil.saveStreamToFile(in2, templateProjectSrc2);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}
	}
}
