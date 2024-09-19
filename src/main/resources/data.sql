-- Create Candidates table
CREATE TABLE IF NOT EXISTS candidate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    collected_votes INT NOT NULL
);

-- Create Voters table
CREATE TABLE IF NOT EXISTS voter (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     voted BOOLEAN NOT NULL DEFAULT FALSE
);

TRUNCATE TABLE candidate;
TRUNCATE TABLE voter;

-- Populate Candidates table
INSERT INTO candidate (name, collected_votes) VALUES
      ('John Doe', 1500),
      ('Jane Smith', 2300),
      ('Alice Johnson', 1200);

-- Populate Voters table
INSERT INTO voter (name, voted) VALUES
     ('Emma Watson', FALSE),
     ('John Smith', TRUE),
     ('Ava Davis', FALSE);