package com.codeminders.demo.model;

public class Dataset {

    private Location parent;

    private final String name;

    public Dataset(Location parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Location getParent() {
        return parent;
    }

    public ProjectDescriptor getProject() {
        return parent.getParent();
    }
}
