<template>
    <section>
        <el-menu
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                style="display: flex;justify-content: flex-end"
        >
            <h2 class="title">红尘商户管理</h2>
            <el-menu-item index="1">
                <el-dropdown trigger="click">
                    <div class="el-dropdown-link header-avatar">
                        <img :src="$store.state.userInfo.headImg" >
                    </div>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><router-link to="/PersonalCenter">个人中心</router-link></el-dropdown-item>
                        <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-menu-item>
            <el-menu-item index="2"><router-link to="/">回到主页</router-link></el-menu-item>
            <el-menu-item index="3"><router-link to="/MerchantManage">商户系统</router-link></el-menu-item>
        </el-menu>
        <el-container style="height: 650px; border-bottom: 1px solid #eee">
            <!--侧边栏-->
            <el-aside width="201px" style="background-color: rgb(238, 241, 246)">
                <el-menu router :default-openeds="['0','1','2']"
                         background-color="#545c64"
                         text-color="#fff"
                         active-text-color="#ffd04b"><!--默认打开菜单--->
                    <el-submenu index="0">
                        <template slot="title">
                            <i class="el-icon-menu"></i>
                            <span>订单管理</span>
                        </template>
                        <el-menu-item index="/MerchantOrder/landlord" :class="$route.path=='/MerchantOrder/landlord'?'is-active':''">
                            <i class="el-icon-document"></i>
                            客户订单
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-menu"></i>
                            <span>房源管理</span>
                        </template>
                        <el-menu-item index="/HouseManager" :class="$route.path=='/HouseManager'?'is-active':''">
                            <i class="el-icon-s-home"></i>
                            我的房源
                        </el-menu-item>
                        <el-menu-item index="/HouseInfo/addHouse" :class="$route.path=='/HouseInfo/addHouse'?'is-active':''">
                            <i class="el-icon-s-opportunity"></i>
                            新增房源
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="2">
                        <template slot="title">
                            <i class="el-icon-menu"></i>
                            <span>资金管理</span>
                        </template>
                        <el-menu-item index="/CashBox" :class="$route.path=='/CashBox'?'is-active':''">
                            <i class="el-icon-discount"></i>钱箱
                        </el-menu-item>
                    </el-submenu>
                    <el-menu-item index="/MerchantMessage" :class="$route.path=='/MerchantMessage'?'is-active':''">
                        <i class="el-icon-chat-dot-round"></i>我的消息
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <!--内容区域-->
            <el-main style="background-color:#efeeec">
                <router-view :key="key"></router-view>
            </el-main>
        </el-container>
    </section>
</template>

<script>
    export default {
        name: "MerchantManage",
        data(){
            return{

            }
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
                sessionStorage.setItem('store', null)
                await this.$router.push({
                        path: "/",
                    }
                )
            },
        },
        computed: {
            key() {
                return this.$route.fullPath;
            },
        },
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
    .title{
        margin-right: 30%;
        color: #fff;
    }
</style>