package com.dubby.base.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.hibernate.SessionFactory;

public class HibernateAwareObjMapper extends ObjectMapper {

   public HibernateAwareObjMapper(SessionFactory sessionFactory){
       Hibernate4Module m=  new Hibernate4Module(sessionFactory);
       m.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING,true);
       registerModule(m);
   }
}
