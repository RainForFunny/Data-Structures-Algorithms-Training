package structures;

/**
 * 环形单链表和约瑟夫环问题
 * Joseph问题为:设编号为1，2，...n的n个人围坐一圈，约定编号为k (1<=k<=n)的人从1开始报数，数到m的那个人出列。
 * 它的下一位又从1开始报数，数到m的那个人又出列，依次类推，直到所有人出列为止，由此产生1个出队编号的序列。
 */
public class Joseph {

    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(20);
        // circleSingleLinkedList.list();
        circleSingleLinkedList.josephPrint(2,3,20);
    }
}

/**
 * 环形链表
 */
class CircleSingleLinkedList {
    private BoyNode first = null;

    /**
     * Joseph问题
     * @param k 编号为k的开始数数
     * @param m 数m个停下
     * @param num 总共多少人
     */
    public void josephPrint(int k, int m, int num) {
        if (first == null || m < 1 || m > num) {
            System.out.println("参数有误，重新输入");
        }
        BoyNode helper = first;

        // 创建辅助变量
        // helper变量指向最后一个节点
        while (helper.getNext() != first) {
            helper = helper.getNext();
        }
        // 报数前，先让helper和first移动k-1次
        for (int i = 0; i < k - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        int count = 0;
        // 报数m-1次直至圈中只剩一个节点
        while (true) {
            count++;
            if (helper == first) {
                break;
            }
            // 让helper和first移动m-1次
            for (int i = 0; i < m - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.println("第" + count + "个出圈的编号为：" + first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.println("第" + count + "个出圈的编号为：" + helper.getNo());
    }

    // 根据参数中的数字遍历添加，构成环形链表
    public void addBoy(int num) {
        if (num < 2) {
            System.out.println("数字有误");
        }

        BoyNode curBoy = null; // 帮助构建环形链表
        for (int i = 1; i <= num; i++) {
            BoyNode boy = new BoyNode(i);
            if (i == 1) {
                first = boy;
                // 构成环
                first.setNext(first);
                curBoy = first;
            } else {
                // 设置下一个节点为boy
                curBoy.setNext(boy);
                // 设置boy的下一个节点为first，构成环形
                boy.setNext(first);
                // 将curBoy后移
                curBoy = boy;
            }
        }
    }

    /**
     * 遍历
     */
    public void list() {
        if (first == null) {
            System.out.println("链表为空~");
        }

        BoyNode curBoy = first;
        while (true) {
            System.out.println("boy的编号为：" + curBoy.getNo());
            if (curBoy.getNext() == first) {
                break;
            }
            curBoy = curBoy.getNext();
        }
    }
}
