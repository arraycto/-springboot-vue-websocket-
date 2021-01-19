<!--聊天框-->
<template>
    <div>
        <!--弹出聊天框-->
        <div class="chat" v-if="showChat">
            <div class="chat-head">
                <span class="online-state">{{userInfo.alias==''?userInfo.username:userInfo.alias}}{{userInfo.isOnline=='true'?'(在线)':'(不在线)'}}</span>
                <i class="el-icon-close icon-close" @click="close"></i>
            </div>
            <div class="chat-contain" ref="chatBox">
                <div class="chatItem" v-for="(item,index) in chatList" :key="index" :class="item.from==currentUser?'self':''">
                    <div class="header">
                        <img :src="item.from==currentUser?$store.state.userInfo.headImg:userInfo.headImg" alt="">
                    </div>
                    <div class="chatContent">{{item.content}}</div>
                </div>
            </div>
            <div class="chat-footer">
                <div class="chat-left">
                    <el-input v-model="chatContent" placeholder="请输入内容" size="small" maxlength="50"></el-input>
                </div>
                <div class="chat-right">
                    <el-button type="primary" size="mini"  style="margin-top: 2px" @click="sendMsg">发送消息</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props:["isShow","username"],
        data(){
            return{
                chatContent:"",/*输入的聊天内容*/
                isOnline:false,/*是否在线*/
                currentUser:this.$store.state.userInfo.username,
                chatList:[],/*聊天内容*/
                socket:"",/*websocket*/
                userInfo:[],/*对方的信息*/
            }
        },
        async created() {
            const _this=this
            //查询对方是否在线
            await axios.get(this.API.Login+this.username+'/null/null')
                .then(function (resp) {
                    _this.userInfo=resp.data
                })
            this.getChat()
        },
        mounted() {
            this.init()//连接websocket
        },
        updated() {
            this.toBottom()//在页面更新时下拉滚动条
        },
        destroyed () {
            // 销毁监听
            this.socket.onclose = this.close
        },
        methods:{
            init: function () {
                if(typeof(WebSocket) === "undefined"){
                    this.$message.error('您的浏览器不支持socket!')
                }else{
                    // 实例化socket
                    this.socket = new WebSocket(this.API.WebSocketPath+this.$store.state.userInfo.username)
                    // 监听socket连接
                    this.socket.onopen = this.open
                    // 监听socket错误信息
                    this.socket.onerror = this.error
                    // 监听socket消息
                    this.socket.onmessage = this.getMessage
                }
            },
            open: function () {
                console.log("socket连接成功")
            },
            error: function () {
                console.log("连接错误")
            },
            getMessage: function (msg) {//得到服务器发送过来的消息
                console.log(JSON.parse(msg.data))
                //let chat ={"id":1, "from":"","to":this.$store.state.userInfo.username,"content":msg.data,"time":new Date()}
                this.chatList.push(JSON.parse(msg.data))//添加到当前的聊天记录里
            },
            sendMsg: function () {//发送消息给指定的人
                this.socket.send(this.chatContent+"|"+this.username)//将要发送的信息和内容拼起来，以便于服务端知道消息要发给谁
                let chat ={"id":1, "from":this.$store.state.userInfo.username,"to":this.username,"content":this.chatContent,"time":new Date()}
                axios.post(this.API.AddChat,chat)//当前发送的消息存入数据库
                this.chatList.push(chat)//添加到当前的聊天记录里
                this.chatContent=""
            },
            close: function () {
                this.showChat=false
                this.socket.close()
            },
            async getChat(){//得到聊天记录
                const _this=this
                await axios.get(this.API.GetChatList+this.$store.state.userInfo.username+'/'+this.thisusername)
                .then(function (resp) {
                    _this.chatList=resp.data
                })
            },
            //让滚动条自动滚到最下面
            toBottom(){
                let chatBox = this.$refs.chatBox;
                chatBox.scrollTop = chatBox.scrollHeight - chatBox.clientHeight;
            },
        },
        computed:{
            showChat:{/*是否显示界面*/
                get(){
                    return this.isShow
                },
                set(val){
                    this.$emit("update:isShow",val)
                }
            },
            thisusername:{/*商家的用户名*/
                get(){
                    return this.username
                },
                set(val){
                    this.$emit("update:username",val)
                }
            },
        },
    }
</script>

<style scoped>
    /*聊天框*/
    .chat{
        z-index: 99;
        position: fixed;
        width: 400px;
        height: 300px;
        background-color: #e8e8e8;
        left: 500px;
        top: 200px;
        overflow: hidden;
    }
    .chat-head{
        width: 100%;
        height: 40px;
        background-color: white;
        display: flex;
        justify-content: space-between;
        background-color: #e8e8e8;
    }
    .online-state{
        display: block;
        width: 97%;
        height: 37px;
        line-height: 42px;
        color: skyblue;
        font-weight: bold;
        font-size: 15px;
    }
    .icon-close{
        line-height: 25px;
        margin-right: 5px;
        cursor: pointer
    }
    .chat-contain{
        width: 100%;
        margin:5px 0;
        height: 200px;
        background-color: white;
        overflow: scroll;
    }
    .chat-footer{
        width: 98%;
        margin: 5px;
        height:50px;
        display: flex;
    }
    .chat-left{
        width: 60%;
        height: 100%;
    }
    .chat-right{
        width: 40%;
        height: 100%;
    }
    /*滚动条样式*/
    *::-webkit-scrollbar {
        /*滚动条整体样式*/
        width : 5px;  /*高宽分别对应横竖滚动条的尺寸*/
        height: 0px;
    }
    *::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius   : 10px;
        background-color: #a2a3a5;
    }
    *::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.2);
        background   : #ededed;
        border-radius: 10px;
    }
    /*消息样式*/
    .chatItem{
        display: flex;
        margin: 5px 10px;
    }
    .self{
        flex-direction: row-reverse;
        justify-content: flex-start;

    }
    .chatItem .header img{
        width: 50px;
        height: 50px;
        border-radius: 50%
    }
    .chatItem .chatContent{
        background: #bbb;
        border-radius: 5px;
        padding: 0px 5px;
        color: #fff;
        margin: 8px 0px 0px 20px;
        line-height: 34px;
        position: relative;
        height: 34px;
    }
    .self .chatContent{
        margin: 8px 20px 0px 0px;
    }
    .chatItem .chatContent::before{/*伪类制造消息框的小箭头 */
        display: block;
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-right: 10px solid #bbb;
        border-top: 10px solid transparent;
        border-bottom: 5px solid transparent;
        top: 10px;
        left: -10px;
    }
    .self .chatContent::before{
        display: block;
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 10px solid #bbb;
        border-top: 10px solid transparent;
        border-bottom: 5px solid transparent;
        top: 10px;
        right: -10px;
        left: initial;
        border-right:initial;
    }

    .header{
        position: relative;
        font-size: 18px;
        font-weight: 900;
        background: skyblue;
        height: 50px;
        text-align: center;
        line-height: 40px;
    }
</style>