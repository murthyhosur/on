package org.on.global.selector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.on.global.DAO;

public abstract class TableSelector implements Selector {
	
	public List<Object> list = new ArrayList<Object>();
	
	public Selectable searchTable;
	
	private String propertyName;
	
	private Object value;
	
	private Map<Operations,Criterion> operationMap = new HashMap<Operations,Criterion>();
	
	@SuppressWarnings("rawtypes")
	protected TableSelector() {
		Constructor constructor;
		Annotation sClass = this.getClass().getAnnotation(SearchClass.class);
		String searchClassName = ((SearchClass) sClass).value();	
		try {
			constructor = Class.forName(searchClassName).getConstructor();
			searchTable = (Selectable)constructor.newInstance();
		} catch(Exception exp) {
			exp.printStackTrace();
		}
	}
	
	public void beforeAction(){};
	
	@SuppressWarnings("unchecked")
	public List<Object> search() {
		beforeAction();
		Annotation rClass = this.getClass().getAnnotation(ResultClass.class);
		String resultClassName = ((ResultClass) rClass).value();
		Criteria crit;
		try {
			crit = DAO.getSession().createCriteria(Class.forName(resultClassName));
			crit = addCriterias(crit,this.searchTable.getClass());
			this.list = crit.list();
		} catch(Exception exp) {
			exp.printStackTrace();
		}
		afterAction();
		return this.list;
	}
	
	private void mapOperations() {
		this.operationMap.put(Operations.EQ, Restrictions.eq(propertyName, value));
		this.operationMap.put(Operations.GE, Restrictions.ge(propertyName, value));
		this.operationMap.put(Operations.GT, Restrictions.gt(propertyName, value));
		this.operationMap.put(Operations.LE, Restrictions.le(propertyName, value));
		this.operationMap.put(Operations.LT, Restrictions.lt(propertyName, value));
	}
	
	@SuppressWarnings("rawtypes")
	private Criteria addCriterias(Criteria crit,Class table) throws IllegalAccessException {
		Field[] fields = table.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			Annotation mapTo = field.getAnnotation(MapTo.class);
			String property = ((MapTo)mapTo).value();
			Operations operation = ((MapTo)mapTo).op();
			this.propertyName = property;
			this.value = field.get(this.searchTable);
			if(this.value != null) {
				mapOperations();
				crit.add(this.operationMap.get(operation));
			}
		}
		if(table.getSuperclass() != null)
			crit = addCriterias(crit,table.getSuperclass());
		return crit;
	}
	
	public void afterAction(){};
}
