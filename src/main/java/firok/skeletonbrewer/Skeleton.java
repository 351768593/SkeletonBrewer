package firok.skeletonbrewer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>标记在需要生成单例维护器的类上</p>
 *
 * <p>marked on Class you want to make a singleton</p>
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Skeleton
{
	/**
	 * <p>保存器名称</p>
	 * <p>接收 {@code Constants.DOLLAR_PREFIX} 或目标类全限定名</p>
	 *
	 * <p>where the keeper Class is</p>
	 * <p>takes {@code Constants.DOLLAR_PREFIX} or full qualified Class name</p>
	 * */
	String at() default Constants.DOLLAR_PREFIX;

	/**
	 * <p>公有静态方法名称</p>
	 *
	 * <p>public static method name you want to use</p>
	 * */
	String via() default "instance";

	/**
	 * <p>代码模板</p>
	 *
	 * <p>code template</p>
	 * */
	Pattern in() default Pattern.DoubleCheckLocking;
}
