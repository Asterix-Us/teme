package database;

import action.ActionManager;
import entertainment.Season;
import entities.*;
import fileio.*;

import java.util.ArrayList;
import java.util.List;

public class RealtimeDatabase {

    //VARIABLES
    public ArrayList<Actor> actors;
    public ArrayList<User> users;
    public ActionManager actionManager;
    public ArrayList<Movie> movies;
    public ArrayList<Serial> serials;

    //CONSTRUCTORS
    public RealtimeDatabase() {
        this.actors = new ArrayList<>();
        this.users = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.serials = new ArrayList<>();
    }

    //METHODS
    public void deepCopyFromInput(Input input) {
        for (ActorInputData actorInput : input.getActors()) {
            Actor actor = new Actor();
            actor.deepCopyFromActorInputData(actorInput);
            this.actors.add(actor);
        }

        for (UserInputData userInput : input.getUsers()) {
            User user = new User();
            user.deepCopyFromUserInputData(userInput);
            this.users.add(user);
        }

        this.actionManager = new ActionManager(input.getCommands());

        for (MovieInputData movieInput : input.getMovies()) {
            Movie movie = new Movie();
            movie.deepCopyFromMovieInputData(movieInput);
            this.movies.add(movie);
        }

        for (SerialInputData serialInput : input.getSerials()) {
            Serial serial = new Serial();
            serial.deepCopyFromSerialInputData(serialInput);
            this.serials.add(serial);
        }
    }

    public void updateGlobalValues() {
        for (Movie m : this.movies) {
            this.updateVideoValues(m);
        }

        for (Serial s : this.serials) {
            this.updateVideoValues(s);
        }

        for (Actor a : this.actors) {
            this.updateActorValues(a);
        }
    }

    private void updateVideoValues(Video v) {
        for (User u : this.users) {
            if (u.getFavoriteMovies().contains(v)) {
                v.setGlobalNrFavorites(v.getGlobalNrFavorites() + 1);
            }

            if (u.getHistory().containsKey(v.getTitle())) {
                v.setGlobalNrViews(v.getGlobalNrViews() + 1);
            }
        }
    }

    private void updateActorValues(Actor a) {
        for (String film : a.getFilmography()) {
            for (Video v : this.movies) {
                if (film.equals(v.getTitle())) {
                    a.filmographyExtended.add(v);
                }
            }

            for (Video v : this.serials) {
                if (film.equals(v.getTitle())) {
                    a.filmographyExtended.add(v);
                }
            }
        }
    }
}