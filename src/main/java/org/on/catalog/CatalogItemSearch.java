package org.on.catalog;

import java.util.Date;

import org.on.global.selector.MapTo;
import org.on.global.selector.Operations;
import org.on.global.selector.Selectable;

public class CatalogItemSearch implements Selectable{
	@MapTo("itemCode")
	public String itemCode;
	@MapTo("itemType")
	public String type;
	@MapTo(value="fromDate",op=Operations.GE)
	public Date fromDate;
	@MapTo(value="toDate",op=Operations.LT)
	public Date toDate;
}
