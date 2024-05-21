package Commands;

import CollectionObject.CityModel;
import Network.Response;
import Network.User;

public interface Command {
    Response execute(User user, String strArgument, CityModel objArgument);
}
