package dat;

import dat.services.FetchDanishMovies;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

        System.out.println("Fetched Movie IDs: " + movieIds);


    }
}