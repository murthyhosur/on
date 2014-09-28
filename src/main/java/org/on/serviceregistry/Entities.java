package org.on.serviceregistry;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.on.global.DAO;
@Entity
@Table(name="SR_Entities")
public class Entities {
	
	private String applicationContext;
	private String entityType;
	@Id
	private String entityDn;
	private Date fromDate;
	private Date toDate;
	
	public String getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(String applicationContext) {
		this.applicationContext = applicationContext;
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
	
	@SuppressWarnings("unchecked")
	public List<Associations> getAssociations() {
		Key key = Key.prepareKey(this.applicationContext, this.entityType, this.entityDn);
		Criteria crit = DAO.getSession().createCriteria(Associations.class);
		crit.add(Restrictions.eq("entityApplicationContext", key.getContext()));
		crit.add(Restrictions.eq("entityDn", key.getDn()));
		crit.add(Restrictions.eq("entityType", key.getType()));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EntityValues> getValues() {
		Key key = Key.prepareKey(this.applicationContext, this.entityType, this.entityDn);
		Criteria crit = DAO.getSession().createCriteria(EntityValues.class);
		crit.add(Restrictions.eq("entityApplicationContext", key.getContext()));
		crit.add(Restrictions.eq("entityDn", key.getDn()));
		crit.add(Restrictions.eq("entityType", key.getType()));
		return crit.list();
	}

}
