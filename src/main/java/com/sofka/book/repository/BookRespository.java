package com.sofka.book.repository;

import com.sofka.book.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRespository extends ReactiveMongoRepository<Book, String> {
}
