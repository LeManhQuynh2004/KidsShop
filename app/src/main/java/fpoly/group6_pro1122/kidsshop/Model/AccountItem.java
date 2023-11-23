package fpoly.group6_pro1122.kidsshop.Model;

public class AccountItem {
    private int img;
    private String tenChucNang;

    public AccountItem(int img, String tenChucNang) {
        this.img = img;
        this.tenChucNang = tenChucNang;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}
