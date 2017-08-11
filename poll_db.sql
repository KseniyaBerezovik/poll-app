CREATE DATABASE poll_db;
USE poll_db;
CREATE TABLE polls (id INT AUTO_INCREMENT,
                    title VARCHAR(100),
                    is_closing VARCHAR(10),
  PRIMARY KEY(id));

CREATE TABLE polls_options (id INT AUTO_INCREMENT,
                            poll_id INT,
                            option_content VARCHAR(100),
                            number_of_voters INT,
  PRIMARY KEY(id),
  FOREIGN KEY(poll_id) REFERENCES polls(id));

INSERT INTO polls (title) VALUES ('С какими операционными системами вы знакомы?');

INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (1, 'Windows', 0);
INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (1, 'MacOS', 0);
INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (1, 'Linux', 0);

INSERT INTO polls (title) VALUES ('Ваша страна:');

INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (2, 'Беларусь', 0);
INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (2, 'Россия', 0);
INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (2, 'Украина', 0);
INSERT INTO polls_options (poll_id, option_content, number_of_voters) VALUES (2, 'другое', 0);