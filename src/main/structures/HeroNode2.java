package structures;

public class HeroNode2 extends HeroNode {
    // 指向前一个节点
    public HeroNode2 pre;
    public HeroNode2 next;

    public HeroNode2() {
    }

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }
}
