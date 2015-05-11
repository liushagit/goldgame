package com.orange.goldgame.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;

public class SerializeUtil {
	
	private static Map<String, List<Method>> classMethod = new HashMap<String, List<Method>>();
	
	private static List<Method> getProperty(Object entityName,String methodName) throws Exception {
		List<Method> list = classMethod.get(methodName + entityName.getClass().toString());
		if (list == null) {
			Map<String, Class> methodMap = new HashMap<String, Class>();
			list = new ArrayList<Method>();
			Class c = entityName.getClass();
			Field field[] = c.getDeclaredFields();
			List<String> nameList = new ArrayList<String>();
			for (Field f : field) {
				if ( f.getAnnotation(NoSerialize.class) == null ){
					nameList.add(f.getName());
					methodMap.put(f.getName(), f.getType());
				}
			}
			Collections.sort(nameList);
			String name = "";
			Method method = null;
			for (String f : nameList) {
				name = f;//f.substring(0, 1).toUpperCase() + f.substring(1);
				if ("set".equals(methodName)) {
					method = c.getMethod(methodName + name,methodMap.get(f));
				}else {
					method = c.getMethod(methodName + name);
				}
				if (method != null) {
					list.add(method);
				}
			}
			classMethod.put(methodName + entityName.getClass().toString(), list);
		}
		return list;
	}

	public static OutputMessage getOutputMessage(Object object) throws Exception{
		OutputMessage om = new OutputMessage();
			List<Method> list = getProperty(object,"get");
			Object o = null;
			for (Method method : list) {
				o = method.invoke(object);
				if (o instanceof Integer) {
					om.putInt((Integer)o);
				}
				if (o instanceof String) {
					om.putString(o.toString());
				}
				if (o instanceof Byte) {
					om.putByte((Byte)o);
				}
				if (o instanceof Boolean) {
					om.putBoolean((Boolean)o);
				}
				if (o instanceof Short) {
					om.putShort((Short)o);
				}
				if (o instanceof List) {
					List l = (List) o;
					int size = l.size();
					om.putShort((short)size);
					OutputMessage oMessage = null;
					for (Object oo : l) {
						oMessage = getOutputMessage(oo);
						om.putByte(oMessage.getBytes());
					}
				}
			}
		return om;
	}
	
	
//	public static String getMessage(Object object) throws Exception{
//		StringBuffer sb = new StringBuffer();
//			List<Method> list = getProperty(object,"get");
//			Object o = null;
//			for (Method method : list) {
//				o = method.invoke(object);
//				if (o instanceof Integer) {
//					sb.append("_").append((Integer)o);
//				}
//				if (o instanceof String) {
//					sb.append("_").append(o.toString());
//				}
//				if (o instanceof Byte) {
//					sb.append("_").append((Byte)o);
//				}
//				if (o instanceof Boolean) {
//				}
//				if (o instanceof Short) {
//					sb.append("_").append((Short)o);
//				}
//				if (o instanceof List) {
//					List l = (List) o;
//					int size = l.size();
//					sb.append("_").append(size);
//					String sb1 = "";
////					OutputMessage oMessage = null;
//					for (Object oo : l) {
//						sb1 = getMessage(oo);
//						sb.append(sb1);
//					}
//				}
//			}
//		sb.deleteCharAt(0);
//		return sb.toString();
//	}
	
	public static void setVo(InputMessage im,Object object)throws Exception{
		List<Method> list = getProperty(object,"set");
		Class o[] = null;
		for (Method method : list) {
			o = method.getParameterTypes();
			if (o == null || o.length <= 0) {
				continue;
			}
			if (o[0].getSimpleName().equals("int") || o[0].getSimpleName().equals("Integer")) {
				method.invoke(object, im.getInt());
			}
			if (o[0].getSimpleName().equals("String")) {
				method.invoke(object, im.getUTF());
			}
			if (o[0].getSimpleName().equals("boolean") || o[0].getSimpleName().equals("Boolean")) {
				
			}
			if (o[0].getSimpleName().equals("short") || o[0].getSimpleName().equals("Short")) {
				method.invoke(object, im.getShort());
			}
			if (o[0].getSimpleName().equals("byte") || o[0].getSimpleName().equals("Byte")) {
				method.invoke(object, im.getByte());
			}
			if (o[0].getSimpleName().equals("List")) {
				short size = im.getShort();
				List<Object> l = new ArrayList<Object>();
				for (int i = 0; i < size; i++) {
					Object obj = newObject(method, object);
					setVo(im, obj);
					l.add(obj);
				}
				method.invoke(object, l);
			}
		}
	}
	
	private static Object newObject(Method method,Object object) throws Exception{
		String name = method.getName();
		name = name.substring(3, name.length());//去掉set
//		name = name.substring(0, 1).toLowerCase() + name.substring(1);//将第一个字母小写
		Field fd = object.getClass().getDeclaredField(name);
		Type o1 = ((ParameterizedType)fd.getGenericType()).getActualTypeArguments()[0];
		String className  = o1.toString();
		className = className.substring(6,className.length());//去掉class
		Class cls = Class.forName(className); 
		Constructor ct = cls.getConstructor(null);
        Object obj = ct.newInstance(null); 
        return obj;
	}
}