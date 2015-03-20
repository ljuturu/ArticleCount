import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class ArticleCount {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> 
	{
		 private final static IntWritable artCount = new IntWritable(1);
		 private Text word = new Text();
		 String keyWord; 
		 
		//This method retrieves the argument and sets into keyWord and word
		 public void configure (JobConf conf)
		 {
			 keyWord=conf.get("argWord");  
			 word.set(keyWord);
		 }
		 public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
		 {
			 String line = value.toString();	
			 String artCont;
			 
			 String tokens[]=line.split("\\t"); //Split each line with respect to tabs
			 word.set(keyWord);
			 artCont = tokens[1] +tokens[3]; //Merging article with content
			 if (artCont.contains(keyWord)) 					    
			 {					 
				 output.collect(word,artCount); //sets the output of each mapper as <word,1> if any article is matched
			 }						 				
		 }
	}

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> 
    {
	     public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
	     {
	    	 int sum = 0;
	    	 while (values.hasNext()) 
	    	 {
	    		 sum += values.next().get(); //Adds up the individual article count for the given keyword 
	    	 }
	    	 
	    	 output.collect(key, new IntWritable(sum)); //Writes the final output as <keyword,number of articles>
	     }
    }
    public static void main(String[] args) throws Exception 
    {
		 int n=1;  
		 JobConf conf = new JobConf(ArticleCount.class);
		 conf.setJobName("articlecount");
		 
		 conf.setOutputKeyClass(Text.class);
		 conf.setOutputValueClass(IntWritable.class);
		 //sets mapper, reducer and number of reducers
		 conf.setMapperClass(Map.class);
		 conf.setReducerClass(Reduce.class);
		 conf.setNumReduceTasks(n);
		 //sets the input format and output format as text
		 conf.setInputFormat(TextInputFormat.class);
		 conf.setOutputFormat(TextOutputFormat.class);
		 //sets the input path, keyword from arguments and output path hard-coded 
		 FileInputFormat.setInputPaths(conf, new Path(args[0]));
		 FileOutputFormat.setOutputPath(conf, new Path("art_count/output"));
		 conf.set("argWord", args[1]);
		 JobClient.runJob(conf);
   }
}
