package CollectionObject;

import java.io.Serializable;

public class Human implements Serializable {
    private String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        if (this.name != null) {
            return name;
        }else{
            return "null";
        }
    }
}
