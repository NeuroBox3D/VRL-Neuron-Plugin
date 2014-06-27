/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.userdata;

import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;

/**
 *
 * @author stephan
 */
public interface LoadHOCFileObserver {
    public void update(HOCFileInfo data);
}
