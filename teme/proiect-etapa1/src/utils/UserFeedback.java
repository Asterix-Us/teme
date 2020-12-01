package utils;

import common.Constants;

import entities.Actor;
import entities.Video;
import fileio.ActionInputData;

import java.util.List;

public class UserFeedback {
    //VARIABLES
    private int feedbackId;
    private String feedbackMessage;

    //CONSTRUCTORS
    public UserFeedback() {
        this.feedbackId = 0;
        this.feedbackMessage = Constants.NO_QUERY;
    }

    //GETTERS & SETTERS
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    //METHODS
    public String outputFavoriteCommand(boolean hasSeenMovie, boolean movieAlreadyInFavorites,
                                        String status, String movieName, int id) {
        this.setFeedbackId(id);
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(movieName);

        if (!hasSeenMovie) {
            outputBuilder.append(Constants.NOT_SEEN);
        }

        if (movieAlreadyInFavorites) {
            outputBuilder.append(Constants.ALREADY_FAV);
        }

        if (hasSeenMovie && !movieAlreadyInFavorites) {
            outputBuilder.append(Constants.ADDED_FAV);
        }
        return outputBuilder.toString();
    }

    public String outputViewCommand (boolean hasSeenMovie, String status,
                                     String movieName, int id,
                                     int viewNumber) {
        this.setFeedbackId(id);
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(movieName);
        outputBuilder.append(Constants.VIEWED);
        outputBuilder.append(viewNumber);

        return outputBuilder.toString();
    }

    public String outputRatingCommand(boolean hasSeenMovie, boolean hasAlreadyRated,
                                      String status, ActionInputData action) {
        this.setFeedbackId(action.getActionId());
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(action.getTitle());

        if (!hasSeenMovie){
            outputBuilder.append(Constants.NOT_SEEN);
        } else {
            if (hasAlreadyRated) {
                outputBuilder.append(Constants.ALREADY_RATED);
            } else {
                outputBuilder.append(Constants.WAS_RATED);
                outputBuilder.append(action.getGrade());
                outputBuilder.append(Constants.BY);
                outputBuilder.append(action.getUsername());
            }
        }

        return outputBuilder.toString();
    }

    public String outputActorsQuery(ActionInputData action, List<Actor> actorList) {
        this.setFeedbackId(action.getActionId());
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT);
        int listIndex = 1;
        for (Actor a : actorList) {
            if (listIndex != actorList.size()) {
                outputBuilder.append(a.getName()).append(", ");
            } else {
                outputBuilder.append(a.getName());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    public String outputVideosQuery(ActionInputData action, List<Video> videoList)  {
        this.setFeedbackId(action.getActionId());
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT);
        int listIndex = 1;
        for (Video v : videoList) {
            if (listIndex != videoList.size()) {
                outputBuilder.append(v.getTitle()).append(", ");
            } else {
                outputBuilder.append(v.getTitle());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + feedbackId +
                ',' + "message:" +
                feedbackMessage + "}";
    }
}