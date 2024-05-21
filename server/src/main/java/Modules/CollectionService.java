
package Modules;

import CollectionObject.City;
import CollectionObject.CityModel;
import CollectionObject.StandardOfLiving;
import Exceptions.DBProviderException;
import Exceptions.NonExistingElementException;
import Network.User;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class CollectionService {
    protected static Long elementsCount = 0L;
    private Date initializationDate;
    protected static ArrayDeque<City> collection;
    private CompareCities comparator;
    private ReentrantLock locker;


    public CollectionService() {
        collection = new ArrayDeque<>();
        this.initializationDate = new Date();
        this.comparator = new CompareCities();
        this.locker = new ReentrantLock();
    }

    private class CompareCities implements Comparator<City>{

        @Override
        public int compare(City o1, City o2) {
            return (int) (o1.getCoordinates().getX() - o2.getCoordinates().getX());
        }

        @Override
        public Comparator<City> reversed() {
            return Comparator.super.reversed();
        }
    }

    public ArrayDeque<City> add(CityModel source) throws DBProviderException {
        locker.lock();
        if (DBProvider.addCity(source)){
            UUID uuid = UUID.randomUUID();
            long mostSignificantBits = uuid.getMostSignificantBits();
            long leastSignificantBits = uuid.getLeastSignificantBits();
            long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
            City newElement = new City(
                    newId,
                    source.getName(),
                    source.getCoordinates(),
                    new Date(),
                    source.getArea(),
                    source.getPopulation(),
                    source.getMetersAboveSeaLevel(),
                    source.getTelephoneCode(),
                    source.getAgglomeration(),
                    source.getStandardOfLiving(),
                    source.getGovernor(),
                    source.getUser().getUsername()
            );

            collection.addLast(newElement);
            locker.unlock();
            return sortByCoords(collection);
        }
        locker.unlock();
        throw new DBProviderException("произошла ошибка при добавлении элемента");
    }

    public String info(){
        return "Тип коллекции: " + collection.getClass() + "\n"
                + "Дата создания: " + initializationDate + "\n"
                + "Количество элементов: " + collection.size();
    }

    public ArrayDeque<City> show(){
        return sortByCoords(collection);
    }

    public  ArrayDeque<City> update(User user, long current_id, CityModel element) throws DBProviderException {
        locker.lock();
        try {
            if (DBProvider.updateCity(user, current_id, element)) {

                for (City city : collection) {
                    if (current_id == city.getId() && city.getCreator().equals(user.getUsername())) {
                        collection.remove(city);

                        City newElement = new City(
                                current_id,
                                element.getName(),
                                element.getCoordinates(),
                                new Date(),
                                element.getArea(),
                                element.getPopulation(),
                                element.getMetersAboveSeaLevel(),
                                element.getTelephoneCode(),
                                element.getAgglomeration(),
                                element.getStandardOfLiving(),
                                element.getGovernor(),
                                element.getUser().getUsername()
                        );

                        collection.add(newElement);
                        break;
                    }
                }
                return new ArrayDeque<>();
            }
            throw new DBProviderException("Произошла ошибка во время изменения элемента");
        } finally {
            locker.unlock();
        }
    }

    public  ArrayDeque<City> removeById(User user, long id) throws DBProviderException {
        locker.lock();
        try {
            if (DBProvider.removeCityById(id)){
                collection.removeIf(city -> city.getId() == id);
                return new ArrayDeque<>();
            }
            throw new DBProviderException("Произошла ошибка при удалении элемента. Возможно элемента с таким id не существует");
        } finally {
            locker.unlock();
        }
    }

    public ArrayDeque<City> clear(User user) throws DBProviderException {
        locker.lock();
        try {
            if (DBProvider.clearCities(user)){
                collection.removeIf(city -> city.getCreator().equals(user.getUsername()));
                return sortByCoords(collection);
            }
            throw new DBProviderException("произошла ошибка при добавлении элемента");
        } finally {
            locker.unlock();
        }
    }

    public  ArrayDeque<City> removeLower(User user, long startId) throws NonExistingElementException, DBProviderException {
        locker.lock();
        try {
            long endId = elementsCount;
            if (startId > endId){
                throw new NonExistingElementException("Элемента с таким id не существует");
            }

            if (DBProvider.removeCitiesLowerThanId(user, startId)){
                collection.removeIf(city -> city.getId() < startId && city.getCreator().equals(user.getUsername()));
                return new ArrayDeque<>();
            }
            throw new DBProviderException("Произошла ошибка при удалении элементов");
        } finally {
            locker.unlock();
        }
    }

    public ArrayDeque<City> filterByStandardOfLiving(String standard) throws NonExistingElementException {
        var filteredCollection = collection.stream().filter(city -> city.getStandardOfLiving().equals(StandardOfLiving.valueOf(standard))).collect(Collectors.toCollection(ArrayDeque::new));
        if (collection.isEmpty()){
            throw new NonExistingElementException("Элементов с таким уровнем жизни не существует");
        }
        return sortByCoords(filteredCollection);
    }

    public ArrayDeque<City> addIfMin(CityModel source) throws DBProviderException {
        locker.lock();
        if (DBProvider.addCity(source)){
            UUID uuid = UUID.randomUUID();
            long mostSignificantBits = uuid.getMostSignificantBits();
            long leastSignificantBits = uuid.getLeastSignificantBits();
            long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
            City newCity = new City(
                    newId,
                    source.getName(),
                    source.getCoordinates(),
                    new Date(),
                    source.getArea(),
                    source.getPopulation(),
                    source.getMetersAboveSeaLevel(),
                    source.getTelephoneCode(),
                    source.getAgglomeration(),
                    source.getStandardOfLiving(),
                    source.getGovernor(),
                    source.getUser().getUsername()
            );

            if (collection.isEmpty() || newCity.getPopulation() < Collections.min(collection, Comparator.comparing(City::getPopulation)).getPopulation()) {
                collection.add(newCity);
                System.out.println("City added successfully.");
            } else {
                System.out.println("City was not added as its population is not minimal.");
            }
            return sortByCoords(collection);
        }
        locker.unlock();
        throw new DBProviderException("произошла ошибка при добавлении элемента");
    }

    public long groupCountingByArea(int area) {
        return collection.stream().filter(city -> city.getArea() == area).count();
    }

    public City minByCreationDate() {
//        if (collection.isEmpty()) {
//            System.out.println("The collection is empty. No objects to display.");
//            return;
//        }
//
//        City minByCreationDateCity = Collections.min(collection, Comparator.comparing(City::getCreationDate));
//
//        System.out.println("Object with the minimum creation date:" + "\n" + minByCreationDateCity.toString());

        return collection.stream().min(Comparator.comparing(City::getCreationDate)).orElseThrow(NoSuchElementException::new);
    }

    private synchronized ArrayDeque<City> sortByCoords(ArrayDeque<City> collection){
        return collection.stream().sorted(comparator).collect(Collectors.toCollection(ArrayDeque::new));
    }
}