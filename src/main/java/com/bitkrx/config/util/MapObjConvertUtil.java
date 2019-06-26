/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.bitkrx.api.auth.vo.CmeLoginVO;


/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: security
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 8.
 */
public class MapObjConvertUtil {

	private MapObjConvertUtil() {};
	
	private static MapObjConvertUtil cUtil = null;
    
    public synchronized static MapObjConvertUtil getinstance(){
        if(cUtil == null ){
        	cUtil = new MapObjConvertUtil();
        }
        return cUtil;
    }
    
	public static Map<String, Object> convertObjectToMap(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		Map<String, Object> map = new HashMap<>();

	    /*Field[] fields = obj.getClass().getDeclaredFields();

	    for(Field field : fields){
	        String filedName = field.getName();
	        String methodName = filedName.replace(filedName.substring(0, 1), filedName.substring(0, 1).toUpperCase());
	        map.put(filedName, obj.getClass().getDeclaredMethod("get" + methodName).invoke(obj));
	    }*/

		Method[] methods = obj.getClass().getDeclaredMethods();
		for(Method method : methods) {
			if(method.getName().indexOf("get") > -1) {
				String field = method.getName().substring(3);
				field = field.substring(0, 1).toLowerCase() + field.substring(1);
				map.put(field, method.invoke(obj));
			}
		}
		
	    return map;
	}
	
	
	public static Object convertMapToObject(Map map, Object objClass){ 
		String keyAttribute = null; 
		String setMethodString = "set"; 
		String methodString = null; 
		Iterator itr = map.keySet().iterator(); 
		while(itr.hasNext()){ 
			keyAttribute = (String) itr.next(); 
			methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1); 
			try { 
				Method[] methods = objClass.getClass().getDeclaredMethods(); 
				for(int i=0;i<=methods.length-1;i++){ 
					if(methodString.equals(methods[i].getName())){ 
						//System.out.println("invoke : "+methodString); 
						methods[i].invoke(objClass, map.get(keyAttribute)); 
					} 
				} 
			} catch (SecurityException e) { 
				e.printStackTrace(); 
			} catch (IllegalAccessException e) { 
				e.printStackTrace(); 
			} catch (IllegalArgumentException e) { 
				e.printStackTrace(); 
			} catch (InvocationTargetException e) { 
				e.printStackTrace(); 
			} 
		} 
		return objClass; 
	}

	
}

