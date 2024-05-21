package CollectionObject;

import Network.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CityModel implements Serializable {

    private String name;
    private Coordinates coordinates;
    private int area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Long telephoneCode;
    private long agglomeration;
    private StandardOfLiving standardOfLiving;
    private Human governor;
    private User user;

    public CityModel(String name, Coordinates coordinates, int area, Integer population, Float metersAboveSeaLevel, Long telephoneCode, long agglomeration, StandardOfLiving standardOfLiving, Human governor, User user) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.telephoneCode = telephoneCode;
        this.agglomeration = agglomeration;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }


    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Long getTelephoneCode() {
        return telephoneCode;
    }

    public long getAgglomeration() {
        return agglomeration;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityModel cityModel = (CityModel) o;
        return area == cityModel.area && agglomeration == cityModel.agglomeration && Objects.equals(name, cityModel.name) && Objects.equals(coordinates, cityModel.coordinates) && Objects.equals(population, cityModel.population) && Objects.equals(metersAboveSeaLevel, cityModel.metersAboveSeaLevel) && Objects.equals(telephoneCode, cityModel.telephoneCode) && standardOfLiving == cityModel.standardOfLiving && Objects.equals(governor, cityModel.governor) && Objects.equals(user, cityModel.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, area, population, metersAboveSeaLevel, telephoneCode, agglomeration, standardOfLiving, governor, user);
    }

    @Override
    public String toString() {
        if (this.governor == null) {
            return "City{" +
                    ", name='" + name + '\'' +
                    ", coordinates=" + coordinates +
                    ", area=" + area +
                    ", population=" + population +
                    ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                    ", telephoneCode=" + telephoneCode +
                    ", agglomeration=" + agglomeration +
                    ", standardOfLiving=" + standardOfLiving +
                    ", governor=" + null +
                    '}';
        }
        return "City{" +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", telephoneCode=" + telephoneCode +
                ", agglomeration=" + agglomeration +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor.getName() +
                '}';
    }

}
