package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class HelpCommand implements Command {
    CommandKeeper commandKeeper;

    public HelpCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("help", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.help(user,arguments, objectArg);
    }
}
