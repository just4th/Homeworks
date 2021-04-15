package markup;

import java.util.List;

public class Paragraph extends AbstractTextGenerator implements ListItemContent{
    public Paragraph (List <Content> content) {
        super(content, "", "", "");
    }
}
