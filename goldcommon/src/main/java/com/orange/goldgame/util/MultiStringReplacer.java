package com.orange.goldgame.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于替换多个字符串的类
 */
public final class MultiStringReplacer {
	// 子节点列表，Map的key是子节点对应的字符
	private Map<Character, MultiStringReplacer> childs = new HashMap<Character, MultiStringReplacer>();

	// 要替换的旧字符串
	private String oldWord;

	// 新字符串
	private String newWord;

	private static class FindResultPosInfo {
		MultiStringReplacer msr; // 关键字对应的节点
		int idx; // 关键字出现的位置

		FindResultPosInfo(MultiStringReplacer msr, int idx) {
			this.msr = msr;
			this.idx = idx;
		}
	}

	/**
	 * 添加一对替换关键字
	 * 
	 * @param oldWords
	 *            要替换的字符串
	 * @param newWords
	 *            新字符串
	 */
	public void add(String oldWords, String newWords) {
		add(oldWords, 0, newWords);
	}

	private void add(String oldWords, int idx, String newWords) {
		if (oldWords.length() == idx) {
			// 达了树的叶子节点，此时对该节点设置字符串对
			this.newWord = newWords;
			this.oldWord = oldWords;
			return;
		}

		// 建立一个字节点
		char next_ch = oldWords.charAt(idx);
		MultiStringReplacer ti = childs.get(next_ch);
		if (ti == null) {
			ti = new MultiStringReplacer();
			childs.put(next_ch, ti);
		}
		// 递归建立下一个字节点
		ti.add(oldWords, idx + 1, newWords);
	}

	/**
	 * 从字符串指定位置开始比较当前位置是否有关键字匹配
	 */
	private FindResultPosInfo compareWords(String s, int idx) {
		// 找到匹配节点，并且该节点没有子节点（满足此条件是为了最长匹配）时，直接返回当前位置
		if (this.oldWord != null && this.childs.size() == 0) {
			return new FindResultPosInfo(this, idx - this.oldWord.length());
		}

		// 如果字符串结束，则返回null
		if (idx >= s.length())
			return null;

		// 查找匹配的字节点
		char c = s.charAt(idx);
		MultiStringReplacer ti = childs.get(c);
		if (ti == null)
			return null;

		// 递归匹配其余的字节点
		FindResultPosInfo fi = ti.compareWords(s, idx + 1);

		// 如果其余的字节点不匹配，则检查当前节点是否需要替换的节点
		if (fi == null && ti.oldWord != null)
			return new FindResultPosInfo(ti, idx + 1 - ti.oldWord.length());

		return fi;
	}

	private FindResultPosInfo findWords(String s, int idx) {
		// 逐个位置比较是否匹配替换关键字
		for (int i = idx; i < s.length(); ++i) {
			FindResultPosInfo wi = compareWords(s, i);
			if (wi != null)
				return wi;
		}
		return null;
	}

	/**
	 * 查找第一个出现的关键字
	 * 
	 * @param s
	 *            要查找的字符串
	 * @param idx
	 *            起始位置
	 * @return Pair(找到的关键字，关键字在字符串中的位置)，如果没找到，返回null
	 */
	public Pair<String, Integer> findFirstWord(String s, int idx) {
		FindResultPosInfo fi = findWords(s, idx);
		if (fi == null)
			return null;
		return Pair.makePair(fi.msr.oldWord, fi.idx);
	}

	/**
	 * 查找第一个出现的关键字
	 * 
	 * @param s
	 *            要查找的字符串
	 * @return Pair(找到的关键字，关键字在字符串中的位置)
	 */
	public Pair<String, Integer> findFirstWord(String s) {
		return findFirstWord(s, 0);
	}

	/**
	 * 检查字符串中是否存在需要替换的关键字
	 * 
	 * @param s
	 *            要检查的字符串
	 * @return
	 */
	public boolean existWords(String s) {
		return findWords(s, 0) != null;
	}

	/**
	 * 计算字符串中出现的关键字次数
	 * 
	 * @param s
	 *            要检查的字符串
	 * @return 关键字出现的次数
	 */
	public int countKeywords(String s) {
		FindResultPosInfo wi = findWords(s, 0); // 查找第一个替换的位置
		if (wi == null)
			return 0;
		int writen = 0;
		int count = 0;
		for (; wi != null && wi.idx < s.length();) {
			++count;
			writen = wi.idx + wi.msr.oldWord.length();
			wi = findWords(s, writen); // 查找下一个替换位置
		}
		return count;
	}

	/**
	 * 替换字符串中的旧字符串
	 * 
	 * @param s
	 *            要替换的字符串
	 * @return
	 */
	public String replace(String s) {
		FindResultPosInfo wi = findWords(s, 0); // 查找第一个替换的位置
		if (wi == null)
			return s;
		StringBuilder sb = new StringBuilder(s.length());
		int writen = 0;
		for (; wi != null && wi.idx < s.length();) {
			sb.append(s, writen, wi.idx); // append 原字符串不需替换部分
			sb.append(wi.msr.newWord); // append 新字符串
			writen = wi.idx + wi.msr.oldWord.length(); // 忽略原字符串需要替换部分
			wi = findWords(s, writen); // 查找下一个替换位置
		}
		sb.append(s, writen, s.length()); // 替换剩下的原字符串
		return sb.toString();
	}

	public static void main(String[] argv) {
		String s = "afabcde dsafwebabccdddfl";
		System.out.println("src: " + s);

		MultiStringReplacer ti = new MultiStringReplacer();
		ti.add("abc", "ABC");
		ti.add("adc", "ADC");
		ti.add("abcde", "---");
		ti.add("cdddf", "CDDDF");
		ti.add("cdddf", "CDDDF");
		ti.add("cdddf", "CDDDF");

		System.out.println("count: " + ti.countKeywords(s));
		String ss = ti.replace(s);

		System.out.println("result: " + ss);
	}
}
