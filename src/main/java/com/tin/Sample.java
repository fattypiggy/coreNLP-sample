package com.tin;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Sample {
    static Map<String, String> relations = new HashMap<>();
    static String key;
    public static void main(String[] args) throws IOException {

        String text = "鲁迅（1881年9月25日－1936年10月19日），原名周樟寿，后改名周树人，字豫山，后改豫才，“鲁迅”是他1918年发表《狂人日记》时所用的笔名，也是他影响最为广泛的笔名，浙江绍兴人。" +
                "著名文学家、思想家，五四新文化运动的重要参与者，中国现代文学的奠基人。毛泽东曾评价：“鲁迅的方向，就是中华民族新文化的方向。” [1-6] ";
        // remove "\t" and HTML tags
        text = text.replaceAll("\t"," ").replaceAll("<[^>]*>", "");

        // System.out.println(text);

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);


        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(NamedEntityTagAnnotation.class);

                // get the key. notice: token index starts from 1, not 0
                if (sentences.indexOf(sentence) == 0 && token.index() == 1) {
                    key = word;
                }

                System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
                // output the result
                // System.out.println(sentence.toString());
                makeTripleTuple(sentence.toString(), word, ne);
            }
        }
        outPutResult(relations);
    }

    private static void outPutResult(Map relations) {
        Set set = relations.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(key + "\t" + entry.getKey() + "\t" + entry.getValue());
        }
    }

    /**
     * @param text the whole sentence
     * @param word the word
     * @param ne the named entity
     */
    private static void makeTripleTuple(String text, String word, String ne) {
        switch (ne) {
            case "PERSON":
                Pattern p = Pattern.compile("原名" + word);
                Matcher m = p.matcher(text);
                if(m.find()) {
                    relations.put("原名", word);
                }
                break;
            case "DATE":
                p = Pattern.compile("(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号]))");
                m = p.matcher(text);
                if(m.find()) {
                    relations.put("出生日期",m.group().replaceAll("[生|出生]", ""));
                }
                break;
            case "STATE_OR_PROVINCE":
                p = Pattern.compile("[出生在|生于]*" + word + "[人]*");
                m = p.matcher(text);
                if(m.find()) {
                    relations.put("出生地", m.group());
                }
                break;
            case "TITLE":
                relations.put("头衔", word);
                break;
            default:
                break;

        }
    }

}