// package name
package edu.gcsc.vrl.MembranePotentialMapping;

// imports
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author sgrein adapted by mbreit
 */
public class MembranePotentialMappingPluginConfigurator extends VPluginConfigurator{

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
       }
   }

    @Override
   public void unregister(PluginAPI api) {
       // nothing to unregister
   }

    @Override
    public void init(InitPluginAPI iApi) {
       // nothing to init
   }
 }
