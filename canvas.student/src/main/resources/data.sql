INSERT INTO replica_tb_roles (role_id, name) VALUES (1, 'student') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO replica_tb_roles (role_id, name) VALUES (2, 'teacher') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO replica_tb_roles (role_id, name) VALUES (3, 'admin') ON CONFLICT (role_id) DO NOTHING;