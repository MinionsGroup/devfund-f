package org.minions.devfund.sergio.movie_database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieDatabase {
    private ArrayList<Movie> movieList;
    private ArrayList<Actor> actorList;

    public MovieDatabase() {
        this.movieList = new ArrayList<>();
        this.actorList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDatabase that = (MovieDatabase) o;

        if (!movieList.equals(that.movieList)) return false;
        return actorList.equals(that.actorList);
    }


    @Override
    public int hashCode() {
        int result = movieList.hashCode();
        result = 31 * result + actorList.hashCode();
        return result;
    }


    public ArrayList<Movie> getMovieList() {
        return this.movieList;
    }


    public ArrayList<Actor> getActorList() {
        return this.actorList;
    }


    private ArrayList<String> getMovieNames() {
        ArrayList<String> movieNames = new ArrayList<>();
        for (Movie movie: this.movieList) {
            movieNames.add(movie.getName());
        }
        return movieNames;
    }


    public void addMovie(String name, String[] actors) {
        if (!this.getMovieNames().contains(name)) {
            ArrayList<Movie> movieList = new ArrayList<>();
            Movie newMovie = new Movie(name);
            movieList.add(newMovie);
            for (String actorName: actors) {
                Actor newActor = new Actor(actorName, movieList);
                newMovie.addActor(newActor);
                boolean added = false;
                for (Actor actor: this.actorList) {
                    if (actorName.equals(actor.getName())) {
                        actor.addMovie(newMovie);
                        added = true;
                    }
                }
                if (!added) this.actorList.add(newActor);

            }
            this.movieList.add(newMovie);
        }
    }

    public void addRating(String name, double rating) {
        for (Movie movie: movieList) {
            if (movie.getName().equals(name)) {
                movie.setRating(rating);
            } else {
                continue;
            }
        }
    }

    public void updateRating(String name, double newRating) {
        this.addRating(name, newRating);
    }

    public String getBestActor() {
        String actorName = "";
        double averageRating = 0.0;

        for (Actor actor: actorList) {
            double totalRating = 0.0;
            for (Movie movie: actor.getMovies()) {
                totalRating += movie.getRating();
            }
            double average = totalRating/actor.getMovies().size();

            if (average > averageRating) {
                averageRating = average;
                actorName = actor.getName();
            }
        }
        return actorName;
    }


    public String getBestMovie() {
        double bestRating = 0.0;
        String movieName = "";
        for (Movie movie: this.movieList) {
            if (movie.getRating() > bestRating) {
                bestRating = movie.getRating();
                movieName = movie.getName();
            }
        }
        return movieName;
    }


    public static void main(String[] args) {

        MovieDatabase mdb = new MovieDatabase();
        String moviesText = "./submissions/Homeworks/SD1x/movie_database/files/movies.txt";
        String splitBy = ",";
        String line;

        // Add movies and actors to the database
        try (BufferedReader br = new BufferedReader(new FileReader(moviesText))) {
            while ((line = br.readLine()) != null) {
                String[] movie = line.split(splitBy);
                String[] actor = new String[1];
                for (int i = 0; i < movie.length; i++) {
                    if (i == 0) {
                        actor[i] = movie[i];
                    }
                    else {
                        mdb.addMovie(movie[i].trim(), actor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the ratings of the various movies
        String ratingsText = "./submissions/Homeworks/SD1x/movie_database/files/ratings.txt";
        splitBy = "\t";

        try (BufferedReader br = new BufferedReader(new FileReader(ratingsText))) {
            while ((line = br.readLine()) != null) {
                String[] rating = line.split(splitBy);
                try {
                    double value = Double.parseDouble(rating[1]);
                    mdb.addRating(rating[0].trim(), value);
                } catch (NumberFormatException e) {
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out best actor and best movie
        System.out.println(mdb.getBestActor());
        System.out.println(mdb.getBestMovie());
    }
}
