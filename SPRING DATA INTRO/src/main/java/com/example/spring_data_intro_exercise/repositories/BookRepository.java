package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.entities.Book;
import com.example.spring_data_intro_exercise.entities.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.entities.enums.EditionType;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //Last Homework
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    //Last Homework
    @Query("SELECT b FROM Book AS b WHERE b.releaseDate < :releaseDate GROUP BY CONCAT(b.author.firstName, ' ', b.author.lastName)")
    List<Book> findAllBookByReleaseDateDesc(@Param("releaseDate") LocalDate localDate);
    //Last Homework
    //  @Query("select b from Book as b where b.author.firstName=:first and b.author.lastName=:last order by b.releaseDate desc , b.title asc ")
    List<Book> findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(@Param("first") String firstName, @Param("last") String lastName);

    //exercise 1
    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);
    //exercise 2
    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);
    //exercise 3
    List<Book> getAllByPriceLessThanOrPriceGreaterThan(BigDecimal min, BigDecimal max);
    //exercise 4
    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);
    //exercise 5
    List<Book> findAllByReleaseDateBefore(LocalDate date);
    //exercise 7
    List<Book> findAllByTitleContaining(String input);
    //exercise 8
    @Query(value = "SELECT b from Book b join b.author a where a.lastName like concat(:param,'%')")
    List<Book> findAllByAuthorLastNameEndsWith(@Param(value = "param") String scr);

    //exercise 9
    @Query(value = "SELECT count (b) from Book b where length(b.title) > :param")
    int countBooksWithNameLongerThan(@Param(value = "param") int param);

    //exercise 9 //WARNING - PROBLEM WITH OUTPUT
    @Query("SELECT sum(b.copies) FROM Book AS b WHERE concat(b.author.firstName,' ', b.author.lastName) = :name ")
    Integer findAllCopiesByAuthor(@Param("name") String fullname);

    //exercise 11
    List<Book> findAllByTitle(String title);

    //exercise 12
    @Modifying
    @Query("UPDATE Book b set b.copies=b.copies + :copies where b.releaseDate > :givenDate")
    int updateAllBooksAfterGivenDate(@Param(value = "givenDate") LocalDate date, @Param(value = "copies") int copies);


    //exercise 13
    @Modifying
    @Transactional
    @Query("delete from Book b where b.copies < :copies")
    int deleteBooksByCopiesLessThan(@Param(value = "copies") int number);

    //exercise 14


}


