package fpoly.group6_pro1122.kidsshop.Model;

public class Category {
    private int category_id;
    private String name;
    private String image;
    private String describe;

    public Category() {
    }

    public Category(int category_id, String name, String image, String describe) {
        this.category_id = category_id;
        this.name = name;
        this.image = image;
        this.describe = describe;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
