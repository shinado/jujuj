package framework.inj.impl;

import android.content.Context;
import android.view.View;
import android.widget.ToggleButton;

import java.util.HashMap;

import framework.inj.entity.utility.Transformable;
import framework.inj.exception.TypeNotSupportedException;

/**
 * 
 * @author ss
 * supported type: Integer, String, Boolean
 */
public class ToggleButtonBinder extends ViewBinder {

	@Override
	public String addParams(View view, HashMap<String, String> params,
			Object bean, String  fieldName, String packageName) throws Exception {
		if (view instanceof ToggleButton) {
			boolean isChecked = ((ToggleButton) view).isChecked();
			Object value = isChecked;
			if (bean instanceof Transformable) {
				//get value for model
				Object valueToServer = ((Transformable) bean).toServer(fieldName, isChecked);
				if(valueToServer != null){
					value = valueToServer;
				}
			}
			params.put(fieldName, value+"");
			return value+"";
		} else {
			return null;
		}
	}


	@Override
	public boolean setContent(Context context, View view, Object bean, String name, Object value, String packageName){
		if (view instanceof ToggleButton) {

			if (value instanceof Boolean) {
				((ToggleButton) view).setChecked((Boolean) value);
			} else {
				throw new TypeNotSupportedException("The type of the field is neither an Integer nor Boolean. In class " +
						bean.getClass().getName() + ", field " + name);
			}
			return true;
		} else {
			return false;
		}
	}
	
}
