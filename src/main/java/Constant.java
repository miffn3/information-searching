public enum Constant {
    COVER("game_cover"),
    NAME("game_name"),
    AMOUNT_OF_ACHIEVEMENTS("game_amount_of_achievements"),
    AMOUNT_OF_POINTS("game_amount_of_points"),
    HREF("game_href");

    public final String value;

    Constant(String value) {
        this.value = value;
    }
}
