--liquibase formatted sql

--changeset pipko:2021-09-03--00001-all-data context:test
INSERT INTO AUTHORS(id,name) VALUES(1, 'Author1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'Author2');
INSERT INTO AUTHORS(id,name) VALUES(3, 'Author3');
INSERT INTO AUTHORS(id,name) VALUES(4, 'Author4');

--changeset pipko:2021-09-03--00002-all-data context:test
INSERT INTO GENRES(id,name) VALUES(1, 'Genre1');
INSERT INTO GENRES(id,name) VALUES(2, 'Genre2');
INSERT INTO GENRES(id,name) VALUES(3, 'Genre3');
INSERT INTO GENRES(id,name) VALUES(4, 'Genre4');

--changeset pipko:2021-09-03--00003-all-data context:test
INSERT INTO BOOKS(id,name) VALUES(1, 'Book1');
INSERT INTO BOOKS(id,name) VALUES(2, 'Book2');
INSERT INTO BOOKS(id,name) VALUES(3, 'Book3');
INSERT INTO BOOKS(id,name) VALUES(4, 'Book4');

--changeset pipko:2021-10-12--00001-all-data context:test
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(1,1 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(2,2 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(2,4 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(3,3 );

--changeset pipko:2021-10-12--00002-all-data context:test
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(1,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(1,2 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(2,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(3,1 );

--changeset pipko:2021-10-12--00003-all-data context:test
INSERT INTO COMMENTS(id, book_id, text) VALUES(1, 1, 'Comment on book 1 #1');
INSERT INTO COMMENTS(id, book_id, text) VALUES(2, 1, 'Comment on book 1 #2');
INSERT INTO COMMENTS(id, book_id, text) VALUES(3, 2, 'Comment on book 2 #1');