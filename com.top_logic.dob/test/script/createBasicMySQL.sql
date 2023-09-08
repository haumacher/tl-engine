CREATE TABLE IF NOT EXISTS BASIC (
 TYPE 		VARCHAR(70)  NOT NULL,
 IDENTIFIER VARCHAR(100) NOT NULL, 
 ATTR 		VARCHAR(70)  NOT NULL,
 VAL_TYPE 	TINYINT NOT NULL,
 LVAL 		BIGINT ,
 DVAL 		DOUBLE,
 SVAL 		VARCHAR(254),
 TVAL 		TEXT,

 PRIMARY KEY (TYPE, IDENTIFIER, ATTR)
);
