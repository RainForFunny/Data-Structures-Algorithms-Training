package structures.recursion;

/**
 * 迷宫问题
 * 1.小球得到的路径，和程序员设置的找路策略有关，即找路的上下左右的顺序相关。
 * 2.在得到小球路径时，可以先使用（下右上左），再改成（上右下左），看看路径是不是变化。
 * 3.测试回溯现象。
 * 4.如何求出最短路径？
 */
public class MiGong {

    public static void main(String[] args) {
        // 先创建一个二维数组，模拟迷宫
        // 地图
        int[][] map = new int[8][7];
        // 使用1表示墙
        // 上下位置全部为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        // 左右位置全部为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        // 设置挡板，1表示
        map[3][1] = 1;
        map[3][2] = 1;
        // 如果堵死这两个地方,则被包围的地方都会被标记为3
        // map[1][2] = 1;
        // map[2][2] = 1;

        // 输出map
        System.out.println("地图的情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // 使用递归回溯找路
        setWay(map, 1, 1);
        // 输出新地图，即为小球走过并标识的地方
        System.out.println("新地图情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯来找路
     * i和j表示从地图的哪个位置开始触发(1,1)
     * 如果小球能到map[6][5]位置，则说明路径找到了
     * 约定：当map[i][j]为0表示该点没有走过，当为1时表示墙，当为2时表示路径可以通过，当为3时表示该点已经走过，但是不通
     * 在走迷宫之前需要定一个策略：
     * @param map 地图坐标
     * @param i 从哪个位置开始找
     * @param j 位置
     * @return
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                // 策略 下->右->上->左
                //假定该点可以通过
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {
                    // 下
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    // 右
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    // 上
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    // 左
                    return true;
                } else {
                    // 死路
                    map[i][j] = 3;
                    return false;
                }
            } else {
                // 如果map[i][j] != 0  1,2,3
                // 1为墙，不能走，返回false
                // 2为已经走过的，不需要走，返回false
                // 3为死路，不能走，返回false
                return false;
            }
        }
    }

    public static boolean setWay2(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                // 策略 上->右->下->左
                //假定该点可以通过
                map[i][j] = 2;
                if (setWay2(map, i - 1, j)) {
                    // 上
                    return true;
                } else if (setWay2(map, i, j + 1)) {
                    // 右
                    return true;
                } else if (setWay2(map, i + 1, j)) {
                    // 下
                    return true;
                } else if (setWay2(map, i, j - 1)) {
                    // 左
                    return true;
                } else {
                    // 死路
                    map[i][j] = 3;
                    return false;
                }
            } else {
                // 如果map[i][j] != 0  1,2,3
                // 1为墙，不能走，返回false
                // 2为已经走过的，不需要走，返回false
                // 3为死路，不能走，返回false
                return false;
            }
        }
    }
}
