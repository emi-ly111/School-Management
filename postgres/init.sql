CREATE DATABASE poo_at2;

\c poo_at2;

CREATE TABLE estudante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade VARCHAR(200) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE professor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade VARCHAR(200) NOT NULL,
    especialidade VARCHAR(255) NOT NULL
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    carga_horaria INT NOT NULL,
    professor_id INT REFERENCES professor(id) ON DELETE SET NULL -- Define que, caso o professor seja excluído, o campo professor_id será NULL
);

CREATE TABLE matricula_estudante (
    id SERIAL PRIMARY KEY,
    estudante_id INT NOT NULL REFERENCES estudante(id) ON DELETE CASCADE,
    curso_id INT NOT NULL REFERENCES curso(id) ON DELETE CASCADE,
    UNIQUE(estudante_id, curso_id)
);

--
CREATE OR REPLACE FUNCTION InsertStudents(new_nome VARCHAR(255), new_idade VARCHAR(200), new_matricula VARCHAR(20)) RETURNS VOID AS $vai$
DECLARE
    estudante_id INT;
BEGIN
    INSERT INTO estudante (nome, idade, matricula) VALUES (new_nome, new_idade, new_matricula) RETURNING id INTO estudante_id;

    RAISE NOTICE 'Inserção realizada com sucesso: % - %', estudante_id, new_matricula;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION InsertTeacher(new_nome VARCHAR(255), new_idade VARCHAR(200), new_especialidade VARCHAR(255)) RETURNS VOID AS $vai$
DECLARE
    teacher_id INT;
BEGIN
    INSERT INTO professor (nome, especialidade, idade) VALUES (new_nome, new_especialidade, new_idade) RETURNING id INTO teacher_id;

    RAISE NOTICE 'Inserção realizada com sucesso: % - %', teacher_id, new_especialidade;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION InsertCourse(new_nome VARCHAR(255), new_carga_horaria INT, professor_id INT) RETURNS VOID AS $vai$
DECLARE
    exist BOOLEAN;
    nome_curso VARCHAR(255);
BEGIN
    SELECT EXISTS(SELECT id FROM professor WHERE id = professor_id) INTO exist;
    IF exist THEN
        INSERT INTO curso (nome, carga_horaria, professor_id) VALUES (new_nome, new_carga_horaria, professor_id) RETURNING nome INTO nome_curso;
        RAISE NOTICE 'Inserção realizada com sucesso: % - %', nome_curso, new_carga_horaria;
    ELSE
        RAISE NOTICE 'Professor não encontrado: %', professor_id;
    END IF;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION InsertMatricula(
    p_estudante_id INT,
    p_curso_id INT
) RETURNS TEXT AS $$
DECLARE
    estudante_existe BOOLEAN;
    curso_existe BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT 1 FROM estudante WHERE id = p_estudante_id) INTO estudante_existe;
    IF NOT estudante_existe THEN
        RETURN 'Estudante não encontrado.';
    END IF;

    SELECT EXISTS(SELECT 1 FROM curso WHERE id = p_curso_id) INTO curso_existe;
    IF NOT curso_existe THEN
        RETURN 'Curso não encontrado.';
    END IF;

    INSERT INTO matricula_estudante (estudante_id, curso_id)
    VALUES (p_estudante_id, p_curso_id);

    RETURN 'Matrícula criada com sucesso.';
END;
$$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteStudents(student_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM estudante WHERE id = student_id;
END;
$BORA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteMatricula(
    p_matricula_id INT
) RETURNS TEXT AS $$
DECLARE
    matricula_existe BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT 1 FROM matricula_estudante WHERE id = p_matricula_id) INTO matricula_existe;
    IF NOT matricula_existe THEN
        RETURN 'Matrícula não encontrada.';
    END IF;

    DELETE FROM matricula_estudante WHERE id = p_matricula_id;

    RETURN 'Matrícula deletada com sucesso.';
END;
$$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteTeacher(teacher_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM professor WHERE id = teacher_id;
END;
$BORA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION DeleteCourse(course_id INT) RETURNS VOID AS $BORA$
BEGIN
    DELETE FROM curso WHERE id = course_id;
END;
$BORA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION UpdateCourse(course_id INT, new_nome VARCHAR(255), new_carga_horaria INT, new_professor_id INT) RETURNS VOID AS $ACABA$
DECLARE
    exist BOOLEAN;
    exist_teacher BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT id FROM curso WHERE id = course_id) INTO exist;
    IF exist THEN
        SELECT EXISTS(SELECT id FROM professor WHERE id = new_professor_id) INTO exist_teacher;
        IF exist_teacher THEN
            UPDATE curso
            SET nome = new_nome, carga_horaria = new_carga_horaria, professor_id = new_professor_id
            WHERE id = course_id;
        ELSE
            RAISE NOTICE 'O professor com o id=% não existe', new_professor_id;
        END IF;
    ELSE
        RAISE NOTICE 'O curso com o id % não existe', course_id;
    END IF;
END;
$ACABA$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION UpdateMatricula(
    p_matricula_id INT,
    p_novo_curso_id INT
) RETURNS TEXT AS $$
DECLARE
    matricula_existe BOOLEAN;
    curso_existe BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT 1 FROM matricula_estudante WHERE id = p_matricula_id) INTO matricula_existe;
    IF NOT matricula_existe THEN
        RETURN 'Matrícula não encontrada.';
    END IF;

    SELECT EXISTS(SELECT 1 FROM curso WHERE id = p_novo_curso_id) INTO curso_existe;
    IF NOT curso_existe THEN
        RETURN 'Novo curso não encontrado.';
    END IF;

    UPDATE matricula_estudante
    SET curso_id = p_novo_curso_id
    WHERE id = p_matricula_id;

    RETURN 'Matrícula atualizada com sucesso.';
END;
$$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION UpdateTeacher(teacher_id INT, new_nome VARCHAR(255), new_idade INT, new_especialidade VARCHAR(255)) RETURNS VOID AS $vai$
DECLARE
    exist BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT id FROM professor WHERE id = teacher_id) INTO exist;
    IF exist THEN
        UPDATE professor
        SET idade = new_idade, nome = new_nome, especialidade = new_especialidade
        WHERE id = teacher_id;
        RAISE NOTICE 'Registro atualizado com sucesso.';
    ELSE
        RAISE NOTICE 'Registro não encontrado.';
    END IF;
END;
$vai$ LANGUAGE plpgsql;

--
CREATE OR REPLACE FUNCTION UpdateStudent(student_id INT, new_nome VARCHAR(255), new_idade INT, new_matricula VARCHAR(255)) RETURNS VOID AS $vai$
DECLARE
    exist BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT id FROM estudante WHERE id = student_id) INTO exist;
    IF exist THEN
        UPDATE estudante
        SET idade = new_idade, nome = new_nome, matricula = new_matricula
        WHERE id = student_id;
        RAISE NOTICE 'Registro atualizado com sucesso.';
    ELSE
        RAISE NOTICE 'Registro não encontrado.';
    END IF;
END;
$vai$ LANGUAGE plpgsql;
