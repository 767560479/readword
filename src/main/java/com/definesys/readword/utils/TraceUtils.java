package com.definesys.readword.utils;

import java.util.List;

public class TraceUtils {

	private static String filter( StackTraceElement stack ){
		String clazzName = stack.getClassName(); 
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		return clazzName;
	}
	
	public static void doPrint( StackTraceElement stack, 
			String title, String msg ){
		String clazzName = filter( stack );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "{跟踪器} %s.%s() {%d} %s\n", 
					clazzName, methodName, lineNum, msg );
	}
	
	public static void print( Exception e ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		doPrint( stack, "Exception", e.getMessage() );
	}

	public static void print(){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = filter( stack );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "{跟踪器} %s.%s() {%d}\n", clazzName, methodName, lineNum );
	}

	public static void print( Object obj ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String ret = (obj == null) ? "null" : obj.toString();
		doPrint( stack, "Object", ret );
	}

	public static void print( List<Object> list ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		//doPrint( stack, "Object", obj.toString() );
	}
	
	public static void print( String message ){
		//1 --> 为当前类,   2-->调用类
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		doPrint( stack, "", message );
	}
	
	public static void printf( String format, Object... args ){
		String message = String.format( format, args );
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		doPrint( stack, "", message );
	}
	
}
