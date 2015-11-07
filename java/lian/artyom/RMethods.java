package lian.artyom;

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
        int x, y, z; // here z is current color, z - neighbour by ax, y - neighbour - by ay, value is number of this color

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;

            result = prime * result + z;
            result = prime * result + x;
            result = prime * result + y;

//            System.out.println("hash return:" + result);
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

    public static void testTupleHash() {
        Tuple x1 = new Tuple(0, 0, 0);
        Tuple x2 = new Tuple(0, 1, 0);
        Tuple x3 = new Tuple(1, 3, 0);
        Tuple x5 = new Tuple(1, 3, 4);
        Tuple x4 = new Tuple(0, 0, 0);

        Map testMap = new HashMap<Tuple, Integer>(5);
        testMap.put(x1, 1);

        System.out.println("map contains x4:");
        System.out.println(testMap.containsKey(x4));
        System.out.println("map contains x1:");
        System.out.println(testMap.containsKey(x1));

    }

    /**
     * container for tuples
     */
    public static final class Result3D {
        public Tuple[] tuples;
        public int[] values;

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
    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/test8bit.bmp";
//    private static final String imgPath = "/home/dodler/Документы/hist[R]/srw/pic/Volunteer_Park_Blues.jpg";

    public static void main(String[] args) throws IOException {
        new HistogramBuilder(50, imgPath).build(HistogramBuilder.MAPPER_1_DIM);
    }
}
