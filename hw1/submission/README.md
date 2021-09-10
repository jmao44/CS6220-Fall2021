# CS6220 Fall 2021 Assignment 1

## Name: Junyan Mao
## GTID: 903343678

### Disclaimer:
- The code for the word count program is adopted from Apache's official MapReduce tutorial found in the following link: https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
- This has been approved by Professor Liu in this Piazza post: https://piazza.com/class/ksp6ajd2q5h69g?cid=26

### Folder structure:
- datasets/ - datasets used in this assignment (smaller ones)
  - 20/ - 20 text files used to find the top 100 words
  - alice/ - full text of Alice's Adventures in Wonderland
  - hello_world/ - simple "Hello World" text
  - mark_twain/ - all of Mark Twain's works
  - pride/ - full text of Pride and Prejudice
- misc/ - miscellaneous files
  - patterns.txt - patterns to be ignored
- outputs/ - outputs of the MapReduce program
- results_on_20_files/ - results of getting the top 100 words from 20 files
  - top_100_words_in_20_books.txt - final results after selecting the top 100 entries from the dataset consisting of 20 well-known books
- screenshots/ - screenshots of output log when running MapReduce jobs
- src/ - containing source files
  - WordCount.java - depreacated WordCount program
  - WordCount2.java - the WordCount program used in this assignment
  - CountTop.java - the program used to sort the results with count as key and word as value and pick the top 100.
- word_count_perf.xlsx - Excel file recording the performance of MapReduce on datasets of different size
- README.md - this file
- analytical_report.pdf - the analytical report of this assignment

### Running the WordCount MapReduce program:
1. Make sure you have installed and started Hadoop on your machine.
2. Run the following command in your terminal:
    - `hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=false <input_directory> <output_directory> -skip <file_containing_patterns_to_skip>`
3. Results will be available in the `<output_directory>` specified above.

### Running the Top-100 Word program:
1. Make sure you have installed and started Hadoop on your machine.
2. Run the following command in your terminal:
    - `hadoop jar ct.jar CountTop <output_of_WordCount_program> <output_directory>`
3. Results will be available in the `<output_directory>` specified above.