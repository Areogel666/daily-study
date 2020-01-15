package cn.lxr.dataStructure.test;

import org.junit.Test;

import cn.lxr.dataStructure.stack.BracketChecker;

public class StackTest {

//	@Test
	@Test(expected = Exception.class)
	public void testCheckBracket() throws Exception {
		BracketChecker.check("[[({})]]");
		BracketChecker.check("[sfd]ga{df13]");
	}
}
