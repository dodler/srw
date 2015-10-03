rm out
mkdir out
cd java
javac lian/artyom/RMethods.java
java -cp . lian.artyom.RMethods /media/artem/385BE95714C3BE20/[R]src/hist/pic/large_test.bmp /media/artem/385BE95714C3BE20/[R]src/hist/out output csv 25
cd ..
Rscript map_reduce_hist 25

