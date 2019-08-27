package group.uchain.project.test;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

/**
 * @author project
 * @title: Test
 * @projectName project
 * @date 19-7-12 下午10:10
 */
public class Test {

    public static void main(String[] args) {

        InputStream is;
        Workbook book;
        try {
            is = new FileInputStream("/home/panghu/Desktop/test.xls");
            book = new HSSFWorkbook(is);

            Sheet sheet = book.getSheetAt(0);

            for(int i = 0; i <= 10; i ++) {
                Row row = sheet.getRow(i);
                int enterCnt = 0;
                int colIdxOfMaxCnt = 1;
                for(int j = 0; j <= 10; j ++) {
                    int rwsTemp = row.getCell(j).toString().split("\n").length;
                    if (rwsTemp > enterCnt) {
                        enterCnt = rwsTemp;
                        colIdxOfMaxCnt = j;
                    }
                }
                System.out.println(colIdxOfMaxCnt + "列的行数：" + enterCnt);
                row.setHeight((short)(enterCnt * 228));
            }

            File f = new File("/home/panghu/Desktop/test.xls");
            FileOutputStream out = new FileOutputStream(f);
            book.write(out);

            out.close();
            is.close();
        } catch (IOException e) {
            return;
        }
    }

}
