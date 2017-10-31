//Ajax异步 javascript
function ajaxTestJs(){
//1.创建XMLHttpRequest对象
    var xhr = new XMLHttpRequest();
//2.设置回调函数
    xhr.onreadystatechange = function(){
        if(xhr.readystate == 4 && xhr.status == 200){
		    var result = xhr.responseText;//获取服务器响应
		    //根据需求调用result内容
	    }
    };
//3.初始化组件并提交
    var url = 'UsersServlet.action?type=1&type2=2';
    var data = 'name=' + name;
    xhr.open('post', url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');//设置请求头
    xhr.send(data);
}

//Ajax异步 jQuery
function ajaxTestJq(){
    $.ajax({
	    url:'',
		data:'',
		type:'post',
		dataType:'json',
		success:function(res){
		    alert(res);
		},
		error:function(res){
			alert(res);
		}
	});
}