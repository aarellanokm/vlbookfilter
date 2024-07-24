package com.knowmadmood.vlbookfilter.service;

import com.knowmadmood.vlbookfilter.model.Book;
import com.knowmadmood.vlbookfilter.model.BookWithPublicationDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class VLBookFilterService {

    private final String DATE_FORMAT = "MM-dd-yyyy";

    public Optional<BookWithPublicationDate> filter (String filter, List<Book> books){

        //escribe en pantalla los libros que no tengan fecha de publicación
        books.stream()
                .filter(book -> book.getPublicationDate() == null)
                .forEach(book -> System.out.println(book.getTitle() + " no tiene fecha de publicación"));

        //filtra, ordena y busca el libro más reciente según el filtro
        Optional<BookWithPublicationDate> bookMostRecent = books.stream()
                .filter(book -> book.getTitle().contains(filter) || book.getSummary().contains(filter) || book.getAuthor().getBio().contains(filter))
                .sorted(Comparator.comparing(Book::getPublicationDate, Comparator.nullsLast(LocalDate::compareTo)).thenComparingInt(book -> Optional.ofNullable(book.getAuthor().getBio()).orElse("").length()))
                .findFirst()
                .map(this::convertToBookPublicationDate);

        return bookMostRecent;
    }

    private BookWithPublicationDate convertToBookPublicationDate(Book book){

        //Crea String con la fecha formateada
        String dateFormatted = book.getPublicationDate() != null ? book.getPublicationDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) : "";

        return new BookWithPublicationDate(book.getId(), book.getTitle(), book.getPages(), book.getSummary(), book.getPublicationDate(), book.getAuthor(), dateFormatted);
    }

}
