package markup;

import java.util.List;

public class Strong extends AbstractTextGenerator implements Content{
    public Strong (List<Content> content) {
        super(content, "__", "[b]", "[/b]");
    }
}
