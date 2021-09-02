INSERT INTO AUTHORS(id,name) VALUES(1, 'TestAuthor1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'TestAuthor2');

INSERT INTO GENRES(id,name) VALUES(1, 'TestGenre1');
INSERT INTO GENRES(id,name) VALUES(2, 'TestGenre2');

INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(1, 'TestBook1',1,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(2, 'TestBook2',1,2);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(3, 'TestBook3',2,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(4, 'TestBook4',2,2);