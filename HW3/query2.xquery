select distinct x.id from reviews v, XMLTABLE('/reviews/review' Passing v.data COLUMNS book_title varchar2(40) PATH 'book_title', review_date date PATH 'review_date') y, books b,XMLTABLE('/books/book' Passing b.data COLUMNS title varchar2(40) PATH 'title',id varchar2(40) PATH 'id') x
where y.book_title=x.title and EXTRACT(YEAR FROM TO_DATE(y.review_date, 'DD-MON-RR'))=2014