import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
/**
 * 
 * Simple Core nlp parser, basically just the parse method out of
 * the Core nlp examples
 */
public class Parser {
	
	
  public static List<Tree> parse(String text) {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation document = new Annotation(text);
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    List<Tree> result = new ArrayList<Tree>();
    for (CoreMap sentence : sentences) {
      Tree tree = sentence.get(TreeAnnotation.class);
      result.add(tree);
    }
    return result;
  }

  
}
