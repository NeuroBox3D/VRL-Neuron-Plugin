// package name
package edu.gcsc.vrl.MembranePotentialMapping;

// imports
import edu.gcsc.vrl.MembranePotentialMapping.types.ClampArrayType;
import edu.gcsc.vrl.MembranePotentialMapping.types.ClampType;
import edu.gcsc.vrl.MembranePotentialMapping.types.LoadHOCFileStringType;
import edu.gcsc.vrl.MembranePotentialMapping.types.LoadHOCFileType;
import edu.gcsc.vrl.MembranePotentialMapping.types.SectionArrayType;
import edu.gcsc.vrl.MembranePotentialMapping.types.SectionType;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Clamp;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.PlotFile;
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
public class MembranePotentialMappingPluginConfigurator extends VPluginConfigurator{
	private File templateProjectSrc;
	private File templateProjectSrc2;

    public MembranePotentialMappingPluginConfigurator() {
        //specify the plugin name and version
       setIdentifier(new PluginIdentifier("VRL-MembranePotentialMapping-Plugin", "0.1"));

       // describe the plugin
       setDescription("Plugin for membrane potential mapping simulations with UG in VRL-Studio.");

       // copyright info
       setCopyrightInfo("VRL-MembranePotentialMapping-Plugin",
                        "(c) sgrein",
                        "www.g-csc.de", "Proprietary", "Proprietary");
       
       // set the icon of the plugin
      /* BufferedImage img = null;
       try {
	img = ImageIO.read(new File("img/neuro.jpg"));
	setIcon(img);
       } catch (IOException ioe) {
          eu.mihosoft.vrl.system.VMessage.warning("Error loading icon", ioe.toString());
       }*/
       
       
       // specify dependencies
       addDependency(new PluginDependency("VRL", "0.4.2", "0.4.2"));
       addDependency(new PluginDependency("VRL-UG", "0.2", "0.2"));
       addDependency(new PluginDependency("VRL-UG-API", "0.2", "0.2"));
       addDependency(new PluginDependency("VRL-UserData", "0.2", "0.2"));
    }
    
    @Override
    public void register(PluginAPI api) {

       // register plugin with canvas
       if (api instanceof VPluginAPI) {
           VPluginAPI vapi = (VPluginAPI) api;

           // Register visual components:
           //
           // Here you can add additional components,
           // type representations, styles etc.
           //
           // ** NOTE **
           //
           // To ensure compatibility with future versions of VRL,
           // you should only use the vapi or api object for registration.
           // If you directly use the canvas or its properties, please make
           // sure that you specify the VRL versions you are compatible with
           // in the constructor of this plugin configurator because the
           // internal api is likely to change.
           //
           // examples:
           //
           // vapi.addComponent(MyComponent.class);
           // vapi.addTypeRepresentation(MyType.class);
           
           vapi.addComponent(MembranePotentialMapping.class);
           vapi.addComponent(MembranePotentialMappingSolver.class);
	   
	   vapi.addTypeRepresentation(ClampType.class);
	   vapi.addTypeRepresentation(ClampArrayType.class);
	   vapi.addComponent(Clamp.class);
	   vapi.addTypeRepresentation(LoadHOCFileStringType.class);
	   vapi.addTypeRepresentation(LoadHOCFileType.class);
	   vapi.addComponent(HOCInterpreter.class);
	   
	   //vapi.addComponent(Section.class);
	   vapi.addTypeRepresentation(SectionType.class);
	   vapi.addTypeRepresentation(SectionArrayType.class);
	   
	   vapi.addComponent(HOCCommand.class);
	   vapi.addComponent(PlotFile.class);
       }
   }

    @Override
   public void unregister(PluginAPI api) {
       // nothing to unregister
   }

    
 @Override
    public void install(InitPluginAPI iApi) {
        // ensure template projects are updated
        new File(iApi.getResourceFolder(), "template-01.vrlp").delete();
	new File(iApi.getResourceFolder(), "template-02.vrlp").delete();
    }
	

	@Override
 	   public void init(InitPluginAPI iApi) {

        CompletionUtil.registerClassesFromJar(
                VJarUtil.getClassLocation(MembranePotentialMappingPluginConfigurator.class));
        
        initTemplateProject(iApi);
    }
    
	
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
                return "MPM Plugin Template Project 1";
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
                return "MPM Plugin Template Project 2";
            }

            @Override
            public BufferedImage getIcon() {
                return null;
            }
        });
	
	}
	
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
