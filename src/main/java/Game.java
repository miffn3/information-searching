public class Game {

    private String cover;
    private String name;
    private Long amountOfAchievements;
    private Long amountOfPoints;
    private String href;

    public Game() {
    }

    public Game(String cover, String name, Long amountOfAchievements, Long amountOfPoints, String href) {
        this.cover = cover;
        this.name = name;
        this.amountOfAchievements = amountOfAchievements;
        this.amountOfPoints = amountOfPoints;
        this.href = href;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmountOfAchievements() {
        return amountOfAchievements;
    }

    public void setAmountOfAchievements(Long amountOfAchievements) {
        this.amountOfAchievements = amountOfAchievements;
    }

    public Long getAmountOfPoints() {
        return amountOfPoints;
    }

    public void setAmountOfPoints(Long amountOfPoints) {
        this.amountOfPoints = amountOfPoints;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
