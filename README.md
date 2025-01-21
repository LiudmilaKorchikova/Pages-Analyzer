A web application that analyzes a webpage by a given URL.

The URL is parsed, validated, and saved to the database upon successful validation.
The page can then be analyzed, returning the response code, title, name, and description of the site.
The title, name, and description are extracted from the HTML of the page.
The results of each analysis are saved in a table linked to the URLs table with a one-to-many relationship using a foreign key.
The application follows the MVC structure, with database interactions performed via SQL queries.
In development, the application uses the H2 database, and in production, it uses PostgreSQL.
Covered by tests, with WebMockServer used for testing.

### Hexlet tests and linter status:
[![Actions Status](https://github.com/LiudmilaKorchikova/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/LiudmilaKorchikova/java-project-72/actions)
### Mainainability:
[![Maintainability](https://api.codeclimate.com/v1/badges/0b40fc097341d227ed04/maintainability)](https://codeclimate.com/github/LiudmilaKorchikova/java-project-72/maintainability)
### Test Coverage:
[![Test Coverage](https://api.codeclimate.com/v1/badges/0b40fc097341d227ed04/test_coverage)](https://codeclimate.com/github/LiudmilaKorchikova/java-project-72/test_coverage)
### Link for deployed app:
https://java-project-72-i8oa.onrender.com/
