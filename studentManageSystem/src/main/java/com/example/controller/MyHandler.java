package com.example.controller;

import com.example.aliases.Stu_subject;
import com.example.aliases.Student;
import com.example.mapper.SubjectMapper;
import com.example.service.AdminService;
import com.example.service.StuManageService;
import com.example.service.Stu_SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MyHandler {

    @Resource
    private AdminService adminServer;

    @Resource
    private StuManageService stuManage;

    @Resource
    private Stu_SubjectService stu_sub_service;

    @Resource
    private SubjectMapper subjectMapper;

    @RequestMapping("adminCheck")
    public String adminCheck(@RequestParam("account") String account, @RequestParam("password") String password, Model model){
        if (adminServer.adminCheck(account,password)){
            model.addAttribute("studentes",stuManage.getAllStu());
            model.addAttribute("sub_list",subjectMapper.getAll());
            return "admin";
        }
        return "redirect:/adminLogin.html?false";
    }

    @ResponseBody
    @RequestMapping("addStu")
    public String addStu(@RequestParam("stuName") String stuName, @RequestParam("speciality") String speciality, @RequestParam("stuClass") String stuClass, @RequestParam("stuAddr") String stuAddr) {
        return "{\"stuNo\":\""+stuManage.insertStu(stuName,speciality,Integer.parseInt(stuClass),stuAddr)+"\"}";
    }

    @ResponseBody
    @RequestMapping("rmStu")
    public String removeStudent(@RequestParam("rmStuNo") String rmStuNo){
        stuManage.removeStu(rmStuNo);
        stu_sub_service.removeStu(rmStuNo);
        return "success";
    }

    @RequestMapping("getGrade")
    public String getGrade(@RequestParam("stuNo") String stuNo, @RequestParam("admin") String admin, HttpServletResponse response,Model model){
        
        response.setCharacterEncoding("UTF-8");
        List<Stu_subject> subList = stu_sub_service.getByStuNo(Integer.parseInt(stuNo));
        Student stu = stuManage.getByStuNo(stuNo);
        model.addAttribute("stuName", stu.getStuName());
        model.addAttribute("stuNo", stu.getStuNo());
        model.addAttribute("sub_list",subjectMapper.getAll());
        if (!subList.isEmpty()) {
            model.addAttribute("stu_sub_list", subList);
            if (admin.equals("true")){
                model.addAttribute("adminText","管理员登录");
                model.addAttribute("right","true");
            }
        }
        return "grade";
    }

    @RequestMapping("stuLoginCheck")
    public String stuLog(@RequestParam("stuNo")String stuNo,@RequestParam("password") String password,HttpServletResponse response,Model model){
        if (stuManage.stuLoginCheck(stuNo,password))
            return getGrade(stuNo,"0",response,model);
        else return "redirect:/index.html?false";
    }

    @ResponseBody
    @RequestMapping("chooseSubject")
    public String chooseSubject(@RequestParam("stuNo")String stuNo,@RequestParam("subject")String subject){
        if (stu_sub_service.contain(stuNo,subject)){
            return "已选该课程，请勿重复选课";
        }
        stu_sub_service.insert(stuNo,subject);
        return "选课成功";
    }

    @ResponseBody
    @RequestMapping("changeGrade")
    public String changeGrade(String stuNo,String subject,String grade){
        stu_sub_service.updateGrade(stuNo,subject,grade);
        return "修改成功";
    }

    @ResponseBody
    @RequestMapping("addSubject")
    public String addSubject(String subject,String teacher){
        if (subjectMapper.getOne(subject)==null) {
            subjectMapper.insertSubject(subject, teacher);
            return "添加成功";
        }
        else return "已存在该课程";
    }
}
