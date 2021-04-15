package markup;

import java.util.List;

public class UnorderedList extends AbstractTextGenerator implements Content, ListItemContent{
    public UnorderedList (List<ListItem> content) {
        super(content, "","[list]", "[/list]");
    }
}
