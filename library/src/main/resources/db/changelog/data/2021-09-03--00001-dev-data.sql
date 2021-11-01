--liquibase formatted sql

--changeset pipko:2021-09-25--00001-all-data context:dev
INSERT INTO AUTHORS(id,name) VALUES(1, 'ProdAuthor1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'ProdAuthor2');
INSERT INTO AUTHORS(id,name) VALUES(3, 'ProdAuthor2');

--changeset pipko:2021-09-25--00002-all-data context:dev
INSERT INTO GENRES(id,name) VALUES(1, 'Thriller');
INSERT INTO GENRES(id,name) VALUES(2, 'Novel');
INSERT INTO GENRES(id,name) VALUES(3, 'Mystery');
INSERT INTO GENRES(id,name) VALUES(4, 'Historical');
INSERT INTO GENRES(id,name) VALUES(5, 'Fantasy');

--changeset pipko:2021-09-25--00003-all-data context:dev
INSERT INTO BOOKS(id,name) VALUES(1, 'Thriller');
INSERT INTO BOOKS(id,name) VALUES(2, 'Historical_Novel');
INSERT INTO BOOKS(id,name) VALUES(3, 'Mystery');
INSERT INTO BOOKS(id,name) VALUES(4, 'Historical_Thriller');
INSERT INTO BOOKS(id,name) VALUES(5, 'Fantasy_Novel');

INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(1,1 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(2,2 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(2,4 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(3,3 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(4,1 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(4,4 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(5,2 );
INSERT INTO BOOKS_GENRES(book_id,genre_id) VALUES(5,5 );

INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(1,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(1,2 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(2,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(3,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(4,1 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(4,2 );
INSERT INTO BOOKS_AUTHORS(book_id,author_id) VALUES(5,2 );

--changeset pipko:2021-09-29--00001-all-data context:dev
INSERT INTO COMMENTS(id, book_id, text) VALUES(1, 1, 'Comment on book 1 #1');
INSERT INTO COMMENTS(id, book_id, text) VALUES(2, 1, 'Comment on book 1 #2');
INSERT INTO COMMENTS(id, book_id, text) VALUES(3, 1, 'Comment on book 1 #3');
INSERT INTO COMMENTS(id, book_id, text) VALUES(4, 1, 'Comment on book 1 #4');
INSERT INTO COMMENTS(id, book_id, text) VALUES(5, 2, 'Comment on book 2 #1');
