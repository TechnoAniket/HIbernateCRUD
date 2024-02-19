package com.hibernate.hibernateDemo.hibernateOMG;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.hibernateDemo.hibernateOMG.HIbernateUtility.HibernateUtility;
import com.hibernate.hibernateDemo.hibernateOMG.entity.Book;

// in this code save and update method is get depricaed 
// and for replacemet we got persist and merge

public class App {
    public static void main(String[] args) {
        try {
            // Save / Persist
            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    Book book = createBook();
                    Book book1 = createBook1();
                    session.persist(book);
                    session.persist(book1);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }

            // Find
            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    Book gotBook = session.find(Book.class, 1);
                    System.out.println(gotBook);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }

            // Update
            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    int bookid = 2;
                    Book findBook = session.find(Book.class, bookid);
                    findBook.setName("New book...");
                    session.merge(findBook);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }

            // Find All
            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    List<Book> findBook = session.createQuery("from Book", Book.class).list();
                    findBook.forEach(b -> {
                        System.out.println("print book name : " + b.getName());
                    });
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }

            // Delete
//            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
//                Transaction transaction = null;
//                try {
//                    transaction = session.beginTransaction();
//                    int bookId = 3;
//                    Book findBook = session.find(Book.class, bookId);
//                    session.remove(findBook);
//                    transaction.commit();
//                } catch (Exception e) {
//                    if (transaction != null) {
//                        transaction.rollback();
//                    }
//                    throw e;
//                }
//            }

            // Native SQL Query
            try (Session session = HibernateUtility.getSessionFactory().openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    String SQL = "SELECT VERSION()";
                    Object singleResult = session.createNativeQuery(SQL).getSingleResult();
                    String resultString = (String) singleResult;
                    System.out.println("SQL Version is : " + resultString);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Book createBook1() {
        Book book1 = new Book();
        book1.setName("Learn Hibernate");
        book1.setDescription("We can Learn it through practice only.");
        return book1;
    }

    private static Book createBook() {
        // Constructor Injection
        return new Book("Core Java", "Learn Java with Coding Example..");
    }
}
