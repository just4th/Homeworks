package markup;

import java.util.List;

public class Strikeout extends AbstractTextGenerator implements Content{
    public Strikeout (List<Content> content) {
        super(content, "~", "[s]", "[/s]");
    }
}
