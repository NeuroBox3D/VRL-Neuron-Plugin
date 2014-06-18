/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.MembranePotentialMapping;

import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;



/**
 *
 * @author mbreit
 */
public class MembranePotentialMappingPluginConfigurator extends VPluginConfigurator{

    public MembranePotentialMappingPluginConfigurator() {
        //specify the plugin name and version
       setIdentifier(new PluginIdentifier("MembranePotentialMapping-Plugin", "0.1"));

       // optionally allow other plugins to use the api of this plugin
       // you can specify packages that shall be
       // exported by using the exportPackage() method:
       //
       // exportPackage("com.your.package");

       // describe the plugin
       setDescription("Plugin for calcium dynamics simulations with UG.");

       // copyright info
       setCopyrightInfo("MembranePotentialMapping-Plugin",
                        "(c) mbreit",
                        "www.g-csc.de", "Proprietary", "Proprietary");

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
