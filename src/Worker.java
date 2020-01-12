import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Worker extends Thread{

    private Master master;
    private String input;
    private int booksToProcessEachIteration;

    public Worker(Master master,String input, int booksToProcessEachIteration)
    {
        this.booksToProcessEachIteration = booksToProcessEachIteration;
        this.master = master;
        this.input = input;
    }

    public void run() {

        //chiedi se ci sono ancora file da processare
        try {

            while (master.isBookavaible()) {
            
                //chiedi l'id/gli id dei libri
                Integer[] bookId = master.giveBookId(this.booksToProcessEachIteration);

                //processa i files
                StringBuilder ngrams2 = new StringBuilder();
                StringBuilder ngrams3 = new StringBuilder();

                for(int h=0;h<this.booksToProcessEachIteration;h++) {

                    if(bookId[h]!=null){

                        //Carica il file
                        ngrams2.append("\n").append("File n: "+bookId[h]).append("\n");
                        ngrams3.append("\n").append("File n: "+bookId[h]).append("\n");
                        File file = new File(input + "/" + bookId[h].toString());
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String st;

                        //Processa IL file
                        //controlla se il file è finito
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
                                for (int i = 0; i < st.length() - 2 + 1; i++) {
                                    ngrams2.append(st, i, i + 2).append(",");
                                }
                                //create 3-gram
                                for (int i = 0; i < st.length() - 3 + 1; i++) {
                                    ngrams3.append(st, i, i + 3).append(",");
                                }
                                ngrams2.append("\n");
                                ngrams3.append("\n");
                            }
                        }
                    }
                }

                master.write2gram(ngrams2.toString());
                master.write3gram(ngrams3.toString());
                
            }
        } catch (Exception e) {
            System.out.println("Thread error");
        }
    }
}