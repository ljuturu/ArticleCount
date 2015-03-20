# ArticleCount
Identify the number of articles in the entire Wikipedia dataset with the help of a given article name using MapReduce program.

Current Directory:- /mnt/CS5331_HomeDirectory/ljuturu/p_project1/art_count
----------------- 
<Run the below two commands only if the file is modified - To compile and create jar>

javac -classpath $HADOOP_CLASSPATH'/*' -d artcount_classes/ ArticleCount.java 
jar -cvf artcount.jar -C artcount_classes/ .

Input from HDFS and output created on HDFS:
-------------------------------------------
Input - /CS5331_Examples/Programming_Project_Dataset.txt
Output- /user/ljuturu/art_count/output

Run the application:
--------------------
<Run 4 test cases>

hadoop jar artcount.jar ArticleCount /CS5331_Examples/Programming_Project_Dataset.txt texas
hadoop jar artcount.jar ArticleCount /CS5331_Examples/Programming_Project_Dataset.txt "Red Raider"
hadoop jar artcount.jar ArticleCount /CS5331_Examples/Programming_Project_Dataset.txt "Texas Tech"
hadoop jar artcount.jar ArticleCount /CS5331_Examples/Programming_Project_Dataset.txt Lubbock

Check Output:
-------------
hadoop fs -ls /user/ljuturu/art_count/output
hadoop fs -cat /user/ljuturu/art_count/output/part-00000 

Delete output folder and run again:
-----------------------------------
hadoop fs -rm -r -skipTrash /user/ljuturu/art_count/output

To copy the hdfs output file to local:
--------------------------------------
hadoop fs -copyToLocal /user/ljuturu/art_count/output/part-00000 /mnt/CS5331_HomeDirectory/ljuturu/p_project1/art_count/output
