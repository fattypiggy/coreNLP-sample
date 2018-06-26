package com.tin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.io.Files;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class Sample {
    public static void main(String[] args) throws IOException {

        String text = "克林顿说，华盛顿将逐步落实对韩国的经济援助。"
                + "金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。";
        Annotation document = new Annotation(text);
        // Setup Chinese Properties by loading them from classpath resources
        Properties props = new Properties();
        props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
        // Or this way of doing it also works
        // Properties props = StringUtils.argsToProperties(new String[]{"-props", "StanfordCoreNLP-chinese.properties"});
        StanfordCoreNLP corenlp = new StanfordCoreNLP(props);
        corenlp.annotate(document);

        System.out.println("DONE");

//        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
//        Properties props = new Properties();
//        props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
//        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//
//        // read some text from the file..
//        File inputFile = new File("src/main/resources/sample.txt");
//        String text = Files.toString(inputFile, Charset.forName("UTF-8"));
//
//        // create an empty Annotation just with the given text
//        Annotation document = new Annotation(text);
//
//        // run all Annotators on this text
//        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//
//        for(CoreMap sentence: sentences) {
//            // traversing the words in the current sentence
//            // a CoreLabel is a CoreMap with additional token-specific methods
//            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//                // this is the text of the token
//                String word = token.get(TextAnnotation.class);
//                // this is the POS tag of the token
//                String pos = token.get(PartOfSpeechAnnotation.class);
//                // this is the NER label of the token
//                String ne = token.get(NamedEntityTagAnnotation.class);
//
//                System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
//            }
//
//            // this is the parse tree of the current sentence
//            Tree tree = sentence.get(TreeAnnotation.class);
//            System.out.println("parse tree:\n" + tree);
//
//            // this is the Stanford dependency graph of the current sentence
//            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//            System.out.println("dependency graph:\n" + dependencies);
//        }
//
//        // This is the coreference link graph
//        // Each chain stores a set of mentions that link to each other,
//        // along with a method for getting the most representative mention
//        // Both sentence and token offsets start at 1!
//        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
    }
}
