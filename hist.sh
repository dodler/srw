rm out
mkdir out
cd java
// TODO rewrite java code launching
javac -cp lib/hamcrest-core-1.3.jar:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar lian/artyom/*.java
#javac -cp 'lib/*.jar' lian/artyom/*.java
#cd ..
#java -cp . lian.artyom.RMethods /media/artem/385BE95714C3BE20/[R]src/hist/pic/test38bit.bmp /media/artem/385BE95714C3BE20/[R]src/hist/out output csv 30
java -cp lib/hamcrest-core-1.3.jar:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar:. lian.artyom.RMethods
#Rscript map_reduce_hist_3dim 30

