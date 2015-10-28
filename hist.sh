rm out
mkdir out
cd java
javac lian/artyom/RMethods.java
java -cp . lian.artyom.RMethods /media/artem/385BE95714C3BE20/[R]src/hist/pic/test38bit.bmp /media/artem/385BE95714C3BE20/[R]src/hist/out output csv 30
cd ..
Rscript map_reduce_hist_3dim 30

