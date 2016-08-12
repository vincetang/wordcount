package trgr.rd.wordcount;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class WordCountApp {
    private static ArrayList<Path> paths;
    static ArrayList<Map<String, Integer>> top25Counts;
    private ArrayList<FileMetadata> fileMetadata;

    /**
     * Constructor for WordCountApp
     */
    private WordCountApp()
    {
        this.fileMetadata = new ArrayList<>();
        top25Counts = new ArrayList<>();
    }

    /**
     * Processes files' metadata and adds to fileMetadat ArrayList
     */
    private void process()
    {
        MetadataParser parser = new MetadataParser();
        for (int i = 0; i < paths.size(); i++)
        {
            this.fileMetadata.add(parser.parse(i + 1, paths.get(i)));
        }
    }

    /**
     * Word counting application.
     */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            //Require 1 argument specify the directory uto use
            System.err.println("Usage: " + WordCountApp.class.getCanonicalName() + " [directory-of-files]");
            System.exit(1);
        }

        Path dir = Paths.get(args[0]);
        if (!Files.exists(dir) || !Files.isDirectory(dir))
        {
            System.err.println("Directory '" + dir.toAbsolutePath() + "' does not exist, or is not a directory.");
            System.exit(2);
        }

        System.out.println("Using directory '" + dir.toAbsolutePath() + "'");

        // Start application logic..
        System.out.println("Word count report...\n");

        // Create an array to store Path objects representing files to read
        paths = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            stream.forEach(paths::add);
        } catch (IOException | DirectoryIteratorException e)
        {
            // Exceptions getting file stream
            e.printStackTrace();
        }

        System.out.print(paths.toString());

        // parse each file and create metadata objects
        WordCountApp wca = new WordCountApp();
        wca.process();

        // Report for each file
        for (FileMetadata f : wca.fileMetadata)
        {
            System.out.println(f.toString());
        }

        // Summary of all files
        Map<String, Integer> combinedMap = new HashMap<>();
        top25Counts.forEach(combinedMap::putAll);
        Map<String, Integer> sortedCombinedMap = FileMetadata.sortByValue(combinedMap);

        System.out.print("Most frequent words in " + dir.toAbsolutePath() + '\n');

        StringBuilder s = new StringBuilder();
        Set keys = sortedCombinedMap.keySet();
        Iterator iter = keys.iterator();
        int i = 0;
        while (iter.hasNext() && i < 25)
        {
            String word = (String) iter.next();
            Integer count = sortedCombinedMap.get(word);
            s.append(word).append(" (").append(count).append(")\n");
            i++;
        }

        System.out.println(s);
    }

}
