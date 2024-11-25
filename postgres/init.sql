create database poo_at2;

\c poo_at2;

CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade VARCHAR(200) NOT NULL
);

CREATE TABLE estudante (
    id SERIAL PRIMARY KEY,
    pessoa_id INT NOT NULL UNIQUE REFERENCES pessoa(id) ON DELETE CASCADE,
    matricula VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE professor (
    id SERIAL PRIMARY KEY,
    pessoa_id INT NOT NULL UNIQUE REFERENCES pessoa(id) ON DELETE CASCADE,
    especialidade VARCHAR(255) NOT NULL
);


CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    carga_horaria INT NOT NULL,
    professor_id INT REFERENCES professor(id) ON DELETE SET NULL -- define que caso delete o professor, serar nulo o troxo
);

CREATE TABLE matricula_estudante (
    id SERIAL PRIMARY KEY,
    estudante_id INT NOT NULL REFERENCES estudante(id) ON DELETE CASCADE,
    curso_id INT NOT NULL REFERENCES curso(id) ON DELETE CASCADE,
    UNIQUE(estudante_id, curso_id)
);

--
CREATE OR REPLACE FUNCTION InsertStudents(nome VARCHAR(255),idade VARCHAR(200), matricula VARCHAR(20)) RETURNS VOID AS $vai$
BEGIN
    INSERT INTO pessoa (nome,idade) VALUES (nome, idade) RETURNING id INTO pessoa_id_new;
    INSERT INTO estudante (pessoa_id,matricula) VALUES (pessoa_id_new, matricula);

    RAISE NOTICE 'Inserção realizada com sucesso: % - %', pessoa_id, matricula;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION InsertTeacher(nome VARCHAR(255),idade VARCHAR(200), especialidade VARCHAR(255)) RETURNS VOID AS $vai$
BEGIN
    INSERT INTO pessoa (nome,idade) VALUES (nome, idade) RETURNING id INTO pessoa_id_new;
    INSERT INTO professor (pessoa_id,especialidade) VALUES (pessoa_id_new, especialidade);

    RAISE NOTICE 'Inserção realizada com sucesso: % - %', pessoa_id, especialidade;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION InsertCourse(nome VARCHAR(255),carga_horaria INT, professor_id INT) RETURNS VOID AS $vai$
DECLARE
    exist BOOLEAN;
BEGIN
    SELECT EXISTS(Select id FROM professor WHERE id = professor_id) INTO exist;
    IF exist THEN
       INSERT INTO curso () VALUES (nome, idade) RETURNING id INTO pessoa_id_new;
       RAISE NOTICE 'Inserção realizada com sucesso: % - %', nome, carga_horaria;
    ELSE
        RAISE NOTICE 'ele não foi achado: %', professor_id;
    END IF;

END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteStudents(student_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM estudante WHERE id = student_id;
END;
$BORA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteTeacher(teacher_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM estudante WHERE id = teacher_id;
END;
$BORA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteCourse(course_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM curso WHERE id = course_id;
END;
$BORA$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION UpdateCourse(course_id INT, new_nome VARCHAR(255), new_carga_horaria INT, new_professor_id INT) RETURNS VOID AS $ACABA$
DECLARE
    exist BOOLEAN;
    exist_teacher BOOLEAN;
BEGIN
    SELECT EXISTS(Select id FROM curso WHERE id = course_id) INTO exist;
    IF exist THEN
       SELECT EXISTS(Select id FROM professor WHERE id = professor_id) INTO exist_teacher;
       IF exist_teacher THEN
          UPDATE curso SET nome = nome, carga_horaria = new_carga_horaria, professor_id = new_professor_id WHERE id = course_id;
       ELSE
          RAISE NOTICE 'o professor com o id=% não existe', professor_id;
       END IF;
    ELSE
        RAISE NOTICE 'o curso com o id % não exite', cource_id;
    END IF;
END;
$ACABA$ LANGUAGE plpgsql;
