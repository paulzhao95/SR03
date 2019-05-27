
DROP PROCEDURE insert_questionnaire(character varying,character varying);
CREATE OR REPLACE PROCEDURE insert_questionnaire(topic_name character varying, questionnaire_name character varying)
  LANGUAGE plpgsql
AS $$

DECLARE
  max_num   integer;
BEGIN
  select max(number) into max_num from Questionnaires where Questionnaires.topic = topic_name;
  if max_num isnull then max_num:=0; else max_num := max_num+1; end if ;

  insert into Questionnaires (number, name, topic, status) values (max_num,questionnaire_name,topic_name,'Active');

END;
$$;

CREATE OR REPLACE PROCEDURE delete_questionnaire(topic_name character varying, questionnaire_number INT)
  LANGUAGE plpgsql
AS $$

DECLARE
  current_num   integer;
BEGIN
  select questionnaire_number into current_num;
  delete from Questionnaires where number = questionnaire_number and topic = topic_name;

  update questionnaires set number = number -1 where topic = topic_name and number >current_num;
END;
$$;


CREATE OR REPLACE PROCEDURE insert_question(topic_name character varying, questionnaire_number INT, question_name character varying)
  LANGUAGE plpgsql
AS $$

DECLARE
  max_num   integer;
BEGIN
  select max(number) into max_num from Questions where topic = topic_name and questionnaire_id = questionnaire_number;
  if max_num isnull then max_num:=0; else max_num := max_num+1; end if ;

  insert into Questions (topic, questionnaire_id, number, description,status) values (topic_name,questionnaire_number,max_num,question_name,'Active');

END;
$$;



CREATE OR REPLACE PROCEDURE delete_question(topic_name character varying, questionnaire_number INT, question_number int)
  LANGUAGE plpgsql
AS $$

DECLARE
  current_num   integer;
BEGIN
  set current_num = question_number;

  delete from Questions where topic = topic_name and questionnaire_id = questionnaire_number and number = question_number;

  update Questions set number = number -1 where  topic = topic_name and questionnaire_id = questionnaire_number and number >current_num;

END;
$$;


CREATE OR REPLACE PROCEDURE insert_choice(topic_name character varying, questionnaire_number INT, question_number int ,choice_description character varying, is_right character varying)
  LANGUAGE plpgsql
AS $$

DECLARE
  max_num   integer;
BEGIN
  select max(number) into max_num from choices where topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number;

  if max_num isnull then max_num:=0; else max_num := max_num+1; end if ;

  insert into Choices (topic, questionnaire_id, question_id, number, description,status, type) values (topic_name,questionnaire_number,question_number,max_num,choice_description,'Active',is_right);

END;
$$;



CREATE OR REPLACE PROCEDURE delete_choice(topic_name character varying, questionnaire_number INT, question_number int, choice_number int)
  LANGUAGE plpgsql
AS $$

DECLARE
  current_num   integer;
BEGIN
  set current_num = question_number;

  delete from Choices where topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number and number = choice_number;

  update Choices set number = number -1 where  topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number and number >current_num;

END;
$$;

CREATE OR REPLACE PROCEDURE exchange_question_order (topic_name character varying, questionnaire_number INT, question_one_number int, question_two_number int)
  LANGUAGE plpgsql
AS $$


BEGIN


  update  questions set number = -1 where topic = topic_name and questionnaire_id = questionnaire_number and number = question_one_number;

  update  questions set number = question_one_number where topic = topic_name and questionnaire_id = questionnaire_number and number = question_two_number;

  update  questions set number = question_two_number where topic = topic_name and questionnaire_id = questionnaire_number and number = -1;

END;
$$;


CREATE OR REPLACE PROCEDURE exchange_choice_order (topic_name character varying, questionnaire_number INT, question_number INT, choice_one_number int, choice_two_number int)
  LANGUAGE plpgsql
AS $$


BEGIN



  update  choices set number = -1 where topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number and number = choice_one_number;

  update  choices set number = choice_one_number where topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number and number = choice_two_number;

  update  choices set number = choice_two_number where topic = topic_name and questionnaire_id = questionnaire_number and question_id = question_number and  number = -1;

  delete from choices where number = -1;
END;
$$;

