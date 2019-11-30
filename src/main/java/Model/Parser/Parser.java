package Model.Parser;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements IParser {

    String[] sentencesList;
    HashMap<String, Integer> wordsDic;
    private static String monthFormat = "(January|February|March|April|May|June|July|August|September|October|November|December|JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";

    public Parser () {
        this.wordsDic = new HashMap<>();
    }

    public String[] convertTextToSentences(String text) {
        InputStream is = getClass().getResourceAsStream("/en-sent.bin");
        SentenceModel model;
        SentenceDetectorME sdetector;
        {
            try {
                model = new SentenceModel(is);
                sdetector = new SentenceDetectorME(model);
                sentencesList = sdetector.sentDetect(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sentencesList;
    }

    public void findName() throws IOException {
        InputStream is = new FileInputStream("Resources/en-ner-date.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();

        NameFinderME nameFinder = new NameFinderME(model);

        String []sentences = new String[]{
                "Mike",
                "Smith",
                "20/08/2000",
                "02.08.19",
                "14 MAY"};

        String str = "ake 14 May hyg";

        Span nameSpans[] = nameFinder.find(sentences);

        for(Span s: nameSpans)
            System.out.println(s.toString());

        for(Span s: nameSpans){
            System.out.println(sentences[s.getStart()]);
        }
    }

    public static List<Pattern> definePatterns () {
        List<Pattern> patterns = new ArrayList<>();
        String percent = "([0-9]*\\.?[0-9]*)\\s*%|([0-9]*\\.?[0-9]*)\\w* percentage|([0-9]*\\.?[0-9]*)\\w* percent";
        Pattern percentPattern = Pattern.compile(percent);
        patterns.add(percentPattern);
        String line = "the per 5 percent is 6% and off course 78.9 percentage";

        String price = "([(0-9]*\\.?[0-9]*)\\s*[$]|([0-9]*\\.?[0-9])*\\w* Dollars|([0-9]*\\ [0-9]*\\/[0-9])*\\w* Dollars";
        Pattern pricePattern = Pattern.compile(price);
        String linePrice = "the per is 6.32 Dollars and 66 4/2 Dollars and 33$";
        patterns.add(pricePattern);

        String date = "^((0?[13578]|10|12)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[01]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1}))|(0?[2469]|11)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[0]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1})))$";
        Pattern datePattern = Pattern.compile(date);
        patterns.add(datePattern);

        return patterns;
    }

    public void convertToTokens(String sentence) {
        String percent = "([0-9]*\\.?[0-9]*)\\s*%|([0-9]*\\.?[0-9]*)\\w* percentage|([0-9]*\\.?[0-9]*)\\w* percent";
        Pattern percentPattern = Pattern.compile(percent);
        String line = "the per 5 percent is 6% and off course 78.9 percentage";

        String price = "([(0-9]*\\.?[0-9]*)\\s*[$]|([0-9]*\\.?[0-9])*\\w* Dollars|([0-9]*\\ [0-9]*\\/[0-9])*\\w* Dollars";
        Pattern pricePattern = Pattern.compile(price);
        String linePrice = "the per is 6.32 Dollars and 66 4/2 Dollars and 33$";

        String date = "((([1-2][0-9])|([1-9])|(3[0-1])) " + monthFormat + ")|(" + monthFormat + " (([1-2][0-9])|([1-9])|(3[0-1])))|([0-9]{4} " + monthFormat + ")|(" + monthFormat + " [0-9]{4})";
        Pattern datePattern = Pattern.compile(date);
        String lineDate = "the NOV 2009 per 1994 MAY and 66 4/2 Dollars and 33$ and also 12 January ans June 22 jdjd MAY 24";

        Matcher matcher = datePattern.matcher(lineDate);
        while (matcher.find()) {
            String str = matcher.group();
            System.out.println(str);
        }
    }

    private void addPercentTokenToSet(Pattern pattern, String word) {

    }



    /*
    public String removeProbChar(String text){
        //for(String token: tokens)
        //token = token.replaceAll("[^a-zA-Z0-9_\\/.$%-]|(?<!\\d)\\.(?!\\d)|(?<!\\w)-(?!\\w)", " ");
        //return tokens;
        return text.replaceAll("/n", " ");
    }

    public String[] tokenizer() throws Exception {
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        // InputStream inputStream = getClass().getResourceAsStream("/en-token.bin");
        // TokenizerModel model = new TokenizerModel(inputStream);
        //  TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize(sentencesList.get(0)[1]);
        //  String[] tokens = tokenizer.tokenize(text);
        return tokens;
    }

    */

    public void insertToDic(String[] tokens){
        for(String token: tokens){

        }

    }

}