SELECT a.USERID auserid,a.name ausername,a.email aemail,b.USERID buserid,b.name busername,b.email bemail from (users a,users b,connection c,connection d, groups) where groups.groupid=c.to1 and groups.groupid=d.to1 and c.from1=a.userid and a.country='USA' and d.from1=b.userid and b.country='USA' and a.userid>b.userid;