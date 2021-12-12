#
# -- user roles
# INSERT INTO roles (id, role)
# VALUES (1, 1);
# INSERT INTO roles (id, role)
# VALUES (2, 1);
# INSERT INTO roles (id, role)
# VALUES (3, 1);

-- some test users
INSERT INTO users (id, active, email, first_name, last_name, password, username)
VALUES (1, true, 'admin@admin.com', 'Kalin', 'Kirevski', '14a8a58be046ccab8d333bf1f3e519306381ca45db2fbbaa319cbb6565f648ccf0e11c57648b79ca', 'kmkalin');

INSERT INTO users (id, active, email, first_name, last_name, password, username)
VALUES (2, true, 'valio@admin.com', 'Valentin', 'Simeonov', '14a8a58be046ccab8d333bf1f3e519306381ca45db2fbbaa319cbb6565f648ccf0e11c57648b79ca',  'svalio');

INSERT INTO users (id, active, email, first_name, last_name, password, username)
VALUES (3, true, 'misho@admin.com', 'Mihail', 'Mihov', '14a8a58be046ccab8d333bf1f3e519306381ca45db2fbbaa319cbb6565f648ccf0e11c57648b79ca', 'mbmihail');

INSERT INTO users (id, active, email, first_name, last_name, password, username)
VALUES (4, true, 'rosen@admin.com', 'Rosen', 'Rusecv', '14a8a58be046ccab8d333bf1f3e519306381ca45db2fbbaa319cbb6565f648ccf0e11c57648b79ca',  'rrosen');
-- user roles
-- admin
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 1);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 2);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 3);
-- moderator
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (2, 2);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (2, 3);
-- user
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (3, 3);
-- user 2
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (4, 3);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (4, 2);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (4, 1);




#
# INSERT INTO pictures(image_url, title, author_id, offer_id)
# VALUES ();


INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (3, 'Because the piano is central to the composition of almost every music genre, learning how to play the piano
can be a lifelong pursuit. That’s why Udemy hosts a wide range of lessons on subjects like jazz piano techniques, piano
chords for beginners, and music theory.', 'https://static.alfred.com/callout_images/alfred_s_basic_piano_course_page_1024x1024.jpg'
, 'Piano Course', 99.88, 1);

    INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (4, 'Designed to provide students with a foundation in dental science, undergraduate dental degrees are also vocational,
meaning they’ll train and prepare students for positions as dentists towards the end of their course. Most bachelor’s dental
degrees will comprise of modules in anatomy, physiology, biology, patient care, and pharmacology, combining both theoretical
and practical forms of study. ', 'https://ied.eu/wp-content/uploads/2018/10/Untitled-1-4.png'
        , 'Dentist Course', 121.88, 1);

INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (3, 'Are you ready to start your guitar journey? This class is for you! We''ll learn all the basic skills and build
the muscles you need to play the guitar with confidence. By the end of Grade 1, you''ll know how to play the eight essential
open chords, the most common strumming patterns, and loads of great songs!', 'https://digitaldefynd.com/wp-content/uploads/
2019/04/Best-Guitar-classes-
courses-tutorial-certification-training-online.jpg'
        , 'Guitar Course', 555.88, 1);

INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (0, 'PHP is a widely used server-side programming language that’s become increasingly fast and powerful over the
years. PHP works well with HTML and databases, making it a great language for anyone interested in building dynamic web
 applications.', 'https://s3.amazonaws.com/coursesity-blog/2019/01/php-1.png'
       , 'PHP Course', 23.88, 1);


INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (0, ' This Oracle Database: PL/SQL Fundamentals training introduces you to PL/SQL and explains the benefits of
this programming language. You''ll learn how to create PL/SQL blocks of application code that can be shared by multiple
forms, reports and data management applications.', 'https://www.zenfotec.com/wp-content/uploads/2018/04/Oracle-PL-SQL.jpg'
       , 'PL/SQL-ORACLE Course', 999.88, 1);


INSERT INTO offers  (category, description, image_url, name, price, author_id)
VALUES (0, 'Welcome to Android Basics in Kotlin! In this course, you''ll learn the basics of building Android apps with
the Kotlin programming language. Along the way, you''ll develop a collection of apps to start your journey as an Android
developer.',
        'https://miro.medium.com/max/1200/1*8YPjY2xhNwQylBBs8dYB0g.png'
       , 'Guitar Course', 281.88, 1);