/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;


import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 *
 * @author stephangrein
 */
@ComponentInfo(name="NCX", category="NeuronPlugin/Channels/AZ", description="creates channels from ion type selection")
public class NCX implements IChannel, Serializable {
        private static final long serialVersionUID=1L;
	private String identifier;
        private double density;
	private double flux;
	private int channels;
	private int Erev;
	private int g_max;
	// TODO: substitute that by PIMPL idiom (i. e. use pointer to private implementation of corresponding AMPA class in UG
	private double dCa_dCa_e;
	private double dCa_dCa_i;
	
        /**
         * @brief constructs one AMPA channel instance with given density
         * 
         * @param identifier
         * @param density 
         * 
         */
	/*public AMPA(@ParamInfo(name="identifier|density", style="", options="min=0; max=10000; invokeOnChange=true") String identifier, double density) {
            this.identifier = identifier;
            this.density = density;
            this.flux = 0.0; 
        }*/
        
        /**
         * @brief set up biophysics wrapper
         */
        @Override
	public void init() {
        }

        public void setup_biophysics(
		@ParamInfo(name="density", style="slider", options="min=0; max=100000; value=1; invokeOnChange=true") int channels,
	        @ParamInfo(name="reversal potential", style="slider", options="min=-200; max=200; value=0; invokeOnChange=true") int Erev,
	        @ParamInfo(name="absolute conductivity", style="slider", options="min=0; max=100; value=27; invokeOnChange=true") int g_max
		) {
		this.channels = channels;
		this.Erev = Erev;
		this.g_max = g_max;
	}
        
        /**
         * @brief advance one time step specified by dt
         * 
         * @param dt 
         */
        @Override
        public void advance(@ParamInfo(name="timestep", style="slider", options="min=0; max=10000; invokeOnChange=true") double dt) {
        }
        
        /**
         * @brief returns the flux in mole
         * @return 
         */
        @Override
	public double flux(
		@ParamInfo(name="Ca_i", options="value=0") double Ca_i,
		@ParamInfo(name="Ca_e", options="value=0") double Ca_o,
		@ParamInfo(name="V_m", options="value=0") double Vm) {
            return this.flux;
	}

	/**
	 * @brief returns the derivative wrt the internal Calcium concentration
	 * @return 
	 */
	@Override
    	public double dCa_dCa_i() {
		return this.dCa_dCa_i;
	}
	
	/**
	 * @brief returns the derivative wrt the external Calcium concentration
	 * @return 
	 */
	@Override
    	public double dCa_dCa_e() {
		return this.dCa_dCa_e;
	}
        
}
