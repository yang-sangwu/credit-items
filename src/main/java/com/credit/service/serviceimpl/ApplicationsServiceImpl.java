package com.credit.service.serviceimpl;

import com.credit.dao.RecordDao;
import com.credit.pojo.Applications;
import com.credit.pojo.Page;
import com.credit.pojo.Paths;
import com.credit.repository.ApplicationsRepository;
import com.credit.repository.PathsRepository;
import com.credit.repository.RejectRepository;
import com.credit.repository.UserRepository;
import com.credit.service.ApplicationsService;
import com.credit.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.credit.util.PageUtils.pagesUtils;

@Service
@Slf4j
public class ApplicationsServiceImpl implements ApplicationsService {
    @Resource
    private ApplicationsRepository applicationsRepository;

    @Resource
    private PathsRepository pathsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RejectRepository rejectRepository;

    @Override
    public ReturnUtil insertApplications(String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, int appGroupCount, int appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime) {
        if (appScore.compareTo(BigDecimal.valueOf(0)) < 1 || appScore.compareTo(BigDecimal.valueOf(10)) > -1) {
            return ReturnUtil.error("学分范围不合常理，请核查后添加！");
        }
        if (appGroupCount < appGroupGrade || appGroupGrade < 0) {
            return ReturnUtil.error("排名不合理，请核查后重新输入！");
        }
        if (appGroupCount == 1 && appGroup.equals("是")) {
            return ReturnUtil.error("集体项目人数不能为1！");
        }
        if (appGroupCount < 1 || userRepository.countUser() < appGroupCount) {
            return ReturnUtil.error("项目人数范围不合理，请核查后重新输入！");
        }

        //使用Date创建日期对象
        Date date = new Date();
        /**
         * 创建格式化时间日期类
         *构造入参String类型就是我们想要转换成的时间形式
         */
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //      System.out.println("格式化后的时间------->"+format.format(date));
        Applications applications = new Applications(appName, appSex, appStuID, appMajor, appClass, appAcademic, appType, appGroup, appGroupCount, appGroupGrade, appScore, appResult, appRecognize, appContent, appUserName, appUserTime, appFacultyName, appFacultyTime, appClassName, appClassTime, appAcademicScore, appAcademicName, appAcademicTime, 0, format.format(date));
        applicationsRepository.save(applications);
        return ReturnUtil.success("添加成功", applications);
    }


    @Override
    public void deleteApplications(int appId) {
        pathsRepository.deleteByAppId(appId);
        applicationsRepository.deleteApplications(appId);
        rejectRepository.deleteReject(appId);
    }

    @Override
    public Response updateApplications(int appId, String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime) {
        if (appGroupCount < 0 || appGroupGrade < 0) return Response.error("共多少人或本人排名不准为负数！");
        if (appGroupGrade > appGroupCount) return Response.error("本人排名不准大于共多少人！");
        if (appScore.compareTo(BigDecimal.valueOf(0)) < 1 || appScore.compareTo(BigDecimal.valueOf(10)) > -1) {
            return Response.error("学分范围不合常理，请仔细核查！");
        } else {
            //使用Date创建日期对象
            Date date = new Date();
            /**
             * 创建格式化时间日期类
             *构造入参String类型就是我们想要转换成的时间形式
             */
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Applications applications = new Applications(appId, appName, appSex, appStuID, appMajor, appClass, appAcademic, appType, appGroup, appGroupCount, appGroupGrade, appScore, appResult, appRecognize, appContent, appUserName, appUserTime, appFacultyName, appFacultyTime, appClassName, appClassTime, appAcademicScore, appAcademicName, appAcademicTime, 0, format.format(date));
            return Response.ok(applicationsRepository.save(applications));
        }
    }

    @Override
    public List<Applications> findAllApplications() {
        return applicationsRepository.findAll();
    }

    @Override
    public Applications findApplicationsById(int appId) {
        return applicationsRepository.findById(appId).orElse(null);
    }

