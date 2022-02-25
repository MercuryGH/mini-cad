package minicad.toolbar;

public enum Operation {
    DRAW_LINE("Line"), 
    DRAW_RECTANGLE("Rectangle"), 
    DRAW_PLAIN_TEXT("Plain Text"),
    DRAW_CIRCLE("Circle"),
    OPEN_FILE("Open File"),
    SAVE_FILE("Save File");

    private final String description;

    private Operation(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
