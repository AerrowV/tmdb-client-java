# SP1\_TMDb

SP1\_TMDb is a Java-based application that interacts with The Movie Database (TMDb) API to retrieve and display information about movies.

## Features

- **Movie Search**: Search for movies by title and retrieve details such as release date, overview, and ratings.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Maven

Additionally, you'll need a TMDb API key. You can obtain one by creating an account on TMDb and requesting an API key.

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/AerrowV/SP1_TMDb.git
   ```

2. **Navigate to the project directory**:

   ```bash
   cd SP1_TMDb
   ```

## Building the Project

Use Maven to build the project. In the project directory, run:

```bash
mvn clean install
```

This command will compile the source code and package the application into a JAR file located in the `target` directory.

## Running the Application

After building the project, execute the JAR file:

```bash
java -jar target/SP1_TMDb-1.0.jar
```

Replace `1.0` with the actual version number if it's different.

## Usage

Upon running, the application will prompt you to choose between searching for a movie, TV show, or actor. Follow the on-screen instructions to input your search query and navigate through the results.

## Contributing

Contributions are welcome! If you'd like to contribute, please fork the repository and create a new branch for your feature or bug fix. Once your changes are ready, submit a pull request for review.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Acknowledgments

- [The Movie Database (TMDb)](https://www.themoviedb.org/) for providing the API and extensive database of movies, TV shows, and actors.

