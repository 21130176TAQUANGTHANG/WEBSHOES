package Product;

public class ShoesColor {
    private int id;
    private int ShoesID;
    private String ShoesColor;

    public ShoesColor(int id, int shoesID, String shoesColor) {
        this.id = id;
        ShoesID = shoesID;
        ShoesColor = shoesColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoesID() {
        return ShoesID;
    }

    public void setShoesID(int shoesID) {
        ShoesID = shoesID;
    }

    public String getShoesColor() {
        return ShoesColor;
    }

    public void setShoesColor(String shoesColor) {
        ShoesColor = shoesColor;
    }
}
