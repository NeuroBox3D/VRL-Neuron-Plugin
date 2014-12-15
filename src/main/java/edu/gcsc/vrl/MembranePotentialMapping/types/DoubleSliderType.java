/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.types;

/// imports
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.*;
import java.awt.Dimension;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import eu.mihosoft.vrl.visual.MessageBox;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import groovy.lang.Script;
import eu.mihosoft.vrl.system.VMessage;

/**
 * @brief type representation for Doubles respectively doubles
 *
 * @author stephan
 */
@TypeInfo(type = Double.class, input = true, output = false, style = "slider")
public final class DoubleSliderType extends TypeRepresentationBase {

	private static final long serialVersionUID = 1L;
	private Integer maxValue = 1000;
	private Integer minValue = 0;
	private Double step = 0.001;
	private JSlider input = new JSlider();
	private TypeRepresentationLabel valueLabel;

	/**
	 * @brief default ctor
	 */
	public DoubleSliderType() {
		VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
		setLayout(layout);

		valueLabel = new TypeRepresentationLabel(this, getMinValue().toString());
		nameLabel.setText("Double:");
		nameLabel.setAlignmentX(0.0f);
		this.add(nameLabel);

		int height = (int) this.input.getMinimumSize().getHeight();
		this.input.setPreferredSize(new Dimension(100, height));
		this.input.setMinimumSize(new Dimension(100, height));
		this.add(input);

		height = (int) this.valueLabel.getMinimumSize().getHeight();
		this.valueLabel.setPreferredSize(new Dimension(40, height));
		this.valueLabel.setMinimumSize(new Dimension(40, height));
		this.add(valueLabel);

		input.setMinimum(getMinValue());
		input.setMaximum(getMaxValue());
		input.setValue(getMinValue());

		setValueOptions("min=" + getMinValue() + ";max =" + getMaxValue() + ";step=" + getStepValue());

		input.setAlignmentX(0.0f);
		input.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Double value = Math.round(input.getValue() * step * 100) / 100.0d;
				valueLabel.setText(value.toString());
				setDataOutdated();
			}
		});
	}

	/**
	 * @brief empty view
	 */
	@Override
	public void emptyView() {
		input.setValue(minValue);
		valueLabel.setText(minValue.toString());
	}

	/**
	 * @param o
	 * @brief sets the view values
	 */
	@Override
	public void setViewValue(Object o) {
		Integer v = null;
		try {
			v = (Integer) o;
		} catch (Exception e) {
		}

		input.setValue(v);
		valueLabel.setText(o.toString());

		if (v < 0 || v > getMaxValue()) {
			getMainCanvas().getEffectPane().pulse(getConnector(),
				MessageType.WARNING_SINGLE);
		}
	}

	/**
	 * @return 
	 * @brief get view value
	 */
	@Override
	public Object getViewValue() {
		Integer o = null;
		try {
			o = new Integer(valueLabel.getText());
		} catch (Exception e) {
		}

		return o;
	}

	/**
	 * @brief evaluates script options
	 */
	@Override
	protected void evaluateContract() {
		Integer v = null;

		try {
			v = (Integer) value;
		} catch (Exception e) {
			VMessage.exception("DoubleSliderType", "Could not evaluate script options. Error: " + e);
		}

		// range condition
		if (v != null) {
			if (v >= getMinValue() && v <= getMaxValue()) {
				double transparency
					= getParentMethod().getParentObject().getParentWindow().
					getTransparency();

				if (!isHideConnector()) {
					getMainCanvas().getEffectPane().pulse(getConnector(),
						MessageType.INFO_SINGLE, transparency);
				}

			} else {

				MessageBox box = getMainCanvas().getMessageBox();
				String vString = "";

				if (v < getMinValue()) {
					v = getMinValue();
					vString = "Min=" + getMinValue();
				}

				if (v > getMaxValue()) {
					v = getMaxValue();
					vString = "Max=" + getMaxValue();
				}

				box.addUniqueMessage("Value out of range:",
					"DoubleSliderType: value does not meet range "
					+ "condition. Therefore value will be trimmed to "
					+ vString, getConnector(),
					MessageType.WARNING_SINGLE);
				setViewValue(v);
				value = v;
				validateValue();
			}
		}

	}

	/**
	 * @brief return maximum value
	 * @return 
	 */
	public final Integer getMaxValue() {
		return maxValue;
	}

	/**
	 * @brief set the maximum value
	 * @param maxValue
	 */
	public final void setMaxValue(Integer maxValue) {
		this.maxValue = (int) (maxValue / this.step);
		input.setMaximum((int) (maxValue / this.step));
	}

	/**
	 * @brief get the minimum value
	 * @return 
	 */
	public final Integer getMinValue() {
		return minValue;
	}

	/**
	 * @brief sets the minimum value
	 * @param minValue 
	 */
	public final void setMinValue(Integer minValue) {
		this.minValue = (int) (minValue / this.step);
		input.setMinimum((int) (minValue / this.step));
	}

	/**
	 * @brief sets the stepping value
	 * @param step
	 */
	public final void setStepValue(Double step) {
		this.step = step;
	}

	/**
	 * @brief gets the stepping value
	 * @return 
	 */
	public final Double getStepValue() {
		return this.step;
	}

	/**
	 * @brief evaluates script options on request
	 * @param script 
	 */
	@Override
	protected void evaluationRequest(Script script) {
		Object property = null;

		if (getValueOptions() != null) {

			if (getValueOptions().contains("min")) {
				property = script.getProperty("min");
			}

			if (property != null) {
				setMinValue((Integer) property);
			}

			property = null;

			if (getValueOptions().contains("max")) {
				property = script.getProperty("max");
			}

			if (property != null) {
				setMaxValue((Integer) property);
			}

			property = null;

			/*if (getValueOptions().contains("step")) {
				property = script.getProperty("step");
			}

			if (property != null) {
				setStepValue(((Number) property).doubleValue());
			}*/
		}
	}

	/**
	 * @brief gets the code for serialization
	 * @return
	 */
	@Override
	public String getValueAsCode() {
		String result = "null";

		Object o = getValue();

		if (o != null) {
			result = o.toString();
		}

		return result;
	}
}
