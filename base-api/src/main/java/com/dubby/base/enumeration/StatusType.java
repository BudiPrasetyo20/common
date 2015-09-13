package com.dubby.base.enumeration;


public enum StatusType implements HasEnum {

	Active('A'),
	Inactive('I'),
    System('S'),
    Void('V'),
    WaitingForApproval('W');

	private Character val;

	StatusType(Character val){
		this.val = val;
	}

	public Character getVal() {
		return val;
	}

	public static StatusType valOf(Character val) {
		if (val.equals(Active.getVal())) {
			return Active;
		} else if (val.equals(Inactive.getVal())) {
			return Inactive;
		} else if (val.equals(WaitingForApproval.getVal())) {
			return WaitingForApproval;
		} else if (val.equals(System.getVal())) {
			return System;
		} else if (val.equals(Void.getVal())) {
			return Void;
		}
		return null;
	}

    @Override
    public String getDictionaryPrefix() {
        return "base.label.enum.statustype";
    }

}
