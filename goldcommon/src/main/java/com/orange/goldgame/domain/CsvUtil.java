/**
 * 
 */
package com.orange.goldgame.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class CsvUtil {

	private BufferedReader bufferedreader = null;
	private List<String> list = new ArrayList<String>();

	public CsvUtil() {

	}

	public CsvUtil(String filename) throws IOException {
		bufferedreader = new BufferedReader(new FileReader(filename));
		String stemp;
		while ((stemp = bufferedreader.readLine()) != null) {
			list.add(stemp);
		}
	}

	public List<String> getList() throws IOException {
		return list;
	}

	// 得到csv文件的行数
	public int getRowNum() {

		return list.size();
	}

	// 得到csv文件的列数
	public int getColNum() {
		if (!list.toString().equals("[]")) {
			if (list.get(0).toString().contains("#")) { // csv文件中，每列之间的是用','来分隔的
				return list.get(0).toString().split("#").length;
			} else if (list.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 取得指定行的值

	public String getRow(int index) {

		if (this.list.size() != 0)
			return (String) list.get(index);
		else
			return null;
	}

	// 取得指定列的值
	public String getCol(int index) {

		if (this.getColNum() == 0) {
			return null;
		}

		StringBuffer scol = new StringBuffer();
		String temp = null;
		int colnum = this.getColNum();

		if (colnum > 1) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp.split(",")[index] + ",");
			}
		} else {
			for (Iterator it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp + ",");
			}
		}
		String str = new String(scol.toString());
		str = str.substring(0, str.length() - 1);
		return str;
	}

	// 取得指定行，指定列的值
	public String getString(int row, int col) {
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			temp = list.get(row).toString().split("#")[col];
		} else if (colnum == 1) {
			temp = list.get(row).toString();
		} else {
			temp = null;
		}
		return temp;
	}

	public void CsvClose() throws IOException {
		this.bufferedreader.close();
	}

}
