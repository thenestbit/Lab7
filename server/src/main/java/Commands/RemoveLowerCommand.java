package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class RemoveLowerCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveLowerCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_lower", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.removeLower(user,arguments, objectArg);
    }
}