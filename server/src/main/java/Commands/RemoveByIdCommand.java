package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class RemoveByIdCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveByIdCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_by_id", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.removeById(user,arguments, objectArg);
    }
}
