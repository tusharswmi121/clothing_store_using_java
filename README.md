This is Object-Oriented Analysis and Design Project which is using MVC architecture and my sql database 
Below Three line command to compile and run this project 

find . -name "*.java" > sources.txt
javac -cp "mysql-connector-j-9.2.0.jar" -d bin @sources.txt
java -cp "bin:mysql-connector-j-9.2.0.jar" Main
