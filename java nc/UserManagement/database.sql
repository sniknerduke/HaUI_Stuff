CREATE DATABASE it6020_data;
USE it6020_data;

CREATE TABLE tbluser (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    user_pass VARCHAR(100) NOT NULL,
    user_fullname VARCHAR(100),
    user_birthday VARCHAR(20),
    user_mobilephone VARCHAR(20),
    user_homephone VARCHAR(20),
    user_officephone VARCHAR(20),
    user_email VARCHAR(100),
    user_address VARCHAR(200),
    user_jobarea VARCHAR(100),
    user_job VARCHAR(100),
    user_position VARCHAR(100),
    user_applyyear SMALLINT,
    user_permission TINYINT,
    user_notes TEXT,
    user_roles TEXT,
    user_logined INT DEFAULT 0,
    user_created_date VARCHAR(20),
    user_last_modified VARCHAR(20),
    user_last_logined VARCHAR(20),
    user_parent_id INT,
    user_actions TEXT,
    user_deleted BOOLEAN DEFAULT FALSE
);
