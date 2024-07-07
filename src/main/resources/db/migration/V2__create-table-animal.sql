CREATE TABLE animal (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT NOT NULL,
    category_id INT NOT NULL,
    birthdate DATE NOT NULL,
    animal_status TEXT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
)