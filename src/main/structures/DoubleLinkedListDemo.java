package structures;

/**
 * 双向链表demo
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        HeroNode2 heroNode = new HeroNode2(0, "一号", "宋江");
        HeroNode2 heroNode1 = new HeroNode2(1, "二号", "卢俊义");
        HeroNode2 heroNode2 = new HeroNode2(2, "三号", "吴用");
        HeroNode2 heroNode3 = new HeroNode2(3, "四号", "林冲");
        doubleLinkedList.add(heroNode);
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode2);
        doubleLinkedList.add(heroNode3);
        doubleLinkedList.list();

        System.out.println("删除一个~");
        doubleLinkedList.delete(2);
        doubleLinkedList.list();
        System.out.println("修改一个~");
        doubleLinkedList.updateByNo(new HeroNode2(0, "1111", "2222"));
        doubleLinkedList.list();
    }
}

/**
 * 双向链表
 */
class DoubleLinkedList {

    // 初始化头节点，不放数据，不动
    private final HeroNode2 head = new HeroNode2(0, "", "");

    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        HeroNode2 temp = head.next;

        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                // 找到需要删除的节点前一个节点temp
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                break;
            }
            temp = temp.next;
        }
    }

    // 根据no修改
    public void updateByNo(HeroNode2 heroNode) {
        if (head.next == null) {
            System.out.println("链表为空~");
        }
        HeroNode2 temp = head.next;
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

    // 不考虑编号顺序时，添加
    // 找到最后一个节点将其next指向需要添加的节点
    public void add(HeroNode2 heroNode) {
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = heroNode;
                heroNode.pre = temp;
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
        HeroNode2 temp = head.next;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }
}