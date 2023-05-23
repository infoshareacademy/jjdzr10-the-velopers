INSERT INTO users(id,
                  name,
                  email,
                  password)
VALUES ('1',
        'admin',
        'admin@up@pl',
        '$2a$12$U3vsOIWLzCoTCcznHwoX0OBRKCRDNBk8Xx8yaLjGGO4pdih19Y5km');
INSERT INTO users(id,
                  name,
                  email,
                  password)
VALUES ('2',
        'user',
        'user@up@pl',
        '$2a$12$n9e6Mk.0Y6SmoCd0R1xbYOpgPK.aBDHsy0eK2lkE467U87POFSsum');
INSERT INTO privilege(id, name) VALUES ('1', 'READ');
INSERT INTO privilege(id, name) VALUES ('2', 'WRITE');
INSERT INTO role(id, name) VALUES ('1', 'ADMIN');
INSERT INTO role(id, name) VALUES ('2', 'USER');
INSERT INTO users_roles(user_id, role_id) VALUES ('1', '1');
INSERT INTO users_roles(user_id, role_id) VALUES ('1', '2');
INSERT INTO users_roles(user_id, role_id) VALUES ('2', '2');
INSERT INTO roles_privileges(role_id, privilege_id) VALUES ('1', '1');
INSERT INTO roles_privileges(role_id, privilege_id) VALUES ('1', '2');
INSERT INTO roles_privileges(role_id, privilege_id) VALUES ('2', '1');