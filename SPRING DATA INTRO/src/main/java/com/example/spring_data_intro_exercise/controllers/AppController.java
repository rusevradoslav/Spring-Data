package com.example.spring_data_intro_exercise.controllers;

import com.example.spring_data_intro_exercise.entities.Book;
import com.example.spring_data_intro_exercise.services.AuthorService;
import com.example.spring_data_intro_exercise.services.BookService;
import com.example.spring_data_intro_exercise.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        //seed Data
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

//        int number = 4;
//        switch (number) {
//            case 1:
//                exerciseOne();
//                break;
//            case 2:
//                exerciseTwo();
//                break;
//            case 3:
//                exerciseThree();
//                break;
//            case 4:
//                exerciseFour();
//                break;
//        }


        // Exercises: Spring Data Advanced

// If you want to run some exercise just enter the number below
        int numberOfExercise = 13;

        switch (numberOfExercise) {
            case 1:
                booksTitlesByAgeRestriction();
                break;
            case 2:
                goldenBooks();
                break;
            case 3:
                booksByPrice();
                break;
            case 4:
                notReleasedBooks();
                break;
            case 5:
                booksReleasedBeforeDate();
                break;
            case 6:
                authorsSearch();
                break;
            case 7:
                booksSearch();
                break;
            case 8:
                bookTitlesSearch();
                break;
            case 9:
                countBooks();
                break;
            case 10:
                totalBookCopies();
                break;
            case 11:
                reducedBook();
                break;
            case 12:
                increaseBookCopies();
                break;
            case 13:
                removeBooks();
                break;
            case 14:
                break;
        }

    }

    private void removeBooks() throws IOException {
        System.out.println("Enter a number:");
        int numberOfBooks = this.bookService.
                getDeletedBooksWitCopiesLowerThan(Integer.parseInt(bufferedReader.readLine()));
        System.out.printf("%d number of books that were deleted!", numberOfBooks);
    }

    private void increaseBookCopies() throws IOException {
        System.out.println("Enter date and copies:");
        String date = bufferedReader.readLine();
        int copies = Integer.parseInt(bufferedReader.readLine());
        int totalCopies = this.bookService.getCountOfAllBooksAfterGivenDate(date, copies) * copies;

        System.out.println(totalCopies);
    }

    private void reducedBook() throws IOException {
        this.bookService.getAllInformationByTitle(bufferedReader.readLine())
                .stream().forEach(book -> System.out.printf("%s %s %s %.2f%n", book.getTitle(), book.getEditionType(), book.getAgeRestriction(), book.getPrice()));
    }

    private void totalBookCopies() throws IOException {
        System.out.println("Enter author name:");
        String name = this.bufferedReader.readLine();
        Integer num = this.bookService.findAllCopiesByAuthor(name);
        System.out.printf("%s %d%n", name, num);
    }

    private void countBooks() throws IOException {
        System.out.println("Enter Count: ");
        System.out.print(this.bookService
                .getAllBooksByTitleSize(Integer.parseInt(bufferedReader.readLine())));
        System.out.println();
    }

    private void bookTitlesSearch() throws IOException {
        System.out.println("Enter a string:");
        final List<Book> allBooksAuthorsLastNameStartsWith = this.bookService.getAllBooksAuthorsLastNameStartsWith(bufferedReader.readLine());
        //.stream().forEach(book ->
        //System.out.printf("%s (%s %s)%n",book.getTitle()
        //        ,book.getAuthor().getFirstName()
        //        ,book.getAuthor().getLastName()
        //        ));
        System.out.println();
    }

    private void booksSearch() throws IOException {
        System.out.println("Enter a string:");
        this.bookService.getAllByTitleContaining(bufferedReader.readLine())
                .stream()
                .forEach(book ->
                        System.out.printf("%s%n", book.getTitle()));
    }

    private void authorsSearch() throws IOException {
        System.out.println("Enter a input:");
        this.authorService.getAllAuthorsNamesEndsWith(bufferedReader.readLine())
                .stream().forEach(author ->
                System.out.printf("%s %s%n", author.getFirstName(), author.getLastName()));
    }

    private void booksReleasedBeforeDate() throws IOException {
        System.out.println("Enter a year:");
        this.bookService.getAllBooksBeforeYear(bufferedReader.readLine())
                .stream().forEach(book ->
                System.out.printf("%s %s %.2f%n", book.getTitle(), book.getEditionType(), book.getPrice()));
    }

    private void notReleasedBooks() throws IOException {
        System.out.println("Enter year:");
        this.bookService.getAllBooksNotInYear(Integer.parseInt(bufferedReader.readLine()))
                .stream().forEach(book ->
                System.out.printf("%s%n", book.getTitle()));
    }

    private void booksByPrice() {

        bookService.getAllByPriceLessThanOrPriceGreaterThan(5, 40)
                .stream()
                .forEach(book ->
                        System.out.printf("%s - $%.2f%n", book.getTitle(), book.getPrice()));

    }

    private void goldenBooks() {
        this.bookService
                .getAllByEditionTypeAndCopiesLessThan("gold", 5000)
                .stream()
                .forEach(book ->
                        System.out.printf("%s%n", book.getTitle()));
    }

    private void booksTitlesByAgeRestriction() throws IOException {
        System.out.println("Enter age restriction:");
        this.bookService
                .getAllByAgeRestriction(bufferedReader.readLine())
                .stream().forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void exerciseFour() {
        List<Book> books = this.bookService.findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc();
        books.forEach(book -> {
            System.out.printf("%s %s %d%n", book.getTitle(), book.getReleaseDate(), book.getCopies());
        });
        System.out.println();
    }

    private void exerciseThree() {
        this.authorService.getAllAuthorsByBookCount()
                .stream()
                .forEach(author ->
                        System.out.printf("%s %s %d%n",
                                author.getFirstName()
                                , author.getLastName(),
                                author.getBooks().size()));
    }

    private void exerciseTwo() {
        this.bookService.getAllAuthorsWithBooksBefore1990()
                .stream()
                .forEach(book ->
                        System.out.printf("%s %s%n", book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
    }

    private void exerciseOne() {
        this.bookService.getAllBooksAfter2000()
                .stream()
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }
}
