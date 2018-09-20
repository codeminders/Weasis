package com.codeminders.demo.model;

public class Location {

    private final ProjectDescriptor parent;

	private final String name;
	private final String id;
	
	public Location(ProjectDescriptor parent, String name, String id) {
	    this.parent = parent;
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}

    public ProjectDescriptor getParent() {
        return parent;
    }

    @Override
	public String toString() {
		return id;
	}
}
