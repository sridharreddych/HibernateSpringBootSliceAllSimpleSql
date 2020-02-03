package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}


/*
 * 
 * 
 * 
 * How To Implement Simple Slice<T> findAll

Most probably this is all you want: How To Fetch Slice<entity>/Slice<dto> Via fetchAll/fetchAllDto

Story: Spring Boot provides an offset based built-in paging mechanism that returns a Page or Slice. Each of these APIs represents a page of data and some metadata. The main difference is that Page contains the total number of records, while Slice can only tell if there is another page available. For Page, Spring Boot provides a findAll() method capable to take as arguments a Pageable and/or a Specification or Example. In order to create a Page that contains the total number of records, this method triggers an SELECT COUNT extra-query next to the query used to fetch the data of the current page . This can be a performance penalty since the SELECT COUNT query is triggered every time we request a page. In order to avoid this extra-query, Spring Boot provides a more relaxed API, the Slice API. Using Slice instead of Page removes the need of this extra SELECT COUNT query and returns the page (records) and some metadata without the total number of records. So, while Slice doesn't know the total number of records, it still can tell if there is another page available after the current one or this is the last page. The problem is that Slice work fine for queries containing the SQL, WHERE clause (including those that uses the query builder mechanism built into Spring Data), but it doesn't work for findAll(). This method will still return a Page instead of Slice therefore the SELECT COUNT query is triggered for Slice<T> findAll(...);.

This implementation:

This is a thin implementation based on a hard-coded SQL: "SELECT e FROM " + entityClass.getSimpleName() + " e;"
Usage example:
public Slice<Author> fetchNextSlice(int page, int size) {
    return authorRepository.findAll(PageRequest.of(page, size));
}

Other implementations::

This is just another minimalist implementation based on CriteriaBuilder instead of hard-coded SQL
This is an implementation that allows us to provide a Sort, so sorting results is possible
This is an implementation that allows us to provide a Sort and a Spring Data Specification
This is an implementation that allows us to provide a Sort, a LockModeType, a QueryHints and a Spring Data Specification
This is an implementation that allows us to provide a Spring Data Pageable and/or Specification by extending the SimpleJpaRepository from Spring Data. Bascially, this implementation is the only one that returns Page<T> instead of Slice<T>, but it doesn't trigger the extra SELECT COUNT since it was eliminated by overriding the Page<T> readPage(...) method from SimpleJpaRepository. The main drawback is that by returing a Page<T> you don't know if there is a next page or the current one is the last. Nevertheless, there are workarounds to have this as well. In this implementation you cannot set LockModeType or query hints.
 * 
 */
