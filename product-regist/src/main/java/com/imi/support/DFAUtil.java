package com.imi.support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * 敏感词过滤工具类
 * 
 * @version 1.0
 * 
 *
 */
public class DFAUtil {
	private static final String ENCODING = "UTF-8";
	private static final String DFAFILE = "SensitiveWord.txt";
	private static final Logger logger = Logger
			.getLogger(DFAUtil.class);
	final static DFANode rootNode = new DFANode('R');

	public static List<String> searchWord(String content) throws Exception {
		if (rootNode.nodes.isEmpty()) {
			logger.info("初始化数据");
			createTree();

		}

		List<String> word = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		int a = 0;
		char[] chars = content.toCharArray();
		DFANode DFANode = rootNode;
		while (a < chars.length) {
			DFANode = findNode(DFANode, chars[a]);
			if (DFANode == null) {
				DFANode = rootNode;
				a = a - word.size();
				word.clear();
			} else if (DFANode.flag == 1) {
				word.add(String.valueOf(chars[a]));
				StringBuffer sb = new StringBuffer();
				for (String str : word) {
					sb.append(str);
				}
				words.add(sb.toString());
				// a = a - word.size() + 1;
				a = a - word.size() + 1;
				word.clear();
				DFANode = rootNode;
			} else {
				word.add(String.valueOf(chars[a]));
			}// end if
			a++;
		}// end while

		return words;
	}

	public static void createTree(String[] arr, DFANode rootNode) {
		for (String str : arr) {
			char[] chars = str.toCharArray();
			if (chars.length > 0)
				insertNode(rootNode, chars, 0);
		}
	}

	/**
	 * 读取敏感词库中的内容，将内容添加到set集合中
	 * 
	 * @return
	 * @version 1.0
	 * @throws Exception
	 */

	private static void createTree() throws Exception {

		ClassPathResource resource = new ClassPathResource(DFAFILE);
		logger.info("根目录路径:"+resource.getPath());
		if (!resource.exists())
			throw new RuntimeException("敏感库配置文件不存在！");
		InputStreamReader read = new InputStreamReader(
				resource.getInputStream(), ENCODING);

		try {

			BufferedReader bufferedReader = new BufferedReader(read);
			String txt = null;
			while ((txt = bufferedReader.readLine()) != null) { // 读取文件，将文件内容放入到set中
				char[] chars = txt.toCharArray();
				if (chars.length > 0)
					insertNode(rootNode, chars, 0);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			read.close(); // 关闭文件流
		}

	}

	public static void main(String[] args) throws Exception {

		List<String> censerWords = DFAUtil.searchWord("傻逼 周杰 2b 王丹丹  胡锦涛 流氓  日本鬼子 草榴 草泥马  国军   哈哈");

		if (censerWords != null && censerWords.size() > 0) {
			System.out.println("发现关键字");
			for (String b : censerWords) {
				System.out.println(b);
			}

		
		}
		
		List<String> censerWords2= DFAUtil.searchWord("Lesbian");

		if (censerWords2 != null && censerWords2.size() > 0) {
			System.out.println("发现关键字");
			for (String b : censerWords2) {
				System.out.println(b);
			}
		}

	}

	private static void insertNode(DFANode DFANode, char[] cs, int index) {
		DFANode n = findNode(DFANode, cs[index]);
		if (n == null) {
			n = new DFANode(cs[index]);
			DFANode.nodes.add(n);
		}

		if (index == (cs.length - 1))
			n.flag = 1;

		index++;
		if (index < cs.length)
			insertNode(n, cs, index);
	}

	private static DFANode findNode(DFANode DFANode, char c) {
		List<DFANode> nodes = DFANode.nodes;
		DFANode rn = null;
		for (DFANode n : nodes) {
			if (n.c == c) {
				rn = n;
				break;
			}
		}
		return rn;
	}
}