    @Override
    public Response findUserByAppPass(int appPass, int pages, int num) {
        Map<String, List> map = new HashMap<>();
        int totalPages;
        int total;
        if (pages <= 0 || num <= 0) {
            List<String> list = new LinkedList<>();
            list.add("请输入合理的页数或查询数量！");
            map.put("error", list);
            return Response.error(map);
        } else {
            int count = recordDao.queryAppAppPassCounts(appPass);
            if (count % num == 0) {
                totalPages = count / num;
            } else {
                total = count / num;
                totalPages = total + 1;
            }
            List<Integer> list = new LinkedList<>();
            list.add(totalPages);
            map.put("总共的页数", list);
            List<Integer> list1 = new LinkedList<>();
            list1.add(count);
            map.put("总条数", list1);
            int thePage = (pages - 1) * num;
            List<RecordUtil> list2 = recordDao.findUserByAppPass(appPass, thePage, num);
            map.put("查询信息", list2);
            return Response.ok(map);
        }
    }

    @Override
    public int queryAppAppPassCounts(int appPass) {
        return recordDao.queryAppAppPassCounts(appPass);
    }

    @Override
    public Page<RecordUtil> getAppRecord(String appAcademic, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countAppRecord(appAcademic);
//        log.info("count:" + count);
        Integer pageTotal = 0;
        int index = (pageNo - 1) * pageSize;
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countAppRecord(appAcademic) / pageSize;
        if (recordDao.getAppRecord(appAcademic, index, pageSize).size() == 0) {
            meg = "未查到你想要的数据！";
        } else if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getAppRecord(appAcademic, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<RecordUtil> getByAppName(String appName, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countByAppName(appName);
//        log.info("count:" + count);
        Integer pageTotal = 0;
        int index = (pageNo - 1) * pageSize;
        if (recordDao.getByAppName(appName, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countByAppName(appName) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getByAppName(appName, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public void updateAppPass(int appPass, int appId) {
        applicationsRepository.updateAppPass(appPass, appId);
    }

    @Override
    public Response queryAppByTime(String appTime, Integer appPass, Integer pages, Integer num) {
        if (pages <= 0 || num <= 0) {
            return Response.error("请输入合理的页数！");
        } else {
            int count = recordDao.queryAppByTimeCounts(appTime, appPass);
            Map<String, Integer> map = pagesUtils(count, pages, num);
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            Map<String, List> map1 = new HashMap<>();
            List<RecordUtil> list = recordDao.queryAppByTime(appTime, appPass, thePage, num);
            List<Integer> list1 = new LinkedList<>();
            list1.add(totalPages);
            List<Integer> list2 = new LinkedList<>();
            list2.add(count);
            map1.put("查询信息为", list);
            map1.put("总共的页数为", list1);
            map1.put("总条数为", list2);
            return Response.ok(map1);
        }
    }

    @Override
    public Response queryAllAppTime(int pages, int num) {
        if (pages <= 0 || num <= 0) {
            return Response.error("请输入合理的页数！");
        } else {
            int count = recordDao.queryAllAppTimeCounts();
            Map<String, Integer> map = pagesUtils(count, pages, num);
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            Map<String, List> map1 = new HashMap<>();
            List<RecordUtil> list = recordDao.queryAllAppTime(thePage, num);
            List<Integer> list1 = new LinkedList<>();
            list1.add(totalPages);
            List<Integer> list2 = new LinkedList<>();
            list2.add(count);
            map1.put("查询信息为", list);
            map1.put("总共的页数为", list1);
            map1.put("总条数为", list2);
            return Response.ok(map1);
        }
    }

    @Override
    public Page<RecordUtil> getAppByNoPass(String appName, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countAppByNoPass(appName);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (recordDao.getAppByNoPass(appName, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countAppByNoPass(appName) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getAppByNoPass(appName, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<RecordUtil> getAppByPass(String appName, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countAppByPass(appName);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (recordDao.getAppByPass(appName, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countAppByPass(appName) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getAppByPass(appName, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<RecordUtil> getAppByWait(String appName, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countAppByWait(appName);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (recordDao.getAppByWait(appName, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countAppByWait(appName) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getAppByWait(appName, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<Applications> getAppAllWait(String appAcademic, Integer pageNo, Integer pageSize) {
        List<Applications> list = new ArrayList<>();
        String meg = "";
        Integer count = applicationsRepository.countAppAllWait(appAcademic);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (applicationsRepository.getAppAllWait(appAcademic, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += applicationsRepository.countAppAllWait(appAcademic) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = applicationsRepository.getAppAllWait(appAcademic, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<Applications> getAppAllPass(String appAcademic, Integer pageNo, Integer pageSize) {
        List<Applications> list = new ArrayList<>();
        String meg = "";
        Integer count = applicationsRepository.countAppAllPass(appAcademic);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (applicationsRepository.getAppAllPass(appAcademic, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += applicationsRepository.countAppAllPass(appAcademic) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = applicationsRepository.getAppAllPass(appAcademic, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<Applications> getAppAllNoPass(String appAcademic, Integer pageNo, Integer pageSize) {
        List<Applications> list = new ArrayList<>();
        String meg = "";
        Integer count = applicationsRepository.countAppAllNoPass(appAcademic);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (applicationsRepository.getAppAllNoPass(appAcademic, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += applicationsRepository.countAppAllNoPass(appAcademic) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = applicationsRepository.getAppAllNoPass(appAcademic, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<RecordUtil> getAppByTime(String appName, String appTime, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countAppByTime(appName, appTime);
        int index = (pageNo - 1) * pageSize;
//        log.info("count:" + count);
        Integer pageTotal = 0;

        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countAppByTime(appName, appTime) / pageSize;
        if (recordDao.getAppByTime(appName, appTime, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        } else if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getAppByTime(appName, appTime, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    @Override
    public Page<Applications> getApplicationsLike(String appAcademic, String search, Integer pageNo, Integer pageSize) {
        List<Applications> list = new ArrayList<>();
        String meg = "";
        Integer count = applicationsRepository.countApplicationsLike(appAcademic, search);
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (count == 0) {
            return Page.error(0, "无相关数据！");
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += applicationsRepository.countApplicationsLike(appAcademic, search) / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            int index = (pageNo - 1) * pageSize;
            list = applicationsRepository.getApplicationsLike(appAcademic, search, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }

    private String pathlode = "C:/PerfLogs/files/";

    @Override
    public ReturnUtil uplode(MultipartFile[] files, Integer appId) {
        List<FileUtils> list = new ArrayList<>();
        if (files == null) {
            return ReturnUtil.error("上传失败！");
        }
        for (int i = 0; i < files.length; i++) {
            String tool = String.valueOf(appId) + "/";
            FileUtils fileUtils = new FileUtils();
            String fileName = files[i].getOriginalFilename();
            File elem = new File(pathlode + tool + fileName);
            if (!elem.getParentFile().exists()) {
                elem.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(elem);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            String visitPath = "http://110.40.212.128:8080/images/" + tool + fileName;
            String path = pathlode + tool + fileName;
            fileUtils.setFileName(fileName);
            fileUtils.setPath(path);
            fileUtils.setVisitPath(visitPath);
            list.add(fileUtils);
            Paths paths = new Paths(null, appId, visitPath, path);
            pathsRepository.save(paths);
        }
        return ReturnUtil.success("上传成功！", list);
    }

    @Override
    public ReturnUtil download(String path, HttpServletResponse response) {
        File file = new File(path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            //文件是否存在
            if (file.exists()) {
                //设置响应
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));

                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
            } else {
                return ReturnUtil.error("文件不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ReturnUtil.success("下载成功!");
    }


    @Override
    public ReturnUtil deleteFile(String path) {
        Integer integer = pathsRepository.deleteByPath(path);
        System.out.println(integer);
        if (integer == null) {
            return ReturnUtil.error("删除失败,数据库不存在数据!");
        }
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                return ReturnUtil.success("删除成功！");
            } else {
                return ReturnUtil.error("删除失败！");
            }
        }
        return ReturnUtil.error("该文件不存在！");
    }
}