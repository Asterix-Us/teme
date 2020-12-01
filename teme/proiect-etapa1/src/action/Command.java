package action;

import common.Constants;
import database.RealtimeDatabase;
import entertainment.Season;
import entities.Movie;
import entities.Serial;
import entities.User;
import fileio.ActionInputData;

import java.util.Map;

import utils.UserFeedback;

public class Command {
    //METHODS
    public UserFeedback favoriteCommand(ActionInputData action, User user,
                                        RealtimeDatabase database) {
        boolean hasSeenMovie = false;
        boolean movieAlreadyInFavorites = false;
        String status = Constants.ERROR;

        UserFeedback userFeedback = new UserFeedback();

        if (user.getHistory().containsKey(action.getTitle())) {
            hasSeenMovie = true;
        }

        if (user.getFavoriteMovies().contains(action.getTitle())) {
            movieAlreadyInFavorites = true;
        }

        if (hasSeenMovie && !movieAlreadyInFavorites) {
            user.getFavoriteMovies().add(action.getTitle());
            for (Movie m : database.movies) {
                if (m.getTitle().equals(action.getTitle())){
                    m.setGlobalNrFavorites(m.getGlobalNrFavorites() + 1);
                    break;
                }
            }
            status = Constants.SUCCESS;
        }
        String message = userFeedback.outputFavoriteCommand(hasSeenMovie,
                movieAlreadyInFavorites, status,
                action.getTitle(), action.getActionId());
        userFeedback.setFeedbackMessage(message);

        return userFeedback;
    }

    public UserFeedback viewCommand(ActionInputData action, User user,
                                    RealtimeDatabase database) {
        boolean hasSeenMovie = false;
        String status = Constants.SUCCESS;

        UserFeedback userFeedback = new UserFeedback();

        if (user.getHistory().containsKey(action.getTitle())) {
            hasSeenMovie = true;
        }

        int viewNumber = 0;
        if (hasSeenMovie) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (entry.getKey().equals(action.getTitle())) {
                    entry.setValue(entry.getValue() + 1);
                    viewNumber = entry.getValue();
                    break;
                }
            }
        } else {
            user.getHistory().put(action.getTitle(), 1);
            viewNumber = 1;
        }

        for (Movie m : database.movies) {
            if (action.getTitle().equals(m.getTitle())) {
                m.setGlobalNrViews(m.getGlobalNrViews() + 1);
                break;
            }
        }

        String message = userFeedback.outputViewCommand(hasSeenMovie, status,
                action.getTitle(), action.getActionId(), viewNumber);
        userFeedback.setFeedbackMessage(message);

        return userFeedback;
    }

    public UserFeedback ratingCommand(ActionInputData action, User user,
                                      RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        boolean hasSeenMovie = false;
        boolean hasAlreadyRated = false;
        String status = Constants.ERROR;

        if (user.getHistory().containsKey(action.getTitle())) {
            hasSeenMovie = true;
        }

        if (user.getRatingRecord().containsKey(action.getTitle())) {
            if (user.getRatingRecord().get(action.getTitle()) != 0) {
                if (user.getRatingRecord().get(action.getTitle())
                        == action.getSeasonNumber()) {
                    hasAlreadyRated = true;
                }
            } else {
                hasAlreadyRated = true;
            }
        }

        if (!hasAlreadyRated) {
            user.getRatingRecord().put(action.getTitle(), action.getSeasonNumber());
        }

        if (!hasAlreadyRated) {
            if (hasSeenMovie) {
                status = Constants.SUCCESS;
                boolean isMovie;
                double sum;
                isMovie = false;
                for (Movie m : database.movies) {
                    if (m.getTitle().equals(action.getTitle())) {
                        isMovie = true;
                        sum = m.getNrOfRatingsReceived() * m.getGlobalRating();
                        m.setNrOfRatingsReceived(m.getNrOfRatingsReceived() + 1);
                        sum += action.getGrade();
                        sum /= m.getNrOfRatingsReceived();
                        m.setGlobalRating(sum);
                        break;
                    }
                }
                if (!isMovie) {
                    for (Serial s : database.serials) {
                        if (s.getTitle().equals(action.getTitle())) {
                            for (Season season : s.getSeasons()) {
                                if (season.getCurrentSeason() == action.getSeasonNumber()) {
                                    sum = season.getNrOfRatingsRecceived() * season.getRatings();
                                    season.setNrOfRatingsRecceived(
                                            season.getNrOfRatingsRecceived() + 1);
                                    sum += action.getGrade();
                                    sum /= season.getNrOfRatingsRecceived();
                                    season.setRatings(sum);
                                    break;
                                }
                            }
                            sum = 0;
                            for (Season season : s.getSeasons()) {
                                sum += season.getRatings();
                            }
                            sum /= s.getNumberOfSeasons();
                            s.setGlobalRating(sum);
                            break;
                        }
                    }
                }
            }
        }

        String message = userFeedback.outputRatingCommand(hasSeenMovie, hasAlreadyRated,
                status, action);
        userFeedback.setFeedbackMessage(message);
        return userFeedback;
    }
}