package Modules;

import CollectionObject.CityModel;
import Commands.Command;
import Network.Response;
import Network.User;

import java.util.HashMap;

public class ConsoleApp {
    // command hashmap. K - command name; V - command class
    public static HashMap<String, Command> commandList = new HashMap<>();
    private Command help;
    private Command info;
    private Command show;
    private Command add;
    private Command update;
    private Command removeById;
    private Command clear;
    private Command addIfMin;
    private Command removeLower;
    private Command history;
    private Command minByCreationDate;
    private Command groupCountingByArea;
    private Command filterByStandardOfLiving;

    public ConsoleApp(Command... commands) {
        this.help = commands[0];
        this.info = commands[1];
        this.show = commands[2];
        this.add = commands[3];
        this.update = commands[4];
        this.removeById = commands[5];
        this.clear = commands[6];
        this.addIfMin = commands[7];
        this.removeLower = commands[8];
        this.history = commands[9];
        this.minByCreationDate = commands[10];
        this.groupCountingByArea = commands[11];
        this.filterByStandardOfLiving = commands[12];
    }

    public Response getHelp(User user, String arguments, CityModel objectArg) {
        return help.execute(user,arguments,objectArg);
    }

    public Response getInfo(User user, String arguments, CityModel objectArg) {
        return info.execute(user,arguments,objectArg);
    }

    public Response getShow(User user, String arguments, CityModel objectArg) {
        return show.execute(user,arguments,objectArg);
    }

    public Response getAdd(User user, String arguments, CityModel objectArg) {
        return add.execute(user,arguments,objectArg);
    }

    public Response getUpdate(User user, String arguments, CityModel objectArg) {
        return update.execute(user,arguments,objectArg);
    }

    public Response getRemoveById(User user, String arguments, CityModel objectArg) {
        return removeById.execute(user,arguments,objectArg);
    }

    public Response getClear(User user, String arguments, CityModel objectArg) {
        return clear.execute(user,arguments,objectArg);
    }

    public Response getAddIfMin(User user, String arguments, CityModel objectArg) {
        return addIfMin.execute(user,arguments,objectArg);
    }

    public Response getRemoveLower(User user, String arguments, CityModel objectArg) {
        return removeLower.execute(user,arguments,objectArg);
    }

    public Response getHistory(User user, String arguments, CityModel objectArg) {
        return history.execute(user,arguments,objectArg);
    }

    public Response getMinByCreationDate(User user, String arguments, CityModel objectArg) {
        return minByCreationDate.execute(user,arguments,objectArg);
    }

    public Response getGroupCountingByArea(User user, String arguments, CityModel objectArg) {
        return groupCountingByArea.execute(user,arguments,objectArg);
    }

    public Response getFilterByStandardOfLiving(User user, String arguments, CityModel objectArg) {
        return filterByStandardOfLiving.execute(user,arguments,objectArg);
    }
}
