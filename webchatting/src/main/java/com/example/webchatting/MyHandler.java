package com.example.webchatting;


import com.alibaba.fastjson.JSONObject;
import com.example.service.ChatService;
import com.example.service.FriendService;
import com.example.service.LoginService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MyHandler {

    @Resource
    LoginService loginService;

    @Resource
    ChatService chatService;

    @Resource
    FriendService friendService;

    //用户登录
    @RequestMapping("loginCheck")
    public String loginCheck(@RequestParam("username") String username, @RequestParam("password") String password, Model model,Session session) throws Exception {
        String state = loginService.loginCheck(username,password,model);
        if ("success".equals(state)){
            model.addAttribute("username",username);
            model.addAttribute("friendList",friendService.getFriendList(loginService.getUserNoByUserName(username)));
            model.addAttribute("userNo",loginService.getUserNoByUserName(username));
            return "webChatting";
        }
        else return "redirect:/index.html?"+state;
    }

    //用户注册
    @RequestMapping("relistUser")
    public String relistUser(@RequestParam("username") String username,@RequestParam("password") String password){
        return "redirect:/index.html?"+loginService.registerUser(username,password);
    }


    //将用户发送出的消息接收并存入聊天记录数据库(chatList)
    @ResponseBody
    @RequestMapping("chatting")
    public String chatting(@RequestParam("userNo") String userNo,@RequestParam("mes") String message,@RequestParam String friendNo,Model model) throws Exception {
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);

        chatService.chatting(userNo,friendNo,message);
        return "{\"state\":\"success\"}";
    }

    //客户端ajax轮询检测是否有消息发送给自己
    @ResponseBody
    @RequestMapping("receiveCheck")
    public String receiveCheck(@RequestParam("userNo") String userNo,HttpServletResponse response,Model model){
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        response.setCharacterEncoding("UTF-8");
        return chatService.friendMes(userNo);
    }

    //用户在浏览器选择好友并显示与该好友的聊天记录
    @ResponseBody
    @RequestMapping("chooseFriend")
    public void chooseFriend(@RequestParam("userNo") String userNo, @RequestParam("friendNo") String friendNo, HttpServletResponse response,Model model) throws Exception {
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);modelProgress(model,userNo);
        response.setCharacterEncoding("UTF-8");

        JSONObject json = chatService.chooseFriend(userNo,friendNo);
        response.getWriter().print(json);
    }

//    @ResponseBody
//    @RequestMapping("leave")
//    public void leaveOnline(@RequestParam("username") String username){
//        loginService.leave(username);
//    }

    @ResponseBody
    @RequestMapping("addFriend")
    public String addFriend(@RequestParam("friendName") String friendName,@RequestParam("userNo") String userNo,HttpServletResponse response,Model model){
        response.setCharacterEncoding("UTF-8");
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        if(loginService.hasUser(friendName)){
            if (friendService.addFriend(Integer.parseInt(userNo),loginService.getUserNoByUserName(friendName))) {
                return "好友申请已发送";
            }
            else return "该用户已经是你的好友";
        }
        return "该用户不存在";
    }

    @RequestMapping("friendApplyCheck")
    public String friendApplyCheck(@RequestParam("userNo") String userNo,Model model){
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        model.addAttribute("friendes", friendService.getApplySet(userNo));

        model.addAttribute("userNo",userNo);
        return "friendApply";
    }

    @RequestMapping("receiveFriendApply")
    public String receiveFriendApply(@RequestParam("userNo") String userNo,@RequestParam("friendNo") String friendNo,Model model){
        friendService.receiveApply(userNo,friendNo);
        return friendApplyCheck(userNo,model);
    }

    @RequestMapping("refuseFriendApply")
    public String refuseFriendApply(@RequestParam("userNo")String userNo,@RequestParam("friendNo")String friendNo,Model model){
        friendService.refuseApply(userNo,friendNo);
        return friendApplyCheck(userNo,model);
    }

    @ResponseBody
    @RequestMapping("deleteFriend")
    public String deleteFriend(@RequestParam("userNo") String userNo,@RequestParam("friendName") String friendName,HttpServletResponse response,Model model){
        response.setCharacterEncoding("UTF-8");
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        if (loginService.hasUser(friendName)){
            if (friendService.delFriend(userNo,Integer.toString(loginService.getUserNoByUserName(friendName)))){
                return "删除成功";
            }
            return "该用户不是你的好友";
        }
        return "该用户不存在";
    }

    public void modelProgress(Model model,String userNo){
        model.addAttribute("username",loginService.getUsername(userNo));
        model.addAttribute("friendList",friendService.getFriendList(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
    }
}
