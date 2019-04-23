CREATE DATABASE IF NOT EXISTS timeclockDb;

USE timeclockDb;

DROP TABLE IF EXISTS user_session;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role_mapping;
DROP TABLE IF EXISTS user_time;
DROP TABLE IF EXISTS user;


CREATE TABLE role (
    role_id INT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    is_enabled BIT NOT NULL DEFAULT 1,
    create_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id)
);

CREATE TABLE user_role_mapping (
    user_role_mapping_id INT(20) NOT NULL AUTO_INCREMENT,
    user_id INT(20) NOT NULL,
    role_id INT(20) NOT NULL,
    create_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_role_mapping_id)
);

CREATE TABLE user (
    user_id int(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NULL,
    password_hash text(65536) NOT NULL,
    salt text(65536) NOT NULL,
    name varchar(255) NOT NULL,
    is_enabled BIT NOT NULL DEFAULT 1,
    create_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
);


CREATE TABLE user_time (
    user_time_id int(20) NOT NULL AUTO_INCREMENT,
    user_id int(20) NOT NULL,
    user_date DATE NOT NULL,
    time_in DATETIME NULL,
    time_out DATETIME NULL,
    PRIMARY KEY (user_time_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE user_session (
    user_session_id int(20) NOT NULL AUTO_INCREMENT,
    user_id int(20) NOT NULL,
    session_key TEXT(65536) NOT NULL,
    expire_dt DATETIME NOT NULL,
    create_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_dt DATETIME ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_session_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

INSERT INTO role (name, is_enabled, create_dt)
VALUES ('admin', 1, CURRENT_TIMESTAMP);

INSERT INTO role (name, is_enabled, create_dt)
VALUES ('user', 1, CURRENT_TIMESTAMP);

-- admin password is password
INSERT INTO user (username, name, is_enabled, create_dt, password_hash, salt)
VALUES ('admin', 'Administrator', 1, CURRENT_TIMESTAMP, '3d91e2cd1e9043654514dbb150572b62', 'd49936c95a7f94104aeebf39cb913eb5');

INSERT INTO user_role_mapping (user_id, role_id, create_dt)
VALUES (1, 1, CURRENT_TIMESTAMP);

COMMIT;