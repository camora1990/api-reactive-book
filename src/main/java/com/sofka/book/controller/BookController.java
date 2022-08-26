package com.sofka.book.controller;

import com.sofka.book.model.Book;
import com.sofka.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/")
    public Flux<Book> getAllBook(){
        return bookService.getAllBook();
    }

    @GetMapping(value = "/presure/{nro}")
    public Flux<Book> getAllBookBackPresure(@PathVariable(name = "nro") Integer nro){
        return bookService.getAllBookBackPresure(nro);
    }

    @GetMapping(value = "/{id}")
    public Mono<Book> findById(@PathVariable(name = "id") String id){
        return bookService.findBy(id);
    }

    @PostMapping(value = "/")
    public Mono<Book> postBook(@RequestBody Book book){
        return bookService.postBook(book);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable(name = "id") String id
            ,@RequestBody Book book){
        return bookService.updateBook(id,book);

    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable(name = "id") String id){
        return bookService.deleteBook(id)
                .map(r-> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
