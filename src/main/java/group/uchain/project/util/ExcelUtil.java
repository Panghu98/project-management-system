package group.uchain.project.util;

import group.uchain.project.dto.RegisterUser;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.exception.MyException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author project
 * @title: ExcelUtil
 * @projectName project
 * @date 19-7-12 下午9:16
 */
public class ExcelUtil {


    public static List<ProjectInfo> importProjectXLS(String fileName){

        ArrayList<ProjectInfo> list = new ArrayList<>();
        try{

            //1.获取文件输入流
            InputStream inputStream = new FileInputStream(fileName);

            //2、获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            //3.创建工作栏
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            //4、循环读取表格数据
            for (Row row : sheetAt) {

                //首行（即表头）不读取
                if (row.getRowNum() == 0) {
                    continue;
                }

                String id = row.getCell(0).getStringCellValue();
                if (id == null){
                    throw new MyException("项目编号不能为空",12);
                }
                String category = row.getCell(1).getStringCellValue();
                String instruction = row.getCell(2).getStringCellValue();
                String level = row.getCell(3).getStringCellValue();
                String grade = row.getCell(4).getStringCellValue();
                Double number = row.getCell(5).getNumericCellValue();
                String variety = row.getCell(6).getStringCellValue();
                Double score = row.getCell(7).getNumericCellValue();
                String leader = row.getCell(8).getStringCellValue();
                String division = row.getCell(9).getStringCellValue();

                ProjectInfo projectInfo = new ProjectInfo();
                projectInfo.setId(id);
                projectInfo.setCategory(category);
                projectInfo.setDivision(AllocationInfoUtil.getInfo(division));
                projectInfo.setGrade(grade);
                projectInfo.setInstruction(instruction);
                projectInfo.setLeader(leader);
                projectInfo.setLevel(level);
                projectInfo.setNumber(number);
                projectInfo.setScore(score);
                projectInfo.setVariety(variety);
                projectInfo.setDate(new Date());

                list.add(projectInfo);
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_READ_ERROR);
        } catch (NullPointerException e){
            e.printStackTrace();
            throw new MyException(CodeMsg.EXCEL_EMPTY_ERROR);
        }catch (Exception e){
            throw new MyException(CodeMsg.XLS_FILE_FORMAT_ERROR);
        }

        return list;

    }


    public static List<RegisterUser> getUsersByExcel(String fileName){

        ArrayList<RegisterUser> list = new ArrayList<>();
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

                Long userId = Long.valueOf(row.getCell(0).getStringCellValue());
                String username = row.getCell(1).getStringCellValue();
                String position = row.getCell(2).getStringCellValue();
                String office = row.getCell(3).getStringCellValue();

                RegisterUser registerUser = new RegisterUser();
                registerUser.setUserId(userId);
                registerUser.setUsername(username);
                registerUser.setPosition(position);
                registerUser.setOffice(office);

                list.add(registerUser);
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_READ_ERROR);
        } catch (IllegalStateException | NumberFormatException e){
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_FORMAT_ERROR);
        }catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeMsg.XLS_FILE_FORMAT_ERROR);
        }

        return list;

    }


}
