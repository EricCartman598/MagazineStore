INSERT INTO user (ROLE, FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE, CREATED, BALANCE, PASS_HASH)
VALUES ('ADMIN', 'SuperAdmin', 'SuperAdmin', 'SuperAdmin', '1999-01-12', '2019-05-27 08:15:57', 0.0, 1742632083),
       ('ADMIN', 'Anna', 'Sergeeva', 'serveeva@anna.anna', '1981-09-11', '2019-05-27 09:37:18', 0.0, 1742632083),
       ('ADMIN', 'Egor', 'Egorov', 'egorov@egor.egor', '1977-06-07', '2019-05-28 15:11:29', 0.0, 1742632083),
       ('CUSTOMER', 'Ivan', 'Ivanov', 'ivanov@ivan.ivan', '1987-04-15 ', '2019-05-29 21:55:47', 11.0, 1742632083),
       ('CUSTOMER', 'Semen', 'Semenov', 'semenov@semen.semen', '1995-07-03', '2019-06-02 14:01:33', 1356.79,
        1742632083),
       ('CUSTOMER', 'Petr', 'Petrov', 'petrov@petr.petr', '2000-08-26', '2019-06-04 11:45:13', 149.0, 1742632083);

INSERT INTO magazine (NAME, DESCRIPTION, PERIOD_DAYS, PRICE)
VALUES ('Java 8 in Action',
        'Java 8 in Action is a clearly written guide to the new features of Java 8. The book covers lambdas, streams, and functional-style programming.',
        7, 8.79),
       ('Spring Boot in Action',
        'Spring Boot in A developer-focused guide to writing applications using Spring Boot. You''ll learn how to bypass the tedious configuration steps so that you can concentrate on your application''s behavior.',
        14, 13.11),
       ('Java Persistence with Hibernate',
        'Java Persistence with Hibernate, Second Edition explores Hibernate by developing an application that ties together hundreds of individual examples.',
        10, 10.56),
       ('Big Data',
        'Big Data teaches you to build big data systems using an architecture that takes advantage of clustered hardware along with new tools designed specifically to capture and analyze web-scale data.',
        21, 23.37),
       ('HTTP/2 in Action',
        'HTTP/2 in Action is a complete guide to HTTP/2, one of the core protocols of the web. Because HTTP/2 has been designed to be easy to transition to, including keeping it backwards compatible.',
        17, 11.43),
       ('Linux in Action',
        'Linux in Action is a task-based tutorial that will give you the skills and deep understanding you need to administer a Linux-based system.',
        9, 7.43),
       ('Docker in Action',
        'Even small applications have dozens of components. Large applications may have thousands, which makes them challenging to install, maintain, and remove.',
        13, 7.12);