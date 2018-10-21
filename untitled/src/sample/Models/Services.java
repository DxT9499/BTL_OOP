package sample.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public final class Services
{
    private static final Services instance = new Services();
    private DictionarySaver dicsaver;
    private ArrayList<Word> dic;
    private String linkdb;
    private Connection dbconn;

    private Services()
    {
        this.linkdb ="jdbc:sqlite:dictionarydata.db";
        this.dicsaver= null;
        this.dic= null;
    }
    public static Services getInstance(){return instance;}
    public ArrayList<Word> Get_Dic() {
        return this.dic;
    }
    public ArrayList<Word> search_Dic(String prefix) {
        return dicsaver.Get_Prefix(prefix);
    }
    public boolean isContain(String word) {return dicsaver.Is_Contain(word);}
    public boolean Is_Connected()
    {
        if (dbconn==null)return false;
        return  true;
    }
    public boolean Is_LoadData()
    {
        if (dic==null ) return false;
        return  true;
    }
    public void Connect_to_Db()
    {
        this.dbconn = null;
        try
        {
            this.dbconn = DriverManager.getConnection(linkdb);
            System.out.println("Connection has been set.");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void Get_Data_From_DB() {
        String sql = "SELECT id, word, description,pronounce FROM av";
        try
        {
            this.dicsaver = new DictionarySaver();
            Statement stmt = this.dbconn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next())
            {
                //System.out.println("result");
                //System.out.println(result.getString("id")+result.getString("description"));
                //System.out.println(result.getInt("id"));
                //System.out.println(result.getString("word"));
                //System.out.println(result.getString("word"));
                dicsaver.Push_Tree(new Word(result.getString("word"),result.getString("description"),
                        result.getString("pronounce"),result.getInt("id")));
               // System.out.println(dicsaver.getRoot().word);
            }
            this.dic = this.dicsaver.Get_Prefix("");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void Insert_To_DB(Word word) {
        String sql = "INSERT INTO av(word,pronounce,description) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = this.dbconn.prepareStatement(sql);
            pstmt.setString(1,word.word);
            pstmt.setString(2,word.pronounce);
            pstmt.setString(3,word.meaning);
            pstmt.executeUpdate();
            dicsaver.Push_Tree(word);
            dic.add(word);
            Collections.sort(dic, Word.wordComparator);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void Update_To_DB(int id,String word,String pronounce,String mean) {
        String sql = "UPDATE av SET  pronounce = ? , description = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = this.dbconn.prepareStatement(sql);
            pstmt.setString(1,pronounce);
            pstmt.setString(2,mean);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            this.dicsaver.Update_Word_Tree(word,pronounce,mean);
            this.dic = this.dicsaver.Get_Prefix("");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void Delete_In_DB(int id) {
        String sql = "DELETE FROM av  WHERE id = ?";
        try {
            PreparedStatement pstmt = this.dbconn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            this.Get_Data_From_DB();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
