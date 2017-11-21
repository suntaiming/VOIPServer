package com.xd.util;

import java.util.List;

public class MySelfUtil {
	
	
	

	
	/**
	 * 将List集合装换为字符串，根据传入的分隔符进行拼接：如：abc,efg,hig
	 * 如果list为null，则返回null;
	 * 如果list为空元素，则返回空字符串；
	 * @param list       list集合
	 * @param separator  元素之间的分隔符（如,）
	 * @return
	 */
	public static final String list2String(List<String> list, String separator){
		if(list == null){
			return null;
		}
		if(list.isEmpty()){
			return "";
		}
		
		StringBuffer strBuf = new StringBuffer();
		for(String str : list){
			if(strBuf.length() == 0){
				strBuf.append(str);
				continue;
			}
			strBuf.append(separator)
			    .append(str);
		}
		
		return strBuf.toString();
	}

}
