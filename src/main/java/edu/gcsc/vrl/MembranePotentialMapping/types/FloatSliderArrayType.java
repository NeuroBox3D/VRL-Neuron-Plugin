/// package's name
package edu.gcsc.vrl.typesincubation;

/// imports
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;
import groovy.lang.Script;
import java.lang.annotation.Annotation;

/**
 *
 * @author stephan
 */
@TypeInfo(type = Float.class, input = true, output = false, style = "slider-array")
public class FloatSliderArrayType extends ArrayBaseType {

	private static final long serialVersionUID = 1L;

	public FloatSliderArrayType() {
		setValueName("Float Slider Array:");
		setElementInputInfo(null);
	}

	/**
	 * @todo implement
	 * @param script 
	 */
	@Override
	public void evaluationRequest(Script script) {
		super.evaluationRequest(script);

		setElementInputInfo(new ParamInfo() {
			@Override
			public String name() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public String style() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public boolean nullIsValid() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public String options() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public String typeName() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});

		/**
		 * @todo on multi-methods branch we can do:
		 * setElementInputInfo(new DefaultParamInfo("", "default",
		 * "elemSize=" + elemRowSize, false));
		 */
	}

}
