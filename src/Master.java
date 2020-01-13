import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicInteger;

public class Master{

    private AtomicInteger counter;
    //private int counter = 0;
    private BufferedWriter bw2;
    private BufferedWriter bw3;

    public Master(String input, String output){
        try {

            //Acquisizione numero file
            Stream<Path> files = Files.list(Paths.get(input));
            int tmp= (int)files.count();
            this.counter = new AtomicInteger(tmp);
            //this.counter = (int)files.count();

            //Inizializzazione buffer scrittura
            File fileOut2 = new File(output + "/output2Parallel");
            FileWriter fw2 = new FileWriter(fileOut2);
            this.bw2 = new BufferedWriter(fw2);
            File fileOut3 = new File(output + "/output3Parallel");
            FileWriter fw3 = new FileWriter(fileOut3);
            this.bw3 = new BufferedWriter(fw3);

        }catch (Exception e){
           System.out.println("Database initialization error");
        }
    }

    //Verifica se ci sono ancora file da processare
    public boolean isBookavaible() {
        if (counter.get() == 0)
            return false;
        else
            return true;
    }

    //Restituisce id del libro/i da processare
    public Integer[] giveBookId(int p)  {

        Integer[] booksId=new Integer[p];
        if(counter.get() >= p) {
            for(int k=0;k<p;k++) {
                booksId[k] = new Integer(counter.get());
		int v;
		do{
                	v=counter.get();
		} while (!counter.compareAndSet(v, v-1));
            }
        }
        else{
            int k=0;
            while(counter.get()>0){
                booksId[k] = new Integer(counter.get());
		int v;
		do{
                	v=counter.get();
		} while (!counter.compareAndSet(v, v-1));
                k++;
            }
        }
        return booksId;
    }

    //Scrive 2-gram processati da 1 worker nel file di output
    public synchronized void write2gram(String ngrams2) throws Exception{
        this.bw2.write(ngrams2);
        bw2.newLine();
    }

    //Scrivi 3-gram
    public synchronized void write3gram(String ngrams3) throws Exception{
        this.bw3.write(ngrams3);
        bw3.newLine();
    }
}
