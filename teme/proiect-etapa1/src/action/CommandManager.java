package action;

import common.Constants;
import database.RealtimeDatabase;
import entities.User;
import fileio.ActionInputData;
import utils.UserFeedback;

public class CommandManager {
    //VARIABLES
    private Command command = new Command();

    //METHODS
    public UserFeedback solveCommands(ActionInputData action, User actionUser,
                                      RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        switch (action.getType()) {
            case Constants.FAVORITE:
                userFeedback = this.command.favoriteCommand(action, actionUser,
                        database);
                break;

            case Constants.VIEW_MOVIES:
                userFeedback = this.command.viewCommand(action, actionUser,
                        database);
                break;

            case Constants.RATING_MOVIES:
                userFeedback = this.command.ratingCommand(action, actionUser,
                        database);
                break;

        }
        return userFeedback;
    }
}