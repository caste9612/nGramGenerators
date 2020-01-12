import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;



public class MyMapper extends Mapper<Object, Text, Text, Text>{

    private Text gram = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        //Leggo file e splitto parole
        String[] words;
        StringBuilder document = new StringBuilder(new String(""));
        words = value.toString().split("\\W+");
        for (String word : words) {
            document.append(word);
        }

        StringBuilder ngrams2 = new StringBuilder();
        StringBuilder ngrams3 = new StringBuilder();


        //Inserisco nome file
        String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        ngrams2.append("\n").append(fileName).append("\n");
        ngrams3.append("\n").append(fileName).append("\n");


        //Calcolo bigrammi
        String st = document.toString();
        int k=1;
        for (int i = 0; i < st.length() - 2 + 1; i++) {
            if(k%40==0)
                ngrams2.append("\n");
            ngrams2.append(st,i, i + 2).append(",");
            k++;
        }

        //outputto il context con i bigrammi di un intero libro
        gram.set(ngrams2.toString());
        //Text nkey = new Text(fileName);
        context.write(new Text(HadoopParallelNGramGenerator.bicode),gram);

        //Calcolo trigrammi
        k=1;
        for (int i = 0; i < st.length() - 3 + 1; i++) {
            if(k%30==0)
                ngrams3.append("\n");
            ngrams3.append(st, i, i + 3).append(",");
            k++;
        }

        //outputto il context con i trigrammi di un intero libro
        gram.set(ngrams3.toString());
        context.write(new Text(HadoopParallelNGramGenerator.tricode),gram);
    }
}
