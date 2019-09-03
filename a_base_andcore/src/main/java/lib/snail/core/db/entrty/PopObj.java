package lib.snail.core.db.entrty;


import android.graphics.drawable.Drawable;

/**
 * list pop 对象
 * 2019-5-13
 */
public class PopObj {

    private String name = "";
    public Drawable itemImg  ;
    private int num =0;

    public PopObj(String name) {
        this.name = name;
    }

    public PopObj(String name, Drawable itemImg) {
        this.name = name;
        this.itemImg = itemImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getItemImg() {
        return itemImg;
    }

    public void setItemImg(Drawable itemImg) {
        this.itemImg = itemImg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
