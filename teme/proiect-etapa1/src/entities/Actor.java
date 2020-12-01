package entities;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    public ArrayList<Video> filmographyExtended;
    private Map<ActorsAwards, Integer> awards;

    //CONSTRUCTORS
    public Actor() {
        this.filmographyExtended = new ArrayList<>();
    }

    //GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    //METHODS
    public void deepCopyFromActorInputData(ActorInputData actorInputData) {
        this.setName(actorInputData.getName());
        this.setCareerDescription(actorInputData.getCareerDescription());
        this.setAwards(actorInputData.getAwards());
        this.setFilmography(actorInputData.getFilmography());
    }
}