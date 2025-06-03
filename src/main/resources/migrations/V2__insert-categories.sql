INSERT INTO categories (id, name, color)
VALUES (1, 'Streaming', '#FF5733')
ON CONFLICT (id) DO NOTHING;

INSERT INTO categories (id, name, color)
VALUES (2, 'Alimentação', '#33FF57')
ON CONFLICT (id) DO NOTHING;

INSERT INTO categories (id, name, color)
VALUES (3, 'Transporte', '#3357FF')
ON CONFLICT (id) DO NOTHING;