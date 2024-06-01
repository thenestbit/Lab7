package Modules;


import CollectionObject.City;
import CollectionObject.CityModel;
import CollectionObject.StandardOfLiving;
import Exceptions.DBProviderException;
import Exceptions.NonExistingElementException;
import Network.Response;
import Network.User;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Stack;


public class CommandKeeper {
    private CollectionService collectionService;
    private static LinkedList<String> commandHistory = new LinkedList<>();

    public CommandKeeper() {
        this.collectionService = new CollectionService();
    }

    public Response help(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            String message =
                    """
                            Список доступных команд:
                             ================================================================================================
                             help : вывести справку по доступным командам
                             info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                             show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                             add {element} : добавить новый элемент в коллекцию
                             update id {element} : обновить значение элемента коллекции, id которого равен заданному
                             remove_by_id id : удалить элемент из коллекции по его id
                             clear : очистить коллекцию
                             save : сохранить коллекцию в файл
                             execute_script file_name : считать и исполнить скрипт из указанного файла. В Wrong command arguments в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                             exit : завершить программу (без сохранения в файл)
                             add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                             remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                             history : вывести последние 13 команд (без их аргументов)
                             min_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является минимальным
                             group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе
                             filter_by_standard_of_living standardOfLiving : вывести элементы, значение поля standardOfLiving которых равно заданному
                             ================================================================================================
                            """;
            return new Response(message, "");
        }
    }

    public Response info(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            var message = collectionService.info();
            return new Response(message, "");
        }
    }

    public synchronized Response show(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            var collection = collectionService.show();

            if (collection.isEmpty()) {
                return new Response("В коллекции пока нету ни одного элемента", "");
            } else {
                return new Response("Коллекция успешно распечатана", collection.toString());
            }
        }
    }

    public synchronized Response add(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank() && objArgument == null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            ArrayDeque<City> collection;
            try {
                collection = collectionService.add(objArgument);
            } catch (DBProviderException e) {
                return new Response(e.getMessage(), "");
            }
            return new Response("Элемент успешно добавлен", collection.toString());
        }
    }

    public Response addIfMin(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank()) {
            return new Response("Wrong command arguments","");
        }
        else{
            try {
                var collection = collectionService.addIfMin(objArgument);
                return new Response("Элемент успешно добавлен", collection.toString());
            } catch (DBProviderException e) {
                return new Response(e.getMessage(),"");
            }
        }
    }

    public synchronized Response update(User user, String strArgument, CityModel objArgument) {
        if (strArgument.isBlank() && objArgument == null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            try {
                long current_id = Long.parseLong(strArgument);

                if (current_id > 0) {

                    if (CollectionService.collection.stream().anyMatch(city -> city.getId() == current_id)) {

                        if (CollectionService.collection.stream().anyMatch(city -> city.getId() == current_id
                                && city.getCreator().equals(user.getUsername()))) {

                            var collection = collectionService.update(user, current_id, objArgument);
                            return new Response("элемент c id " + current_id + " успешно обновлён", collection.toString());
                        }
                        return new Response("Вы не можете изменить этот объект", "");

                    }
                    return new Response("Элемента с таким id не существует", "");

                } else {
                    return new Response("id не может быть отрицательным", "");
                }

            } catch (NumberFormatException e) {
                return new Response("Неверный формат аргументов", "");
            } catch (DBProviderException e) {
                return new Response(e.getMessage(), "");
            }
        }
    }

    public synchronized Response minByCreationDate(String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments","");
        }
        else {
            var collection = collectionService.minByCreationDate();
            try {
                return new Response("Минимальный элемент по дате создания: ", collection.toString());
            } catch (NullPointerException e){
                return new Response("Коллекция пуста, нечего отображать", "");
            }
        }
    }

    public synchronized Response removeById(User user, String strArgument, CityModel objArgument) {
        if (strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            try {
                long id = Long.parseLong(strArgument);

                if (id > 0) {
                    if (CollectionService.collection.stream().anyMatch(city -> city.getId() == id)) {
                        if (CollectionService.collection.stream().anyMatch(city -> city.getId() == id
                                && city.getCreator().equals(user.getUsername()))) {

                            var collection = collectionService.removeById(user, id);
                            return new Response("Элемент с id " + id + " успешно удалён", collection.toString());
                        }
                        return new Response("Вы не можете удалить этот объект", "");
                    }
                    return new Response("Элемента с таким id не существует", "");
                }
                return new Response("id не может быть отрицательным", "");

            } catch (NumberFormatException e) {
                return new Response("Неверный формат аргументов", "");
            } catch (DBProviderException e) {
                return new Response(e.getMessage(), "");
            }
        }
    }

    public Response filterByStandardOfLiving(String strArgument, CityModel objArgument) { //args required
        if (strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            try {
                var collection = collectionService.filterByStandardOfLiving(strArgument.toUpperCase());
                return new Response("Элементы с указанным уровнем жизни: ", collection.toString());
            } catch (NonExistingElementException e){
                return new Response(e.getMessage(),"");
            }
        }

    }

    public synchronized Response groupCountingByArea(String strArgument, CityModel objArgument) {
        if (strArgument.isBlank() || objArgument != null){
            return new Response("Неверные аргументы команды", "" );

        } else {
            try {
                int area = Integer.parseInt(strArgument);
                if (area > 0) {
                    var count = collectionService.groupCountingByArea(area);
                    return new Response("Количество городов с area " + area + ":   " + count, "" );

                } else {
                    return new Response("Area не может быть отрицательной", "" );
                }

            } catch (NumberFormatException e){
                return new Response("Неверный формат аргументов", "" );
            }
        }
    }

    public synchronized Response clear(User user, String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            ArrayDeque<City> collection;
            try {
                collection = collectionService.clear(user);
            } catch (DBProviderException e) {
                return new Response(e.getMessage(), "");
            }
            return new Response("коллекция успешно очищена", collection.toString());
        }
    }

    public synchronized Response removeLower(User user, String strArgument, CityModel objArgument) {
        if (strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            try {
                long startId = Long.parseLong(strArgument) + 1;

                if (startId > 0) {
                    var collection = collectionService.removeLower(user, startId);
                    return new Response("элементы успешно удалены", collection.toString());

                } else {
                    return new Response("id не может быть отрицательным", "");
                }

            } catch (NumberFormatException e) {
                return new Response("Неверный формат аргументов", "");
            } catch (NonExistingElementException | DBProviderException e) {
                return new Response(e.getMessage(), "");
            }
        }
    }

    public Response history(User user, String strArgument, CityModel objArgument) {
        StringBuilder historyList = new StringBuilder();

        if (!strArgument.isBlank() || objArgument != null) {
            return new Response("Неверные аргументы команды", "");

        } else {
            for (String command : commandHistory) {
                historyList.append(command).append("\n");
            }
        }
        return new Response("Последние 13 команд, введённые пользователем: \n" + historyList, "");
    }





    public synchronized static void addCommand(String command) {
        if (commandHistory.size() == 13) {
            commandHistory.removeFirst();
        }
        commandHistory.addLast(command);
    }
}