drop type if exists Type_user cascade ;
CREATE TYPE Type_user as enum ('Administrator','Intern');

drop table if exists public.Topics CASCADE;

CREATE TABLE public.Topics
(
  Topic character varying not null,
  primary key (Topic)
);



drop table if exists public.Users CASCADE;
CREATE TABLE public.Users
(
  Password character varying NOT NULL,
  Status BOOLEAN NOT NULL,
  Name character varying NOT NULL,
  Company character varying,
  Tel character varying,
  Creating_time timestamp without time zone NOT NULL,
  Email character varying NOT NULL,
  Type_user Type_user NOT NULL,
  PRIMARY KEY (Email)
);


drop table if exists public.Questionnaires CASCADE;
CREATE TABLE public.Questionnaires
(
  Number integer NOT NULL,
  Name character varying not null ,
  Topic character varying NOT NULL,
  Status boolean,
  PRIMARY KEY (Topic,Number)
);

alter table Questionnaires add constraint FK_Theme foreign key (Topic)
  references Topics(Topic) ON DELETE CASCADE;

drop table if exists public.Questions CASCADE;
CREATE TABLE public.Questions
(
  Topic character varying NOT NULL,
  Questionnaire_Id integer NOT NULL ,
  Number integer NOT NULL,
  Description character varying not null ,
  Status boolean NOT NULL,
  PRIMARY KEY (Topic,Questionnaire_Id, Number)
);

ALTER TABLE Questions add constraint FK foreign key(Topic,Questionnaire_Id)
  references Questionnaires(Topic,Number) ON DELETE CASCADE;


drop table if exists  public.Choices CASCADE;
CREATE TABLE public.Choices
(
  Topic character varying NOT NULL,
  Questionnaire_Id integer NOT NULL ,
  Question_Id integer NOT NULL,
  Number integer NOT NULL,
  Description character varying,
  Status boolean NOT NULL,
  Type boolean NOT NULL,
  PRIMARY KEY (Topic, Questionnaire_Id, Question_Id,Number)
);

ALTER TABLE Choices add constraint FK_Choice foreign key(Topic, Questionnaire_Id,Question_Id)
  references Questions(Topic, Questionnaire_Id,Number) ON DELETE CASCADE;


drop table if exists public.Attempts CASCADE;
CREATE TABLE public.Attempts
(
  Attempt_Id serial NOT NULL,
  Topic character varying NOT NULL,
  Questionnaire_Id integer NOT NULL ,
  User_email character varying NOT NULL,
  Duration time NOT NULL,
  Start_time timestamp NOT NULL,
  Score integer not null ,
  full_marks integer not null ,
  PRIMARY KEY (Attempt_Id)
);


ALTER TABLE Attempts add constraint FK_Evaluation_User foreign key(User_email) references Users(Email) ON DELETE CASCADE;
ALTER TABLE Attempts add constraint FK_Evaluation_Questionnaire foreign key(Topic, Questionnaire_Id) references Questionnaires (Topic, Number) ON DELETE CASCADE;

drop table if exists public.User_choices CASCADE;
CREATE TABLE public.User_choices
(
  Attempt_Id integer NOT NULL,
  Topic character varying NOT NULL,
  Questionnaire_Id integer NOT NULL ,
  Question_Id integer NOT NULL,
  Choice_Id integer NOT NULL,
  Type boolean NOT NULL,
  PRIMARY KEY (Attempt_Id, Topic,Questionnaire_Id,Question_Id ,Choice_Id)
);

ALTER TABLE User_choices add constraint FK_UC_Evaluation foreign key(Attempt_Id) references Attempts(Attempt_Id) ON DELETE CASCADE;
ALTER TABLE User_choices add constraint FK_UC_Choice foreign key(Topic,Questionnaire_Id,Question_Id,Choice_Id) references Choices(Topic,Questionnaire_Id, Question_Id,Number) ON DELETE CASCADE;


