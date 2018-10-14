/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btloop1;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author 123456789
 */


public class DictionaryManagement {
    //Hàm thêm từ mới
    public void insertFromCommandline(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập số lượng từ vựng muốn thêm:");
        int numAdd = scan.nextInt();// khai bao tra ve so luong tu ban dau
        scan.nextLine();
        for (int i = 0; i < numAdd; i++) {
            Word input = new Word();// khai bao du lieu them vao kieu word
            System.out.println("Nhập từ tiếng anh:"); input.setWordTarget(scan.nextLine());
            System.out.println("Nhập từ giải nghĩa:"); input.setWordExplain(scan.nextLine());
            Dictionary.Arr.add(input);
        }
    }
    //Hàm đọc file từ file .txt
    public void insertFromFile(){
        File fileData = new File("");
        System.out.println(fileData.getAbsolutePath());
        File file = new File(fileData.getAbsolutePath()+"\\dictionaries.txt");
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                Word word = new Word();
                String str = scan.nextLine();
                word.setWordTarget(str.substring(0, str.indexOf("\t")));
                word.setWordExplain(str.substring(str.indexOf("\t") + 1));
                System.out.println(word.getWordExplain());
                System.out.println(word.getWordTarget());
                Dictionary.Arr.add(word);
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file!!!" + e);
        }
    }
    //Hàm tra cứu từ vựng
    public void dictionaryLookup(){
        System.out.println("Nhập từ cần tra cứu: ");
        Scanner scan = new Scanner(System.in);
        String wordLookUp = scan.nextLine();
        int temp = 0;
        for (int i = 0; i < Dictionary.Arr.size(); i++) {
            if ( wordLookUp.equals(Dictionary.Arr.get(i).getWordTarget()) ){
                temp++;
                System.out.println("Nghĩa của từa vừa nhập là: " + Dictionary.Arr.get(i).getWordExplain());
            }
        }
        if (temp == 0) {
            System.out.println("Không tìm thấy từ này!!!");
        }
    }
    //Hàm thêm từ
    public void addWord(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập số lượng từ vựng muốn thêm:");
        int numAdd = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < numAdd; i++) {
            Word input = new Word();
            System.out.println("Nhập nghĩa tiếng anh:"); input.setWordTarget(scan.nextLine());
            System.out.println("Nhập nghĩa tiếng việt"); input.setWordExplain(scan.nextLine());
            Dictionary.Arr.add(input);
        }
    }
    //Hàm xóa từ
    public void deleteWord(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập số lượng từ vựng muốn xoá:");
        int numDelete = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < numDelete; i++) {
            System.out.println("Nhập từ cần xóa: ");
            String delete = scan.next();
            int t = 0;
            for (int j = 0; j < Dictionary.Arr.size(); j++) {
                if (delete.equalsIgnoreCase(Dictionary.Arr.get(j).getWordTarget())) {
                    t++;
                    Dictionary.Arr.remove(Dictionary.Arr.get(j));
                }
            }
            if (t == 0) {
                System.out.println("Không tìm thấy từ này!!!");
            }
        }
    }
    //Hàm sửa từ trong từ điển
    public void repairWord(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập số lượng từ vựng muốn sửa:");
        int numRepair = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < numRepair; i++) {
            System.out.println("Nhập từ cần sửa: ");
            String repair = scan.next();
            int temp = 0;
            for (int j = 0; j < Dictionary.Arr.size(); j++) {
                if (repair.equalsIgnoreCase(Dictionary.Arr.get(j).getWordTarget())) {
                    temp++;
                    System.out.println("Nhập nghĩa mới của từ: ");
                    scan.nextLine();
                    Dictionary.Arr.get(j).setWordExplain(scan.nextLine());
                }
            }
            if (temp == 0) {
                System.out.println("Không tìm thấy từ này!!!");
            }
        }
    }

    //Hàm input file ra định dạng text
    public void dictionaryExportToFile(){
        File file = new File("out.txt");
        try (PrintWriter print = new PrintWriter(file)) {
            for (int i = 0; i < Dictionary.Arr.size(); i++) {
                print.println(Dictionary.Arr.get(i).getWordTarget() + "\t" + Dictionary.Arr.get(i).getWordExplain());
            }
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("Lỗi ghi ra file");
        }
    }
}

