package Product;

public class LogoShoes {
    private int id;
    private int ShoesID;
    private String logoShoesName;

    public LogoShoes(int id, int shoesID, String logoShoesName) {
        this.id = id;
        ShoesID = shoesID;
        this.logoShoesName = logoShoesName;
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

    public String getLogoShoesName() {
        return logoShoesName;
    }

    public void setLogoShoesName(String logoShoesName) {
        this.logoShoesName = logoShoesName;
    }
}
