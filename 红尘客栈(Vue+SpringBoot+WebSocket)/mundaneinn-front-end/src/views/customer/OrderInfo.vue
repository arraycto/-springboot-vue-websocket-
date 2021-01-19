<template>
    <article>
        <el-menu
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                style="display: flex;justify-content: flex-end">
            <el-menu-item index="1"><a href="/" >主页</a></el-menu-item>
            <el-menu-item index="2"><a @click="loginOut">退出</a></el-menu-item>
            <el-menu-item index="3"><a href="/MerchantManage" target="_blank">商户系统</a></el-menu-item>
        </el-menu>
        <div class="order-fill-page">
            <div class="order-main">
                <div class="orderinfo-cont">
                    <div class="col-header">
                        <span class="col-title">预订信息</span>
                    </div>
                    <div class="orderinfo-main">
                        <el-form  label-width="80px">
                            <el-form-item label="入住时段" style="margin-bottom: 0">
                                <span>{{currentHouseData.chooseDate[0]}}至{{currentHouseData.chooseDate[1]}}</span>
                            </el-form-item>
                            <el-form-item label="支付金额" style="margin-bottom: 0">
                                <span>{{totalPrice}}</span>
                            </el-form-item>
                            <el-form-item label="民宿名称" style="margin-bottom: 0">
                                <span>{{currentHouseData.title}}</span>
                            </el-form-item>
                            <el-form-item label="商家电话" style="margin-bottom: 0">
                                <span>{{currentHouseData.username}}</span>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="col-header">
                        <span class="col-title">入住人信息</span>
                    </div>
                    <div class="orderinfo-main">
                        <el-form  label-width="80px">
                            <el-form-item label="姓名" style="margin-bottom: 0">
                                <span>{{$store.state.userInfo.alias}}</span>
                            </el-form-item>
                            <el-form-item label="性别" style="margin-bottom: 0">
                                <span>{{$store.state.userInfo.sex=='0'?'男':'女'}}</span>
                            </el-form-item>
                            <el-form-item label="身份证号" style="margin-bottom: 0">
                                <span>{{$store.state.userInfo.idNumber}}</span>
                            </el-form-item>
                            <el-form-item label="联系电话" style="margin-bottom: 0">
                                <span>{{$store.state.userInfo.username}}</span>
                            </el-form-item>
                        </el-form>
                    </div>
                    <form action="http://localhost:8081/pay" method="post" v-show="false">
                        订单名称：<input type="text" name="WIDsubject" v-model="totalPrice" required><br/>
                        付款金额：<input type="text" name="WIDtotal_amount" v-model="totalPrice" required><br/>
                        WIDbody：<input type="text" name="WIDbody" v-model="currentHouseData.title"><br/>
                        <input type="submit" value="下单">
                    </form>
                    <div class="submit-order-btn" @click="placeOrder">提交订单</div>
                </div>
            </div>
        </div>
    </article>
</template>

<script>
    export default {
        props:["houseData","totalPrice"],
        data(){
            return{
                currentHouseData:[],/*当前房屋信息*/
            }
        },
        created() {
            this.currentHouseData=JSON.parse(this.houseData)
        },
        methods:{
            loginOut(){//退出登录
                this.$store.state.isLogin=false
                this.$store.state.userInfo=[]
                sessionStorage.setItem('store', null)
                this.$router.push({
                        path:"/",
                    }
                )
            },
            placeOrder(){/*提交订单*/
                let order={"id":null,"timeSlot":this.currentHouseData.chooseDate.toString(),"money":this.totalPrice,"title":this.currentHouseData.title,
                "username":this.currentHouseData.username,"customerId":this.$store.state.userInfo.username,"state":1}
                const _this=this
                axios.post(this.API.AddOrder,order).then(function (resp) {
                    if(resp.data=="error"){
                        _this.$notify.error({
                            title: '错误',
                            message: '该房间已经被他人抢先预定!'
                        });
                    }
                    if(resp.data=="success"){
                        _this.$notify({
                            title: '成功',
                            message: '您已成功预定!3秒后去支付',
                            type: 'success'
                        });
                        setTimeout(() => {
/*                            _this.$router.push({
                                    path:"/"
                                }
                            )*/
                            document.forms[2].submit()
                        }, 3000);
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .order-fill-page {
        margin: 0 auto;
        width: 1190px;
    }
    .order-main {
        margin: 15px 0;
        padding: 40px 30px;
        width: 800px;
        box-shadow: 0 0 3px 0 #ccc;
        box-sizing: inherit;
    }
    /*预定信息*/
    .orderinfo-cont {
        margin-bottom: 36px;
    }
    .col-header {
        width: 800px;
        padding: 8px 0;
        border-bottom: 2px solid #ddd;
        display: flex;
        justify-content: flex-start;
    }
    .col-title {
        padding-left: 8px;
        border-left: 3px solid #fd8238;
        font-size: 18px;
        font-weight: 700;
        color: #333;
    }
    .orderinfo-main {
        margin-top: 8px;
        font-size: 14px;
        color: #333;
    }
    .submit-order-btn{
        margin: 18px auto 0;
        width: 124px;
        height: 36px;
        line-height: 36px;
        font-size: 16px;
        border-radius: 2px;
        cursor: pointer;
        background-color: #fd8238;
        color: #fff;
        margin-top: 56px;
    }
</style>