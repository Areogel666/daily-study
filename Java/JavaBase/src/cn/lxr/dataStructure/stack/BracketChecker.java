package cn.lxr.dataStructure.stack;

/**
 * 分隔符检测
 * 
 * @author admin
 *
 */
public class BracketChecker {

	public static void check(String str) throws Exception {
		StackX stack = new StackX(str.length());
		for (int i = 0; i < str.length(); i++) {
			char ckChar = str.charAt(i);
			switch (ckChar) {
			case '(':
			case '[':
			case '{':
				stack.push(ckChar);
				break;
			case ')':
			case ']':
			case '}':
				if (stack.isEmpty()) {
					System.out.println(ckChar + "，位置：" + i + "，未找到前置字符");
					throw new Exception(ckChar + "，位置：" + i + "，未找到前置字符");
				}
				char pop = stack.pop();
				if ((pop == '(' && ckChar != ')') || (pop == '[' && ckChar != ']') || (pop == '{' && ckChar != '}')) {
					System.out.println(ckChar + "，位置：" + i + "，与前置字符" + pop + "不匹配");
					throw new Exception(ckChar + "，位置：" + i + "，与前置字符" + pop + "不匹配");
				}
				break;
			default:
				break;
			}
		}
		if (!stack.isEmpty()) {
			System.out.println("未找到后置字符");
			throw new Exception("未找到后置字符");
		}
		System.out.println("ok");
	}
}
