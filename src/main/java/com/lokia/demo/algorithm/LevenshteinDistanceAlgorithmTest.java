package com.lokia.demo.algorithm;

/**
 * refer: http://blog.csdn.net/zzq900503/article/details/36901149
 * 
 * @author gushu
 * @date 2018/02/08
 */
public class LevenshteinDistanceAlgorithmTest {

	private static int min(int one, int two, int three) {  
        int min = one;  
        if (two < min) {  
            min = two;  
        }  
        if (three < min) {  
            min = three;  
        }  
        return min;  
    }  
  
	/**
	 * 
	 * 定义一个矩阵d[n][m]，维度是 n,m. 其中 n为str1的长度; m为str2的长度
	 * <li> d[i][0](i=0..n): 值为 从0到str1的长度的值 
	 * <li> d[0][j](j=0..m): 值为 从0到str2的长度的值  
	 * <p>
	 * 
	 * <br>
	 * 其他单元的值的计算方式为： 取以下三个元素中的最小值
	 * <li>当前单元左边的元素+1
	 * <li>当前单元上边的元素+1
	 * <li>左上角元素+cost
	 * </p>
	 * 
	 * <br>
	 * <p>
	 * 公式为：
	 * 	<code> d[i][j] = min(d[i-1][j]+1,d[i][j-1]+1,d[i-1][j-1]+cost)</code>
	 * <br>
	 * cost的计算方式：str1的第i个元素 变换为 str2的第j个元素所需要的代价. 不用变的化，cost=0；需要改变(增加一个字符/减少一个字符/改变一个字符)，cost=1;
	 * </p>
	 * <br>
	 * 
	 * 最后的d[n][m]为字符串str1跟str2的距离
	 * 
	 * @param str1
	 * @param str2
	 * @return 最后的d[n][m]为字符串str1跟str2的距离
	 */
    public static int levenshteinDistance(String str1, String str2) {  
        int d[][]; // 矩阵  
        int n = str1.length();  
        int m = str2.length();  
        int i; // 遍历str1的  
        int j; // 遍历str2的  
        char ch1; // str1的  
        char ch2; // str2的  
        int cost; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1  
        if (n == 0) {  
            return m;  
        }  
        if (m == 0) {  
            return n;  
        }  
        d = new int[n + 1][m + 1];  
        for (i = 0; i <= n; i++) { // 初始化第一列  
            d[i][0] = i;  
        }  
        for (j = 0; j <= m; j++) { // 初始化第一行  
            d[0][j] = j;  
        }  
        for (i = 1; i <= n; i++) { // 遍历str1  
            ch1 = str1.charAt(i - 1);  
            // 去匹配str2  
            for (j = 1; j <= m; j++) {  
                ch2 = str2.charAt(j - 1);  
                if (ch1 == ch2) {  
                    cost = 0;  
                } else {  
                    cost = 1;  
                }  
                // 左边+1,上边+1, 左上角+temp取最小  
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ cost);  
            }  
        }  
        return d[n][m];  
    }  
    public static double similarity(String str1, String str2) {  
        try {  
            double ld = levenshteinDistance(str1, str2);  
            return (1-ld/Math.max(str1.length(), str2.length()));  
        } catch (Exception e) {  
            return 0.1;  
        }  
    }  
  
    public static void main(String[] args) {  
        String str1 = "测试1231";  
        String str2 = "测试123";  
        System.out.println("ld=" + levenshteinDistance(str1, str2));  
        System.out.println("sim=" + similarity(str1, str2));  
    }  

}
