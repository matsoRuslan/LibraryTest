package dao;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BookDaoImpl implements BookDao<Book, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    private static SessionFactory sessionFactory;

    public BookDaoImpl() {
        BookDaoImpl.sessionFactory = getSessionFactory();
    }

    public Session openCurrentSession() {
        currentSession = sessionFactory.openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = sessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(entity.Book.class);
        configuration.configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Book entity) {
        getCurrentSession().save(entity);
    }

    public void update(Book entity) {
        getCurrentSession().update(entity);
    }

    public Book findById(Long id) {
        Book book = (Book) getCurrentSession().get(Book.class, id);
        return book;
    }

    public void delete(Book entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        List<Book> books = (List<Book>) getCurrentSession().createQuery("from Book").list();
        return books;
    }

    public void deleteAll() {
        List<Book> entityList = findAll();
        for (Book entity : entityList) {
            delete(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> findByTitle(String title) {
        List<Book> books = (List<Book>) getCurrentSession().createQuery("from Book where title = '" + title + "'").list();
        return books;
    }

    @SuppressWarnings("unchecked")
    public List<Book> findAllOrdered() {
        List<Book> books = (List<Book>) getCurrentSession().createQuery("from Book order by author asc").list();
        return books;
    }
}
