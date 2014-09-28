package org.on.serviceregistry;

import java.util.ArrayList;
import java.util.List;

public class BasketStructure {

	private Entities entity;
	private List<EntityValues> entityValues;
	private List<BasketStructure> children;
	
	public BasketStructure() {
		children = new ArrayList<BasketStructure>();
	}
	public Entities getEntity() {
		return entity;
	}
	public void setEntity(Entities entity) {
		this.entity = entity;
	}
	public List<EntityValues> getEntityValues() {
		return entityValues;
	}
	public void setEntityValues(List<EntityValues> entityValues) {
		this.entityValues = entityValues;
	}
	public List<BasketStructure> getChildren() {
		return children;
	}
	public void setChildren(List<BasketStructure> children) {
		this.children = children;
	}
	
	
}
