<template>
    <section>
        <el-menu
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                style="display: flex;justify-content: flex-end">
            <h2 class="title"><i class="el-icon-s-custom"></i>个人中心</h2>
            <el-menu-item index="1">
                <el-dropdown trigger="click">
                    <div class="el-dropdown-link header-avatar">
                        <img :src="$store.state.userInfo.headImg" >
                    </div>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><a href="/PersonalCenter" >个人中心</a></el-dropdown-item>
                        <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-menu-item>
            <el-menu-item index="2"><a href="/">回到主页</a></el-menu-item>
            <el-menu-item index="3"><a href="/MerchantManage" >商户系统</a></el-menu-item>
        </el-menu>
        <el-container style="height: 650px; border-bottom: 1px solid #eee">
            <!--侧边栏-->
            <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                <el-menu router :default-openeds="['0','1']"
                         background-color="#545c64"
                         text-color="#fff"
                         active-text-color="#ffd04b"><!--默认打开菜单--->
                    <el-submenu index="0">
                        <template slot="title">
                            <i class="el-icon-menu"></i>
                            <span>个人中心</span>
                        </template>
                        <el-menu-item index="/UserInfo" :class="$route.path=='/UserInfo'?'is-active':''" >
                            <i class="el-icon-document"></i>个人信息
                        </el-menu-item>
                        <el-menu-item index="/Order/user" :class="$route.path=='/Order/user'?'is-active':''" >
                            <i class="el-icon-tickets"></i>我的订单
                        </el-menu-item>
                        <el-menu-item index="/Collection" :class="$route.path=='/Collection'?'is-active':''" >
                            <i class="el-icon-star-off"></i>我的收藏
                        </el-menu-item>
                        <el-menu-item index="/Message" :class="$route.path=='/Message'?'is-active':''" >
                            <i class="el-icon-chat-dot-round"></i>我的消息
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <!--内容区域-->
            <el-main style="background-color:#efeeec">
                <router-view></router-view>
            </el-main>
        </el-container>
    </section>
</template>

<script>
    export default {
        components: {},
        data(){
            return{
            }
        },
        created() {
            //console.log(this.$router.options.routes)//得到当前所有路由的路径
        },
        methods:{
            async loginOut(){//退出登录
                //修改用户在线状态
                this.$store.state.userInfo.isOnline='false'
                await axios.put(this.API.UpdateUser, this.$store.state.userInfo)
                //清除数据
                this.$store.state.isLogin=false
                this.$store.state.userInfo=[]
                sessionStorage.setItem('store', null)
                await this.$router.push({
                        path: "/",
                    }
                )
            },
        }
    }
</script>

<style scoped>
    a{
        color: #606266;
    }
    .header-avatar{
        width: 32px;
        height: 32px;
        border-radius: 50%;
        overflow: hidden;
    }
    img {
        display: block;
        width: 100%;
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
    .title{
        margin-right: 30%;
        color: #fff;
    }
</style>