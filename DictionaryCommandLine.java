/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btloop1;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author 123456789
 */


public class DictionaryCommandLine {
    //Hiển thị toàn bộ từ điển
    public void showAllWord(){
        int sizeArrWord = Dictionary.Arr.size();
        if (Dictionary.Arr.isEmpty()) {
            System.out.println("Empty!!!");
        } else {
            System.out.printf("%-10s%-2s%-50s%-2s%-50s\n", "STT", "|", "English", "|", "Vietnamese");
            System.out.printf("%-10s%-2s%-50s%-2s%-50s\n", 1, "|", Dictionary.Arr.get(0).getWordTarget(), " | ", Dictionary.Arr.get(0).getWordExplain());
            for (int i = 1; i < sizeArrWord; i++) {
                System.out.printf("%-10s%-2s%-50s%-2s%-50s\n", i + 1, "|", Dictionary.Arr.get(i).getWordTarget(), "|", Dictionary.Arr.get(i).getWordExplain());
            }
        }
    }
    //Hàm gọi ínertFromCommandLine và showAllWord
    public void dictionaryBasic() {
        DictionaryManagement goiham = new DictionaryManagement();
        goiham.insertFromCommandline();
        this.showAllWord();
    }
    //Hàm gọi hàm insertFromFile(), showAllWords() và dictionaryLookup() 
    public void dictionaryAdvanced() {
        DictionaryManagement goiham = new DictionaryManagement();
        goiham.insertFromFile();
        this.showAllWord();
        goiham.dictionaryLookup();
    }
    //Hàm tìm từ theo tiếng anh
    public void dictionarySearcher(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập từ cần tìm: ");
        ArrayList<Word> arrWordcpy = Dictionary.Arr;
        while (!arrWordcpy.isEmpty() && arrWordcpy.size() != 1) {
            String input = scan.nextLine();
            for (int i = 0; i < Dictionary.Arr.size(); i++) {
                int lw = Dictionary.Arr.get(i).getWordTarget().length();
                int linput = input.length();
                int t;
                if (linput > lw) {
                    t = lw;
                } else {
                    t = linput;
                }
                for (int j = 0; j < t; j++) {
                    if (((input.toLowerCase().charAt(j)) != (Dictionary.Arr.get(i).getWordTarget().toLowerCase().charAt(j))) || (linput > lw)) {
                            arrWordcpy.remove(arrWordcpy.get(i));
                                i--;
             break;
                    }
                }
            }
            for (int k = 0; k < arrWordcpy.size(); k++) {
                    System.out.println(arrWordcpy.get(k).getWordTarget());
            }
        }

    }
}
