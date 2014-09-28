package org.on.serviceregistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.on.global.DAO;

@Entity
@Table(name="SR_Associations")
public class Associations {
	
	private String applicationContext;
	private String associationType;
	@Id
	private String associationDn;
	private String entityType;
	private String entityDn;
	private String entityApplicationContext;
	private String assocEntityType;
	private String assocEntityDn;
	private String assocApplicationContext;
	private Date fromDate;
	private Date toDate;
	public String getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(String applicationContext) {
		this.applicationContext = applicationContext;
	}
	public String getAssociationType() {
		return associationType;
	}
	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}
	public String getAssociationDn() {
		return associationDn;
	}
	public void setAssociationDn(String associationDn) {
		this.associationDn = associationDn;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityDn() {
		return entityDn;
	}
	public void setEntityDn(String entityDn) {
		this.entityDn = entityDn;
	}
	public String getEntityApplicationContext() {
		return entityApplicationContext;
	}
	public void setEntityApplicationContext(String entityApplicationContext) {
		this.entityApplicationContext = entityApplicationContext;
	}
	public String getAssocEntityType() {
		return assocEntityType;
	}
	public void setAssocEntityType(String assocEntityType) {
		this.assocEntityType = assocEntityType;
	}
	public String getAssocEntityDn() {
		return assocEntityDn;
	}
	public void setAssocEntityDn(String assocEntityDn) {
		this.assocEntityDn = assocEntityDn;
	}
	public String getAssocApplicationContext() {
		return assocApplicationContext;
	}
	public void setAssocApplicationContext(String assocApplicationContext) {
		this.assocApplicationContext = assocApplicationContext;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public List<Entities> getEntities() {
		List<Entities> entityList = new ArrayList<Entities>();
		Key key = Key.prepareKey(this.entityApplicationContext, this.entityType, this.entityDn.toString());
		if(getEntityForKey(key) != null)
			entityList.add(getEntityForKey(key));
		key = Key.prepareKey(this.assocApplicationContext, this.assocEntityType, this.assocEntityDn);
		if(getEntityForKey(key) != null)
			entityList.add(getEntityForKey(key));
		return entityList;
			
	}
	
	private Entities getEntityForKey(Key key) {
		Criteria crit = DAO.getSession().createCriteria(Entities.class);
		crit.add(Restrictions.eq("applicationContext",key.getContext()));
		crit.add(Restrictions.eq("entityType", key.getType()));
		crit.add(Restrictions.eq("entityDn",key.getDn()));
		return (Entities)crit.uniqueResult();
	}
	
}
