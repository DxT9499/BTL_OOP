package sample.Models;

import java.util.ArrayList;
import java.util.Collections;

public class DictionarySaver
{
    public class Node
    {
        public Node[] list;
        public Word word;
        public Node()
        {
            list = new Node[26];
            word = null;
        }

        public void setWord(Word word)
        {
            this.word = word;
        }
    }

    // thuôc tính
    private Node root;
    // method
    public Node getRoot()
    {
        return root;
    }
    public DictionarySaver()
    {
        this.root= new Node();
        root.setWord( new Word("deafaul","defaul"));
    }
    public DictionarySaver(String Name)
    {
        this.root = new Node();
        root.setWord(new Word(Name,"defaul"));
    }
    public void Push_Tree(Word word)
    {

        Node stand = this.root;
        String temp = word.word.toLowerCase();
        StringBuilder wordName = new StringBuilder();
        int n = temp.length();
        for (int i=0;i<n;i++) {
            char c = temp.charAt(i);
            if (c<='z' && c>='a') wordName.append(c);
        }
        n = wordName.length();
        for (int i=0; i<n;i++)
        {
            if (i==(n-1)) {
                if (stand.list[wordName.charAt(i)-'a'] == null)
                {
                    stand.list[wordName.charAt(i)-'a'] = new Node();
                    stand.list[wordName.charAt(i)-'a'].word = word;
                } else {
                    stand.list[wordName.charAt(i)-'a'].word = word;
                }
            } else {
                if (stand.list[wordName.charAt(i)-'a']!= null)
                    stand = stand.list[wordName.charAt(i)-'a'];
                else {
                    stand.list[wordName.charAt(i)-'a'] = new Node();
                    stand = stand.list[wordName.charAt(i)-'a'];
                }
            }
        }
        //System.out.println(word.word+"---- "+word.pronounce);



    }
    public void Update_Word_Tree(String word, String mean, String pronounce)
    {
        Node stand = this.root;
        String tmp=word.toLowerCase();
        StringBuilder name = new StringBuilder();
        int n= tmp.length();
        for(int i=0;i<n;i++)
            if(tmp.charAt(i)>='a'&&tmp.charAt(i)<='z')
                name = name.append(tmp.charAt(i));
        n= name.length();
        for (int i=0;i<n;i++)
        {
            stand = stand.list[name.charAt(i)-'a'];
        }
        stand.word.pronounce = pronounce;
        stand.word.meaning = mean;
    }
    public void deep_Search(Node stand, ArrayList<Word> ans)
    {
        /*if (stand.word==null){ ans.add(stand.word);}
        for (int i=0;i<26;i++)
            if (stand.list[i]!=null) deep_Search(stand.list[i],ans);*/
        if (stand.word!=null) {
            ans.add(stand.word);
        }
        for (int i=0;i<26;i++) {
            if (stand.list[i] != null) {
                deep_Search(stand.list[i],ans);
            }
        }
    }
    public ArrayList<Word> Get_Prefix(String prefix)
    {ArrayList<Word> ans = new ArrayList();
        if ( prefix.equals(""))
        {
            for(int i=0;i<26;i++)
                if (root.list[i]!=null) deep_Search(root.list[i],ans);
            /*for (int i=0;i<10;i++)
             *//*{
                S
        ystem.out.println("-----------------"+ans.get(i).word);
            }*/

               return ans;
        }
        //else

        Node stand= this.root;
        prefix = prefix.toLowerCase();
        StringBuilder prefix1 = new StringBuilder();
        int n= prefix.length();
        for (int i=0;i<n;i++)
            if (prefix.charAt(i)>='a'&&prefix.charAt(i)<='z') prefix1=prefix1.append(prefix.charAt(i));
        n = prefix1.length();
        for (int i=0;i<n; i++)
        {
           if (stand.list[prefix1.charAt(i)-'a']==null) return ans;
           else stand = stand.list[prefix1.charAt(i)-'a'];
        }
        deep_Search(stand,ans);
        Collections.sort(ans, Word.wordComparator);// sap xep trong java
        return ans;
    }
    public boolean Is_Contain(String word)
    {
        word = word.toLowerCase();
        StringBuilder name = new StringBuilder();
        int n = word.length();
        for (int i=0;i<n;i++)
        {
            char c = word.charAt(i);
            if (c<='z' && c>='a') name.append(c);
        }
        Node stand = this.root;
        n = name.length();
        for (int i=0;i<n;i++) {
            if (i==n-1) {
                if (stand.list[name.charAt(i)-'a']==null) return false;
                else if (stand.list[name.charAt(i)-'a'].word == null) return false;
                else return true;
            } else {
                if (stand.list[name.charAt(i)-'a'] == null) return false;
                else stand = stand.list[name.charAt(i)-'a'];
            }
        }
        return false;
    }
}
