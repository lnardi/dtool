CREATE TABLE "logins"
(    
   "id" INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),   
   "docbase_host" VARCHAR(30),     
   "docbase" VARCHAR(20),     
   "login" VARCHAR(20),
   "password" VARCHAR(20),
   "creation_time" TIMESTAMP
);


CREATE TABLE "dql_queries"
(    
   "id" INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),   
   "query" VARCHAR(4000),     
   "dsc" VARCHAR(100),
	"query_hash" INTEGER, 
   "creation_time" TIMESTAMP
);

CREATE UNIQUE INDEX QUERY_HASH ON "dql_queries"("query_hash")


INSERT INTO luc.logins (host, docbase_host, login, password,creation_time) VALUES ('123123123', 'tstes', 'tesettest', 'asdfasdfasdf')

INSERT INTO LUC."logins" (host, docbase_host, login, password,creation_time) VALUES ('123123123', 'tstes', 'tesettest', 'asdfasdfasdf')

SELECT * FROM LUC."logins"

Select login, password from luc.logins where host = '172.21.20.9' and docbase = 'GPS04'

INSERT INTO LUC."logins" ("docbase_host", "docbase", "login", "password", "creation_time") VALUES ('172.21.20.9', 'RVD01', 'dctmadmin', 'Dctm@dmin65','2015-06-13 13:55:29.532')

update "logins" set "password" = 'Dctm@dmin65' where "docbase_host" = '172.21.20.9' and "docbase" = 'RVD01' and "login" = 'dctmadmin'

INSERT INTO LUC."logins" ("docbase_host", "docbase", "login", "password", "creation_time") VALUES ('172.21.20.9', 'RVD01', 'dctmadmin', 'Dctm@dmin65','2015-06-13 13:55:29.532')

INSERT INTO LUC."dql_queries" ("query", "creation_time") VALUES ('select * from dm_user','2015-06-13 13:55:29.532')

ALTER TABLE LUC."dql_queries"
ALTER COLUMN "query" SET DATA TYPE VARCHAR(4000)

ALTER TABLE LUC."dql_queries"
ALTER COLUMN "query" SET INCREMENT BY 1;