SEQUENTIAL JAVA: jdk13 suggested

/home/caste/Scrivania/jdk-13.0.1/bin/javac NGramGenerator.java

/home/caste/Scrivania/jdk-13.0.1/bin/java NGramGenerator /home/caste/Scrivania/inputHadoop /home/caste/Scrivania/output     




PARALLEL JAVA: jdk13 suggested

/home/caste/Scrivania/jdk-13.0.1/bin/javac BiTiGramGenerator.java Master.java Worker.java   

/home/caste/Scrivania/jdk-13.0.1/bin/java BiTiGramGenerator /home/caste/Scrivania/inputHadoop /home/caste/Scrivania/output 1




HADOOP STANDALONE: jdk8 suggested
edit on conf-standalone/hadoop-env.sh and replace:

# The java implementation to use.  Required.
export JAVA_HOME=/home/caste/Scrivania/java-se-8u40-ri

with your jdk folder
then run from this folder:

hadoop --config conf-standalone jar ./out/artifacts/HadoopParallelNGramGenerator_jar/HadoopParallelNGramGenerator.jar /home/caste/Scrivania/inputHadoop /home/caste/Scrivania/outputHadoop

 and change 2 last arguments with your input output paths.

HADOOP PSEUDO: jdk8 suggested

1 /usr/lib/hadoop-3.2.1/bin/hadoop namenode -format
2 /usr/lib/hadoop-3.2.1/bin/hadoop fs -mkdir /tmp/hadoop-caste/input
3 /usr/lib/hadoop-3.2.1/bin/hadoop fs -ls /tmp/hadoop-caste
4 /usr/lib/hadoop-3.2.1/bin/hadoop --config /home/caste/Scrivania/HadoopParallelNGramGenerator/conf-pseudo jar /home/caste/Scrivania/HadoopParallelNGramGenerator/out/artifacts/HadoopParallelNGramGenerator_jar/HadoopParallelNGramGenerator.jar /tmp/hadoop-caste/input /tmp/hadoop-caste/output 
