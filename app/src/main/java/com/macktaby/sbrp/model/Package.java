package com.macktaby.sbrp.model;

import java.io.Serializable;

public class Package implements Serializable {

    private int packageID;

    private String name;
    private String techReflection;
    private String mngReflection;
    private String bzReflection;

    private int parentID;

    public Package() {
        this.packageID = 0;
        this.name = "";
        this.techReflection = "";
        this.mngReflection = "";
        this.bzReflection = "";
        this.parentID = 0;
    }

    public Package(int packageID, String name, String techReflection, String mngReflection, String bzReflection,
                   int parentID) {
        this.packageID = packageID;
        this.name = name;
        this.techReflection = techReflection;
        this.mngReflection = mngReflection;
        this.bzReflection = bzReflection;
        this.parentID = parentID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechReflection() {
        return techReflection;
    }

    public void setTechReflection(String techReflection) {
        this.techReflection = techReflection;
    }

    public String getMngReflection() {
        return mngReflection;
    }

    public void setMngReflection(String mngReflection) {
        this.mngReflection = mngReflection;
    }

    public String getBzReflection() {
        return bzReflection;
    }

    public void setBzReflection(String bzReflection) {
        this.bzReflection = bzReflection;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

}
