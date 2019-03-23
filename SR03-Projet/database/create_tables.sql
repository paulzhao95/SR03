drop type if exists States cascade ;
CREATE TYPE States as enum ('Active','Inactive');

drop type if exists Type_user cascade ;
CREATE TYPE Type_user as enum ('Administrator','Intern');

drop type if exists Type_choice cascade ;
CREATE TYPE Type_choice as enum ('Wrong_choice','Right_choice');

drop table if exists public."Skills" CASCADE;
CREATE TABLE public."Skills"
(
  "Skill" character varying not null,
  primary key ("Skill")
);

drop table if exists public."Users" CASCADE;
CREATE TABLE public."Users"
(
    "Password" character varying NOT NULL,
    "Status" states NOT NULL,
    "Name" character varying NOT NULL,
    "Company" character varying,
    "Tel" character varying,
    "Creating_time" timestamp without time zone NOT NULL,
    "Email" character varying NOT NULL,
	"Type_user" Type_user NOT NULL,
    PRIMARY KEY ("Email")
);


drop table if exists public."Questionnaires" CASCADE;
CREATE TABLE public."Questionnaires"
(
    "Number" integer NOT NULL,
    "Theme" character varying NOT NULL,
    "Status" states,
    PRIMARY KEY ("Number")
);

alter table "Questionnaires" add constraint FK_Theme foreign key ("Theme")
references "Skills"("Skill");

drop table if exists public."Questions" CASCADE;
CREATE TABLE public."Questions"
(
    "Questionnaire_Id" integer NOT NULL ,
    "Number" integer NOT NULL,
    "Description" character varying,
	  "Status" States NOT NULL,
    "Score" integer NOT NULL,
    PRIMARY KEY ("Questionnaire_Id", "Number")
);

ALTER TABLE "Questions" add constraint FK foreign key("Questionnaire_Id") 
references "Questionnaires"("Number");


drop table if exists  public."Choices" CASCADE;
CREATE TABLE public."Choices"
(
    "Questionnaire_Id" integer NOT NULL ,
	"Question_Id" integer NOT NULL,
    "Number" integer NOT NULL,
    "Description" character varying,
    "Status" States NOT NULL,
	"Type" "type_choice" NOT NULL,
    PRIMARY KEY ("Questionnaire_Id", "Question_Id","Number")
);

ALTER TABLE "Choices" add constraint FK_Choice foreign key("Questionnaire_Id","Question_Id") 
references "Questions"("Questionnaire_Id","Number");


drop table if exists public."Evaluation" CASCADE;
CREATE TABLE public."Evaluation"
(
	"Evaluation_Id" integer NOT NULL,
    "Questionnaire_Id" integer NOT NULL ,
    "User_email" character varying NOT NULL,
	"Duration" time NOT NULL,
	"Date" timestamp NOT NULL,
    PRIMARY KEY ("Evaluation_Id")
);


ALTER TABLE "Evaluation" add constraint FK_Evaluation_User foreign key("User_email") references "Users"("Email");
ALTER TABLE "Evaluation" add constraint FK_Evaluation_Questionnaire foreign key("Questionnaire_Id") references "Questionnaires" ("Number");

drop table if exists public."User_choice" CASCADE;
CREATE TABLE public."User_choice"
(
	"Evaluation_Id" integer NOT NULL,
    "Questionaire_Id" integer NOT NULL ,
	"Question_Id" integer NOT NULL,
    "Choice_Id" integer NOT NULL,
	"Type" "type_choice" NOT NULL,
    PRIMARY KEY ("Evaluation_Id", "Questionaire_Id","Question_Id" ,"Choice_Id")
);

ALTER TABLE "User_choice" add constraint FK_UC_Evaluation foreign key("Evaluation_Id") references "Evaluation"("Evaluation_Id");
ALTER TABLE "User_choice" add constraint FK_UC_Choice foreign key("Questionaire_Id","Question_Id","Choice_Id") references "Choices"("Questionnaire_Id", "Question_Id","Number");


