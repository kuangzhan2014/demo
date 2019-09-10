accessid = ''
host = ''
policy = ''
signature = ''
g_object_name = ''
expire = ''

function random_string(len) {
　　len = len || 32;
　　var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
　　var maxPos = chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
    　　pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename)
{
    suffix = get_suffix(filename)
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    g_object_name = 'inVideo/' +year+month+strDate+'-'+ random_string(10) + suffix
    console.log(g_object_name)
}

function tokenIsTimeOut(up, filename){
    now = timestamp = Date.parse(new Date()) / 1000;
    if(expire != '' && expire > now + 3){
        set_upload_param(up, filename);
    }else {
        $.ajax({
            url: "/upload/createHtmlUploadToken",
            type: "POST",
            data: "",
            dataType: "json",
            cache: false,
            success: function (data) {
                policy = data.policy;
                accessid = data.accessid;
                signature = data.signature;
                expire = data.expire;
                host = data.host;
                set_upload_param(up, filename);
            }, error: function(xhr, status, error) {
                var msg = xhr.responseJSON.message;
                layer.msg(msg == null ? '操作失败' : msg);
            }
        });
    }
}

function set_upload_param(up, filename)
{
    if (filename != '') {
        suffix = get_suffix(filename)
        calculate_object_name(filename)
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policy,
        'OSSAccessKeyId': accessid,
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });
    up.start();
}
window.onload = function(){
    var uploader = new plupload.Uploader({
        runtimes : 'html5,flash,silverlight,html4',
        browse_button : 'uploadVideo',
        //multi_selection: false,
        url : 'http://oss.aliyuncs.com',

        init: {
            FilesAdded: function(up, files) {
                plupload.each(files, function(file) {
                    document.getElementById('ossFile').innerHTML ='<div id="'+file.id+'">'
                        +'<div class="progress"><div class="progress-bar" style="width: 0%">'+'<b></b>'+'</div></div>'
                        +'</div>';
                    document.getElementById('videoSubmit').setAttribute('disabled',true);
                    tokenIsTimeOut(up, file.name);
                });
            },

            UploadProgress: function(up, file) {
                var d = document.getElementById(file.id);
                d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                var prog = d.getElementsByTagName('div')[0];
                var progBar = prog.getElementsByTagName('div')[0]
                progBar.style.width= file.percent+'%';
                progBar.setAttribute('aria-valuenow', file.percent);
            },

            FileUploaded: function(up, file, info) {
                if (info.status == 200)
                {
                    layer.msg('success', {
                        icon: 16
                        ,shade: 0.01
                    });
                    document.getElementById('videoSubmit').removeAttribute('disabled')
                    document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'success!';
                    var name = g_object_name.replace("inVideo/", "");
                    var last = name.lastIndexOf('.');
                    if(last != -1){
                        name = name.substring(0, last+1);
                    }
                    $('input[name="videoUrl"]').val(name);
                    var picture = "http://rpfc-library-prod.oss-cn-hangzhou.aliyuncs.com/image/"+name+"jpg"
                    setTimeout(function () {
                        var html = '<div ><img src="' + picture + '" class="product-picture" /></div>'
                        $('#uploadVideo').html(html) //图片链接（base64）
                    },3000)
                }
                else
                {
                    document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
                }
            },

            Error: function(up, err) {
                document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
            }
        }
    });
    uploader.init();
}
