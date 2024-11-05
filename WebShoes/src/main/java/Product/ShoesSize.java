package Product;

public class ShoesSize {
    private int id;
    private int ShoesID;
    private  int ShoesSize;

    public ShoesSize(int id, int shoesID, int shoesSize) {
        this.id = id;
        ShoesID = shoesID;
        ShoesSize = shoesSize;
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

    public int getShoesSize() {
        return ShoesSize;
    }

    public void setShoesSize(int shoesSize) {
        ShoesSize = shoesSize;
    }
}
