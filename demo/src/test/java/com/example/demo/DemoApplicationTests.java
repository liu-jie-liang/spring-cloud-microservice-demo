package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
//
//	@Autowired
//	private StudentService studentService;
//
//	@Test
//	public void test() throws Exception {
//		Student student1 = this.studentService.queryStudentBySno("001");
//		System.out.println("学号" + student1.getSno() + "的学生姓名为：" + student1.getSname());
//
//		Student student2 = this.studentService.queryStudentBySno("001");
//		System.out.println("学号" + student2.getSno() + "的学生姓名为：" + student2.getSname());
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"one", "two", "three"})
//	@DisplayName("参数化测试1")
//	public void parameterizedTest1(String string) {
//		System.out.println(string);
//		Assertions.assertTrue(StringUtils.isNotBlank(string));
//	}
//
//	@RepeatedTest(10) //表示重复执行10次
//	@DisplayName("重复测试")
//	public void testRepeated() {
//		Assertions.assertTrue(1 == 1);
//	}
//
//	@TestFactory
//	@DisplayName("动态测试")
//	Iterator<DynamicTest> dynamicTests() {
//		return Arrays.asList(
//				DynamicTest.dynamicTest("第一个动态测试", () -> Assertions.assertTrue(true)),
//				DynamicTest.dynamicTest("第二个动态测试", () -> Assertions.assertEquals(4, 2 * 2))
//		).iterator();
//	}
//
//	@ParameterizedTest
//	@MethodSource("method")    //指定方法名
//	@DisplayName("方法来源参数")
//	public void testWithExplicitLocalMethodSource(String name) {
//		System.out.println(name);
//		Assertions.assertNotNull(name);
//	}

}
