package com.example.webchatting;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.aliases.ChatList;
import com.example.aliases.FriendList;
import com.example.aliases.User;
import com.example.mapper.ChatListMapper;
import com.example.mapper.FriendListMapper;
import com.example.mapper.UserMapper;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class MyHandler {

    //检测是否有消息发送给用户，key(integer)代表用户，value(Set)记录发消息给用户的friendNo
    public static Map<Integer,Set<Integer>> signal;

    public static Set<Integer> online = new HashSet<Integer>();

    public static Map<Integer,Set<User>> friendApply = new HashMap<Integer,Set<User>>();

    @Resource
    UserMapper usermapper;

    @Resource
    FriendListMapper friendlistmapper;

    @Resource
    ChatListMapper chatlistmapper;

    //用户登录
    @RequestMapping("loginCheck")
    public String loginCheck(@RequestParam("username") String username, @RequestParam("password") String password, Model model,Session session) throws Exception {
        if(signal==null){
            signal = new HashMap<>();
            int[] allUserNo = usermapper.getAllUserNo();
            for(int i:allUserNo){
                signal.put(i,new HashSet<Integer>());
            }
        }
        User user = usermapper.getByUsername(username);
        if (user != null) {
            if (online.contains(user.getUserNo()))
                return "redirect:/index.html?online";
            if (user.getPassword().equals(password)) {
                online.add(user.getUserNo());
                model.addAttribute("username",username);
                model.addAttribute("friendList",friendlistmapper.getFriendes(user.getUserNo()));
                model.addAttribute("userNo",user.getUserNo());
                return "webChatting";
            }
        }
        return "redirect:/index.html?false";
    }

    //用户注册
    @RequestMapping("relistUser")
    public String relistUser(@RequestParam("username") String username,@RequestParam("password") String password){
        if(usermapper.getByUsername(username)!=null)return "redirect:/relist.html?false";
        usermapper.insertOne(username,password);
        return "redirect:/index.html?true";
    }


    //将用户发送出的消息接收并存入聊天记录数据库(chatList)
    @ResponseBody
    @RequestMapping("chatting")
    public String chatting(@RequestParam("userNo") String userNo,@RequestParam("mes") String message,@RequestParam String friendNo,Model model) throws Exception {
        User user = usermapper.getByUserNo(Integer.parseInt(userNo));
        model.addAttribute("username",user.getUsername());
        model.addAttribute("friendList",friendlistmapper.getFriendes(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        int userNo_int = Integer.parseInt(userNo),friendNo_int=Integer.parseInt(friendNo);
        if (friendlistmapper.getOne(userNo_int,friendNo_int)!=null) {
            signal.get(Integer.parseInt(friendNo)).add(Integer.parseInt(userNo));
            chatlistmapper.insertChat(new ChatList(getChatNo(userNo, friendNo), message, getTime(), Integer.parseInt(userNo), Integer.parseInt(friendNo)));
        }
        return "{\"state\":\"success\"}";
    }

    //客户端ajax轮询检测是否有消息发送给自己
    @ResponseBody
    @RequestMapping("receiveCheck")
    public String receiveCheck(@RequestParam("userNo") String userNo,HttpServletResponse response,Model model){
        User user = usermapper.getByUserNo(Integer.parseInt(userNo));
        model.addAttribute("username",user.getUsername());
        model.addAttribute("friendList",friendlistmapper.getFriendes(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        response.setCharacterEncoding("UTF-8");
        StringBuffer buf = new StringBuffer();
        Set<Integer> set = signal.get(Integer.parseInt(userNo));
        if (!set.isEmpty()){
            for (Integer i:set){
                buf.append(i+",");
            }
        }
        return buf.toString();
    }

    //用户在浏览器选择好友并显示与该好友的聊天记录
    @ResponseBody
    @RequestMapping("chooseFriend")
    public void chooseFriend(@RequestParam("userNo") String userNo, @RequestParam("friendNo") String friendNo, HttpServletResponse response,Model model) throws Exception {
//        return "{\"state\":\"success\"}";
        model.addAttribute("username",usermapper.getByUserNo(Integer.parseInt(userNo)).getUsername());
        model.addAttribute("friendList",friendlistmapper.getFriendes(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
//        ChatList chatlist = new ChatList(1,"你好","7-28",1,2);
//        json.putOpt("chat",JSON.toJSONString(chatlist));
        int chatNo = getChatNo(userNo,friendNo);
        List<ChatList> chatlist = chatlistmapper.getChat(chatNo);
        int i = 1;
        for (ChatList chat : chatlist){
            json.put(Integer.toString(i++),JSON.toJSONString(chat));
        }
        response.getWriter().print(json);
        if (signal.get(Integer.parseInt(userNo)).contains(Integer.parseInt(friendNo)))
            signal.get(Integer.parseInt(userNo)).remove(Integer.parseInt(friendNo));
    }

    @ResponseBody
    @RequestMapping("leave")
    public void leaveOnline(@RequestParam("userNo") String userNo){
        int userNo_in_online = Integer.parseInt(userNo);
        online.remove(userNo_in_online);
    }

    @ResponseBody
    @RequestMapping("addFriend")
    public String addFriend(@RequestParam("friendName") String friendName,@RequestParam("userNo") String userNo,HttpServletResponse response,Model model){
        response.setCharacterEncoding("UTF-8");
        model.addAttribute("username",usermapper.getByUserNo(Integer.parseInt(userNo)).getUsername());
        model.addAttribute("friendList",friendlistmapper.getFriendes(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        User user = usermapper.getByUsername(friendName);
        if(user!=null){
            if (friendlistmapper.getOne(user.getUserNo(),Integer.parseInt(userNo))==null) {
                if (!friendApply.containsKey(user.getUserNo())) {
                    friendApply.put(user.getUserNo(), new HashSet<User>());
                }
                friendApply.get(user.getUserNo()).add(usermapper.getByUserNo(Integer.parseInt(userNo)));
                return "好友申请已发送";
            }
            else return "该用户已经是你的好友";
        }
        return "该用户不存在";
    }

    @RequestMapping("friendApplyCheck")
    public String friendApplyCheck(@RequestParam("userNo") String userNo,Model model){
        int userNO_int = Integer.parseInt(userNo);
        if (friendApply.get(userNO_int)!=null)
            model.addAttribute("friendes",friendApply.get(userNO_int));
        model.addAttribute("userNo",userNo);
        return "friendApply";
    }

    @RequestMapping("receiveFriendApply")
    public String receiveFriendApply(@RequestParam("userNo") String userNo,@RequestParam("friendNo") String friendNo,Model model){
        int userNo_int = Integer.parseInt(userNo),friendNo_int = Integer.parseInt(friendNo);
        friendlistmapper.insertFriend(new FriendList(userNo_int,friendNo_int,usermapper.getByUserNo(friendNo_int).getUsername()));
        friendlistmapper.insertFriend(new FriendList(friendNo_int,userNo_int,usermapper.getByUserNo(userNo_int).getUsername()));
        Set<User> set = friendApply.get(userNo_int);
        set.removeIf(user -> friendNo_int == user.getUserNo());
        return friendApplyCheck(userNo,model);
    }

    @ResponseBody
    @RequestMapping("deleteFriend")
    public String deleteFriend(@RequestParam("userNo") String userNo,@RequestParam("friendName") String friendName,HttpServletResponse response,Model model){
        response.setCharacterEncoding("UTF-8");
        model.addAttribute("username",usermapper.getByUserNo(Integer.parseInt(userNo)).getUsername());
        model.addAttribute("friendList",friendlistmapper.getFriendes(Integer.parseInt(userNo)));
        model.addAttribute("userNo",userNo);
        User user = usermapper.getByUsername(friendName);
        int userNo_int = Integer.parseInt(userNo);
        if(user!=null){
            int friendNo = user.getUserNo();
            if (friendlistmapper.getOne(userNo_int,friendNo)!=null){
                friendlistmapper.deleteFriend(userNo_int,friendNo);
                friendlistmapper.deleteFriend(friendNo,userNo_int);
                return "删除成功";
            }
            else return "你的好友列表没有该好友";
        }
        return "你想要删除的用户不存在";
    }

    public static int getChatNo(String userNo, String friendNo){
        int userNo_int=Integer.parseInt(userNo),friendNo_int = Integer.parseInt(friendNo);
        return userNo_int<friendNo_int?(userNo_int*10+friendNo_int):(userNo_int+friendNo_int*10);
    }

    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        return format.format(date);
    }
}
