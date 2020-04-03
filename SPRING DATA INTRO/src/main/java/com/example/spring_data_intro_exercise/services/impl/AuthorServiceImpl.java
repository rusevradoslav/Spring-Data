package com.example.spring_data_intro_exercise.services.impl;

import com.example.spring_data_intro_exercise.entities.Author;
import com.example.spring_data_intro_exercise.repositories.AuthorRepository;
import com.example.spring_data_intro_exercise.services.AuthorService;
import com.example.spring_data_intro_exercise.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.spring_data_intro_exercise.constants.GlobalConstants.AUTHORS_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;

    }


    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }
        String[] fileContent = this.fileUtil.readFileContent(AUTHORS_PATH);

        Arrays.stream(fileContent).forEach(r -> {
                    String[] tokens = r.split("\\s+");
                    Author author = new Author(tokens[0], tokens[1]);
                    authorRepository.saveAndFlush(author);
                }
        );


    }

    @Override
    public int getAllAuthorsCount() {

        return (int) authorRepository.count();
    }

    @Override
    public List<Author> getAllAuthorsByBookCount() {

        return this.authorRepository.getAllAuthorsOrderByCountOfBooksDesk();
    }

    @Override
    public Author findAuthorById(Long id) {
        return null;
    }

    @Override
    public List<Author> getAllAuthorsNamesEndsWith(String input) {

        return this.authorRepository.findAllByFirstNameEndingWith(input);
    }

}
