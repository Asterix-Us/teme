package action;

import actor.ActorsAwards;
import common.Constants;
import database.RealtimeDatabase;
import entities.Actor;
import entities.Video;
import fileio.ActionInputData;
import utils.UserFeedback;

import java.util.*;

public class ActorQuery {

    //METHODS
    public UserFeedback solveQuery(ActionInputData action, RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setFeedbackId(action.getActionId());
        switch (action.getCriteria()) {
            case Constants.AVERAGE:
                List<Actor> newSortedActorList = database.actors;
                newSortedActorList = sortActorsListAverage(newSortedActorList, action);
                if (newSortedActorList == null) {
                    userFeedback.setFeedbackMessage(Constants.NO_QUERY);
                    userFeedback.setFeedbackId(action.getActionId());
                } else {
                    int index = 1;
                    List<Actor> feedbackList = new ArrayList<>();
                    for (Actor a : newSortedActorList) {
                        double ratingOverall = 0;
                        for (Video v : a.filmographyExtended) {
                            ratingOverall += v.getGlobalRating();
                        }
                        if (ratingOverall != 0) {
                            if (index <= action.getNumber()) {
                                feedbackList.add(a);
                            }
                            index++;
                        }
                    }
                    userFeedback.setFeedbackMessage(
                            userFeedback.outputActorsQuery(action, feedbackList));
                }
                break;

            case Constants.AWARDS:
                boolean allDeleted = true;
                newSortedActorList = sortActorsListAwards(database.actors, action);
                for (Actor a : newSortedActorList) {
                    if (!a.getName().equals("")) {
                        allDeleted = false;
                        break;
                    }
                }
                if (allDeleted){
                    userFeedback.setFeedbackMessage(Constants.NO_QUERY);
                    userFeedback.setFeedbackId(action.getActionId());
                } else {
                    List<Actor> feedbackList = new ArrayList<>();
                    for (int i = 0 ; i < action.getNumber(); i++) {
                        feedbackList.add(newSortedActorList.get(i));
                    }
                    userFeedback.setFeedbackMessage(
                            userFeedback.outputActorsQuery(action, feedbackList));
                }
                break;

            case Constants.FILTER_DESCRIPTIONS:
                allDeleted = true;
                newSortedActorList = sortActorsListFilter(database.actors, action);
                for (Actor a : newSortedActorList) {
                    if (!a.getName().equals("")) {
                        allDeleted = false;
                        break;
                    }
                }
                if (allDeleted){
                    userFeedback.setFeedbackMessage(Constants.NO_QUERY);
                    userFeedback.setFeedbackId(action.getActionId());
                } else {
                    List<Actor> feedbackList = new ArrayList<>();
                    for (int i = 0; i < action.getNumber();i++) {
                        feedbackList.add(newSortedActorList.get(i));
                    }
                    userFeedback.setFeedbackMessage(
                            userFeedback.outputActorsQuery(action, feedbackList));
                }
                break;
        }

        return userFeedback;
    }

    private List<Actor> sortActorsListAverage(List<Actor> actors,
                                      ActionInputData action) {
        actors.sort(new SortActorsByName());
        actors.sort(new SortActorsByRating());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(actors);
        }
        return actors;
    }

    private List<Actor> sortActorsListAwards (List<Actor> actors, ActionInputData action) {
        actors = filterActorsListByAwards(actors, action);
        boolean allDeleted = true;
        for (Actor a : actors) {
            if (!a.getName().equals("")) {
                allDeleted = false;
                break;
            }
        }
        if (allDeleted) {
            return actors;
        } else {
            actors = sortActorsByAwards(actors, action);
            return actors;
        }
    }

    private List<Actor> sortActorsListFilter (List<Actor> actors, ActionInputData action) {
        int index = 1;
        for (List<String> listOfOptions : action.getFilters()) {
            if (index == 3) {
                for (String word : listOfOptions) {
                    boolean hasWord;
                    for (Actor actor : actors) {
                        hasWord = false;
                        String actorDescription = actor.getCareerDescription();
                        if (actorDescription.contains(word)) {
                            hasWord = true;
                        }

                        if (!hasWord) {
                            actor.setName("");
                        }
                    }
                }
            }
            index++;
        }
        return actors;
    }


    private List<Actor> filterActorsListByAwards (List<Actor> actors,
                                         ActionInputData action) {
        int index = 1;
        for (List<String> listOfOptions : action.getFilters()) {
            if (index == action.getFilters().size()) {
                for (String award : listOfOptions) {
                    boolean hasAward;
                    for (Actor actor : actors) {
                        hasAward = false;
                        for (Map.Entry<ActorsAwards, Integer> entry :
                                actor.getAwards().entrySet()) {
                            if (entry.getKey().name().equals(award)) {
                                hasAward = true;
                                break;
                            }
                        }

                        if (!hasAward) {
                            actor.setName("");
                        }
                    }
                }
            }
            index++;
        }
        return actors;
    }

    private List<Actor> sortActorsByAwards (List<Actor> actors, ActionInputData action) {
        int index = 1;
        for (List<String> listOfOptions : action.getFilters()) {
            if (index == action.getFilters().size()) {
                for (String award : listOfOptions) {
                    for (int i = 0 ; i < actors.size(); i++) {
                        int awardSize1 = 0;
                        for (Map.Entry<ActorsAwards, Integer> entry : actors.get(i).getAwards().entrySet()) {
                            if (entry.getKey().name().equals(award)) {
                                awardSize1 = entry.getValue();
                                break;
                            }
                        }
                        for (int j = i+1; j < actors.size(); j++) {
                            int awardSize2 = 0;
                            for (Map.Entry<ActorsAwards, Integer> entry : actors.get(j).getAwards().entrySet()) {
                                if (entry.getKey().name().equals(award)) {
                                    awardSize2 = entry.getValue();
                                    break;
                                }
                            }
                            if (action.getSortType().equals(Constants.ASC)) {
                                if (awardSize1 > awardSize2) {
                                    Collections.swap(actors, i, j);
                                }
                            } else {
                                if (awardSize1 < awardSize2) {
                                    Collections.swap(actors, i , j);
                                }
                            }
                        }
                    }
                }
            }
            index++;
        }
        return actors;
    }

    private class SortActorsByName implements Comparator<Actor>{
        @Override
        public int compare(Actor a1, Actor a2) {
            return a1.getName().compareTo(a2.getName());
        }
    }

    private class SortActorsByRating implements Comparator<Actor>{
        @Override
        public int compare(Actor a1, Actor a2) {
            double ratingOverall1 = 0;
            for (Video v : a1.filmographyExtended) {
                ratingOverall1 += v.getGlobalRating();
            }

            double ratingOverall2 = 0;
            for (Video v : a2.filmographyExtended) {
                ratingOverall2 += v.getGlobalRating();
            }

            return (int) (ratingOverall1 - ratingOverall2);
        }
    }
}
