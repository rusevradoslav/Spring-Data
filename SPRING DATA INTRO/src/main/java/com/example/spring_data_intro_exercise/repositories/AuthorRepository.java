package com.example.spring_data_intro_exercise.repositories;

        import com.example.spring_data_intro_exercise.entities.Author;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    //Last Homework
    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> getAllAuthorsOrderByCountOfBooksDesk();

    //exercise 6
    List<Author> findAllByFirstNameEndingWith(String input);


}
