package structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式
 */
public class PolandNotation {

    public static void main(String[] args) {
        // 先定义一个逆波兰表达式（后缀表达式） 可以查一下这个表达式的定义
        // (3+4)x5-6 -> 3 4 + 5 x 6 -
        // 使用空格隔开
        String suffixExpression = "30 4 + 5 * 6 - ";
        // 思路
        // 1. 先将"3 4 + 5 x 6 - " =>放到ArrayList中
        // 2. 将ArrayList 传递给一个方法，遍历ArrayList，配合栈完成计算
        List<String> rpnList = getListString(suffixExpression);
        System.out.println(calculate(rpnList));
    }

    // 将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
    public static List<String> getListString(String suffixExpression) {
        assert suffixExpression.length() > 0;
        // 分割
        String[] split = suffixExpression.split(" ");
        return new ArrayList<>(Arrays.asList(split));
    }

    // 完成对逆波兰表达式的运算
    /**
     * 1) 从左到右扫描，将3和4压入堆栈；
     *    遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值7，再将结果压入栈；
     *    将5入栈；
     *    接下来是x运算符，因此弹出5和7，计算出7x5的值35，将35入栈；
     *    将6入栈；
     *    最后是-运算符，计算出35-6的值29，即为最终结果
     */
    public static int calculate(List<String> ls) {
        // 创建一个栈
        Stack<String> stack = new Stack<>();
        // 遍历ls
        for (String item : ls) {
            // 使用正则表达式取出数 匹配的是多位数
            if (item.matches("\\d+")) {
                stack.push(item);
            } else {
                // pop出两个数并运算再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                switch (item) {
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num1 / num2;
                        break;
                    default:
                        throw new RuntimeException("运算符错误");
                }
                // 把res入栈
                stack.push(String.valueOf(res));
            }
        }
        // 最后留在stack中的数据即为运算结果
        return Integer.parseInt(stack.pop());
    }
}
