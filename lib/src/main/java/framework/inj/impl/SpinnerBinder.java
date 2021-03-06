package framework.inj.impl;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import framework.inj.entity.utility.Transformable;
import framework.inj.exception.TypeNotSupportedException;

/**
 * 
 * @author ss
 * supported type Map, String
 */
public class SpinnerBinder extends ViewBinder {

	@SuppressWarnings("unchecked")
	@Override
	public String addParams(View view, HashMap<String, String> params, Object bean, String fieldName, String packageName)
			throws Exception{
		if(view instanceof Spinner){
			String value;
			Spinner spinner = (Spinner) view;
			String key = spinner.getSelectedItem().toString();
			//TODO
			Object mapValue = null;
			if (bean instanceof Transformable) {
				Object valueToServer = ((Transformable) bean).toServer(fieldName, bean);
				if(valueToServer != null){
					mapValue = valueToServer;
				}
			}
			if(mapValue instanceof Map){
				Map<String, String> map = (Map<String, String>) mapValue;
				value = map.get(key);
				params.put(fieldName, value+"");
			}else{
				throw new TypeNotSupportedException("The type of the field is not a Map. In class " +
						bean.getClass().getName() + ", field " + fieldName);
			}
			return value;
		}else{
			return null;
		}
	}


	@Override
	public boolean setContent(Context context, View view, Object bean, String name, Object value, String packageName){
		if(view instanceof Spinner){

			if(value instanceof Map){
				Map<String, String> map = (Map<String, String>) value;
				Spinner spinner = (Spinner) view;
				java.util.Set<String> keySet = map.keySet();
				String[] items = new String[keySet.size()];
				for(int i=0; i<keySet.size(); i++){
					String key = (String) keySet.toArray()[i];
					items[i] = key;
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
						context, R.layout.simple_spinner_item, items);
				spinner.setAdapter(adapter);
			}else{
				throw new TypeNotSupportedException("The type of the field is not a Map. In class" +
						bean.getClass().getName() + ", field " + name);
			}
			return true;
		}else{
			return false;
		}
	}

}
