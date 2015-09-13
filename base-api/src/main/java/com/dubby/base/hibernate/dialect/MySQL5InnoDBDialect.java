package com.dubby.base.hibernate.dialect;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/14/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MySQL5InnoDBDialect extends org.hibernate.dialect.MySQL5InnoDBDialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_cs";
    }
}
