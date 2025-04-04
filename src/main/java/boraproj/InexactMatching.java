package boraproj;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.wordnet.SynonymMap;

import boraproj.controller.NGram;
import boraproj.services.RepoConnect;

public class InexactMatching {

	private NGram n_gram;
	private RepoConnect store;

	public void typosMatch(String entity) {

		/*
		 * Calculating the Levenstein distance of given strings
		 */
		store = new RepoConnect("../BoraAI/src/main/resources/repository");
		ArrayList<String> classes = store.getClasses();
		LevenshteinDistance lev = new LevenshteinDistance();
		for(String cl : classes) {
			if(lev.apply(entity, cl) < 3) {
				System.out.println("Classes with typo match are: "+cl);
			}
//		 	System.out.println("Dallimi "+entity+" dhe " + cl+" eshte " + lev.apply(entity, cl));
		}


	}

	public void synonimMatch(String[] words) {

		n_gram = new NGram("../BoraAI/src/main/resources/repository");
		ArrayList<String> entities = new ArrayList<String>();
		String[] synonyms;
		SynonymMap map;
		try {
			map = new SynonymMap(new FileInputStream("../BoraAI/src/main/resources/wn_s.pl"));
			for (int i = 0; i < words.length; i++) {
				synonyms = map.getSynonyms(words[i].toLowerCase());
				System.out.println(words[i] + ":" + java.util.Arrays.asList(synonyms).toString());

				for (int j = 0; j < synonyms.length; j++) {
					String cap = synonyms[j].substring(0, 1).toUpperCase() + synonyms[j].substring(1);
					entities.add(synonyms[j]);
				}

			}

			for (String e : entities) {
//				System.out.println(e);

				String str = e;
				String eCap = str.substring(0, 1).toUpperCase() + str.substring(1);
				System.out.println("ecap format: "+eCap);
				n_gram.oneGram(eCap);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
