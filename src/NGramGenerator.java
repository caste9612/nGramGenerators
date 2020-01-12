import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class NGramGenerator {

    public static void main(String[] args) {

        long starTime = System.nanoTime();

        long counter = 1;
        String input = args[0];
        System.out.println("Input Database location: " + input);
        String output = args[1];
        System.out.println("Output location: " + input);

        try{
            //return the number of books
            Stream<Path> files = Files.list(Paths.get(input));
            counter = files.count();

            File fileOut2 = new File(output+"/output2");
            FileWriter fw2 = new FileWriter(fileOut2);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            File fileOut3 = new File(output+"/output3");
            FileWriter fw3 = new FileWriter(fileOut3);
            BufferedWriter bw3 = new BufferedWriter(fw3);


        try{
            for(int i=1;i<=counter; i++) {
                   
                File file = new File(input + "/"+i);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;

                StringBuilder ngrams2 = new StringBuilder();
                StringBuilder ngrams3 = new StringBuilder();
                ngrams2.append("\n").append("File n: "+i).append("\n");
                ngrams3.append("\n").append("File n: "+i).append("\n");

 				while ((st = br.readLine()) != null) {
                    if(!st.isBlank()) {

                     	//trasformo la stringa in una stringa senza spazi
                        String[] words;
                        StringBuilder line = new StringBuilder(new String(""));
                        words = st.split("\\s+");
                        for (String word : words) {
                            line.append(word);
                        }

                        //crea 2-gram
                        st = line.toString();
                        for (int j = 0; j < st.length() - 2 + 1; j++) {
                            ngrams2.append(st, j, j + 2).append(",");
                        }
                        //create 3-gram
                        for (int j = 0; j < st.length() - 3 + 1; j++) {
                            ngrams3.append(st, j, j + 3).append(",");
                        }
                        ngrams2.append("\n");
                        ngrams3.append("\n");
                    }
                }

                bw2.write(ngrams2.toString());
                bw3.write(ngrams3.toString());
            }
        }catch (Exception e){
            System.out.println("Elaboration error");
        }
        }catch (Exception e){
            System.out.println("Database initialization error");
        }

        long endTime = System.nanoTime();
        long totalTime = (endTime - starTime)/1000000000;

        System.out.println("Execution time: " + totalTime + " s");
    }
}