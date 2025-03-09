package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book kobzar;
    private Book lisovaPisnya;
    private Book marusyaChuray;

    @BeforeEach
    public void setUp() {
        library = new Library();
        kobzar = new Book("Кобзар", "Тарас Шевченко");
        lisovaPisnya = new Book("Лісова пісня", "Леся Українка");
        marusyaChuray = new Book("Маруся Чурай", "Ліна Костенко");
    }

    @Test
    public void testAddBooksAndCount() {
        assertEquals(0, library.getBookCount(), "На початку бібліотека має бути порожньою");

        library.addBook(kobzar);
        assertEquals(1, library.getBookCount(), "Після додавання 1 книги має бути 1 книга");

        library.addBook(lisovaPisnya);
        assertEquals(2, library.getBookCount(), "Після додавання ще однієї книги має бути 2 книги");

        library.addBook(marusyaChuray);
        assertEquals(3, library.getBookCount(), "Після додавання 3 книг має бути 3 книги");
    }

    @Test
    public void testGetBooksContent() {
        library.addBook(kobzar);
        library.addBook(lisovaPisnya);

        List<Book> books = library.getBooks();
        assertEquals(2, books.size(), "Список має містити 2 книги");
        assertTrue(books.contains(kobzar), "Список має містити книгу 'Кобзар'");
        assertTrue(books.contains(lisovaPisnya), "Список має містити книгу 'Лісова пісня'");
    }

    @Test
    public void testRemoveBookValid() {
        library.addBook(kobzar);
        library.addBook(lisovaPisnya);
        library.addBook(marusyaChuray);

        boolean removed = library.removeBook(lisovaPisnya);
        assertTrue(removed, "Книга 'Лісова пісня' повинна бути успішно видалена");
        assertEquals(2, library.getBookCount(), "Після видалення має залишитись 2 книги");

        List<Book> books = library.getBooks();
        assertFalse(books.contains(lisovaPisnya), "Список не повинен містити 'Лісова пісня'");
    }

    @Test
    public void testRemoveBookNotPresent() {
        library.addBook(kobzar);

        boolean removed = library.removeBook(lisovaPisnya);
        assertFalse(removed, "Видалення книги, що не міститься в бібліотеці, має повернути false");
        assertEquals(1, library.getBookCount(), "Кількість книг має залишатися 1");
    }

    @Test
    public void testRemoveBookNull() {
        library.addBook(kobzar);
        boolean removed = library.removeBook(null);
        assertFalse(removed, "Видалення null має повернути false");
        assertEquals(1, library.getBookCount(), "Кількість книг має залишатись 1");
    }

    @Test
    public void testAddBookNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
        assertEquals("Book cannot be null", exception.getMessage());
    }

    @Test
    public void testGetBooksUnmodifiable() {
        library.addBook(kobzar);
        List<Book> books = library.getBooks();
        assertThrows(UnsupportedOperationException.class, () -> books.add(lisovaPisnya));
    }

    @Test
    public void testRemoveSameBookTwice() {
        library.addBook(kobzar);
        assertTrue(library.removeBook(kobzar), "Перше видалення має повернути true");
        assertFalse(library.removeBook(kobzar), "Друге видалення має повернути false");
        assertEquals(0, library.getBookCount(), "Бібліотека має бути порожньою після видалення");
    }

    @Test
    public void testAddDuplicateBooks() {
        library.addBook(kobzar);
        library.addBook(kobzar);  // Додамо ту саму книгу вдруге
        assertEquals(2, library.getBookCount(), "Додавання дублікатів має збільшувати кількість книг");
        assertTrue(library.removeBook(kobzar));
        assertEquals(1, library.getBookCount(), "Після видалення однієї копії має залишитись 1 книга");
    }
}
