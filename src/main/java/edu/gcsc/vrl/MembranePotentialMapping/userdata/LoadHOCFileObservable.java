/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;
import edu.gcsc.vrl.ug.api.I_Transformator;
import edu.gcsc.vrl.ug.api.Transformator;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @todo how to handle the hoc interpreter?
 *       singleton instance for obversable?
 *       also I_Transformator should or must be singleton instance,
 *       for safety and consistency reasons also on tehe c++ counterpart
 *
 * @brief the hoc file observable
 * @author stephan grein <stephan.grein@gcsc.uni-frankfurt.de>
 */
public class LoadHOCFileObservable {
	/**
	 * @brief stores the observers and the 
	 * hoc file info from the specific file
	 */
	private class HOCFileTag {
		public Collection<LoadHOCFileObserver> observers = new HashSet<LoadHOCFileObserver>();
		public HOCFileInfo data = null;
	}

	/**
	 * @brief stores the hoc global tab
	 */
	private class HOCFileGlobalTag {
		public Collection<LoadHOCFileObserver> observers = new HashSet<LoadHOCFileObserver>();
	}

	/**
	 * @brief if multiple file loaders exist they are distinguished by the hoc_tag
	 * @param hoc_tag
	 * @param id
	 * @param object
	 */
	private class Identifier {
		private final String hoc_tag;
		private final Object object;
		private final int windowID;

		/**
		 * @brief default ctor
		 * @param hoc_tag
		 * @param object
		 * @param windowID 
		 */
		public Identifier(String hoc_tag, Object object, int windowID) {
			this.hoc_tag = hoc_tag;
			this.object = object;
			this.windowID = windowID;
		}
		
		/**
		 * @brief hashcode
		 */
		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + hoc_tag.hashCode();
			result = 37 * result + object.hashCode();
			result = 37 * result + windowID;
			return result;
		}

		/**
		 * @brief equals
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (obj.getClass() != getClass()) {
				return false;
			}

			LoadHOCFileObservable.Identifier rhs = (LoadHOCFileObservable.Identifier) obj;

			return (hoc_tag.equals(rhs.hoc_tag)) && (object == rhs.object) && (windowID == rhs.windowID);
		}
	}

	/**
	 * @brief get the HOC file tag
	 * @param hoc_tag
	 * @param object
	 * @param windowID
	 * @param create
	 */
	private synchronized HOCFileTag getTag(String hoc_tag, Object object, int windowID, boolean create) {
		LoadHOCFileObservable.Identifier id = new LoadHOCFileObservable.Identifier(hoc_tag, object, windowID);
		if (hoc_tags.containsKey(id)) {
			return hoc_tags.get(id);
		}
		if (create) {
			hoc_tags.put(id, new LoadHOCFileObservable.HOCFileTag());
			return getTag(hoc_tag, object, windowID, false);
		}

		return null;
	}

	/**
	 * @brief get the global file tag for the HOC file
	 * @param hoc_tag
	 * @param create
	 * @return
	 */
	private synchronized LoadHOCFileObservable.HOCFileGlobalTag getGlobalTag(String hoc_tag, boolean create) {
		if (globalTags.containsKey(hoc_tag)) {
			return globalTags.get(hoc_tag);
		}

		if (create) {
			globalTags.put(hoc_tag, new LoadHOCFileObservable.HOCFileGlobalTag());
			return getGlobalTag(hoc_tag, false);
		}

		return null;
	}

	/**
	 * @brief Add an observer to this Observable. The observer listens to a
	 * hoc_tag. The observer will be updated with the current data
	 * automatically.
	 *
	 * @see this is a window based hoc_tag, i. e. this is a window-local
	 * tag, for a global hoc_tag which listens to a single HOCFileObservable
	 * from different windows use the below addObserver method for global
	 * tags!
	 *
	 * @param obs the observer to add
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 */
	public synchronized void addObserver(LoadHOCFileObserver obs, String hoc_tag, Object object, int windowID) {
		getTag(hoc_tag, object, windowID, true).observers.add(obs);
		obs.update(getTag(hoc_tag, object, windowID, false).data);
	}

