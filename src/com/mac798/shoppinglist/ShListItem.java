package com.mac798.shoppinglist;


public class ShLisptItem implements Comparable {
	static String[] statusString = {"new", "cancelled", "done"};
	static RegEx strRegex = null;
	
	static {
		initRegex();
	}
	
	static void initRegEx() {
		StringBuilder s = new StringBuilder("(");
		s.append(statusString[0]);
		for(int i=1;i<statusString.length;i++) {
			s.append("|");
			s.append(statusString[i]);
		}
		s.append(")\\s+((.*)\\s*/|)\s*([^\\/]+)\\s*");
		strRegex = new RegEx(s.toString(), "i");
	}
	
	private String category;
	private String name;
	private int status;
	private ShoppingList list;
	
	public ShListItem(ShoppingList list, String category, String name, int status) {
		this.category = category;
		this.name = name;
		this.status = status;
		setParentList(list);
	}
	
	public ShListItem(ShoppingList list, String record) {
	}
	
	public ShListItem(ShoppingList list, String category, String name) {
		this(list, category, name, new ShListStatus (ShListStatus.NEW));
	}
	
	public ShListItem(ShoppingList list, String line) throws FormatException {
	String [] matches=null;
		if((matches = strRegex.match(line))!=null) {
			int st=0;
			for(st=0;i<statusString.length && !statusString[status].equals(matches[1]);i++);
			if (s<statusString.length) {
				this(list, matches[2], matches[3], st);
				return;
			}
		}
		throw(new FormatException("Line format exceptiin"));	
	}
	
	void setParentList(list) {
		if (this.list==list)
			return;
		if (this.list) {
			this.list.delete(this);
		}
		this.list = list;
		if (this.list) {
			this.list.add(this);
		}
	}
	
	void updateParent() {
		if (list!=null)
			list.itemUpdated(this);
	}
	
	public String getCategory() {
		return category;
	}
	
	piblic void setCategory(String newCategory) {
		if (category.equalsIgnorease(newCategory))
			return;
		category = newCategory;
		updateParent();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		if (name.equals(newName))
			return;
		name = newName;
		updateParent();
	}
	
	public String.toString() {
		StringBuilder s = new StringBuilder(statusString[status]);
		s.append("\t");
		if (category!=null) {
			s.append(category);
			s.append("/");
		}
		s.append(name);
		return s.toString();
	}
	
	public int compareTo(ShListItem itm) {
		int res=0;
		res = (category==null)?(itm.getCategory()==null):0:10):0;
		if (res!=0)
			return res;
		if ((res=category.compareToIgnoreCase(itm.getCategory()))!=0)
			return res;
		if (res=status.compareTo(itm.getStatus())!=0)
			return res;
		return name.compareTo(itm.getName);
	}
}