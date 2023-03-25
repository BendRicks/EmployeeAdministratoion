INSERT INTO main_schema.users(id, name_en, name_ru, surname_en, surname_ru, name_by_father_ru, email, creation_time, passport_id, password, role)
VALUES (1, 'admin', 'admin', 'admin', 'admin', 'admin', 'admin@admin.com', now(), 'kdiecdkefdswkf', '$2a$10$o.fuD0J.O5CLZKqNS8Rs7usrqXXZtHbh0pgjt1TH2UI3kOYqYcQoa', 'ROLE_ADMIN');

INSERT INTO main_schema.users(id, name_en, name_ru, surname_en, surname_ru, name_by_father_ru, email, creation_time, passport_id, password, role)
VALUES (2, 'employee', 'employee', 'employee', 'employee', 'employee', 'employee@employee.com', now(), 'kdiecdkefdswkf', '$2a$10$o.fuD0J.O5CLZKqNS8Rs7usrqXXZtHbh0pgjt1TH2UI3kOYqYcQoa', 'ROLE_EMPLOYEE');