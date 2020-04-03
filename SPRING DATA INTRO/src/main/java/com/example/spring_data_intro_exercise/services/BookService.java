package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.entities.Book;
import com.example.spring_data_intro_exercise.entities.enums.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> findAllTitles();

    List<String> findAllAuthors();

    List<String> findGeorgePowellBooks();

    List<Book> getAllBooksAfter2000();

    List<Book> getAllAuthorsWithBooksBefore1990();

    List<Book> findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc();

    List<Book> getAllByAgeRestriction(String ageRestriction);


    List<Book> getAllByEditionTypeAndCopiesLessThan(String editionType, int copies);

    List<Book> getAllByPriceLessThanOrPriceGreaterThan(double min, double max);

    List<Book> getAllBooksNotInYear(int year);

    List<Book> getAllBooksBeforeYear(String date);


    List<Book> getAllByTitleContaining(String input);

    List<Book> getAllBooksAuthorsLastNameStartsWith(String str);

    int getAllBooksByTitleSize(int num);

    Integer findAllCopiesByAuthor(String fullName);

    List<Book> getAllInformationByTitle(String title);

    int getCountOfAllBooksAfterGivenDate(String date , int number);

    int getDeletedBooksWitCopiesLowerThan(int number);
}
