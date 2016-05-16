package si.klika.spoandro;

public class TabData {

    private String title;
    private int baseColor;
    private int highlightColor;
    private int backgroundImage;
    private int animation;

    public TabData(String title, int baseColor, int highlightColor, int backgroundImage, int animation) {
        this.title = title;
        this.baseColor = baseColor;
        this.highlightColor = highlightColor;
        this.backgroundImage = backgroundImage;
        this.animation = animation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.baseColor = baseColor;
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

}