	/**
	 * @brief Add an observer to this Observable. The observer listens to a
	 * hoc_tag. The observer will be updated with the current data
	 * automatically.
	 *
	 * @param obs the observer to add
	 * @param hoc_tag the hoc_tag
	 */
	public synchronized void addObserver(LoadHOCFileObserver obs, String hoc_tag) {
		getGlobalTag(hoc_tag, true).observers.add(obs);

		for (Map.Entry<LoadHOCFileObservable.Identifier, LoadHOCFileObservable.HOCFileTag> entry : hoc_tags.entrySet()) {
			if (entry.getKey().hoc_tag.equals(hoc_tag)) {
				obs.update(entry.getValue().data);
			}
		}
        	/**
		 * @todo should we inform the listeners here?!
		 */
		// obs.update(getTag(hoc_tag, object, windowID, false).data);
	}

	/**
	 * @brief Removes an observer from this Observable
	 * 
	 * @param obs the observer to remove
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 */
	public synchronized void deleteObserver(LoadHOCFileObserver obs, String hoc_tag, Object object, int windowID) {
		LoadHOCFileObservable.Identifier id = new LoadHOCFileObservable.Identifier(hoc_tag, object, windowID);
		if (hoc_tags.containsKey(id)) {
			hoc_tags.get(id).observers.remove(obs);
		}
		if (globalTags.containsKey(hoc_tag)) {
			globalTags.get(hoc_tag).observers.remove(obs);
		}
	}

	/**
	 * @brief Removes an observer from this Observable
	 * 
	 * @param obs the observer to remove
	 */
	public synchronized void deleteObserver(LoadHOCFileObserver obs) {

		for (Map.Entry<LoadHOCFileObservable.Identifier, LoadHOCFileObservable.HOCFileTag> entry : hoc_tags.entrySet()) {
			entry.getValue().observers.remove(obs);
		}
		for (Map.Entry<String, LoadHOCFileObservable.HOCFileGlobalTag> entry : globalTags.entrySet()) {
			entry.getValue().observers.remove(obs);
		}
	}

	/**
	 * @brief Removes all observer of a hoc_tag from this Observable
	 * 
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 */
	public synchronized void deleteObservers(String hoc_tag, Object object, int windowID) {
		LoadHOCFileObservable.Identifier id = new LoadHOCFileObservable.Identifier(hoc_tag, object, windowID);
		if (hoc_tags.containsKey(id)) {
			hoc_tags.get(id).observers.clear();
		}
		for (Map.Entry<String, LoadHOCFileObservable.HOCFileGlobalTag> entry : globalTags.entrySet()) {
			entry.getValue().observers.clear();
		}
	}

	/**
	 * @brief Notifies all observers of a hoc_tag about the currently given data
	 * 
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 */
	public synchronized void notifyObservers(String hoc_tag, Object object, int windowID) {
		System.err.println("within notifyObservers");
		// get data for hoc_tag
		LoadHOCFileObservable.HOCFileTag hocTag = getTag(hoc_tag, object, windowID, false);
		if (hocTag == null) {
			System.err.println("hoc tag null -> therefore no update!");
		}
		// if no such hoc_tag present, return (i.e. no observer)
		if (hocTag != null) {

			// notify observers of this hoc_tag
			for (LoadHOCFileObserver b : hocTag.observers) {
				System.err.println("hoctag observers");
				b.update(hocTag.data);
				System.err.println("hoctag observers");
			}
		}

		// get data for global hoc_tag
		LoadHOCFileObservable.HOCFileGlobalTag hocGlobalTag = getGlobalTag(hoc_tag, false);

		// if no such hoc_tag present, return (i.e. no observer)
		if (hocGlobalTag == null) {
			System.err.println("No global observers present!");
		}

		if (hocGlobalTag != null) {

			// notify observers of this hoc_tag
			for (LoadHOCFileObserver b : hocGlobalTag.observers) {
				if (hocTag != null) {
					b.update(hocTag.data);
					System.err.println("b.update(hocTag.data) called");
				} else {
					System.err.println("b.update(null) called!");
					b.update(null);
				}
			}
		}
	}

