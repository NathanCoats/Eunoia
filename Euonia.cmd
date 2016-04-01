del Connection.class
del Edge.class
del Euonia.class
del Graph.class
del Mongo.class
del Node.class
del Parser.class
del Rank.class

cls
javac -Xdiags:verbose -cp .;./bin/jsoup-1.8.3.jar;./bin/mongo-2.13.1.jar; Euonia.java
java -cp .;./bin/jsoup-1.8.3.jar;./bin/mongo-2.13.1.jar; Euonia
