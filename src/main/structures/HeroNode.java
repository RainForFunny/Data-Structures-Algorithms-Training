package structures;

public class HeroNode {
    // 节点属性值
    public int no;
    public String name;
    public String nickname;
    // 指向下一个节点
    public HeroNode next;

    public HeroNode() {
    }

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
