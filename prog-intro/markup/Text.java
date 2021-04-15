package markup;

public class Text implements Content{
    private final String content;
    public Text (String content) {
        this.content = content;
    }

    @Override
    public void toBBCode(StringBuilder ans) {
        ans.append(content);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        ans.append(content);
    }
}
