package com.sofka.book.services;

import com.sofka.book.model.Book;
import com.sofka.book.repository.BookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookService {

    @Autowired
    private  BookRespository bookRespository;

    public Flux<Book> getAllBook(){
        return bookRespository.findAll()
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    public Flux<Book> getAllBookBackPresure(Integer limitRequest){
        Flux<Book> bookFlux = bookRespository.findAll()
                .delayElements(Duration.ofSeconds(1))
                .log();
        return bookFlux.limitRate(limitRequest);
    }

    public Mono<Book> findBy(String id){
        return bookRespository.findById(id);
    }

    public Mono<Book> postBook(Book book){
        return bookRespository.save(book).log();
    }

    public Mono<ResponseEntity<Book>> updateBook(String id, Book book){
        return bookRespository.findById(id)
                .flatMap(oldBook->{
                    oldBook.setTitle(book.getTitle());
                    oldBook.setAuthor(book.getAuthor());
                    return bookRespository.save(oldBook);
                }).map(updateBook-> new ResponseEntity<>(updateBook, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.OK));
    }

    public Mono<Book> deleteBook(String id){
        return bookRespository.findById(id)
                .flatMap(deleteBook->bookRespository.delete(deleteBook)
                .then(Mono.just(deleteBook)));
    }

}
