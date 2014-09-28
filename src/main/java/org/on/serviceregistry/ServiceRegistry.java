package org.on.serviceregistry;

import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.annotations.Parent;
import org.hibernate.criterion.Restrictions;
import org.on.global.Catalog;
import org.on.global.DAO;
import org.on.global.Global;
import org.on.orders.Basket;
import org.on.orders.BasketItem;
import org.on.orders.BasketItemCharacteristics;
import org.on.orders.Order;

public class ServiceRegistry {
	
	private static final String catalogApplicationContext = "CATALOG";
	private static final String externalApplicationContext = "EXTASSOC";
	private static final String locationApplicationContext = "LOCATION";
	private static final String ownerApplicationContext = "OWNER";
	private static final String locationAssocContext = "ADDRESS";
	private static final String ownerAssocContext = "CUSTOMER";

	public static void sendOrderToSR(Order order) {
		Basket basket = order.basket;
		List<BasketItem> items = basket.bItems;
		Service addService = new AddService();
		for(BasketItem item : items) {
			if(item.state.equals("KEEP"))
				continue;
			if(Catalog.hasItemIdentifier(item.getCatalogItem(), "SR")) {
				addService = (AddService)addEntites(item,addService);
				addService = (AddService)addAssociations(item,addService);
			}
		}
		saveAllServices(addService);
	}
	
	public static Order createOrderAndLoadServices(String customerId,String addressId) {
		Order order = new Order(customerId,addressId);
		Service customerService = new AddService();
		customerService.allAssoc.addAll(getAssociationsForCustAndAddr(customerId,addressId));
		List<BasketStructure> bStruct = new ArrayList<BasketStructure>();
		for(Associations assoc : customerService.allAssoc)
			bStruct.addAll(createBasketStructure(assoc,customerService));
		order = populateBasket(order,bStruct,null);
		return order;
	}

	private static void saveAllServices(Service addService) {
		for(Entities e : addService.allEntities) {
			DAO.saveObject(e);
			List<EntityValues> eValues = addService.entityValues.get(e);
			try {
			for(EntityValues eValue : eValues)
				DAO.saveObject(eValue); 
			} catch(Exception exp) {
				System.out.println("Exception!!! :O");
			}
		}
		for(Associations a : addService.allAssoc)
			DAO.saveObject(a);
	}

