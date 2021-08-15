// //当前窗口所在的好友
// let presentFriend = 0;
// function sendMes(){
//     if (presentFriend!=0) {
//         $.getJSON(
//             "chatting",
//             {"userNo": "[[${userNo}]]", "mes": $("#chatting").val(), "friendNo": presentFriend.value},
//             function (result) {
//                 document.getElementById("chatList").innerHTML += '<div style=\"color: chartreuse\">' + "[[${username}]]" + ':</div>' + '<div style=\"color: chartreuse\">' + $("#chatting").val() + '</div>' + '<br/>';
//                 document.getElementById("chatting").value = null;
//                 document.getElementById("chatList").scrollTop = document.getElementById("chatList").scrollHeight;
//             }
//         );
//     }
// }
//
// function chooseFriend(present){
//     document.getElementById("chatList").innerHTML="";
//     presentFriend = present;
//     document.getElementById("chatFriendName").innerHTML=present.className;
//     $.getJSON(
//         "chooseFriend",
//         {"userNo":"[[${userNo}]]","friendNo":presentFriend.value},
//         function (result) {
//             // let json = $.parseJSON(result.chat);
//             $.each(result,function (i,element) {
//                 let ele = JSON.parse(element);
//                 if (ele.sendNo=="[[${userNo}]]")
//                     document.getElementById("chatList").innerHTML+='<div style=\"color: chartreuse\">'+ele.chatTime+'</div>'+'<div style=\"color: chartreuse\">'+"[[${username}]]"+':</div>'+'<div style=\"color: chartreuse\">'+ele.chatMessage+'</div> '+'<br/>';
//                 else document.getElementById("chatList").innerHTML+='<div>'+ele.chatTime+':</div>'+'<div>'+present.className+':</div>'+'<div>'+ele.chatMessage+'</div>'+'<br/>';
//             })
//             document.getElementById("chatList").scrollTop = document.getElementById("chatList").scrollHeight;
//         }
//     );
//     present.style.backgroundColor="";
// }
//
// setInterval(receiveCheck,2000);
// function receiveCheck() {
//     $.ajax({
//         url:"receiveCheck",
//         method:"post",
//         data:"userNo="+"[[${userNo}]]",
//         success:function (result,status) {
//             let array = result.split(',');
//             array.forEach(function (x, i, a) {
//                 if (document.getElementById(x)!=null)
//                     document.getElementById(x).style.backgroundColor = "yellow";
//             })
//         }
//     })
// }
//
// window.onbeforeunload = function () {
//     $.getJSON(
//         "leave",
//         {"userNo":"[[${userNo}]]"},
//         function (result) {
//         }
//     );
// }
// function addFriend() {
//     let friendName = prompt("请输入你想添加的好友名称:","");
//     $.ajax({
//         url:"addFriend",
//         data:"friendName="+friendName+"&userNo="+"[[${userNo}]]",
//         method:"post",
//         success:function(result,status){
//             alert(result);
//         }
//
//     })
// }
//
// function friendApply(){
//     window.open("friendApplyCheck?"+"userNo=[[${userNo}]]");
// }
//
// function deleteFriend(){
//     let friendName = prompt("请输入你想要删除的好友名称:","");
//     $.ajax({
//         url:"deleteFriend",
//         method:"post",
//         data:"userNo=[[${userNo}]]&friendName="+friendName,
//         success:function (result,status) {
//             alert(result);
//             location.reload();
//         }
//     })
// }