<!--消息-->
<template>
    <div style="width: 50%">
        <div @click="chooseUser(item.from)"  class="useritem" v-for="(item,index) in messageList" :key="index">
            <div class="left unread"   :class="{online:userInfo[index].isOnline!='undefined'?(userInfo[index].isOnline=='true'?true:false):false,unread:true}">
                <img :src="userInfo[index].headImg">
            </div>
            <div class="right">
                <div class="username">{{userInfo[index].alias==''?item.from:userInfo[index].alias}} (电话{{item.from}})</div>
                <div class="msg">{{item.content}}</div>
            </div>
        </div>
        <Chat :is-show.sync="showChat" :username.sync="currentUsername" v-if="showChat"></Chat>
    </div>
</template>

<script>
    import Chat from "./Chat";
    export default {
        components: {Chat},
        data(){
            return {
                messageList: [],/*消息列表*/
                showChat:false,/*打开聊天框*/
                currentUsername:"",/*当前聊天对象*/
                userInfo:[],/*聊天对象的信息列表*/
                timer:null,/*定时器*/
            }
        },
        created() {
            this.getMessage()
        },
        mounted(){
            //每隔3秒调用,得到消息列表
            this.timer = setInterval(() => {
                this.getMessage()
            }, 3000);
        },
        destroyed() {
            clearInterval(this.timer);
        },
        methods:{
            async getMessage(){//得到消息和用户头像，从聊天记录表得到消息，然后从用户表得到头像，
                const _this=this
                let msgList;
                await axios.get(this.API.GetUserChatList+this.$store.state.userInfo.username).then((resp)=>{
                    //_this.messageList=resp.data
                    msgList=resp.data
                })
                _this.userInfo=[];
                //得到发出消息对应用户的信息
                for(let i=0;i<msgList.length;i++){
                    await axios.get(this.API.Login+msgList[i].from+'/null/null').then((resp)=>{
                        _this.userInfo.push(resp.data)
                    })
                }
                this.messageList=msgList
            },
            chooseUser(from){/*选择用户开始聊天*/
                this.showChat=true;
                this.currentUsername=from
            }


        }
    }
</script>

<style scoped>
    .unread{
        position: relative;
    }
    .unread::before{/*未读消息的小红点伪类*/
        position: absolute;
        content: "";
        display: block;
        width: 10px;
        height: 10px;
        border-radius: 5px;
        background: red;
        bottom:5px ;
        right: 5px;
    }
    .useritem .left{
        filter: grayscale(1)
    }
    .useritem .right{
        padding: 0 10px;
    }
    .username{
        padding-bottom: 20px;
        text-align: left;
    }
    .useritem{
        display:flex;
        height: 80px;
        background: white;
        border-bottom: 1px solid #ccc;
        align-items: center;
        padding: 0 10px;
    }
    .useritem .left img{
        width: 60px;
        height: 60px;
        border-radius: 50%;
    }
    .headerimg{
        height:50px;
        width: 50px;
        filter: grayscale(1);/*默认头像为灰色，登录后变亮*/
        margin:0 10px;
    }
    .online{
        filter: grayscale(0) !important;/*!important增加权重，避免在线的自己被灰色样式覆盖*/
    }
    .nav{
        height: 80px;
        width: 100vw;
        background: skyblue;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .headerimg img{
        height: 50px;
        width: 50px;
        border-radius: 50%;
    }
    .nav .title{
        font-weight: 900;
        font-size: 18px
    }
</style>