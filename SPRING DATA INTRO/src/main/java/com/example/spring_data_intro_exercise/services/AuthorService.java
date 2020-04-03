package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    List<Author> getAllAuthorsByBookCount();

    Author findAuthorById(Long id);

    List<Author> getAllAuthorsNamesEndsWith(String input);

}
