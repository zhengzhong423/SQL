select distinct y.reviewer,y.rating,y.review_date,x.publish_date from reviews v, XMLTABLE('/reviews/review' Passing v.data COLUMNS book_title varchar2(40) PATH 'book_title', reviewer varchar(40) PATH 'reviewer',rating integer PATH 'rating', review_date date PATH'review_date') y, books b,XMLTABLE('/books/book' Passing b.data COLUMNS title varchar2(40) PATH 'title',publish_date date PATH'publish_date') x
where y.book_title=x.title and y.rating>3 order by x.publish_date asc, y.review_date desc;