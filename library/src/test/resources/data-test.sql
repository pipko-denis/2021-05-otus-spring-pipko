INSERT INTO AUTHORS(id,name) VALUES(1, 'Author1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'Author2');

INSERT INTO GENRES(id,name) VALUES(1, 'Genre1');
INSERT INTO GENRES(id,name) VALUES(2, 'Genre2');

INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(1, 'Book1',1,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(2, 'Book2',1,2);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(3, 'Book3',2,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(4, 'Book4',2,2);