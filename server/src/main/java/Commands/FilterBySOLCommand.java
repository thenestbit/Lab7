package Commands;

import CollectionObject.CityModel;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Network.Response;
import Network.User;

public class FilterBySOLCommand implements Command{
    private CommandKeeper commandKeeper;

    public FilterBySOLCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("filter_by_standard_of_living", this);
    }

    @Override
    public Response execute(User user, String arguments, CityModel objectArg) {
        return commandKeeper.filterByStandardOfLiving(arguments, objectArg);
    }
}
