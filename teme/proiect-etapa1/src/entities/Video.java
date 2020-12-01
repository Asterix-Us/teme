package entities;

import java.util.ArrayList;

public class Video {
    //VARIABLES
    private int year;
    private int duration;
    private int globalNrViews;
    private int globalNrFavorites;
    private double globalRating;
    private String title;
    private ArrayList<String> cast;
    private ArrayList<String> genres;

    //CONSTRUCTORS
    public Video() {
        this.cast = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    //GETTERS & SETTERS
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getGlobalNrViews() {
        return globalNrViews;
    }

    public void setGlobalNrViews(int globalNrViews) {
        this.globalNrViews = globalNrViews;
    }

    public int getGlobalNrFavorites() {
        return globalNrFavorites;
    }

    public void setGlobalNrFavorites(int globalNrFavorites) {
        this.globalNrFavorites = globalNrFavorites;
    }

    public double getGlobalRating() {
        return globalRating;
    }

    public void setGlobalRating(double globalRating) {
        this.globalRating = globalRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
