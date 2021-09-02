INSERT INTO AUTHORS(id,name) VALUES(1, 'ProdAuthor1');
INSERT INTO AUTHORS(id,name) VALUES(2, 'ProdAuthor2');

INSERT INTO GENRES(id,name) VALUES(1, 'ProdGenre1');
INSERT INTO GENRES(id,name) VALUES(2, 'ProdGenre2');

INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(1, 'ProdBook1',1,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(2, 'ProdBook2',1,2);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(3, 'ProdBook3',2,1);
INSERT INTO BOOKS(id,name,author_id, genre_id) VALUES(4, 'ProdBook4',2,2);