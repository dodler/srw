package lian.artyom;

import lian.artyom.sign.Sign;
import lian.artyom.sign.impl.FirstAngleMoment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by artem on 27.09.15.
 */
public class RMethods {
    /**
     * class that contains data after mapping data in picture
     * key - value of color
     * value - number of entering of key in pic
     */
    @Deprecated
    public static final class Result {
        public int[] key, value;

        public Result(int size) {
            this.key = new int[size];
            this.value = new int[size];
        }
    }

    ;

    /**
     * class that contains one color tuple for 3dim histogram
     */
    public static final class Tuple extends Object {
        public int x, y, z; // here z is current color, z - neighbour by ax, y - neighbour - by ay, value is number of this color

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;

            result = prime * result + z;
            result = prime * result + x;
            result = prime * result + y;

            return result;
        }

        private EqualityOperation operation;

        @Override
        public boolean equals(Object tuple) {
            if (!tuple.getClass().equals(Tuple.class)) return false;

            return operation.equals(this, tuple);
        }

        public void setOperation(EqualityOperation operation) {
            this.operation = operation;
        }

        private Tuple() {
            this.operation = CHECK_COORDINATES_WITH_ORDER;
        }

        public static final EqualityOperation CHECK_COORDINATES_WITHOUT_ORDER = new EqualityOperation() { // default check operation checks if all coordinates are equal
            // order of coordinates should be equal
            @Override
            public boolean equals(Object tuple1, Object tuple2) {
                Tuple t1, t2;
                try {
                    t1 = (Tuple) tuple1;
                    t2 = (Tuple) tuple2;
                } catch (ClassCastException e) {
                    return false;
                }
                return (t1.z == t2.z);
            }
        };

        public static final EqualityOperation CHECK_COORDINATES_WITH_ORDER = new EqualityOperation() { // default check operation checks if all coordinates are equal
            // order of coordinates should be equal
            @Override
            public boolean equals(Object tuple1, Object tuple2) {
                Tuple t1, t2;
                try {
                    t1 = (Tuple) tuple1;
                    t2 = (Tuple) tuple2;
                } catch (ClassCastException e) {
                    return false;
                }
                return ((t1.z == t2.z) && (t1.x == t2.x) && (t1.y == t2.y));
            }
        };

        public Tuple(int z, int x, int y) {
            this();
            this.z = z;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(z);
            sb.append(":");
            sb.append(x);
            sb.append(":");
            sb.append(y);
            sb.append(")");
            return sb.toString();
        }
    }

    ;

    /**
     * container for histogram
     * it contains array of tuples
     * and count of each tuples
     */
    public static final class Result3D {
        // tuple contains 3 ints -
        // color of current pixel (color possibly should be replaced with brightness )
        //
        public Tuple[] tuples; // single tuple
        public int[] values; // count of tuple

        private Result3D() {

        }

        public Result3D(int length) {
            tuples = new Tuple[length];
            values = new int[length];
        }

        @Override
        public boolean equals(Object result) {
            Result3D result3D;
            try {
                result3D = (Result3D) result;
            } catch (ClassCastException e) {
                return false;
            }
            if (result == null) return false;
            if (result3D.values == null || result3D.tuples == null) return false;

            Map<Integer, Tuple> source = new HashMap<>(), dest = new HashMap<>();

            for (int i = 0; i < values.length; i++) {
                source.put(values[i], tuples[i]);
            }

            for (int i = 0; i < result3D.values.length; i++) {
                dest.put(values[i], tuples[i]);
            }
            return source.equals(dest);
        }
    }

    public RMethods() {

    }

    //    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/test_mono_2.png";
//    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/test_mono.bmp";
//    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/test_double.bmp";
//    private static final String imgPath = "/media/artem/385BE95714C3BE20/IdeaProjects/Custom/hist/srw/pic/test8bit.bmp";
    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/test8bit.bmp";
//    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/Volunteer_Park_Blues.jpg";

    public static void main(String[] args) throws IOException {
        HistogramBuilder builder = new HistogramBuilder(1, imgPath);
//        Result3D result = builder.build(HistogramBuilder.MAPPER_3_DIM);
        builder.build(HistogramBuilder.MAPPER_1_DIM);
        System.out.println("result ready");
//        Sign firstAngleMoment = new FirstAngleMoment();
//        firstAngleMoment.calc(result);
//        System.out.println("first angle");
//        System.out.println(firstAngleMoment.getResult());

//        new HistogramBuilder(1, imgPath).build(HistogramBuilder.MAPPER_3_DIM);
    }
}
