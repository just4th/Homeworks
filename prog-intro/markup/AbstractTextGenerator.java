package markup;

import java.util.List;

public abstract class AbstractTextGenerator implements TextGenerator{
    private List<? extends TextGenerator> content;
    private final String tagBegin;
    private final String tagEnd;
    private final String tagMarkdown;

    protected AbstractTextGenerator (List <? extends  TextGenerator> content, String tagMarkdown, String tagBegin, String tagEnd) {
        this.content = content;
        this.tagMarkdown = tagMarkdown;
        this.tagBegin = tagBegin;
        this.tagEnd = tagEnd;
    }

    @Override
    public void toBBCode(StringBuilder ans) {
        ans.append(tagBegin);
        for (TextGenerator t: content) {
            t.toBBCode(ans);
        }
        ans.append(tagEnd);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        ans.append(tagMarkdown);
        for (TextGenerator t: content) {
            t.toMarkdown(ans);
        }
        ans.append(tagMarkdown);
    }

}
