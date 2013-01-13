package org.compare;

import org.zhiwu.utils.Debug;

/**
 * <B>Levenshtein</B>
 * 比较两个文本,并提出修正建议.注意:这里并没有求出所有公共子串,只是随机生成一个可能解,以节省时间.
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-1-29 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class Levenshtein {

	/**
	 * 返回根据Levenshtein算法生成的矩阵 str1或str2的长度为0返回另一个字符串的长度。
	 * 
	 * 初始化(n+1)*(m+1)的矩阵d，并让第一行和列的值从0开始增长。
	 * 
	 * 扫描两字符串（n*m级的），如果：str1[i] ==
	 * str2[j]，用temp记录它，为0。否则temp记为1。然后在矩阵d[i][j]赋于d[i-1][j]+1
	 * 、d[i][j-1]+1、d[i-1][j-1]+temp三者的最小值。
	 * 
	 * 扫描完后，返回矩阵的最后一个值即d[n][m]
	 * 
	 * 最后返回的是它们的距离。怎么根据这个距离求出相似度呢？因为它们的最大距离就是两字符串长度的最大值。对字符串不是很敏感。现我把相似度计算公式定为1-
	 * 它们的距离/字符串长度最大值。
	 * 
	 * @param s
	 *            the source array
	 * @param t
	 *            the target array
	 * 
	 * @return the graph
	 */
	public int[][] getGraph(Object[] s, Object[] t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException();
		}
		int n = s.length;// coloum
		int m = t.length;// row
		if (n == 0) {
			return null;// TODO
		} else if (m == 0) {
			return null;// TODO
		}

		int[][] r=new int[m+1][n+1];// result
		for (int j = 0; j <= n; j++) {
			r[0][j] = j;// initial the first row.
		}
		for(int i=1;i<=m;i++){
			r[i][0]=i;// initial the first coloum.
		}

		int tmp;
		Object o;
		for(int i=1;i<=m;i++){
			o=t[i-1];
			for(int j=1;j<=n;j++){
				tmp=o.equals(s[j-1])?0:1;
				r[i][j]
				     =min(r[i-1][j-1]+tmp,r[i][j-1]+1,r[i-1][j]+1);
			}
		}
		return r;
	}


	private int min(int a, int b, int c){
		int d=a<b?a:b;
		return d<c?d:c;
	}

	/**
	 * Gets the operation path.
	 * 
	 * @param graph the graph
	 * 
	 * @return the operation path
	 */
	public int[][] getOperationPath(int[][] graph){
		if (graph == null) {
			return null;
		}

		int m=graph.length;	// rows
		int n=graph[0].length; // coloums
		int[][] r=new int[m+ n][3];// r[i][0]=operation  r[i][1]=target[i] r[j]=source[j]
		int index=r.length-1; // the index of the r
		int i=m-1,j=n-1;	// curren index of the graph
		int tmp ;	// current value of the special index(i,j) of the graph
		int min;	//the next value
		int op; // the operation flag
		int p=0,q=0; // current index of the graph
		for(;i>0 && j>0 ;index--){
			p=i;
			q=j;

			tmp = graph[i][j];
			min = min(graph[i - 1][j - 1], graph[i][j - 1], graph[i - 1][j]);
			//			System.out.println(i + ", " + j);
			if (min == graph[i - 1][j - 1]) {
				op = 1; // replace
				r[index][1]=i;
				r[index][2]=j;
				i--;
				j--;
			} else if (min == graph[i][j - 1]) {
				min = graph[i][j - 1];
				op = 2; // insert
				r[index][1]=i;
				r[index][2]=j;
				j--;
			} else {
				min = graph[i - 1][j];
				r[index][1]=i;
				r[index][2]=j;
				op = 3; // remove
				i--;
			}

			if (tmp == min) {
				r[index][0] = -1; // do nothing
			} else {
				r[index][0] = op;
			}
		}
		i=p;
		j=q;
		if (index >=0) {
			if (i>1) {
				for(;index>=0 && i>0;index--,i--){
					tmp=graph[i][j];
					min=graph[i][j];
					r[index][0]=3; //remove 
					r[index][1]=i;
					r[index][2]=j;
				}
			}else if (j>1) {
				for(;index>=0 && j>0 ;index--,j--){
					r[index][0]=2; //insert
					r[index][2]=j;
					r[index][2]=j;
				}
			}
		}
		Debug.printArray(r);
		return r;

	}

	public static void main(String[] args) {
		String source="A B C A C A D F \n 0 0 9  87  6  5 4 4  3 45 66 7 8 8 8  7 66 5 5 5 ";
		String target="D B C A C A D";
		Levenshtein l=new Levenshtein();
		int[][] r= l.getGraph(source.split(" "),target.split(" "));

		for (int[] element : r) {
			for (int element2 : element) {
				System.out.printf("%3d ", element2);
			}
			System.out.println();
		}

		int[][] r2=l.getOperationPath(r);
		for (int[] element : r2) {
			System.out.printf("%d:%d,%d  ",element[0],element[1],element[2]);
		}
	}
}