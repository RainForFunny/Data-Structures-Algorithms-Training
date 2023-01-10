package structures.recursion;

public class RecursionTest {
    public static void main(String[] args) {
        test(4);
    }

    /**
     * Exception in thread "main" java.lang.StackOverflowError
     * 本质上递归是开辟一个栈空间，然后依次进行计算
     * @param n
     */
    private static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }
}
