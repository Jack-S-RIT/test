package parser;

import user.User;

public abstract class ArgumentParser {

    protected User loggedInUser;

    protected String operation;

    protected String data;

    public ArgumentParser(User user, String operation, String data) {
        loggedInUser = user;
        this.operation = operation;
        this.data = data;
    }

    public abstract Object execute();

    public abstract boolean isDataValid();
}
