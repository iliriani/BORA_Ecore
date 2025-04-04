package boraproj.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.simple.*;

@Service
public class NLPService {

	public NLPService() {

	}

	public ArrayList getRelevantTokens(String text) {

//		String text = "I like a model for Ordering thinks online";
//	    // set up pipeline properties
//	    Properties props = new Properties();
//	    // set the list of annotators to run
//	    props.setProperty("annotators", "tokenize,ssplit,pos");
//	    // build pipeline
//	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//	    // create a document object
//	    CoreDocument document = pipeline.processToCoreDocument(text);
//	    // display tokens
//	    for (CoreLabel tok : document.tokens()) {
//	      System.out.println(String.format("%s\t%s", tok.word(), tok.tag()));
//	}
		ArrayList<String> names = new ArrayList<String>();
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLableList = coreDocument.tokens();
		for (CoreLabel coreLabel : coreLableList) {
			if (coreLabel.tag().equals("NN") || coreLabel.tag().equals("NNP") || coreLabel.tag().equals("VB")
					|| coreLabel.tag().equals("VBD") || coreLabel.tag().equals("VBG") || coreLabel.tag().equals("VBN")
					|| coreLabel.tag().equals("VBP") || coreLabel.tag().equals("VBZ")) {
				names.add(coreLabel.lemma());
//				System.out.println("The names are: " + coreLabel.word() +" "+coreLabel.tag()+" "+coreLabel.lemma());
			}
//			String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//			String tag = coreLabel.tag();
//			String lemma = coreLabel.lemma();
//			System.out.println(coreLabel.originalText() + " = " + tag + " " + lemma);
		}
		return names;

	}

	public ArrayList getLemmas(String text) {

//		String text = "I like a model for Ordering thinks online";

		ArrayList<String> lemmas = new ArrayList<String>();

		StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLableList = coreDocument.tokens();
		for (CoreLabel coreLabel : coreLableList) {

			String lemma = coreLabel.lemma();
			lemmas.add(lemma);
//			System.out.println(coreLabel.originalText()+ " = "+ lemma);
		}

		return lemmas;
	}

//	Not used in my approach
	public void getNamedEntities(String text) {

		ArrayList<String> names = new ArrayList<String>();
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLableList = coreDocument.tokens();
		for (CoreLabel coreLabel : coreLableList) {
			String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
			System.out.println(coreLabel.originalText()+" - "+ner);
		}

	}
	
	
	
	public void getPartOfSpeach(String text) {

		ArrayList<String> names = new ArrayList<String>();
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLableList = coreDocument.tokens();
		for (CoreLabel coreLabel : coreLableList) {
			String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
			System.out.println(coreLabel.originalText()+" - "+pos);
		}

	}
	
	public void getSentimentAnalysis(String text) {
		
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreSentence> coreLableList = coreDocument.sentences();
		for (CoreSentence coreLabel : coreLableList) {
			String sent = coreLabel.sentiment();
			System.out.println(coreLabel+" - "+ sent+"\t");
		}

	}
	
	public void sentenceSpliting(String text) {
		
	    // set up pipeline properties
	    Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    // create a document object
	    CoreDocument doc = new CoreDocument(text);
	    // annotate
	    pipeline.annotate(doc);
	    // display sentences
	    for (CoreSentence sent : doc.sentences()) {
	        System.out.println(sent.text());
	    }
	}
	
	
	public void getCoReferences(String text) {
		
//	    Annotation document = new Annotation("Barack Obama was born in Hawaii.  He is the president. Obama was elected in 2008.");
		Annotation document = new Annotation(text);
	    Properties props = new Properties();
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    pipeline.annotate(document);
	    System.out.println("---");
	    System.out.println("coref chains");
	    for (CorefChain cc : document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
	      System.out.println("\t" + cc);
	    }
	    for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
	      System.out.println("---");
	      System.out.println("mentions");
	      for (Mention m : sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)) {
	        System.out.println("\t" + m);
	       }
	    }
	 }
	
	public void getOpenIE(String text) {
		
		  // Create a CoreNLP document
	    Document doc = new Document(text);
//		Document doc = new Document("Give me all classes that have only two referenes.");

	    // Iterate over the sentences in the document
	    for (Sentence sent : doc.sentences()) {
	      // Iterate over the triples in the sentence
	      for (RelationTriple triple : sent.openieTriples()) {
	        // Print the triple
	        System.out.println(triple.confidence + "\t" +
	            triple.subjectLemmaGloss() + "\t" +
	            triple.relationLemmaGloss() + "\t" +
	            triple.objectLemmaGloss());
	      }
	    }
	 }
	
	public void testNLP(String text) {
		
		ArrayList<String> userLemmas = this.getLemmas(text);
		String lematized = "";
		for(String lemma: userLemmas) {lematized = lematized + " " + lemma;}
//		Tokenize now the search string
		ArrayList<String> userTerms = this.getRelevantTokens(lematized);
	
		System.out.println("All terms and tokens: "+userTerms);
		
		String tokenized = "";
		for(String t:userTerms) {tokenized += t +" ";}
		
		this.getNamedEntities(tokenized);
		
	}
	
	
}
