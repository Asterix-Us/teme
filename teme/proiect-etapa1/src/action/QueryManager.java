package action;

import common.Constants;
import database.RealtimeDatabase;
import fileio.ActionInputData;
import utils.UserFeedback;

public class QueryManager {
    //VARIABLES
    private final ActorQuery actorQuery = new ActorQuery();
    private final MovieQuery movieQuery = new MovieQuery();
    private final UserQuery userQuery = new UserQuery();

    //METHODS
    public UserFeedback solveQueries(ActionInputData action, RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        switch (action.getObjectType()) {
            case Constants.ACTORS:
                userFeedback = this.actorQuery.solveQuery(action, database);
                break;

            case Constants.MOVIES:
                userFeedback = this.movieQuery.solveQuery(action, database);
                break;

            case Constants.USERS:
                userFeedback = this.userQuery.solveQuery(action, database);
                break;
        }
        return userFeedback;
    }
}
