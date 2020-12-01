package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private double ratings;

    private int nrOfRatingsRecceived;


    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = 0;
        this.nrOfRatingsRecceived = 0;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(final double ratings) {
        this.ratings = ratings;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getNrOfRatingsRecceived() {
        return nrOfRatingsRecceived;
    }

    public void setNrOfRatingsRecceived(int nrOfRatingsRecceived) {
        this.nrOfRatingsRecceived = nrOfRatingsRecceived;
    }


    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

