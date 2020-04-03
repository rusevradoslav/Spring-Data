package com.example.spring_data_intro_exercise.services.impl;

import com.example.spring_data_intro_exercise.entities.Author;
import com.example.spring_data_intro_exercise.entities.Book;
import com.example.spring_data_intro_exercise.entities.Category;
import com.example.spring_data_intro_exercise.entities.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.entities.enums.EditionType;
import com.example.spring_data_intro_exercise.repositories.BookRepository;
import com.example.spring_data_intro_exercise.services.AuthorService;
import com.example.spring_data_intro_exercise.services.BookService;
import com.example.spring_data_intro_exercise.services.CategoryService;
import com.example.spring_data_intro_exercise.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.spring_data_intro_exercise.constants.GlobalConstants.BOOKS_PATH;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] fileContent = fileUtil.readFileContent(BOOKS_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            String[] params = r.split("\\s+");

            Author author = this.getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);

            int copies = Integer.parseInt(params[2]);

            BigDecimal price = new BigDecimal(params[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

            String title = getTitle(params);

            Set<Category> categories = getRandomCategories();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(categories);
            this.bookRepository.saveAndFlush(book);
            System.out.println();
        });

    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();
        int boud = random.nextInt(3) + 1;

        Set<Category> categories = new HashSet<>();

        for (int i = 1; i <= boud; i++) {
            int categoryId = random.nextInt(8) + 1;
            categories.add(this.categoryService.getCategoryById((long) categoryId));
        }

        return categories;
    }

    private String getTitle(String[] param) {
        StringBuilder sb = new StringBuilder();

        for (int i = 5; i < param.length; i++) {
            sb.append(param[i]).append(" ");
        }
        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;
        return authorService.findAuthorById((long) randomId);
    }

    @Override
    public List<String> findAllTitles() {

        return null;
    }

    @Override
    public List<String> findAllAuthors() {
        return null;
    }

    @Override
    public List<String> findGeorgePowellBooks() {
        return null;
    }


    @Override
    public List<Book> getAllBooksAfter2000() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("31/12/2000", formatter);
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public List<Book> getAllAuthorsWithBooksBefore1990() {
        return this.bookRepository.findAllBookByReleaseDateDesc(LocalDate.of(1990, 12, 31));

    }

    @Override
    public List<Book> findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc() {
        List<Book> books = this.bookRepository.findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("Gorge", "Powell");
        return books;
    }

    @Override
    public List<Book> getAllByAgeRestriction(String ageRestriction) {
        return bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()));
    }

    @Override
    public List<Book> getAllByPriceLessThanOrPriceGreaterThan(double min, double max) {
        return bookRepository.getAllByPriceLessThanOrPriceGreaterThan(new BigDecimal(min), new BigDecimal(max));
    }

    @Override
    public List<Book> getAllBooksNotInYear(int year) {
        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);
        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
    }

    @Override
    public List<Book> getAllBooksBeforeYear(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return this.bookRepository.findAllByReleaseDateBefore(localDate);
    }

    @Override
    public List<Book> getAllByTitleContaining(String input) {
        return this.bookRepository.findAllByTitleContaining(input);
    }

    @Override
    public List<Book> getAllBooksAuthorsLastNameStartsWith(String str) {
        return this.bookRepository.findAllByAuthorLastNameEndsWith(str);
    }


    @Override
    public List<Book> getAllByEditionTypeAndCopiesLessThan(String editionType, int copies) {
        return bookRepository
                .findAllByEditionTypeAndCopiesLessThan(EditionType.valueOf(editionType.toUpperCase()), copies);
    }


    @Override
    public int getAllBooksByTitleSize(int num) {
        return this.bookRepository.countBooksWithNameLongerThan(num);
    }

    @Override
    public Integer findAllCopiesByAuthor(String fullName) {
        return this.bookRepository.findAllCopiesByAuthor(fullName);
    }

    @Override
    public List<Book> getAllInformationByTitle(String title) {
        return this.bookRepository.findAllByTitle(title);
    }

    @Override
    public int getCountOfAllBooksAfterGivenDate(String date, int number) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        return this.bookRepository.updateAllBooksAfterGivenDate(localDate,number);
    }

    @Override
    public int getDeletedBooksWitCopiesLowerThan(int number) {
        return this.bookRepository.deleteBooksByCopiesLessThan(number);
    }
}
