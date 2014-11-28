/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;

/**
 * @brief interface for observer pattern
 * @author stephan grein <stephan.grein@gcsc.uni-frankfurt.>
 */
public interface LoadHOCFileObserver {
    public void update(HOCFileInfo data);
}
