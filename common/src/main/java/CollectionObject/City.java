package CollectionObject;

import Network.User;

import java.util.Date;
import java.util.Objects;

public class City {

    private long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private int area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Long telephoneCode;
    private long agglomeration;
    private StandardOfLiving standardOfLiving;
    private Human governor;
    private String creator;

    public City(long id, String name, Coordinates coordinates, Date creationDate, int area, Integer population, Float metersAboveSeaLevel, Long telephoneCode, long agglomeration, StandardOfLiving standardOfLiving, Human governor, String creator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.telephoneCode = telephoneCode;
        this.agglomeration = agglomeration;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.creator = creator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Long getTelephoneCode() {
        return telephoneCode;
    }

    public void setTelephoneCode(Long telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    public long getAgglomeration() {
        return agglomeration;
    }

    public void setAgglomeration(Long agglomeration) {
        this.agglomeration = agglomeration;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && area == city.area && agglomeration == city.agglomeration && Objects.equals(name, city.name) && Objects.equals(coordinates, city.coordinates) && Objects.equals(creationDate, city.creationDate) && Objects.equals(population, city.population) && Objects.equals(metersAboveSeaLevel, city.metersAboveSeaLevel) && Objects.equals(telephoneCode, city.telephoneCode) && standardOfLiving == city.standardOfLiving && Objects.equals(governor, city.governor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, area, population, metersAboveSeaLevel, telephoneCode, agglomeration, standardOfLiving, governor);
    }

    @Override
    public String toString() {
        if (this.governor == null) {
            return "City{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", coordinates=" + coordinates +
                    ", creationDate=" + creationDate +
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
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
