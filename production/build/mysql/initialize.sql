USE blackjack;
CREATE TABLE IF NOT EXISTS player_ranking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL UNIQUE,
    games_played INT NOT NULL,
    games_won INT NOT NULL,
    score INT NOT NULL
);
CREATE DATABASE IF NOT EXISTS blackjack_test;
GRANT ALL PRIVILEGES ON blackjack_test.* TO 'devs'@'%';
FLUSH PRIVILEGES;
USE blackjack_test;
CREATE TABLE IF NOT EXISTS player_ranking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL UNIQUE,
    games_played INT NOT NULL,
    games_won INT NOT NULL,
    score INT NOT NULL
);
