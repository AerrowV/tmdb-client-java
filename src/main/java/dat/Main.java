package dat;

import dat.services.FetchDanishMovies;

import java.util.List;

import static dat.services.FetchDanishMovies.printAllMovieDetails;

public class Main {
    public static void main(String[] args) {

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

        printAllMovieDetails();


    }
}