	/**
	 * @brief Notifies a specific observer of a hoc_tag about 
	 * the currently given data
	 *
	 * @param obs the observer
	 * @param hoc_tag the hoc_tag
	 * @param object the object containing the observer
	 * @param windowID the window containing the object
	 */
	public synchronized void notifyObserver(LoadHOCFileObserver obs, String hoc_tag, Object object, int windowID) {
		// try getting data for hoc_tag
		LoadHOCFileObservable.HOCFileTag hocTag = getTag(hoc_tag, object, windowID, false);

		if (hocTag != null) {
			obs.update(hocTag.data);
		}
	}

	/**
	 * @brief sets a filename for a hoc_tag. The file will be analysed and the
	 * contained data will be broadcasted to all observer of the hoc_tag.
	 *
	 * @param file the file
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 * @return empty string if successful, error-msg if error occured
	 */
	public synchronized String setSelectedFile(File file, String hoc_tag, Object object, int windowID) {

		LoadHOCFileObservable.HOCFileTag hocTag = getTag(hoc_tag, object, windowID, true);

		if (!file.toString().endsWith(".hoc")) {
			setInvalidFile(hoc_tag, object, windowID);
			return "Invalid Filename: " + file.toString() + ". Must be *.hoc.";
		}
		
		/// populate the hoc file object
		ArrayList<String> names = new ArrayList<String>();
		transformator.load_geom(file.toString());
		String sections = transformator.get_section_names_as_string();
		/** @todo this could be certainly improved here */
		String[] sections_exploded = sections.split(";");
		List<String> finals = Arrays.asList(sections_exploded);
		for (String s : finals) {
			names.add(s);
		}
		System.err.println("Sections: " + sections);
		hocTag.data = new HOCFileInfo();
		hocTag.data.set_names_sections(names);
		hocTag.data.set_num_sections(names.size());

		// now we notify the obersver of this hoc_tag
		notifyObservers(hoc_tag, object, windowID);
		System.err.println("Notify Observers called");

		return "";
	}

	/**
	 * @brief Sets that a hoc_tag has an invalid file.
	 *
	 * @param hoc_tag the hoc_tag
	 * @param object
	 * @param windowID
	 */
	public synchronized void setInvalidFile(String hoc_tag, Object object, int windowID) {
		LoadHOCFileObservable.HOCFileTag hocTag = getTag(hoc_tag, object, windowID, true);

		// set to new (empty) data
		hocTag.data = null;

		// now we notify the obersver of this hoc_tag
		notifyObservers(hoc_tag, object, windowID);
	}

	// stores the file infos (i. e. sections etc)
	private final transient Map<Identifier, HOCFileTag> hoc_tags = new HashMap<Identifier, HOCFileTag>();
	private final transient Map<String, HOCFileGlobalTag> globalTags = new HashMap<String, HOCFileGlobalTag>();

	// singleton instance
	private static volatile LoadHOCFileObservable instance = null;

	// transformator instance (should be a singleton from cpp side TODO)
	private static final I_Transformator transformator = new Transformator();

	/**
	 * @brief private ctor for singleton
	 */
	private LoadHOCFileObservable() {

	}

	/**
	 * @brief returns the singleton instance
	 * @return instance
	 */
	public static LoadHOCFileObservable getInstance() {
		if (instance == null) {
			synchronized (LoadHOCFileObservable.class) {
				if (instance == null) {
					instance = new LoadHOCFileObservable();
				}
			}
		}
		return instance;
	}
}
