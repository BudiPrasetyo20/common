/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.security.enumeration;

import com.dubby.base.enumeration.HasEnum;

public enum DataAccessRestriction implements HasEnum {

    AllBranch('A'),
    SelfBranch('S'),
    Subordinate('O'),
    Custom('C');
    
    private Character val;

    DataAccessRestriction(Character val) {
        this.val = val;
    }

    public static DataAccessRestriction valOf(Character val) {
        if (val.equals(AllBranch.getVal())) {
            return AllBranch;
        } else if (val.equals(SelfBranch.getVal())) {
            return SelfBranch;
        } else if(val.equals(Subordinate.getVal())) {
        	return Subordinate;
    	}else if (val.equals(Custom.getVal())) {
            return Custom;
        }

        return null;
    }

    public Character getVal() {
        return val;
    }

    @Override
    public String getDictionaryPrefix() {
        return "security.label.enum.data.access.restriction";
    }

}
