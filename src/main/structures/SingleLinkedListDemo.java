package structures;

import java.util.Stack;

/**
 * 单链表demo
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode heroNode = new HeroNode(0, "一号", "宋江");
        HeroNode heroNode1 = new HeroNode(1, "二号", "卢俊义");
        HeroNode heroNode2 = new HeroNode(2, "三号", "吴用");
        HeroNode heroNode3 = new HeroNode(3, "四号", "林冲");

        // singleLinkedList.add(heroNode);
        // singleLinkedList.add(heroNode3);
        // singleLinkedList.add(heroNode2);
        // singleLinkedList.add(heroNode1);
        singleLinkedList.addByOrder(heroNode);
        singleLinkedList.addByOrder(heroNode3);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode1);

        singleLinkedList.list();

        // singleLinkedList.updateByNo(new HeroNode(3, "孙悟空", "齐天大圣"));
        // System.out.println("修改后~");
        // singleLinkedList.list();
        // System.out.println("当前有效节点个数为："  + singleLinkedList.size());
        //
        // singleLinkedList.delete(0);
        // System.out.println("删除后~");
        // singleLinkedList.list();
        //
        // System.out.println("当前有效节点个数为："  + singleLinkedList.size());
        // System.out.println("倒数第一个节点为：" + singleLinkedList.getNodeCountBack(1));
        // System.out.println("翻转~");
        // singleLinkedList.reverse();
        // singleLinkedList.list();
        System.out.println("逆序打印单链表~");
        singleLinkedList.printReverse();
    }


}

class SingleLinkedList {

    // 初始化头节点，不放数据，不动
    private final HeroNode head = new HeroNode(0, "", "");


    // 逆序打印单链表
    public void printReverse() {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            System.out.println(stack.pop());
        }

    }

    /**
     * 反转链表
     */
    public void reverse() {
        if (head.next == null || head.next.next == null) {
            System.out.println("无需反转");
        }
        // 辅助链表
        HeroNode reverseHead = new HeroNode(0, "", "");
        HeroNode current = head.next;
        HeroNode next; // 存放下一个节点
        while (current != null) {
            next = current.next;
            current.next = reverseHead.next; // 将新链表的头节点的next指向current的next
            reverseHead.next = current; // 将current指向头节点的next
            current = next; // current后移
        }
        head.next = reverseHead.next;
    }

    // 不考虑编号顺序时，添加
    // 找到最后一个节点将其next指向需要添加的节点
    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = heroNode;
                break;
            }
            temp = temp.next;
        }
    }

    // 添加时根据编号顺序排位插入节点，有相同编号则不插入并返回失败
    public void addByOrder(HeroNode heroNode) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > heroNode.no) {
                break;
            }
            if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.println("已有相同编号~");
        } else {
            // 为了第一次进入添加
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    // 根据no修改
    public void updateByNo(HeroNode heroNode) {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == heroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            temp.name = heroNode.name;
            temp.nickname = heroNode.nickname;
        } else {
            System.out.println("没有找到需要修改的节点");
        }
    }

    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        HeroNode temp = head;

        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                // 找到需要删除的节点前一个节点temp
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

    // 遍历
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        HeroNode temp = head.next;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //单链表有效节点个数
    public int size() {
        if (head.next == null) {
            return 0;
        }
        int count = 0;
        HeroNode temp = head.next;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // 获取倒数第k个节点
    public HeroNode getNodeCountBack(int k) {
        if (head.next == null) {
            return null;
        }
        // 获取单链表长度
        int size = size();
        if (k > size || k < 1) {
            return null;
        }
        int count = 0;
        HeroNode temp = head.next;
        for (int i = 0; i < size - k; i++) {
            temp = temp.next;
        }
        return temp;
    }
}

