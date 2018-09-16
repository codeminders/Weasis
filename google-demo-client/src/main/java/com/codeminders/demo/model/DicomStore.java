package com.codeminders.demo.model;

public class DicomStore {

    private final Dataset parent;

    private final String name;

    public DicomStore(Dataset parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Dataset getParent() {
        return parent;
    }

    public ProjectDescriptor getProject() {
        return parent.getProject();
    }

    public Location getLocation() {
        return parent.getParent();
    }

}
