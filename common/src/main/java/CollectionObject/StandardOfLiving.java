package CollectionObject;

public enum StandardOfLiving {
    HIGH("Высокий"),
    LOW("Низкий"),
    NIGHTMARE("Ужасный");

    private final String type;
    StandardOfLiving(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
