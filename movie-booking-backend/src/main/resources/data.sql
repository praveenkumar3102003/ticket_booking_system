-- Run this manually once in psql after the app starts and creates the tables:
-- psql -U postgres -d movie_booking_db -f data.sql

INSERT INTO movies (name, price, language, genre, release_date)
SELECT * FROM (VALUES
  ('Leo',             200, 'Tamil', 'Action', '2024-01-01'),
  ('Jailer',          250, 'Tamil', 'Drama',  '2024-02-01'),
  ('Vikram',          300, 'Tamil', 'Action', '2024-03-01'),
  ('Ponniyin Selvan', 350, 'Tamil', 'Drama',  '2024-04-01'),
  ('Don',             400, 'Tamil', 'Action', '2024-05-01'),
  ('Sita Ramam',      450, 'Tamil', 'Drama',  '2024-06-01'),
  ('Pathaan',         500, 'Tamil', 'Action', '2024-07-01'),
  ('KGF 2',           550, 'Tamil', 'Action', '2024-08-01')
) AS v(name, price, language, genre, release_date)
WHERE NOT EXISTS (SELECT 1 FROM movies);
