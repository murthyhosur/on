package org.on.global;

import java.util.List;

import org.hibernate.Query;
import org.on.catalog.CatalogGroup;
import org.on.catalog.CatalogGroupItems;
import org.on.catalog.CatalogItem;
import org.on.catalog.CatalogItemAttribute;
import org.on.catalog.CatalogItemRelation;
import org.on.catalog.Identifier;

public class Catalog {
	
	public static CatalogItem getItem(String itemCode) {
		CatalogItem catItem = (CatalogItem)DAO.getSession().get(CatalogItem.class, itemCode);
		return catItem;
	}
	
	@SuppressWarnings("unchecked")
	public static List<CatalogItemAttribute> getItemAttributes(CatalogItem item) {
		Query attributes = DAO.getSession().createQuery("FROM CatalogItemAttribute where item = :item");
		attributes.setParameter("item", item);
		return attributes.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<CatalogItemRelation> getItemRelations(CatalogItem item) {
		Query relations = DAO.getSession().createQuery("FROM CatalogItemRelation where itemRelatedFrom = :relatedFrom");
		relations.setParameter("relatedFrom", item);
		return relations.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<CatalogGroup> getItemGroups(CatalogItem item) {
		Query groups = DAO.getSession().createQuery("FROM CatalogGroup where item = :item");
		groups.setParameter("item", item);
		return groups.list();
	}
	
	public static CatalogGroup getItemGroup(CatalogItem item,String groupName) {
		Query group = DAO.getSession().createQuery("FROM CatalogGroup where item = :item and groupName = :groupName");
		group.setParameter("item", item);
		group.setString("groupName", groupName);
		return (CatalogGroup)group.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Identifier> getItemIdentifiers(CatalogItem item) {
		Query identifiers = DAO.getSession().createQuery("From Identifier where item = :item");
		identifiers.setParameter("item", item);
		return identifiers.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Identifier> getItemIdentifier(CatalogItem item,String systemId) {
		Query identifiers = DAO.getSession().createQuery("From Identifier where item = :item and systemId = :systemId");
		identifiers.setParameter("item", item);
		identifiers.setString("systemId", systemId);
		return identifiers.list();
	}
	
	public static boolean hasItemIdentifier(CatalogItem item,String systemId) {
		Query identifiers = DAO.getSession().createQuery("From Identifier where item = :item and systemId = :systemId");
		identifiers.setParameter("item", item);
		identifiers.setString("systemId", systemId);
		return identifiers.list().isEmpty() ? false : true;
	}
	
	@SuppressWarnings("unchecked")
	public static List<CatalogGroupItems> getGroupItems(CatalogGroup group,CatalogItem item) {
		Query groupItems = DAO.getSession().createQuery("From CatalogGroupItems where group = :group and item = :item");
		groupItems.setParameter("group", group);
		groupItems.setParameter("item", item);
		return groupItems.list();
	}
}
