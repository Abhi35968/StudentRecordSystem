# Student Record System

A tiny, menu-driven Java console app that demonstrates how to perform CRUD-style actions against a MySQL database using plain JDBC. You can register new students, list every record, or delete an entry by ID without needing any frameworks.

## Features

- **Add students** – Capture `name`, `age`, and `course` from the console and persist the record via [src/dao/StudentDAO.java](src/dao/StudentDAO.java).
- **View roster** – Print every row in the `students` table with IDs so you can decide what to edit/delete next.
- **Delete by ID** – Remove a student with a single menu selection; the DAO issues a parameterized `DELETE` to avoid SQL injection.
- **MySQL-backed JDBC** – Connection handling lives in [src/util/DBConnection.java](src/util/DBConnection.java), and the repo already bundles MySQL Connector/J 9.6.0 under `lib/`.

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java 21 (works with any Java 11+) |
| Database | MySQL 8.x (`student_db` schema) |
| Driver | mysql-connector-j-9.6.0.jar |

## Project Structure

```
StudentRecordSystem/
├── lib/
│   └── mysql-connector-j-9.6.0.jar
├── src/
│   ├── dao/
│   │   └── StudentDAO.java      # SQL access for add/view/delete
│   ├── main/
│   │   └── Main.java            # Console menu loop
│   ├── model/
│   │   └── Student.java         # POJO
│   └── util/
│       └── DBConnection.java    # Creates JDBC connections
└── README.md
```

## Prerequisites

- Java Development Kit (JDK) 17+ (tested on JDK 21)
- MySQL Server 8.x with a user that can create databases
- The MySQL Connector/J JAR (already available in `lib/`)

## 1. Database Setup

Open your MySQL client and execute:

```sql
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	age INT NOT NULL,
	course VARCHAR(100) NOT NULL
);
```

## 2. Configure Credentials

`DBConnection` currently points to `jdbc:mysql://127.0.0.1:3306/student_db` with username `root` and password `abhi123`. Update [src/util/DBConnection.java](src/util/DBConnection.java) if your credentials differ, or externalize them into environment variables before rebuilding.

## 3. Build & Run

From `StudentRecordSystem/StudentRecordSystem/`:

```bash
mkdir -p out
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d out \
	src/model/Student.java \
	src/util/DBConnection.java \
	src/dao/StudentDAO.java \
	src/main/Main.java

# Windows: use ';' instead of ':' in the classpath below
java -cp "out:lib/mysql-connector-j-9.6.0.jar" main.Main
```

Prefer an IDE? Mark `src/` as a source folder, add `lib/mysql-connector-j-9.6.0.jar` to the module/class path, and run `main.Main`.

## Using the Console Menu

1. **Add Student** – Prompts for the student’s name, age, and course, then inserts the row.
2. **View Students** – Outputs all rows in the `students` table.
3. **Delete Student** – Request the numeric ID and runs a `DELETE` statement.
4. **Exit** – Terminates the application.

Input is captured with `java.util.Scanner`, so provide numeric values where prompted to avoid `InputMismatchException`.

## Key Classes

- [model/Student.java](src/model/Student.java) – Immutable data holder with two constructors (with/without ID).
- [dao/StudentDAO.java](src/dao/StudentDAO.java) – Encapsulates SQL statements and prints confirmation messages after each mutation.
- [util/DBConnection.java](src/util/DBConnection.java) – Registers the MySQL driver and prints a status message whenever a connection is created.
- [main/Main.java](src/main/Main.java) – Infinite loop that renders the menu and routes actions to the DAO.

## Troubleshooting

- **`Driver not found`** – Ensure `mysql-connector-j-9.6.0.jar` is on the compile/run classpath and matches your MySQL version.
- **`Connection failed`** – Confirm MySQL is running, credentials are correct, and the `student_db` schema exists.
- **`InputMismatchException`** – Clear the scanner buffer (press Enter) and provide the expected data type.

## Next Steps

1. Add update/search operations to the DAO.
2. Introduce validation (e.g., deny negative ages) before executing SQL.
3. Replace console menus with a JavaFX or Spring Boot REST front end once the core DAO is stable.