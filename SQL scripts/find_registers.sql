SELECT Users.userid, users.name, users.email, users.registrationtime from users where month(registrationdate)='01' AND day(registrationdate)='24' order by users.registrationtime desc;
