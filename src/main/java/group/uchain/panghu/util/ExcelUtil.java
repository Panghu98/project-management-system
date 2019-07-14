package group.uchain.panghu.util;

import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.exception.MyException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author panghu
 * @title: ExcelUtil
 * @projectName panghu
 * @date 19-7-12 下午9:16
 */
public class ExcelUtil {


    public static List<ProjectInfo> importXLS(String fileName){

        ArrayList<ProjectInfo> list = new ArrayList<>();
        try{

            //1.获取文件输入流
            InputStream inputStream = new FileInputStream(fileName);

            //2、获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheetAt = workbook.getSheetAt(0);
            //4、循环读取表格数据

            for (Row row : sheetAt) {
                //首行（即表头）不读取
                if (row.getRowNum() == 0) {
                    continue;
                }

                //读取当前行中单元格数据，索引从0开始 不用获取序号 所以从1开始
                String category = row.getCell(1).getStringCellValue();
                String instruction = row.getCell(2).getStringCellValue();
                String level = row.getCell(3).getStringCellValue();
                String grade = row.getCell(4).getStringCellValue();
                String number = row.getCell(5).getStringCellValue();
                String variety = row.getCell(6).getStringCellValue();
                Double score = row.getCell(7).getNumericCellValue();
                String leader = row.getCell(8).getStringCellValue();
                String division = row.getCell(9).getStringCellValue();

                ProjectInfo projectInfo = new ProjectInfo();
                projectInfo.setCategory(category);
                projectInfo.setDivision(division);
                projectInfo.setGrade(grade);
                projectInfo.setInstruction(instruction);
                projectInfo.setLeader(leader);
                projectInfo.setLevel(level);
                projectInfo.setNumber(number);
                projectInfo.setScore(score);
                projectInfo.setVariety(variety);

                list.add(projectInfo);
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_READ_ERROR);
        } catch (IllegalStateException e){
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_FORMAT_ERROR);
        }

        return list;

    }


}
