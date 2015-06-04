package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatalogEntry implements Serializable {

	private static final long serialVersionUID = 1221790962892568835L;
	private String id;
	private String name;
	private CatalogEntry parent;
	private List<CatalogEntry> children;

	public CatalogEntry() {
		super();
		children = new ArrayList<CatalogEntry>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CatalogEntry getParent() {
		return parent;
	}

	public void setParent(CatalogEntry parent) {
		if (this.parent != null)
			this.parent.children.remove(this);
		this.parent = parent;
		if (this.parent != null)
			this.parent.children.add(this);
	}

	public Iterator<CatalogEntry> getChildrenIterator() {
		return children.iterator();
	}
	
	public List<CatalogEntry> getParentList(){
		List<CatalogEntry> result = new ArrayList<CatalogEntry>();
		addParentToListRec(result);
		return result;
	}
	
	private void addParentToListRec(List<CatalogEntry> list){
		if (this.parent != null){
			parent.addParentToListRec(list);
			list.add(parent);
		}
	}
	
	public boolean hasChildren(){
		return this.children.size() >0;
	}
	
	public boolean isCourse(){
		return false;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof CatalogEntry))
			return false;
		CatalogEntry otherCatalogEntry = (CatalogEntry) other;
		if (this.id.equals(otherCatalogEntry.id))
			return true;
		return false;
	}
}
