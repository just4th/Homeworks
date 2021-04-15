package markup;

import java.util.List;

public class Emphasis extends AbstractTextGenerator implements Content{
    public Emphasis (List<Content> content) {
        super(content, "*", "[i]", "[/i]");
    }
}
