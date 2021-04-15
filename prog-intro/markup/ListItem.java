package markup;

import java.util.List;

public class ListItem extends AbstractTextGenerator {
    public ListItem (List<ListItemContent> content) {
        super(content, "", "[*]", "");
    }
}
