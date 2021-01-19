<!--头部-->
<template>
    <!--顶部导航-->
    <div class="hc-home-header-wrapper">
        <div class="hc-header">
            <div class="logo-area">
                <a href="/">
                    <img src="../assets/img/upload/static/logo.png" alt="红尘客栈">
                </a>
            </div>
            <div class="slot-area"></div>
            <div class="tools-area">
                <div class="tools-item" v-if="!this.$store.state.isLogin">
                    <span @click="showLogin=true" >注册</span>
                </div>
                <div class="tools-item" v-if="!this.$store.state.isLogin">
                    <span @click="showLogin=true" >登录</span>
                </div>
                <div class="tools-item" v-if="this.$store.state.isLogin">
                    <el-dropdown trigger="click">
                        <div class="el-dropdown-link header-avatar">
                            <img :src="$store.state.userInfo.headImg" >
                        </div>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="toPersonalCenter">个人中心</el-dropdown-item>
                            <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <div class="tools-item" v-if="$store.state.isLogin">
                    <el-dropdown trigger="click">
                        <span class="el-dropdown-link">通知</span>
                        <el-dropdown-menu slot="dropdown" class="dropdown-notice">
                            <div class="classified-navigation">
                                <div class="classified-navigation-item">
                                    <el-link @click="showNoticeType='system'">系统通知</el-link>
                                </div>
                                <div class="classified-navigation-item">
                                    <el-link @click="showNoticeType='discount'">优惠促销</el-link>
                                </div>
                            </div>
                            <div class="classified-contain">
                                <div class="classified-contain-wrapper">
                                    <div class="classified-contain-item" v-for="(item,key) in notice" v-show="item.noticeType===showNoticeType">
                                        <a :href="item.url" style="color: black">
                                            <div class="notification-type" >
                                                <span>{{item.noticeType=="system"?"系统通知":"优惠促销"}}</span>
                                                <span>{{dateFormat(item.datetime)}}</span>
                                            </div>
                                            <p class="notification-describe">{{item.describe}}</p>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="tips"></div>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <div class="tools-item" v-if="this.$store.state.isLogin">
                    <span @click="toMerchantManage">商户系统</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props:['thatnotice','thatshowLogin'],
        data(){
            return{
                showNoticeType:"system",/*显示通知的类型*/
            }
        },
        created() {
        },
        methods:{
            async loginOut(){//退出登录
                //修改用户在线状态
                let user;
                await axios.get(this.API.Login+ this.$store.state.userInfo.username+'/null/null').then((resp)=>{
                    user=resp.data
                })
                user.isOnline='false'
                await axios.put(this.API.UpdateUser, user)
                //清除数据
                this.$store.state.isLogin=false
                this.$store.state.userInfo=[]
                this.notice=[]
                sessionStorage.setItem('store', null)
                await this.$router.push({
                        path: "/",
                    }
                )
            },
            dateFormat(date) {//格式化日期
                var date = new Date(date);
                var year = date.getFullYear();
                var month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : "" + (date.getMonth() + 1);
                var day = date.getDate() < 10 ? "0" + date.getDate() : "" + date.getDate();
                var hour = date.getHours() < 10 ? "0" + date.getHours() : "" + date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : "" + date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : "" + date.getSeconds();
                return (year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds);
            },
            toPersonalCenter(){//去个人中心
                this.$router.push({
                        path:"/PersonalCenter",
                    }
                )
            },
            toMerchantManage(){//去商户管理
                this.$router.push({
                        path:"/MerchantManage",
                    }
                )
            }
        },
        computed:{
            showLogin:{/*是否显示登录界面*/
                get(){
                    return this.thatshowLogin
                },
                set(val){
                    this.$emit("update:thatshowLogin",val)
                }
            },
            notice:{/*通知*/
                get(){
                    return this.thatnotice
                },
                set(val){
                    this.$emit("update:thatnotice",val)
                }
            }
        },
    }
</script>

<style scoped>
    /*头部导航*/
    .hc-home-header-wrapper{
        position: absolute;
        top: 0;
        z-index: 1;
        left: 0;
        right: 0;
        background: linear-gradient(180deg,rgba(0,0,0,.3),transparent);
        color: #fff;
        height: 70px;
        min-width: 1180px;
        font-weight: 700;
        display: block;
    }
    .hc-header{
        display: flex;
        -webkit-box-pack: justify;
        justify-content: space-between;
        padding-top: 18px;
    }
    .logo-area{
        width: 115px;
        height: 34px;
        margin-left: 80px;
    }
    img{
        display: block;
        width: 100%;
    }
    .slot-area{
        flex-grow: 1;
        margin-left: 20px;
    }
    .tools-area{
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        margin-right: 60px;
        height: 34px;
        line-height: 34px;
    }
    .tools-item{
        font-size: 14px;
        margin: 0 20px;
        cursor: pointer;
        white-space: nowrap;
    }
    .header-avatar{
        width: 32px;
        height: 32px;
        border-radius: 50%;
        overflow: hidden;
    }
    .hc-home-header-wrapper span{
        color: #fff;
    }
    .dropdown-notice{
        width: 250px;
        height: 280px;
        padding: 20px;
    }
    .dropdown-notice div{
        width: 100%;
    }
    .classified-navigation{
        display: flex;
        justify-content: center;
    }
    .classified-navigation-item{
        width: 80px;
        height: 20px;
        text-align: center;
    }
    .classified-contain{
        height: 260px;
    }
    .classified-contain-wrapper{
        height: 100%;
        /* auto 溢出的时候才显示滚动条 不溢出不显示滚动条 */
        overflow: auto;
    }
    .classified-contain-item
    {
        width: 95% !important;
        cursor: pointer;
        margin: 10px auto;
    }
    .notification-type{
        width: 100%;
        height: 20px;
        display: flex;
        justify-content: space-between;
    }
    .notification-describe{
        margin: 0;
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
</style>