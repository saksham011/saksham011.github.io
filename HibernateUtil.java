package com.cts.criteria.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.cts.criteria.hibernate.entity.Department;
import com.cts.criteria.hibernate.entity.Employee;

/**
 * @author imssbora
 */
public class HibernateUtil {

   private static StandardServiceRegistry registry;
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory == null) {
         try {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL,"jdbc:mysql://localhost:3306/test");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            registryBuilder.applySettings(settings);
            registry = registryBuilder.build();
            MetadataSources sources = new MetadataSources(registry)
                  .addAnnotatedClass(Department.class).addAnnotatedClass(Employee.class);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
         } catch (Exception e) {
            if (registry != null) {
               StandardServiceRegistryBuilder.destroy(registry);
            }
            e.printStackTrace();
         }
      }
      return sessionFactory;
   }

   public static void shutdown() {
      if (registry != null) {
         StandardServiceRegistryBuilder.destroy(registry);
      }
   }
}
