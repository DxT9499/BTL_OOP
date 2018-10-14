/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btloop1;
import java.util.Scanner;

/**
 *
 * @author 123456789
 */
public class BTLOOP1 {
    public static void main(String[] args) {
            DictionaryManagement dictionary = new DictionaryManagement();
            DictionaryCommandLine testdictionary = new DictionaryCommandLine();
            dictionary.insertFromFile();
            System.out.println("********TỪ ĐIỂN ANH-VIỆT*******");
            System.out.println("1.Tra cứu từ");
            System.out.println("2.Thêm từ mới ");
            System.out.println("3.Xóa từ khỏi từ điển");
            System.out.println("4.Sửa từ vựng trong từ điển");
            System.out.println("5.Tìm kiếm");
            System.out.println("6.In từ điển ra file");
            System.out.println("7.In ra danh sách ");
            System.out.println("---------------------");
            System.out.println("Chose:");
            Scanner scan = new Scanner(System.in);
            int chose = scan.nextInt();
            switch (chose){
                case 1: {
                    dictionary.dictionaryLookup();
                    break;
                }
                case 2: {
                    dictionary.addWord();
                    break;
                }
                case 3:{
                    dictionary.deleteWord();
                    testdictionary.showAllWord();
                    break;
                }
                case 4:{
                    dictionary.repairWord();
                    break;
                }
                case 5:{
                    testdictionary.dictionarySearcher();
                    break;
                }
                case 6:{
                    dictionary.dictionaryExportToFile();
                    break;
                }
                case 7:{
                    testdictionary.showAllWord();
                    break;
                }
            }
        }

}
