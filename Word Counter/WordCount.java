import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;

class Word
{
	String word;
	int cnt;
	Word(String word)
	{
		this.word = word;
		cnt = 1;
	}
	void UpCount()
	{
		cnt++;
	}
}

public class WordCount {
	public static void main(String args[]) {
		Word[] wordList = new Word[1200];
		for(int i = 0; i < wordList.length; i++) {
			wordList[i] = new Word(" ");
		}
		int wordCnt = 0;
		FileReader reader = null;
		String article = "";
		try {
			reader = new FileReader("article2.txt");
			while (true) {
				int data = reader.read();
				if (data == -1)
					break;
				char ch = (char) data;
				String a = Character.toString(ch);
				article = article.concat(a);
			}
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("파일이 존재하지 않습니다.");
		}
		catch (IOException ioe) {
			System.out.println("파일을 읽을 수 없습니다.");
		}
		finally {
			try {
				reader.close();
			}
			catch (Exception e) {
				
			}
		}
		
		StringTokenizer stok = new StringTokenizer(article, " .,()?''", false);
		while (stok.hasMoreTokens()) {
			String str = stok.nextToken();
			if(str.equals("A") || str.equals("a") || str.equals("the") || str.equals("to") || str.equals("of") || str.equals("for") || str.equals("in") || str.equals("on") || str.equals("The") || str.equals("with") || str.equals("-") || str.equals("at") || str.equals("by") || str.equals("as"))
				continue;
			int index = findWord(wordList, wordCnt, str);
			if(index == -1)
				wordList[wordCnt++] = new Word(str);
			else
				wordList[index].UpCount();
		}
		
		for(int i = 0; i < wordList.length; i++) {
			if(wordList[i].word.equals(" "))
				break;
			else
				System.out.println(wordList[i].word + " " + wordList[i].cnt);
		}
		
		String header[] = {"단어", "횟수"};
		String contents[][] = new String[wordList.length][2];
		String a;
		for (int i = 0; i < wordList.length; i++) {
			contents[i][0] = wordList[i].word;
			if(wordList[i].word.equals(""))
				contents[i][1] = "";
			else
				contents[i][1] = Integer.toString(wordList[i].cnt);
		}
		for (int i = 0; i < wordList.length - 1; i++) {
			for (int j = i + 1; j < wordList.length; j++) {
				if (Integer.parseInt(contents[i][1]) < Integer.parseInt(contents[j][1])) {
					for(int k = 0; k < 2; k++) {
						a = contents[i][k];
						contents[i][k] = contents[j][k];
						contents[j][k] = a;
					}
				}
			}
		}
		JFrame frame = new JFrame("Wordcount");
		frame.setPreferredSize(new Dimension(500, 700));
		Container contentPane = frame.getContentPane();
		JTable table = new JTable(contents, header);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }
	static int findWord(Word[] wordList, int wordCnt, String stok) {
		for(int i = 0; i < wordList.length; i++) {
			if (wordList[i].word.equals(stok))
				return i;
		}
		return -1;
	}
}




