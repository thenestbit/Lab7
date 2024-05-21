package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class ShowCommand implements Command{
    private CommandKeeper commandKeeper;

    public ShowCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("show", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.show(user,arguments, objectArg);
    }
}
