package markup;

public interface TextGenerator {
    void toBBCode (StringBuilder ans);
    void toMarkdown(StringBuilder ans);
}
