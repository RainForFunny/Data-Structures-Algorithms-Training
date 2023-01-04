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
        // 二、完成将一个中缀表达式转成后缀表达式的功能
        // 说明
        // 1. str = 1+((2+3)x4)-5 => 转成 1 2 3 + 4 x 5 -
        // 2. 因为直接对str进行操作不方便，因此先将其转换成对应的list
        // 即"1+((2+3)x4)-5" => ArrayList [1,+,(,(,2,+,3,),x,4,),-,5]
        // 将得到的 中缀表达式 list转换为 后缀表达式 的list
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpression(expression);
        System.out.println("中缀表达式对应的list：" + infixExpressionList);
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式对应的list：" + suffixExpressionList);
        int calculate = calculate(suffixExpressionList);
        System.out.println(calculate);
        /*// 一、先定义一个逆波兰表达式（后缀表达式） 可以查一下这个表达式的定义
        // (3+4)x5-6 -> 3 4 + 5 x 6 -
        // 使用空格隔开
        String suffixExpression = "30 4 + 5 * 6 - ";
        // 思路
        // 1. 先将"3 4 + 5 x 6 - " =>放到ArrayList中
        // 2. 将ArrayList 传递给一个方法，遍历ArrayList，配合栈完成计算
        List<String> rpnList = getListString(suffixExpression);
        System.out.println(calculate(rpnList));*/

    }

    /**
     * 将得到的 中缀表达式 list转换为 后缀表达式 的list
     */
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        // 定义两个栈
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 存储中间结果的栈，说明：因为s2这个栈在整个转换过程中，没有pop操作，而且后续还需要逆序输出
        // 因此直接将s2用List代替
        ArrayList<String> s2 = new ArrayList<>();
        for (String item : ls) {
            // 如果是一个数字，加入s2
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if ("(".equals(item)) {
                // 如果是"("，则直接存入s1
                s1.push(item);
            } else if (")".equals(item)) {
                // 如果是")"，则一次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这对括号丢弃
                if (!"(".equals(s1.peek())) {
                    s2.add(s1.pop());
                }
                // 将左括号弹出
                s1.pop();
            } else {
                // 考虑运算符优先级的问题
                // 当item优先级小于等于栈顶的运算符时，将s1栈顶的运算符弹出并压入s2，再次转到(4.1)与s1中心的栈顶运算符相比较
                // 问题：我们缺少一个比较优先级高低的方法
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                // 还需要将item压入栈
                s1.push(item);
            }
        }
        // 将s1中剩余的运算符依次弹出并加入到s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * 将中缀表达式转换成对应的list
     */
    public static List<String> toInfixExpression(String s) {
        // 定义一个list，存放中缀表达式对应的内容
        ArrayList<String> ls = new ArrayList<>();
        int i = 0;// 这是一个指针，用于遍历中缀表达式字符串
        StringBuilder str;// 对多位数的拼接
        char c;// 每遍历一个非数字，我需要加入到ls
        do {
            // 如果c是一个非数字，我需要加入到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else {
                // 如果是一个数字，需要考虑多位数
                // 先将str置""
                str = new StringBuilder();
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str.append(c);
                    i++;
                }
                ls.add(str.toString());
            }
        } while (i < s.length());
        return ls;
    }

    /**
     * 将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
     */
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
                int res;
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

/**
 * 增加一个类Operation，可以返回一个运算符对应的优先级
 */
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    /**
     * 返回对应的优先级数
     */
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
}