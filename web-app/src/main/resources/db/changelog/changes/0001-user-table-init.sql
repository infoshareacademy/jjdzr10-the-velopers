CREATE TABLE users
(
    id    bigint PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)  NOT NULL,
    password  VARCHAR(255)  NOT NULL,
    email VARCHAR(120) NOT NULL,
    UNIQUE (email),
    CHECK (LENGTH(name) > 0),
    CHECK (LENGTH(password) > 0),
    CHECK (LENGTH(email) > 0)
);

CREATE TABLE role
(
    id   bigint PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name),
    CHECK (LENGTH(name) > 0)
);
CREATE TABLE privilege
(
    id   bigint PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name),
    CHECK (LENGTH(name) > 0)
);

CREATE TABLE users_roles
(
    user_id bigint(36) NOT NULL REFERENCES users (id),
    role_id bigint(36) NOT NULL REFERENCES role (id)
);
CREATE TABLE roles_privileges
(
    role_id bigint(36) NOT NULL REFERENCES role (id),
    privilege_id bigint(36) NOT NULL REFERENCES privilege (id)
);