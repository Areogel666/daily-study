//Ajax�첽 javascript
function ajaxTestJs(){
//1.����XMLHttpRequest����
    var xhr = new XMLHttpRequest();
//2.���ûص�����
    xhr.onreadystatechange = function(){
        if(xhr.readystate == 4 && xhr.status == 200){
		    var result = xhr.responseText;//��ȡ��������Ӧ
		    //�����������result����
	    }
    };
//3.��ʼ��������ύ
    var url = 'UsersServlet.action?type=1&type2=2';
    var data = 'name=' + name;
    xhr.open('post', url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');//��������ͷ
    xhr.send(data);
}

//Ajax�첽 jQuery
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