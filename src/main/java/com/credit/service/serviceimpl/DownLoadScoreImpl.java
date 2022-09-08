package com.credit.service.serviceimpl;

import com.credit.pojo.Applications;
import com.credit.pojo.User;
import com.credit.repository.ApplicationsRepository;
import com.credit.repository.InquiryRepository;
import com.credit.repository.UserRepository;
import com.credit.service.DownloadScore;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class DownLoadScoreImpl implements DownloadScore {
    @Resource
    private InquiryRepository inquiryRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ApplicationsRepository applicationsRepository;


    @Override
    public void excelDownloadScore() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpServletRequest request = requestAttributes.getRequest();

        // 文件名
        String filename = "Applications.xls";

        try {

            // 写到服务器上
            String path = request.getSession().getServletContext().getRealPath("") + "/" + filename;

            // 写到服务器上（这种测试过，在本地能够，放到linux服务器就不行）
            //String path =  this.getClass().getClassLoader().getResource("").getPath()+"/"+filename;

            File name = new File(path);
            // 建立写工做簿对象
            WritableWorkbook workbook = Workbook.createWorkbook(name);
            // 工做表
            WritableSheet sheet = workbook.createSheet("河南科技学院大学生创新创业实践学分申请汇总", 0);
            // 设置字体;
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat(font);
            // 设置背景颜色;
            cellFormat.setBackground(Colour.WHITE);
            // 设置边框;
            cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
            // 设置文字居中对齐方式;
            cellFormat.setAlignment(Alignment.CENTRE);
            // 设置垂直居中;
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            // 分别给1,5,6列设置不一样的宽度;
            sheet.setColumnView(0, 15);
            sheet.setColumnView(4, 60);
            sheet.setColumnView(5, 35);
            // 给sheet电子版中全部的列设置默认的列的宽度;
            sheet.getSettings().setDefaultColumnWidth(20);
            // 给sheet电子版中全部的行设置默认的高度，高度的单位是1/20个像素点,但设置这个貌似就不能自动换行了
            // sheet.getSettings().setDefaultRowHeight(30 * 20);
            // 设置自动换行;
            cellFormat.setWrap(true);

            // 单元格
            Label label0 = new Label(0, 0, "序号", cellFormat);
            Label label1 = new Label(1, 0, "姓名", cellFormat);
            Label label2 = new Label(2, 0, "学号", cellFormat);
            Label label3 = new Label(3, 0, "年级", cellFormat);
            Label label4 = new Label(4, 0, "所在专业", cellFormat);
            Label label5 = new Label(5, 0, "所在班级", cellFormat);
            Label label6 = new Label(6, 0, "科技创新学分", cellFormat);
            Label label7 = new Label(7, 0, "学院竞赛学分", cellFormat);
            Label label8 = new Label(8, 0, "创业实践学分", cellFormat);
            Label label9 = new Label(9, 0, "社会实践学分", cellFormat);
            Label label10 = new Label(10, 0, "职业技能学分", cellFormat);
            Label label11 = new Label(11, 0, "申请学分合计", cellFormat);
            Label label12 = new Label(12, 0, "学院审核结果", cellFormat);
            Label label13 = new Label(13, 0, "教务处认定结果", cellFormat);
            Label label14 = new Label(14, 0, "备注", cellFormat);

            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);
            sheet.addCell(label5);
            sheet.addCell(label6);
            sheet.addCell(label7);
            sheet.addCell(label8);
            sheet.addCell(label9);
            sheet.addCell(label10);
            sheet.addCell(label11);
            sheet.addCell(label12);
            sheet.addCell(label13);
            sheet.addCell(label14);

            // 给第二行设置背景、字体颜色、对齐方式等等;
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            // 设置文字居中对齐方式;
            cellFormat2.setAlignment(Alignment.CENTRE);
            // 设置垂直居中;
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

/***************************************************************************/
            // 记录行数
            int n = 1;

            //表序号
            int first=1;

            // 查找全部用户
            List<Applications>appList= applicationsRepository.findAllAppTwo();
            System.out.println("11111111"+appList);
            Set<Applications>appSet=new HashSet<>();
            for(int i=0;i<appList.size();i++){
                appSet.add(appList.get(i));
            }
            System.out.println("222222"+appSet);
            List<User> userList=new LinkedList<>();
            for(Applications b:appSet){
                userList.add(userRepository.findUserByCode(b.getAppStuID()));
            }
            System.out.println("333333"+userList);
            if (! userList.isEmpty() && userList.size() > 0) {

                // 遍历
                for (User a : userList) {
                    Label lt0 = new Label(0, n, first+"", cellFormat2);
                    Label lt1 = new Label(1, n, a.getUserName(), cellFormat2);
                    Label lt2 = new Label(2, n, a.getUserCode(), cellFormat2);
                    Label lt3 = new Label(3, n, a.getUserGrade(), cellFormat2);
                    Label lt4 = new Label(4, n, a.getUserMajor(), cellFormat2);
                    Label lt5 = new Label(5, n, a.getUserClass(), cellFormat2);
                    Label lt6 = new Label(11, n, a.getUserApplication()+"", cellFormat2);
                    Label lt7 = new Label(12, n, a.getUserResult()+"", cellFormat2);
                    Label lt8 = new Label(13, n, a.getUserRecognize(), cellFormat2);
                    Label lt9 = new Label(14, n, a.getUserRemark(), cellFormat2);

                    sheet.addCell(lt0);
                    sheet.addCell(lt1);
                    sheet.addCell(lt2);
                    sheet.addCell(lt3);
                    sheet.addCell(lt4);
                    sheet.addCell(lt5);
                    sheet.addCell(lt6);
                    sheet.addCell(lt7);
                    sheet.addCell(lt8);
                    sheet.addCell(lt9);

                    n++;
                    first++;
                }
            }
/**************************************************************************************/

            //开始执行写入操做
            workbook.write();
            //关闭流
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 第六步，下载excel

        OutputStream out = null;
        try {

            // 1.弹出下载框，并处理中文
            /** 若是是从jsp页面传过来的话，就要进行中文处理，在这里action里面产生的直接能够用
             * String filename = request.getParameter("filename");
             */
            /**
             if (request.getMethod().equalsIgnoreCase("GET")) {
             filename = new String(filename.getBytes("iso8859-1"), "utf-8");
             }
             */

            response.addHeader("content-disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(filename, "utf-8"));

            // 2.下载
            out = response.getOutputStream();
            String path3 = request.getSession().getServletContext().getRealPath("") + "/" + filename;

            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
            InputStream is = new FileInputStream(path3);

            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                out.write(b, 0, size);
                size = is.read(b);
            }
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
