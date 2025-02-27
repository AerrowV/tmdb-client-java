package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dto.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class FetchDanishMovies {

    private static final String apiKey = System.getenv("MOVIE_API_KEY");
    private static final String apiUrl = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&primary_release_date.gte=2020-01-01&primary_release_date.lte=2025-02-25&sort_by=popularity.desc&with_original_language=da&api_key=" + apiKey;

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static List<Long> fetchMovieIds() {
        List<Long> movieIds = new ArrayList<>();
        String urlWithFirstPage = apiUrl + "&page=1";

        try {
            String firstPageResponse = getDataFromUrl(urlWithFirstPage);
            if (firstPageResponse == null) {
                return movieIds;
            }

            MovieResponseDTO firstMovieResponse =
                    objectMapper.readValue(firstPageResponse, MovieResponseDTO.class);

            for (MovieDTO movie : firstMovieResponse.results) {
                movieIds.add(movie.getId());
            }

            int totalPages = firstMovieResponse.total_pages;

            for (int page = 2; page <= totalPages; page++) {
                String urlWithPage = apiUrl + "&page=" + page;
                String jsonResponse = getDataFromUrl(urlWithPage);

                if (jsonResponse != null) {
                    MovieResponseDTO movieResponse =
                            objectMapper.readValue(jsonResponse, MovieResponseDTO.class);
                    for (MovieDTO movie : movieResponse.results) {
                        movieIds.add(movie.getId());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return movieIds;
    }

    private static String getDataFromUrl(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MovieDTO fetchMovieDetails(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;

        try {
            String jsonResponse = getDataFromUrl(url);
            if (jsonResponse != null) {
                return objectMapper.readValue(jsonResponse, MovieDTO.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GenreResponseDTO fetchGenreDetails() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey;

        try {
            String jsonResponse = getDataFromUrl(url);
            if (jsonResponse != null) {
                return objectMapper.readValue(jsonResponse, GenreResponseDTO.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<DirectorDTO> fetchDirectorDetails(Long movieId) {
        MovieCreditsDTO credits = fetchMovieCredits(movieId);
        List<DirectorDTO> directors = new ArrayList<>();

        if (credits != null && credits.getCrew() != null) {
            for (DirectorDTO crewMember : credits.getCrew()) {
                if ("Director".equalsIgnoreCase(crewMember.getJob())) {
                    directors.add(crewMember);
                }
            }
        }
        return directors;
    }

    public static List<ActorDTO> fetchActorDetails(Long movieId) {
        MovieCreditsDTO credits = fetchMovieCredits(movieId);
        return (credits != null) ? credits.getCast() : new ArrayList<>();
    }

    public static MovieCreditsDTO fetchMovieCredits(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + apiKey;

        try {
            String jsonResponse = getDataFromUrl(url);
            if (jsonResponse != null) {
                return objectMapper.readValue(jsonResponse, MovieCreditsDTO.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printAllMovieDetails() {
        List<Long> movieIds = fetchMovieIds();

        for (Long movieId : movieIds) {
            MovieDTO movie = fetchMovieDetails(movieId);
            if (movie != null) {
                System.out.println("Movie ID: " + movie.getId());
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Release Date: " + movie.getReleaseDate());
                System.out.println("Overview: " + movie.getOverview());
                System.out.println("-----------------------------");
            }
        }
    }
}
