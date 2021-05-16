CREATE TABLE email (
 id INT NOT NULL,
 email VARCHAR(100)
);

ALTER TABLE email ADD CONSTRAINT PK_email PRIMARY KEY (id);


CREATE TABLE instructor (
 id INT NOT NULL,
 first_name VARCHAR(100),
 last_name VARCHAR(201),
 social_security_number VARCHAR(12) NOT NULL,
 zip_code VARCHAR(10),
 city VARCHAR(100),
 street VARCHAR(100)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (id);


CREATE TABLE instructor_email (
 email_id INT NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE instructor_email ADD CONSTRAINT PK_instructor_email PRIMARY KEY (email_id,instructor_id);


CREATE TABLE phone_number (
 id INT NOT NULL,
 phone_number VARCHAR(25)
);

ALTER TABLE phone_number ADD CONSTRAINT PK_phone_number PRIMARY KEY (id);


CREATE TABLE student (
 id INT NOT NULL,
 first_name VARCHAR(100),
 last_name VARCHAR(201),
 age VARCHAR(2),
 social_security_number VARCHAR(12) NOT NULL,
 zip_code VARCHAR(10),
 city VARCHAR(100),
 street VARCHAR(100)
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (id);


CREATE TABLE student_email (
 student_id INT NOT NULL,
 email_id INT NOT NULL
);

ALTER TABLE student_email ADD CONSTRAINT PK_student_email PRIMARY KEY (student_id,email_id);


CREATE TABLE student_phone (
 student_id INT NOT NULL,
 phone_number_id INT NOT NULL
);

ALTER TABLE student_phone ADD CONSTRAINT PK_student_phone PRIMARY KEY (student_id,phone_number_id);


CREATE TABLE application (
 student_id INT NOT NULL,
 skill_level VARCHAR(25),
 instrument VARCHAR(100),
 sibling VARCHAR(500)
);

ALTER TABLE application ADD CONSTRAINT PK_application PRIMARY KEY (student_id);


CREATE TABLE audition (
 instructor_id INT NOT NULL,
 student_id INT NOT NULL,
 scheduled_time TIMESTAMP(6),
 scheduled_date DATE,
 evaluation VARCHAR(100)
);

ALTER TABLE audition ADD CONSTRAINT PK_audition PRIMARY KEY (instructor_id,student_id);


CREATE TABLE class_is_full (
 student_id INT NOT NULL,
 student_contact_details VARCHAR(100)
);

ALTER TABLE class_is_full ADD CONSTRAINT PK_class_is_full PRIMARY KEY (student_id);


CREATE TABLE ensamble_lesson (
 id INT NOT NULL,
 instructor_id INT NOT NULL,
 type VARCHAR(100),
 price VARCHAR(10),
 scheduled_date DATE,
 scheduled_time TIME(10),
 maximum_amount_of_students INT,
 minimum_amount_of_students INT,
 nr_of_students INT,
 weekend_extra_charge DECIMAL(10),
 skill_level VARCHAR(25)
);

ALTER TABLE ensamble_lesson ADD CONSTRAINT PK_ensamble_lesson PRIMARY KEY (id);


CREATE TABLE group_lesson (
 id INT NOT NULL,
 instructor_id INT NOT NULL,
 type VARCHAR(100),
 price VARCHAR(10),
 scheduled_date DATE,
 scheduled_time TIME(10),
 maximum_amount_of_students INT,
 minimum_amount_of_students INT,
 nr_of_students INT,
 weekend_extra_charge DECIMAL(10),
 skill_level VARCHAR(25)
);

ALTER TABLE group_lesson ADD CONSTRAINT PK_group_lesson PRIMARY KEY (id);


CREATE TABLE guardian (
 student_id INT NOT NULL,
 first_name VARCHAR(100),
 last_name VARCHAR(201),
 nr_of_children VARCHAR(2)
);

ALTER TABLE guardian ADD CONSTRAINT PK_guardian PRIMARY KEY (student_id);


CREATE TABLE individual_lesson (
 id INT NOT NULL,
 instructor_id INT NOT NULL,
 price VARCHAR(10),
 scheduled_date DATE,
 scheduled_time TIME(10),
 weekend_extra_charge DECIMAL(10),
 skill_level VARCHAR(25)
);

ALTER TABLE individual_lesson ADD CONSTRAINT PK_individual_lesson PRIMARY KEY (id);


CREATE TABLE instructor_phone (
 phone_number_id INT NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE instructor_phone ADD CONSTRAINT PK_instructor_phone PRIMARY KEY (phone_number_id,instructor_id);


CREATE TABLE rental_of_instrument (
 id INT NOT NULL,
 type_of_instrument VARCHAR(100),
 brand VARCHAR(100),
 price DECIMAL(10),
 student_id INT,
 rented VARCHAR(10)
);

ALTER TABLE rental_of_instrument ADD CONSTRAINT PK_rental_of_instrument PRIMARY KEY (id);


CREATE TABLE sibling_discount (
 id INT NOT NULL,
 student_id INT NOT NULL,
 discount VARCHAR(6),
 nr_of_siblings VARCHAR(2)
);

ALTER TABLE sibling_discount ADD CONSTRAINT PK_sibling_discount PRIMARY KEY (id,student_id);


CREATE TABLE lease_period (
 rental_of_instrument_id INT NOT NULL,
 max_lease_period VARCHAR(2),
 start_of_rental DATE,
 teminated  VARCHAR(10)
);


ALTER TABLE instructor_email ADD CONSTRAINT FK_instructor_email_0 FOREIGN KEY (email_id) REFERENCES email (id);
ALTER TABLE instructor_email ADD CONSTRAINT FK_instructor_email_1 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE student_email ADD CONSTRAINT FK_student_email_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE student_email ADD CONSTRAINT FK_student_email_1 FOREIGN KEY (email_id) REFERENCES email (id);


ALTER TABLE student_phone ADD CONSTRAINT FK_student_phone_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE student_phone ADD CONSTRAINT FK_student_phone_1 FOREIGN KEY (phone_number_id) REFERENCES phone_number (id);


ALTER TABLE application ADD CONSTRAINT FK_application_0 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE audition ADD CONSTRAINT FK_audition_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);
ALTER TABLE audition ADD CONSTRAINT FK_audition_1 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE class_is_full ADD CONSTRAINT FK_class_is_full_0 FOREIGN KEY (student_id) REFERENCES application (student_id);


ALTER TABLE ensamble_lesson ADD CONSTRAINT FK_ensamble_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE guardian ADD CONSTRAINT FK_guardian_0 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE individual_lesson ADD CONSTRAINT FK_individual_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE instructor_phone ADD CONSTRAINT FK_instructor_phone_0 FOREIGN KEY (phone_number_id) REFERENCES phone_number (id);
ALTER TABLE instructor_phone ADD CONSTRAINT FK_instructor_phone_1 FOREIGN KEY (instructor_id) REFERENCES instructor (id);


ALTER TABLE rental_of_instrument ADD CONSTRAINT FK_rental_of_instrument_0 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE sibling_discount ADD CONSTRAINT FK_sibling_discount_0 FOREIGN KEY (student_id) REFERENCES student (id);


ALTER TABLE lease_period ADD CONSTRAINT FK_lease_period_0 FOREIGN KEY (rental_of_instrument_id) REFERENCES rental_of_instrument (id);


