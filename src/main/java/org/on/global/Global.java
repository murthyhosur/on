package org.on.global;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Global {

	@SuppressWarnings("rawtypes")
	public static List<Field> getAllFields(Class objClass,boolean recursion) {
		List<Field> fieldList = new ArrayList<Field>();
		Field fields[] = objClass.getDeclaredFields();
		for(Field field : fields) {
			fieldList.add(field);
		}
		if(recursion) {
			if(objClass.getSuperclass() != null)
				fieldList.addAll(getAllFields(objClass.getSuperclass(),recursion));
		}
		return fieldList;
	}
}
