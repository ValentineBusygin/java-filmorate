insert into mpaRatings (name)
select 'G' from dual
where not exists (select 1 from mpaRatings where name = 'G');

insert into mpaRatings (name)
select 'PG' from dual
where not exists (select 1 from mpaRatings where name = 'PG');

insert into mpaRatings (name)
select 'PG-13' from dual
where not exists (select 1 from mpaRatings where name = 'PG-13');

insert into mpaRatings (name)
select 'R' from dual
where not exists (select 1 from mpaRatings where name = 'R');

insert into mpaRatings (name)
select 'NC-17' from dual
where not exists (select 1 from mpaRatings where name = 'NC-17');

insert into genres (name)
select 'Комедия' from dual
where not exists (select 1 from genres where LOWER(name) = 'комедия');

insert into genres (name)
select 'Драма' from dual
where not exists (select 1 from genres where LOWER(name) = 'драма');

insert into genres (name)
select 'Мультфильм' from dual
where not exists (select 1 from genres where LOWER(name) = 'мультфильм');

insert into genres (name)
select 'Триллер' from dual
where not exists (select 1 from genres where LOWER(name) = 'триллер');

insert into genres (name)
select 'Документальный' from dual
where not exists (select 1 from genres where LOWER(name) = 'документальный');

insert into genres (name)
select 'Боевик' from dual
where not exists (select 1 from genres where LOWER(name) = 'боевик');