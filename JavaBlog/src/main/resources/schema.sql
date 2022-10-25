
DROP TABLE IF EXISTS BLOG_AUDIT;  

CREATE TABLE BLOG_AUDIT(
id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
blogPostId INT NOT NULL,
blogTitle VARCHAR(512) NOT NULL,  
blogBody VARCHAR(4000) NOT NULL,
createdBy int,
action smallint default 0, 
actionedTime TIMESTAMP);
 










