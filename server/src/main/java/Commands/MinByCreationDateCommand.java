package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class MinByCreationDateCommand implements Command{
    private CommandKeeper commandKeeper;

    public MinByCreationDateCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("min_by_creation_date", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.minByCreationDate(arguments, objectArg);
    }
}