// Code adopted from Apache's official MapReduce tutorial: https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
import java.io.IOException;
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

  public static class CountTopMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String[] pair = value.toString().split("\\s+");

      String word = pair[0];
      IntWritable count = new IntWritable(Integer.valueOf(pair[1]));

      context.write(count, new Text(word));

    }
  }

  public static class CountTopReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
    public void reduce(IntWritable key, Text values, Context context) throws IOException, InterruptedException {

      context.write(key, values);
    }
  }

  public static class ReverseComparator extends WritableComparator {
    protected ReverseComparator() {
      super(IntWritable.class, true);
    }

    @SuppressWarnings("rawtypes")

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
      IntWritable i1 = (IntWritable) w1;
      IntWritable i2 = (IntWritable) w2;

      return -1 * i1.compareTo(i2);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");

    job.setJarByClass(CountTop.class);
    job.setMapperClass(CountTopMapper.class);
    job.setCombinerClass(CountTopReducer.class);
    job.setReducerClass(CountTopReducer.class);

    job.setSortComparatorClass(ReverseComparator.class);
  
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(Text.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}