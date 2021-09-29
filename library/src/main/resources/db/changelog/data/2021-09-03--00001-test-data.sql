--liquibase formatted sql

--changeset pipko:2021-09-03--00001-all-data context:test
INSERT INTO AUTHORS(id,name) VALUES(1, 'Author1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'Author2');

--changeset pipko:2021-09-03--00002-all-data context:test
INSERT INTO GENRES(id,name) VALUES(1, 'Genre1');
INSERT INTO GENRES(id,name) VALUES(2, 'Genre2');

--changeset pipko:2021-09-03--00003-all-data context:test
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(1, 'Book1',1,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(2, 'Book2',1,2);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(3, 'Book3',2,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(4, 'Book4',2,2);

--changeset pipko:2021-09-29--00001-all-data context:test
INSERT INTO COMMENTS(id, book_id, text) VALUES(1, 1, 'Comment on book 1 #1');
INSERT INTO COMMENTS(id, book_id, text) VALUES(2, 1, 'Comment on book 1 #2');
INSERT INTO COMMENTS(id, book_id, text) VALUES(3, 2, 'Comment on book 2 #1');