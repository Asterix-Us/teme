package entities;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie extends Video{
    //VARIABLES
    private int nrOfRatingsReceived;

    //GETTERS & SETTERS
    public int getNrOfRatingsReceived() {
        return nrOfRatingsReceived;
    }

    public void setNrOfRatingsReceived(int nrOfRatingsReceived) {
        this.nrOfRatingsReceived = nrOfRatingsReceived;
    }

    //CONSTRUCTORS
    public Movie() {
        super();
        this.setNrOfRatingsReceived(0);
        this.setGlobalRating(0);
        this.setGlobalNrViews(0);
        this.setGlobalNrFavorites(0);
    }

    //METHODS
    public void deepCopyFromMovieInputData(MovieInputData movieInputData) {
        this.setTitle(movieInputData.getTitle());
        this.setYear(movieInputData.getYear());
        this.setCast(movieInputData.getCast());
        this.setGenres(movieInputData.getGenres());
        this.setDuration(movieInputData.getDuration());
    }
}