package com.hibernate.hibernateDemo.hibernateOMG.HIbernateUtility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.hibernate.hibernateDemo.hibernateOMG.entity.Book;

public class HibernateUtility {

	// StandardServiceRegistory is for Bootstrap hibernate or we can say to
	// initialize hibernate
	// its interface
	public static StandardServiceRegistry standardServiceRegistry;
	public static SessionFactory sessionFactory;
	static {
		try {
			if (sessionFactory == null) {
				// create StandardServiceRegistory
				// StandardServiceRegistryBuilder its implemetation class of
				// StandardServiceRegistory interface
				standardServiceRegistry = new StandardServiceRegistryBuilder()
											.configure()
//											.configure("hibernate.cfg.xml") // this file automatically/internally calling hibernate.cfg.xml file. no need to write 
											.build();

				// creating metadataSources
				MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
				// either you can map from here or you can map this file from hibernate,cfg.xml file  with mapping tag  
				metadataSources.addAnnotatedClass(Book.class);

				// creting metadata
				Metadata metadata = metadataSources.getMetadataBuilder().build();

				// create SessionFactory
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (standardServiceRegistry != null) {
				StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
			}
		}
	}

	// here we are returning sessionFactory object that we globally created
	// (good example of using static block)

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutDown() {
		if (standardServiceRegistry != null) {
			StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
		}
	}
}
