package org.example.homework.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.homework.exception.CustomNotFoundException;
import org.example.homework.model.Book;
import org.example.homework.model.request.BookRequest;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class BookRepository {

    @PersistenceContext
    private  final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Book createBook(BookRequest bookRequest) {
        Book book = new Book(
                null,
                bookRequest.getTitle(),bookRequest.getDescription(),bookRequest.getAuthor(),bookRequest.getPublicationYear());
        entityManager.persist(book);
        return book;
    }

    public Book getBookById(UUID id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new CustomNotFoundException("Book not found with ID: " + id);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT book FROM Book book ", Book.class).getResultList();
    }

    public void deleteBookById(UUID id) {
        Book book1 = entityManager.find(Book.class, id);
        if (book1 != null){
            entityManager.remove(book1);
        }else {
            throw new CustomNotFoundException("Book not found with ID: "+ id);
        }
    }


    public Book UpdateBookById(UUID id, BookRequest bookRequest) {
        Book book1 = entityManager.find(Book.class, id);
        if (book1 == null) {
            throw new CustomNotFoundException("Book not found with ID: " + id);
        }
        entityManager.detach(book1);
        book1.setTitle(bookRequest.getTitle());
        book1.setDescription(bookRequest.getDescription());
        book1.setAuthor(bookRequest.getAuthor());
        book1.setPublicationYear(bookRequest.getPublicationYear());
        entityManager.merge(book1);
        return book1;
    }


    public List<Book> getBooksByTitle(String title) {
        String query = "SELECT book FROM Book book WHERE LOWER(book.title) LIKE LOWER(:title)";
        List<Book> books = entityManager.createQuery(query, Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();

        if (books.isEmpty()) {
            throw new CustomNotFoundException("No books found with title: " + title);
        }

        return books;
    }

}
