package Model.Reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * This class represents an object that receives a path and know to read all the txt files inside
 */
public class ReadFile {

    private String[] docsList, rawDocsList, refinedDocList;
    private String docNum;
    private String docText;
    private ArrayList<String> filesPaths;
    private File folder;
    private Queue<String> corpus;

    /**
     * Constructor
     */
    public ReadFile(File folder) {
        String[] docsList;
        String docNum;
        String docText;
        corpus = new ArrayDeque<>();
        filesPaths = new ArrayList<>();
        this.folder = folder;
    }


    /**
     *The function receives a folder and return a list of paths to all the files inside it
     */
    public void  listFilesForFolder(){
        filesPaths = listFilesForFolderAss(folder, filesPaths);
    }

    /**The function is assistant function to listFilesForFolder that operate the recursive operation
     * The function
     * @param folder
     * @param filesPaths
     * @return ArrayList with all the pats
     */
    public ArrayList<String> listFilesForFolderAss(final File folder, ArrayList<String> filesPaths) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolderAss(fileEntry,filesPaths);
            } else {
                filesPaths.add(fileEntry.getPath());
            }
        }
        return filesPaths;
    }

    /**
     * The function reads all the corpus
     */
    public void readAllCorpus(){
        for(String path:filesPaths){
            corpus.add(ReadWholeFile(path));
        }
    }

    /**
     * The function receives a path to txt file and read all the txt inside it
     * @param path
     * @return string with all the text inside the file
     */
    public String ReadWholeFile(String path) {
        String stringFile= "";
        try {
            stringFile = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return stringFile;
    }

    /**
     *The function separate all the text between the <TEXT> </TEXT> tags in the document
     * @param file
     */
    public void exportTextFromFile(String file){
        rawDocsList = file.split("</TEXT>");
        refinedDocList = new String[rawDocsList.length - 1];
        for(int i = 0; i < refinedDocList.length; ++i){
            refinedDocList[i] = rawDocsList[i].split("<TEXT>")[1];
            // cleanDocsList[i] =  cleanDocsList[i].replaceAll("\\R+", " ");
        }
    }


}