package entities;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends Video{
    //VARIABLES
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    //GETTERS & SETTERS

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    //CONSTRUCTORS
    public Serial() {
        super();
        this.setGlobalRating(0);
        this.setGlobalNrViews(0);
        this.setGlobalNrFavorites(0);
        this.seasons = new ArrayList<>();
    }

    //METHODS
    public void deepCopyFromSerialInputData(SerialInputData serialInputData) {
        this.setYear(serialInputData.getYear());
        this.setNumberOfSeasons(serialInputData.getNumberSeason());
        this.setTitle(serialInputData.getTitle());
        this.setCast(serialInputData.getCast());
        this.setGenres(serialInputData.getGenres());
        this.setSeasons(serialInputData.getSeasons());
    }
}