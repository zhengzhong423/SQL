create table userS(UserID varchar(10), Name VARCHAR(20), Email varchar(20), Country varchar(20), Zip varchar(10), RegistrationDate Date, RegistrationTime Time, Status varchar(10), PRIMARY KEY(UserID));

create table groups(GroupID VARCHAR(10), GroupName varchar(50), primary key(GroupID));

create table company(CompanyID VARCHAR(10), CompanyName varchar(10), primary key(CompanyID));

create table post(PostID varchar(10), SenderID VARCHAR(10), ReceiverID varchar(10), PostType varchar(10), content varchar(200), ShareType varchar(10), AttachedResource varchar(10), DateTime Datetime, PRIMARY KEY(PostID));

create table comment(CommentID VARCHAR(10), SenderID varchar(10), PostID VARCHAR(10), content varchar(200), isLike integer, isShare integer, PRIMARY KEY(CommentID));

create table resource(ResourceID VARCHAR(10), TYPE VARCHAR(10),LINK VARCHAR(50), PRIMARY KEY (ResourceID));

create table connection(FROM1 VARCHAR(10), TO1 VARCHAR(10), TYPE VARCHAR(10));

create table userresource(UserID VARCHAR(10), RESOURCEID VARCHAR(10));


load data infile 'C:/USER.CSV' into table users fields terminated by ',' lines terminated by '\n';
load data infile 'C:/GROUP.CSV' into table groups fields terminated by ',' lines terminated by '\n';
load data infile 'C:/COMPANY.CSV' into table company fields terminated by ',' lines terminated by '\n';
load data infile 'C:/POST.CSV' into table post fields terminated by ',' lines terminated by '\n';
load data infile 'C:/COMMENT.CSV' into table comment fields terminated by ',' lines terminated by '\n';
load data infile 'C:/RESOURCE.CSV' into table resource fields terminated by ',' lines terminated by '\n';
load data infile 'C:/CONNECTION.CSV' into table connection fields terminated by ',' lines terminated by '\n';
load data infile 'C:/USERRESOURCE.CSV' into table userresource fields terminated by ',' lines terminated by '\n';