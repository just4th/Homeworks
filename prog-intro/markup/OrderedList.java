package markup;

import java.util.List;

public class OrderedList extends AbstractTextGenerator implements Content, ListItemContent{
    public OrderedList (List<ListItem> content) {
        super(content, "", "[list=1]", "[/list]");
    }
}
