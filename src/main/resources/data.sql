-- INSERT INTO categories (id, name, color)
-- VALUES (1, 'Streaming', '#FF5733')
-- ON CONFLICT (id) DO NOTHING;

-- INSERT INTO categories (id, name, color)
-- VALUES (2, 'Alimentação', '#33FF57')
-- ON CONFLICT (id) DO NOTHING;

-- INSERT INTO categories (id, name, color)
-- VALUES (3, 'Transporte', '#3357FF')
-- ON CONFLICT (id) DO NOTHING;

INSERT INTO categories (name, color, type) VALUES
-- EXPENSE categories
('Food', '#FF5733', 'EXPENSE'),
('Housing', '#33FF57', 'EXPENSE'),
('Transport', '#3357FF', 'EXPENSE'),
('Health', '#FF33A8', 'EXPENSE'),
('Education', '#FFA533', 'EXPENSE'),
('Entertainment', '#33FFF5', 'EXPENSE'),
('Subscriptions', '#A833FF', 'EXPENSE'),
('Clothing', '#FF3333', 'EXPENSE'),
('Pets', '#33FFB5', 'EXPENSE'),
('Personal Care', '#FFCC33', 'EXPENSE'),
('Gifts', '#B533FF', 'EXPENSE'),
('Travel', '#33A1FF', 'EXPENSE'),
('Donations', '#FF8C33', 'EXPENSE'),
('Taxes', '#33FFD1', 'EXPENSE'),
('Home Maintenance', '#C70039', 'EXPENSE'),
('Electronics', '#581845', 'EXPENSE'),
('Childcare', '#FFC300', 'EXPENSE'),
('Insurance', '#DAF7A6', 'EXPENSE'),
('Services', '#FF33EC', 'EXPENSE'),

-- INCOME categories
('Salary', '#28B463', 'INCOME'),
('Freelance', '#1ABC9C', 'INCOME'),
('Investments', '#3498DB', 'INCOME'),
('Rent Received', '#9B59B6', 'INCOME'),
('Bonus', '#F39C12', 'INCOME'),
('Gifts Received', '#D35400', 'INCOME'),
('Dividends', '#2ECC71', 'INCOME'),
('Reimbursements', '#16A085', 'INCOME'),
('Scholarships', '#8E44AD', 'INCOME'),
('Interest', '#27AE60', 'INCOME'),
('Item Sales', '#1F618D', 'INCOME'),
('Affiliates', '#7D3C98', 'INCOME'),
('Cashback', '#D68910', 'INCOME'),
('Commissions', '#45B39D', 'INCOME'),
('Tips', '#2471A3', 'INCOME'),
('Pension', '#BB8FCE', 'INCOME'),
('Business Income', '#F4D03F', 'INCOME'),
('Other Income', '#85929E', 'INCOME');