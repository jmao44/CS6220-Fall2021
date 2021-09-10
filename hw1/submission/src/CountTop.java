import java.io.IOException;
import java.lang.Math;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CountTop {

  public static class CountTopMapper extends Mapper<Object, Text, LongWritable, Text>{
    
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      String[] tokens = value.toString().split("\\s+");
      
      String word = tokens[0];
      long wordCount = Long.parseLong(tokens[1]);

      wordCount = (-1) * wordCount;

      context.write(new LongWritable(wordCount), new Text(word));

    }
  }

  public static class CountTopReducer extends Reducer<LongWritable, Text, LongWritable, Text> {

    static int count;

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
      count = 0;
    }

    @Override
    public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
      for (Text val : values) {
        String word = val.toString();
        if (count < 100) {
          context.write(new LongWritable(Math.abs(key.get())), new Text(word));
          count++;
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");

    job.setJarByClass(CountTop.class);
    job.setMapperClass(CountTopMapper.class);
    job.setCombinerClass(CountTopReducer.class);
    job.setReducerClass(CountTopReducer.class);
  
    job.setOutputKeyClass(LongWritable.class);
    job.setOutputValueClass(Text.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}