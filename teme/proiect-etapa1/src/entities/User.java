package entities;

import action.Command;
import fileio.ActionInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    //VARIABLES
    private String username;
    private String subscriptionType;
    private ArrayList<String> favoriteMovies;
    private Map<String, Integer> history;
    private Map<String, Integer> ratingRecord;

    //CONSTRUCTORS
    public User(String username, String subscriptionType,
                Map<String, Integer> history,
                ArrayList<String> favoriteMovies,
                Map<String, Integer> ratingRecord) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratingRecord = ratingRecord;
    }

    public User() {
        this.ratingRecord = new HashMap<>();
    }

    //GETTERS & SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public Map<String, Integer> getRatingRecord() {
        return ratingRecord;
    }

    public void setRatingRecord(Map<String, Integer> ratingRecord) {
        this.ratingRecord = ratingRecord;
    }

    //METHODS
    public void deepCopyFromUserInputData(UserInputData userData) {
        this.setUsername(userData.getUsername());
        this.setSubscriptionType(userData.getSubscriptionType());
        this.setHistory(userData.getHistory());
        this.setFavoriteMovies(userData.getFavoriteMovies());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", history=" + history +
                ", favoriteMovies=" + favoriteMovies +
                '}';
    }
}