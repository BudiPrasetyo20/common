package com.dubby.security.model.entity;


import java.util.ArrayList;
import java.util.List;

public class RightsTreeItem extends Rights {

    private static final long serialVersionUID = 4017096121162970780L;

    private List<RightsTreeItem> child = new ArrayList<RightsTreeItem>();

	public List<RightsTreeItem> getChild() {
		return child;
	}

    public void setChild(List<RightsTreeItem> child) {
        this.child = child;
    }

    public int getLevel(){
		return getId().split("-").length;
	}
	
}
