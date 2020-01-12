import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import java.io.IOException;

public class MyReducer extends Reducer<Text,Text,Text,Text> {

    private MultipleOutputs output;

    @Override
    public void setup(Context context) {
        output = new MultipleOutputs(context);
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        output.close();
    }

    public void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
        StringBuilder text = new StringBuilder();
        for (Text v : value) {
            text.append(v.toString());
            //Ogni tot parole si scrive per evitare di far crashare hadoop
            if(text.length()>10000){
                Text bi_grams = new Text(text.toString());
                //I reducers non hanno più bisogno del controllo sulla chiave grazie alle chiavi specifiche
                output.write(key.toString(), bi_grams, new Text(""));
                text = new StringBuilder();
            }
        }
    }
}
