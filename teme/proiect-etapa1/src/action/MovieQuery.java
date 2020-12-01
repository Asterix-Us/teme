package action;

import common.Constants;
import database.RealtimeDatabase;
import entities.Video;
import fileio.ActionInputData;
import utils.UserFeedback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovieQuery {

    public UserFeedback solveQuery (ActionInputData action, RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setFeedbackId(action.getActionId());
        String yearParameterString = "";
        String genreParameter = "";
        int i = 0;
        for (List<String> listOfOptions : action.getFilters()) {
            if (listOfOptions != null) {
                if (i==0) {
                    yearParameterString = listOfOptions.toString();
                }

                if (i==1) {
                    genreParameter = listOfOptions.toString();
                }
                i++;
            }
        }

        StringBuilder builder = new StringBuilder(yearParameterString);
        builder.deleteCharAt(0);
        builder.deleteCharAt(yearParameterString.length() - 2);

        String yearParameterHelper = builder.toString();

        int yearParameterInt = 0;
        if (!yearParameterHelper.equals("null")) {
            yearParameterInt = Integer.parseInt(yearParameterHelper);
        }

        if (genreParameter.equals("null")) {
            genreParameter = "0";
        }

        switch (action.getCriteria()) {
            case Constants.RATINGS:
                boolean atLeastOneVideo = false;
                List<Video> newSortedVideoList = new ArrayList<>();
                if (database.movies != null) {
                    newSortedVideoList.addAll(database.movies);
                }
                if (database.serials != null) {
                    newSortedVideoList.addAll(database.serials);
                }
                newSortedVideoList = sortListByRatings(newSortedVideoList, action);
                List<Video> feedbackList = new ArrayList<>();
                for (Video video : newSortedVideoList) {
                    if (video.getYear() == yearParameterInt
                            || yearParameterInt == 0) {
                        if (video.getGenres().contains(genreParameter)
                                || genreParameter.equals("0")) {
                            atLeastOneVideo = true;
                            feedbackList.add(video);
                        }
                    }
                }
                if (!atLeastOneVideo) {
                    userFeedback.setFeedbackMessage(Constants.NO_QUERY);
                    userFeedback.setFeedbackId(action.getActionId());
                } else {
                    userFeedback.setFeedbackMessage(
                            userFeedback.outputVideosQuery(action, feedbackList));
                }
                break;

            case Constants.FAVORITE:
                atLeastOneVideo = false;
                userFeedback = new UserFeedback();
                userFeedback.setFeedbackId(action.getActionId());
                newSortedVideoList = new ArrayList<>();
                newSortedVideoList = sortListByFavorite(newSortedVideoList, action);
                feedbackList = new ArrayList<>();
                for (Video video : newSortedVideoList) {
                    if (video.getYear() == yearParameterInt
                            || yearParameterInt == 0) {
                        if (video.getGenres().contains(genreParameter)
                                || genreParameter.equals("0")) {
                            atLeastOneVideo = true;
                            feedbackList.add(video);
                        }
                    }
                }
                if (!atLeastOneVideo) {
                    userFeedback.setFeedbackMessage(Constants.NO_QUERY);
                    userFeedback.setFeedbackId(action.getActionId());
                } else {
                    userFeedback.setFeedbackMessage(
                            userFeedback.outputVideosQuery(action, feedbackList));
                }
                break;
        }
        return userFeedback;
    }

    private List<Video> sortListByRatings(List<Video> videos, ActionInputData action) {
        videos.sort(new SortVideoByRating());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(videos);
        }
        return videos;
    }

    public List<Video> sortListByFavorite(List<Video> videos, ActionInputData action) {
        videos.sort(new SortVideoByFavorite());
        if (action.getSortType().equals(Constants.DESC)){
            Collections.reverse(videos);
        }
        return videos;
    }

    private class SortVideoByFavorite implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2) {
            return v1.getGlobalNrFavorites() - v2.getGlobalNrFavorites();
        }
    }

    private class SortVideoByRating implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2) {
            return (int) (v1.getGlobalRating() - v2.getGlobalRating());
        }
    }
}
