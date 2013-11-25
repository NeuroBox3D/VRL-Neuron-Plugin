/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;

/**
 *
 * @author stephangrein
 */
public interface IChannel {
    public void init();
    public void advance(double dt);
    public double flux(double Ca_i, double Ca_e, double Vm);
    public double dCa_dCa_i();
    public double dCa_dCa_e();
}