	private static Service addEntites(BasketItem item,Service service) {
		Entities srEntity = new Entities();
		srEntity.setEntityDn(item.guid.toString());
		srEntity.setApplicationContext(catalogApplicationContext);
		srEntity.setEntityType(item.getCatalogItem().getType());
		srEntity.setFromDate(new Date());
		service.allEntities.add(srEntity);
		try {
			addEntityValues(srEntity,item,service);
			addEntityValuesForCharacteristics(srEntity,item.characteristics,service);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return service;
	}

	private static void addEntityValuesForCharacteristics(Entities entityItem,
			List<BasketItemCharacteristics> characteristics, Service service) throws IllegalAccessException {
		
		List<Field> values = Global.getAllFields(BasketItemCharacteristics.class, true);
		if(service.entityValues.get(entityItem) == null)
			service.entityValues.put(entityItem, setEntityValuesForCharacterisitcs(entityItem,characteristics,values));
		else {
			List<EntityValues> valueList = service.entityValues.get(entityItem);
			valueList.addAll(setEntityValuesForCharacterisitcs(entityItem,characteristics,values));
			service.entityValues.put(entityItem, valueList);
		}
	}

	private static List<EntityValues> setEntityValuesForCharacterisitcs(
			Entities entityItem,
			List<BasketItemCharacteristics> characteristics, List<Field> values) {
		
		List<EntityValues> entityValues = new ArrayList<EntityValues>();
		Properties prop = new Properties();
		try {
			InputStream is = ServiceRegistry.class.getResourceAsStream("SR.properties");
			prop.load(is);
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(BasketItemCharacteristics charac : characteristics) {
			if(charac.value == null)
				continue;
			if(prop.getProperty(charac.attribute) != null && prop.getProperty(charac.attribute).equals("N"))
				continue;
			EntityValues eValue = new EntityValues();
			eValue.setEntityApplicationContext(catalogApplicationContext);
			eValue.setEntityType(entityItem.getEntityType());
			eValue.setEntityDn(entityItem.getEntityDn());
			eValue.setCharacteristic(charac.attribute);
			eValue.setValue(charac.value);
			eValue.setFromDate(new Date());
			entityValues.add(eValue);
		}
		return entityValues;
	}

	private static void addEntityValues(Entities entityItem,BasketItem item, Service service) throws IllegalAccessException {
		List<Field> values = Global.getAllFields(BasketItem.class, true);
		if(service.entityValues.get(entityItem) == null)
			service.entityValues.put(entityItem, setEntityValues(item,values));
		else {
			List<EntityValues> valueList = service.entityValues.get(entityItem);
			valueList.addAll(setEntityValues(item,values));
			service.entityValues.put(entityItem, valueList);
		}
	}

	private static List<EntityValues> setEntityValues(BasketItem item, List<Field> values) throws IllegalAccessException {
		List<EntityValues> entityValues = new ArrayList<EntityValues>();
		Properties prop = new Properties();
		try {
			InputStream is = ServiceRegistry.class.getResourceAsStream("SR.properties");
			prop.load(is);
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(Field value : values) {
			if(value.get(item) == null)
				continue;
			if(prop.getProperty(value.getName()) != null && prop.getProperty(value.getName()).equals("N"))
				continue;
			EntityValues evalue = new EntityValues();
			evalue.setEntityApplicationContext(catalogApplicationContext);
			evalue.setEntityDn(item.guid.toString());
			evalue.setEntityType(item.getCatalogItem().getType());
			evalue.setCharacteristic(value.getName());
			evalue.setValue(value.get(item).toString());
			evalue.setFromDate(new Date());
			entityValues.add(evalue);
		}
		return entityValues;
	}
	
	private static Service addAssociations(BasketItem item,Service service) {
		addAddressAssociation(item,service);
		addCustomerAssociation(item,service);
		for(BasketItem bItem : item.getAllChildren(false)) {
			if(Catalog.hasItemIdentifier(bItem.getCatalogItem(), "SR"))
				addItemAssociation(item,bItem,service);
		}
		return service;
	}

	private static void addAddressAssociation(BasketItem item, Service service) {
		Associations assoc = new Associations();
		assoc.setApplicationContext(externalApplicationContext);
		assoc.setAssociationDn(item.guid + "@" + item.addressId);
		assoc.setAssocApplicationContext(locationApplicationContext);
		assoc.setEntityApplicationContext(catalogApplicationContext);
		assoc.setEntityType(item.getCatalogItem().getType());
		assoc.setEntityDn(item.guid.toString());
		assoc.setAssocEntityType(locationAssocContext);
		assoc.setAssocEntityDn(item.addressId);
		assoc.setAssociationType(item.getCatalogItem().getType() + "_" + locationAssocContext);
		assoc.setFromDate(new Date());
		service.allAssoc.add(assoc);
	}
	
	private static void addCustomerAssociation(BasketItem item, Service service) {
		if(item.parentBasketItemId == null) {
			Associations assoc = new Associations();
			assoc.setApplicationContext(externalApplicationContext);
			assoc.setAssociationDn(item.guid + "@" + item.customerId);
			assoc.setAssocApplicationContext(ownerApplicationContext);
			assoc.setEntityApplicationContext(catalogApplicationContext);
			assoc.setEntityType(item.getCatalogItem().getType());
			assoc.setEntityDn(item.guid.toString());
			assoc.setAssocEntityType(ownerAssocContext);
			assoc.setAssocEntityDn(item.customerId);
			assoc.setAssociationType(item.getCatalogItem().getType() + "_" + ownerAssocContext);
			assoc.setFromDate(new Date());
			service.allAssoc.add(assoc);
		}
	}
	
	private static void addItemAssociation(BasketItem item, BasketItem bItem,
			Service service) {
		Associations assoc = new Associations();
		assoc.setApplicationContext(catalogApplicationContext);
		assoc.setAssociationDn(item.guid + "@" +bItem.guid);
		assoc.setAssocApplicationContext(catalogApplicationContext);
		assoc.setEntityApplicationContext(catalogApplicationContext);
		assoc.setEntityType(item.getCatalogItem().getType());
		assoc.setEntityDn(item.guid.toString());
		assoc.setAssocEntityType(bItem.getCatalogItem().getType());
		assoc.setAssocEntityDn(bItem.guid.toString());
		assoc.setAssociationType(item.getCatalogItem().getType() + "_" + bItem.getCatalogItem().getType());
		assoc.setFromDate(new Date());
		service.allAssoc.add(assoc);
	}
	
	@SuppressWarnings("unchecked")
	private static List<Associations> getAssociationsForCustAndAddr(String customerId,
			String addressId) {
		Key custKey = Key.prepareKey(ownerApplicationContext, ownerAssocContext, customerId);
		Key addrKey = Key.prepareKey(locationApplicationContext, locationAssocContext, addressId);
		List<Associations> assocList = new ArrayList<Associations>();
		Criteria critCust = DAO.getSession().createCriteria(Associations.class);
		critCust.add(Restrictions.eq("assocApplicationContext", custKey.getContext()));
		critCust.add(Restrictions.eq("assocEntityType", custKey.getType()));
		critCust.add(Restrictions.eq("assocEntityDn", custKey.getDn()));
		List<Associations> custAssocList = critCust.list();
		Iterator<Associations> custItr = custAssocList.iterator();
		while(custItr.hasNext()) {
			Associations assoc = custItr.next();
			Criteria critAddr = DAO.getSession().createCriteria(Associations.class);
			critAddr.add(Restrictions.eq("assocApplicationContext", addrKey.getContext()));
			critAddr.add(Restrictions.eq("assocEntityType", addrKey.getType()));
			critAddr.add(Restrictions.eq("assocEntityDn", addrKey.getDn()));
			critAddr.add(Restrictions.eq("entityDn", assoc.getEntityDn()));
			assocList.addAll(critAddr.list());
		}
		return assocList;
	}
	
	/*private static void createBasketStructure(BasketStructure bStruct,Associations assoc,Service customerService) {
		List<Entities> entities = assoc.getEntities();
		for(Entities entity : entities) {
			if(customerService.allEntities.contains(entity))
				continue;
			customerService.allEntities.add(entity);
			bStruct.setEntity(entity);
			bStruct.setEntityValues(entity.getValues());
			List<Associations> assocs = entity.getAssociations();
		}
		
	}*/
	
	private static List<BasketStructure> createBasketStructure(Associations assoc,Service customerService) {
		List<BasketStructure> basketStructures = new ArrayList<BasketStructure>();

		List<Entities> entities = assoc.getEntities();
		
		for(Entities entity : entities) {
			if(customerService.allEntities.contains(entity))
				continue;
			customerService.allEntities.add(entity);
			BasketStructure bStruct = new BasketStructure();
			bStruct.setEntity(entity);
			bStruct.setEntityValues(entity.getValues());
			List<Associations> assocs = entity.getAssociations();
			for(Associations childAssoc : assocs) {
				List<BasketStructure> struct = createBasketStructure(childAssoc, customerService);
				if(!struct.isEmpty())
					bStruct.getChildren().addAll(struct);
			}
			basketStructures.add(bStruct);
		}
		
		return basketStructures;
	}
	
	private static Order populateBasket(Order order,List<BasketStructure> bStruct,Long parentBasketItemId) {
		//Order order = new Order(customerId,addressId);
		//List<BasketItem> bItems = new ArrayList<BasketItem>();
		for(BasketStructure structure : bStruct) {
			BasketItem item = createItemFromStructure(structure,parentBasketItemId);
			item.customerId = order.customerId;
			item.addressId = order.addressId;
			item.basket = order.basket;
			for(BasketItemCharacteristics chars : item.characteristics)
				chars.basket = order.basket;
			order.basket.bItems.add(item);
			DAO.saveOrUpdate(item);
			if(!structure.getChildren().isEmpty())
				order = populateBasket(order,structure.getChildren(),item.basketItemId);
		}
		
		return order;
		
	}

	private static BasketItem createItemFromStructure(BasketStructure structure,Long parentBasketItemId) {
		BasketItem item = new BasketItem();
		if(structure.getEntity().getToDate() != null && structure.getEntity().getToDate().compareTo(new Date()) > 0)
			return null;
		item.itemCode = getValue("itemCode", structure).getValue();
		item.parentBasketItemId = parentBasketItemId;
		item.guid = Long.parseLong(structure.getEntity().getEntityDn());
		item.source = "SR";
		item.state = "KEEP";
		item.createdTime = new Date();
		for(EntityValues values : structure.getEntityValues()) {
			BasketItemCharacteristics chars = new BasketItemCharacteristics();
			chars.basketItem = item;
			chars.attribute = values.getCharacteristic();
			chars.value = values.getValue();
			item.characteristics.add(chars);
		}
		return item;
	}
	
	private static EntityValues getValue(String characteristic,BasketStructure structure) {
		for(EntityValues value : structure.getEntityValues()) {
			if(value.getCharacteristic().equals(characteristic)) {
				structure.getEntityValues().remove(value);
				return value;
			}
		}
		return null;
	}
}
