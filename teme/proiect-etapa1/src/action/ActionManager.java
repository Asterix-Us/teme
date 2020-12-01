package action;

import common.Constants;

import database.RealtimeDatabase;
import entities.User;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.UserFeedback;

import java.util.ArrayList;
import java.util.List;


public class ActionManager {
    //VARIABLES
    private List<ActionInputData> listOfActions;
    public List<UserFeedback> feedbacks;

    //GETTERS & SETTERS
    public List<ActionInputData> getListOfActions() {
        return listOfActions;
    }

    public void setListOfActions(List<ActionInputData> listOfActions) {
        this.listOfActions = listOfActions;
    }

    //CONSTRUCTORS
    public ActionManager(List<ActionInputData> actions) {
        this.setListOfActions(actions);
        this.feedbacks = new ArrayList<UserFeedback>();
    }

    //METHODS
    public void solveActions(RealtimeDatabase database) {
        UserFeedback userFeedback = new UserFeedback();
        for (ActionInputData action : listOfActions) {
            switch (action.getActionType()) {
                case Constants.COMMAND -> {
                    CommandManager commandManager = new CommandManager();
                    for (User u : database.users) {
                        if (action.getUsername().equals(u.getUsername())) {
                            userFeedback = commandManager.solveCommands(action, u, database);
                            feedbacks.add(userFeedback);
                            break;
                        }
                    }
                }
                case Constants.QUERY -> {
                    QueryManager querryManager = new QueryManager();
                    userFeedback = querryManager.solveQueries(action, database);
                    feedbacks.add(userFeedback);
                }
            }
        }
    }
}