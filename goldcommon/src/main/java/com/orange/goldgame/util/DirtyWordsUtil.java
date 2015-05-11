package com.orange.goldgame.util;


public class DirtyWordsUtil {

	private static MultiStringReplacer replacer = new MultiStringReplacer();
	private static final String REPLACEWORLDS = "*";
	public static final String DIRTYWORLDS = "dirty_worlds";
	public static void initDirtyWords(String dirty_word){
		if (dirty_word != null) {
			String tmp[] = dirty_word.split("\\|");
			for (String word : tmp) {
				replacer.add(word, REPLACEWORLDS);
			}
		}
	}
	
	public static boolean existsWords(String word){
		return replacer.existWords(word);
	}
	
	public static String replace(String word){
		return replacer.replace(word);
	}
	public static void main(String[] args) {
		String s = "afabcde dsafwebabccdddfl";
		System.out.println("src: " + s);
		DirtyWordsUtil.initDirtyWords("abc|cdddf");

		System.out.println("exists: " + DirtyWordsUtil.existsWords(s));
		String ss = DirtyWordsUtil.replace(s);

		System.out.println("result: " + ss);
	}
}
