package structures.recursion;

/**
 * 8皇后问题
 */
public class Queen8 {

    // 定义一个max表示多少个皇后
    int max = 8;
    static int count = 0;
    static int judgeCount = 0;
    // 定义一个数组arr，保存皇后放置位置的结果，下标表示第几行
    int[] array = new int[max];

    public static void main(String[] args) {
        Queen8 queen = new Queen8();
        queen.check(0);
        System.out.printf("一共有%d种解法", count);
        System.out.println();
        System.out.printf("判断冲突的次数一共有有%d次", judgeCount);
    }

    /**
     * 放置第n个皇后
     */
    public void check(int n) {
        if (n == max) {
            print();
            count++;
            return;
        }
        // 依次放入皇后，并判断是否冲突 check是每一次递归时，进入到check中都有for循环，，因此会有回溯
        // 1.第一个皇后先放第一行第一列
        // 2.第二个皇后放在第二行第一列、然后判断是否可行，如果不行，继续放在第二列、第三列......依次放完所有列，找到一个合适的点
        // 3.继续放第三个皇后，还是跟2一样，从第一列、第二列......知道第8个皇后也能放在一个不冲突的位置，即为找到一个正确解
        // 4.当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后放到第一列的所有正确解全部得到 5.然后回头继续第一个皇后放第二列，后面继续循环执行上面4个步骤
        for (int i = 0; i < max; i++) {
            // 先放入该行的第一列
            array[n] = i;
            // 判断当放置第n个皇后到i时，是否冲突
            if (judge(n)) {
                // 放入第n+1个皇后，开始递归
                check(n + 1);
            }
            // 如果发生冲突，就继续执行array[n] = i，即将第n个皇后放置在本行后移的一个位置
        }
    }

    /**
     * 查看当放置第n个皇后时，就去检测该皇后和前面已经摆放的皇后是否存在冲突
     */
    public boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {
            // 1.array[i] == array[n] 表示判断第n个皇后和前面n-1个皇后在同一列
            // 2.Math.abs(n - i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后和前面n-1个皇后是否在同一斜线

            if (array[i] == array[n]
                    || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                // 说明在同一列或同一斜线
                return false;
            }
        }
        return true;
    }

    /**
     * 输出皇后摆放的位置
     */
    public void print() {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
