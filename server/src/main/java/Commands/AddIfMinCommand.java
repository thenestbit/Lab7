package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class AddIfMinCommand implements Command{
    private CommandKeeper commandKeeper;

    public AddIfMinCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("add_if_min", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.addIfMin(user, arguments, objectArg);
    }
}