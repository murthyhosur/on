package org.on.serviceregistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Service {

	List<Associations> allAssoc = new ArrayList<Associations>();
	List<Entities> allEntities = new ArrayList<Entities>();
	Map<Entities,List<EntityValues>> entityValues = new HashMap<Entities,List<EntityValues>>();
}
