package service;

import entity.Book;
import dao.BookDaoImpl;

import java.util.List;

public class BookService {
    private static BookDaoImpl bookDaoImpl;

    public BookService() {
        bookDaoImpl = new BookDaoImpl();
    }

    public void persist(Book entity) {
        bookDaoImpl.openCurrentSessionwithTransaction();
        bookDaoImpl.persist(entity);
        bookDaoImpl.closeCurrentSessionwithTransaction();
    }

    public void update(Book entity) {
        bookDaoImpl.openCurrentSessionwithTransaction();
        bookDaoImpl.update(entity);
        bookDaoImpl.closeCurrentSessionwithTransaction();
    }

    public Book findById(Long id) {
        bookDaoImpl.openCurrentSession();
        Book book = bookDaoImpl.findById(id);
        bookDaoImpl.closeCurrentSession();
        return book;
    }

    public void delete(Long id) {
        bookDaoImpl.openCurrentSessionwithTransaction();
        Book book = bookDaoImpl.findById(id);
        bookDaoImpl.delete(book);
        bookDaoImpl.closeCurrentSessionwithTransaction();
    }

    public List<Book> findAll() {
        bookDaoImpl.openCurrentSession();
        List<Book> books = bookDaoImpl.findAll();
        bookDaoImpl.closeCurrentSession();
        return books;
    }

    public void deleteAll() {
        bookDaoImpl.openCurrentSessionwithTransaction();
        bookDaoImpl.deleteAll();
        bookDaoImpl.closeCurrentSessionwithTransaction();
    }

    public List<Book> findByTitle(String title) {
        bookDaoImpl.openCurrentSession();
        List<Book> books = bookDaoImpl.findByTitle(title);
        bookDaoImpl.closeCurrentSession();
        return books;
    }

    public List<Book> findAllOrdered() {
        bookDaoImpl.openCurrentSession();
        List<Book> books = bookDaoImpl.findAllOrdered();
        bookDaoImpl.closeCurrentSession();
        return books;
    }

    public BookDaoImpl bookDaoImpl() {
        return bookDaoImpl;
    }
}
