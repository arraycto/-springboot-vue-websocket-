<template>
    <article class="g-unit-detail">
        <!--顶部导航-->
        <Header :thatnotice.sync="thisnotice" :thatshow-login.sync="thisshowLogin"></Header>
        <!--背景大图-->
        <div class="unit-image">
            <el-carousel trigger="click"  height="610px" style="margin-top: 80px">
                <el-carousel-item v-for="imgItem in houseData.img">
                    <img ref="imgHeight" :src=imgItem  width="100%" height="100%" object-fit="cover">
                </el-carousel-item>
            </el-carousel>
        </div>
        <!--导航-->
        <div class="detail-nav" :class="isFixedTop?'detail-nav-isFixed':'detail-nav'">
            <div class="unit-navbar">
                <div class="unit-navbar__container">
                    <div class="unit-navbar__container__main">
                        <ul class="unit-navbar__nav clearfix">
                            <li class="unit-navbar__nav__item"><a  :class="navIndex==0?'item--select':''" @click="navIndex=0,scroll(810)">房屋介绍</a></li>
                            <li class="unit-navbar__nav__item"><a  :class="navIndex==1?'item--select':''" @click="navIndex=1,scroll(1610)">交易规则</a></li>
                            <li class="unit-navbar__nav__item"><a  :class="navIndex==2?'item--select':''" @click="navIndex=2,scroll(2100)">入住须知</a></li>
                            <li class="unit-navbar__nav__item"><a  :class="navIndex==3?'item--select':''" @click="navIndex=3,scroll(2400)">房屋点评</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!--内容-->
        <div class="detail-container clearfix">
            <!--左边的内容-->
            <div class="detail-container__left">
                <div class="detail-container__main">
                    <!--地址-->
                    <div id="address">
                        <div class="unit-title">
                            <h1 class="unit-title__name">
                                {{houseData.title}}
                            </h1>
                            <address class="unit-title__address">
                                <span>地址：{{houseData.cityAndRegion}}</span>
                            </address>
                        </div>
                        <div class="unit-house-info">
                            <ul class="clearfix">
                                <li>
                                    <i class="icon-fangwuleixing"></i>
                                    <span>普通公寓</span>
                                </li>
                                <li>
                                    <i class="icon-huxing"></i>
                                    <span>2室1厅1卫</span>
                                </li>
                                <li>
                                    <i class="icon-chuangxing"></i>
                                    <span>{{houseData.bedNumber}}张床</span>
                                </li>
                                <li>
                                    <i class="icon-yizhujiren"></i>
                                    <span>宜住{{houseData.peopleNumber}}人</span>
                                </li>
                                <li>
                                    <i class="icon-duliweiyu"></i>
                                    <span>独立卫浴</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="unit-layout__item">
                        <h5 > 房屋描述 </h5>
                        <div class="unit-description">
                            <div v-html="houseData.describe"></div>
                        </div>
                    </div>
                    <div class="unit-layout__item">
                        <h5>设施服务</h5>
                        <div class="unit-facilities">
                            <ul class="simple-version">
                                <li>
                                    <h2 class="facilities-title">基础设施</h2>
                                    <p><i class="icon-wifi"></i>无线网络</p>
                                    <p><i class="icon-airconditioner"></i>空调（冷暖)</p>
                                    <p><i class="icon-tv"></i>电视</p>
                                    <p><i class="icon-washingmachine"></i>洗衣机</p>
                                    <p><i class="icon-icebox"></i>冰箱 </p>
                                </li>
                                <li>
                                    <h2 class="facilities-title">卫浴设施</h2>
                                    <p><i class="icon-hotwater"></i>可洗热水澡</p>
                                    <p><i class="icon-hairandbodylotion"></i>洗浴用品</p>
                                    <p><i class="icon-towel"></i>毛巾</p>
                                    <p><i class="icon-toothbrush"></i>牙具</p>
                                    <p><i class="icon-independentbathroom"></i>独立卫浴</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!--地图-->
                    <div class="unit-layout__item" id="location">
                        <h5> 房屋位置 </h5>
                        <div class="unit-local">
                            <div id="map" style="width:100%;height:400px;"></div>
                        </div>
                    </div>
                    <!--交易规则-->
                    <div class="unit-layout__item" id="rules">
                        <h5>交易规则</h5>
                        <div class="unit-trade__all">
                            <ul class="trade-lists">
                                <li>
                                    <span>付款方式：</span>
                                    <div class="rules__intro">全额预付房费</div>
                                </li>
                                <li>
                                    <span>无须确认：</span>
                                    <div class="rules__intro">下单后即有房，无需等待房东确认</div>
                                </li>
                                <li>
                                    <span>入住押金：</span>
                                    <div class="rules__intro">免收</div>
                                </li>
                                <li>
                                    <span>清洁费：</span>
                                    <div class="rules__intro">一客一扫，在线收取清洁费25元，已包含在当前价格中</div>
                                </li>
                            </ul>
                            <el-steps :active="1" style="width: 90%">
                                <el-step title="预定成功" icon="el-icon-success" description="支付费用"></el-step>
                                <el-step title="入住当天" icon="el-icon-s-home" description="取消订单收取房费的100%"></el-step>
                                <el-step title="离店当天" icon="el-icon-house" description="提前离店收取剩余房费的100%"></el-step>
                            </el-steps>
                        </div>
                    </div>
                    <!--入住须知-->
                    <div class="unit-layout__item" id="checkin" >
                        <h5> 入住须知 </h5>
                        <div class="unit-checkin">
                            <ul class="services-lists">
                                <li><span>入住时间：</span><div>15:00后入住</div></li>
                                <li><span>退房时间：</span><div>12:00前退房</div></li>
                                <li><span>自助入住：</span><div>通过智能门锁自助入住</div></li>
                                <li><span>提供发票：</span><div>开具发票请与房东协商</div></li>
                                <li><span>卫生打扫：</span><div>1客1扫</div></li>
                                <li><span>卫生打扫：</span><div>1客1扫</div></li>
                                <li><span>被单更换：</span><div>1客1换</div></li>
                                <li><span>洗漱更换：</span><div>1客1换</div></li>
                                <li><span>毛巾更换：</span><div>1客1换</div></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--评论-->
                <div class="comment">
                    <div class="unit-comment">
                        <div class="unit-comment__nav clearfix">
                            <span class="unit-comment__nav__this nav--choose">本房屋点评（{{houseData.commentsNumber}}条）</span>
                            <span class="unit-comment__nav__other">房东所有点评</span>
                        </div>
                        <div class="one-px__height"></div>
                        <div class="unit-comment__container">
                            <div class="unit-comment__container__rate clearfix">
                                <span>综合评分：</span>
                                <el-rate
                                        v-model="houseData.score"
                                        disabled
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}"
                                style="width: 150px; float: left">
                                </el-rate>
                                <span>分</span>
                            </div>
                            <!--评论列表-->
                            <ul class="unit-comment__container__comments">
                                <li class="unit-comment__container__item" v-for="item in comments">
                                    <div class="unit-comment__container__item__top">
                                        <img class="unit-comment__container__item__top__img swiper-lazy"
                                             :src="item.headImg" alt="">
                                        <div>
                                            <p><span>{{item.userId}}</span></p>
                                            <p>{{dateFormat(item.commentTime)}}入住</p>
                                        </div>
                                    </div>
                                    <p style="text-align: left">{{item.describe}}</p>
                                    <div class="one-px__border"></div>
                                </li>
                            </ul>
                            <!--页码条-->
                            <div class="unit-comment__container__page clearfix">
                                <el-pagination
                                        background
                                        layout="prev, pager, next"
                                        :total="total"
                                        :page-size="pageSize"
                                        :current-page="currentPage"
                                        @current-change="pageChange"
                                         style="float: right">
                                </el-pagination>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--右边的浮动，预定功能-->
            <div id="rightside" :class="isFixedTop?'g-unitList-selected-isFixed':'detail-container__right'">
                <div class="unit-price">
                    <div class="unit-price__price">
                        <span class="unit-price__price--final"><span>￥</span><span class="price__count">{{houseData.housePrice}}</span>每晚</span>
                    </div>
                    <div class="unit-price__calandar">
                        <el-date-picker format="yyyy-MM-dd"
                                        value-format="yyyy-MM-dd"
                                        v-model="chooseDate"
                                        type="daterange"
                                        range-separator="-"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                                        :picker-options="pickerOptions"
                                        style="width: 100%"
                                        @change="totalPrice=DateDiff(chooseDate[0],chooseDate[1])*houseData.housePrice"
                        >
                        </el-date-picker>
                    </div>
                    <button class="common__button button--abled" @click="toOrderInfo">
                        <span class="common__button_price">立刻预订（{{chooseDate==''?'0':DateDiff(chooseDate[0],chooseDate[1])}}晚¥
                            {{totalPrice}}）</span>
                    </button>
                </div>
                <div class="unit-contact">
                    <div class="unit-contact__land">
                        <img :src="houseData.headImg"
                             class="unit-contact__land__photo" alt="">
                        <div class="unit-contact__land__main">
                            <a>房东{{houseData.username}}</a>
                            <p>
                                <span>商户经营</span><span> · 营业执照已验证</span>
                            </p>
                        </div>
                    </div>
                    <div class="unit-contact__contact">
                        <div class="unit-contact__contact__phone">
                            <el-tooltip :content="'请拔打电话'+houseData.username" placement="bottom" effect="light">
                                <el-button style="background-color: transparent;border: 0"><span>拨打电话</span></el-button>
                            </el-tooltip>
                        </div>
                        <div>
                            <a class="unit-contact__contact__consult chat-btn" @click="chat">
                                <span><i class="el-icon-chat-dot-round"></i>在线咨询</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Chat :is-show.sync="showChat" :username.sync="houseData.username" v-if="showChat"></Chat>
    </article>
</template>

<script>
    import Header from "../../components/Header";
    import Chat from "../../components/Chat";
    export default {
        components: {Chat, Header},
        props:['showLogin','chooseHouse'],
        data(){
            return{
                thisnotice:'',/*通知*/
                thisshowLogin:this.showLogin,/*是否显示登录*/
                houseData:'',/*房屋数据*/
                navIndex:0,/*当前导航索引*/
                total:0,/*评论数量*/
                pageSize:2,/*每页显示的内容个数*/
                currentPage:1,/*当前页码*/
                isFixedTop:false,/*是否固定在顶部*/
                totalPrice:0,/*总价*/
                showChat:false,/*显示聊天框*/
                comments:[],/*评论*/
                pickerOptions: {/*限制只能选择今天及以后的时间*/
                },
                chooseDate:'',/*选择日期*/
                dateSet:[],/*已订日期*/
            }
        },
        beforeCreate() {

        },
        created() {
            if(JSON.parse(sessionStorage.getItem('store'))!=null){
                this.getUserNotice(this.$store.state.userInfo.username)//得到用户通知
            }

            //得到房屋详情
            this.getHouseDetail()
            //得到已存在的订单
            this.getOrder()
            //限制选定的日期
            const _this=this;
            this.pickerOptions={
                disabledDate(time) {
                    if(time.getTime() < Date.now()  || time.getTime() <(new Date(_this.houseData.chooseDate[0])- 8.64e7) ||
                        time.getTime() > new Date(_this.houseData.chooseDate[1])){
                        return  true;
                    }
                    for(let i=0;i<_this.dateSet.length;i++){
                        if(time.getTime()>(new Date(_this.dateSet[i].timeSlot[0])-8.64e7)&&time.getTime()<new Date(_this.dateSet[i].timeSlot[1])){
                            return true
                        }
                    }
                }
            }
            //得到评论
            this.getComments()
        },
        mounted() {
            // 百度地图API功能
            let map = new BMap.Map("map");// 创建Map实例
            map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
            let opts = {
                width : 150,     // 信息窗口宽度
                title:"房屋位置"
            }
            let infoWindow = new BMap.InfoWindow(this.houseData.cityAndRegion, opts); // 创建信息窗口对象
            // 创建地址解析器实例
            let myGeo = new BMap.Geocoder();
            // 将地址解析结果显示在地图上，并调整地图视野
            myGeo.getPoint(this.houseData.cityAndRegion, function(point){
                    if (point) {
                        map.centerAndZoom(point, 13);
                        map.addOverlay(new BMap.Marker(point));
                        map.openInfoWindow(infoWindow, map.getCenter());// 打开信息窗口
                    }
                },
                this.houseData.cityAndRegion.split(",")[0]);
            // 滚动条的获取
            window.addEventListener('scroll', this.handleScroll, true)
        },
        methods:{
            /*下拉滚动条时将筛选条件固定在顶部*/
            handleScroll(){
                if(window.pageYOffset>676){
                    this.isFixedTop=true
                }
                if(window.pageYOffset<676){
                    this.isFixedTop=false
                }
                if(window.pageYOffset>800&&window.pageYOffset<1600){
                    this.navIndex=0
                }
                else if(window.pageYOffset>1600&&window.pageYOffset<2100){
                    this.navIndex=1
                }
                else if(window.pageYOffset>2100&&window.pageYOffset<2400){
                    this.navIndex=2
                }
                else if(window.pageYOffset>=2400){
                    this.navIndex=3
                }
               /*navIndex=3*/
            },
            /*计算2个日期相隔多少天*/
            DateDiff(sDate1, sDate2){    //sDate1和sDate2是2006-12-18格式
                var  aDate,  oDate1,  oDate2,  iDays
                aDate   =  sDate1.split("-")
                oDate1 = new Date(aDate[0] , aDate[1] ,aDate[2])    //转换为12-18-2006格式
                aDate   =  sDate2.split("-")
                oDate2 = new Date(aDate[0] , aDate[1] , aDate[2])
                iDays   =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
                return  iDays
            },
            async getUserNotice(username){//得到用户通知
                const _this=this
                await axios.get(this.API.GetNotice+username).then(function (resp) {
                    _this.thisnotice=resp.data
                })
            },
            getHouseDetail(){//得到房屋详情
                this.houseData=JSON.parse(this.chooseHouse)
                this.houseData.img=this.houseData.img.split(",")
                this.houseData.chooseDate=this.houseData.chooseDate.split(",")
                this.houseData.cityAndRegion=this.houseData.cityAndRegion.toString().replaceAll(",","")
            },
            async getOrder(){//得到房屋已存在的订单
                const _this=this
                await axios.get(this.API.GetOrderByTitle+this.houseData.username+"/"+this.houseData.title).then(resp=>{
                    _this.dateSet=resp.data
                })
                for(let i=0;i<_this.dateSet.length;i++){
                    _this.dateSet[i].timeSlot=_this.dateSet[i].timeSlot.split(",")
                }
            },
            getComments(){/*得到房屋评论*/
                const _this=this
                axios.get(this.API.GetCommentTotal+this.houseData.username+'/'
                    +this.houseData.title).then(function (resp) {
                    _this.total=resp.data
                })
                axios.get(this.API.GetCommentList+this.houseData.username+'/'
                    +this.houseData.title+'/'+this.currentPage+'/'+this.pageSize).then(function (resp) {
                    _this.comments=resp.data
                })

            },
            pageChange(Page){/*换页,得到评论*/
                this.currentPage=Page
                this.getComments()
            },
            chat(){/*打开聊天*/
                if(this.$store.state.userInfo.username==this.houseData.username){
                    this.$message({
                        message: '不能和自己聊天',
                        type: 'warning'
                    });
                    return false;
                }
                this.showChat=true
            },
            dateFormat(date) {//格式化日期
                var date = new Date(date);
                var year = date.getFullYear();
                var month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : "" + (date.getMonth() + 1);
                var day = date.getDate() < 10 ? "0" + date.getDate() : "" + date.getDate();
                var hour = date.getHours() < 10 ? "0" + date.getHours() : "" + date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : "" + date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : "" + date.getSeconds();
                return (year + "-" + month + "-" + day);
            },
            scroll(height){//滚动屏幕
                window.scrollTo(0,height);//滚动条滑动到指定坐标X,Y
            },
            toOrderInfo(){//去订单页
                if(this.chooseDate==''||this.totalPrice==0){
                    this.$message({
                        message: '请选择日期',
                        type: 'error'
                    });
                    return false;
                }
                this.houseData.chooseDate=this.chooseDate
                this.$router.push({
                        path:"/OrderInfo/",
                        query:{
                            houseData:JSON.stringify(this.houseData),
                            totalPrice:this.totalPrice,
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>
    .g-unit-detail{
        width: 100%;
        min-width: 1280px;
        position: relative;
        background-color: #f5f5f5;
        --main-price-token: dHVqaWFjc3Mw;
        line-height: 1.4;
        font-size: 14px;
    }
    /*背景大图*/
    .unit-image{
        height: 690px;
        width: 100%;
        min-width: 1280px;
        position: relative;
        color: #fff;
        background-position: 50%;
        background-size: cover;
        background-repeat: no-repeat;
        display:inline-block;
        background-color: #f5f5f5;
    }
    .unit-image p {
        display: block;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
        padding-top: 50px;
        text-align: left;
        margin-left: 20px;
    }
    /*导航*/
    .detail-nav{
        width: 100%;
        background: #fff;
        position: relative;
        z-index: 1;
        top: 0px;
    }
    /*固定导航*/
    .detail-nav-isFixed{
        width: 100%;
        background: #fff;
        position: fixed;
        z-index: 1;
    }
    .unit-navbar {
        position: relative;
        z-index: 1;
    }
    .unit-navbar .unit-navbar__container{
        width: 100%;
        min-width: 1280px;
        background: #fff;
        position: static;
        -webkit-box-shadow: 0 1px 3px 0 rgba(0,0,0,.05);
        box-shadow: 0 1px 3px 0 rgba(0,0,0,.05);
    }
    .unit-navbar__container__main{
        width: 1190px;
        margin: 0 auto;
        background: #fff;
        height: 56px;
        color: #333;
        cursor: pointer;
    }
    .unit-navbar__container__main .unit-navbar__nav{
        float: left;
        margin: 0;
        padding: 0;
    }
    .unit-navbar__container__main .unit-navbar__nav .unit-navbar__nav__item{
        float: left;
        margin-right: 38px;
        height: 56px;
        line-height: 56px;
    }
    .unit-navbar__container__main .unit-navbar__nav .unit-navbar__nav__item .item--select{
        font-weight: 600;
        font-family: PingFangSC-Regular;
        color: #fd8238;
    }
    /*内容*/
    .detail-container{
        position: relative;
        width: 1190px;
        margin: 0 auto;
        display: flex;
    }
    .detail-container .detail-container__left{
        width: 810px;
        margin-top: 30px;
        margin-right: 20px;
        float: left;
    }
    .detail-container__left .detail-container__main {
        margin-bottom: 20px;
        background: #fff;
        padding: 40px 30px 30px;
    }
    .detail-container__main #address {
        margin-top: 0;
    }
    /*标题*/
    .unit-title .unit-title__name {
        font-size: 28px;
        color: #333;
        margin-bottom: 12px;
    }
    .unit-title .unit-title__address {
        color: #9b9b9b;
        line-height: 20px;
        margin-bottom: 20px;
        display: block;
        margin: 0;
        padding: 0;
    }
    address {
        font-style: normal;
        font-weight: 400;
    }
    /*房屋信息*/
    .unit-house-info {
        margin-top: 50px;
        background: transparent;
        display: inline-block;
    }
    .unit-house-info ul{
        display: inline-block;
        margin: 0;
        padding: 0;
    }
    .unit-house-info ul li {
        position: relative;
        float: left;
        width: 76px;
        text-align: center;
    }
    /*精灵图片*/
    .unit-house-info ul li i{
        display: block;
        text-align: center;
        width: 28px;
        height: 28px;
        margin: 0 auto 15px;
    }
    .icon-fangwuleixing, .icon-guanjinglutai {
        background-image: url("../../assets/img/upload/static/houseInfo-icon.1308d999.png");
        width: 28px;
        height: 28px;
    }
    .icon-fangwuleixing{/*控制精灵图片的位置*/
        background-position: -64px 0;
    }
    .icon-huxing, .icon-jiangjing{
        background-image: url("../../assets/img/upload/static/houseInfo-icon.1308d999.png");
        width: 28px;
        height: 28px;
    }
    .icon-huxing {
        background-position: 0 0;
    }
    .icon-chuangxing{
        background-image: url("../../assets/img/upload/static/houseInfo-icon.1308d999.png");
        width: 28px;
        height: 28px;
    }
    .icon-chuangxing{
        background-position: -64px -64px;
    }
    .icon-yangguangfang, .icon-yizhujiren {
        background-image: url("../../assets/img/upload/static/houseInfo-icon.1308d999.png");
        width: 28px;
        height: 28px;
    }
    .icon-yizhujiren{
        background-position: -96px -96px;
    }
    .icon-dulidanjian, .icon-duliweiyu{
        background-image: url("../../assets/img/upload/static/houseInfo-icon.1308d999.png");
        width: 28px;
        height: 28px;
    }
    .icon-duliweiyu{
        background-position: -32px -32px;
    }
    .unit-layout__item {
        margin-top: 50px;
    }
    /*房屋描述*/
    .unit-layout__item h5{
        font-size: 20px;
        color: #333;
        margin-bottom: 25px;
        font-weight: 500;
    }
    .unit-description {
        white-space: pre-line;
        font-size: 14px;
        color: #333;
        line-height: 26px;
        letter-spacing: 0;
        text-overflow: ellipsis;
        display: -webkit-box;
        overflow: hidden;
        -webkit-box-orient: vertical;
        text-align: left;
    }
    .unit-facilities ul li{
        margin-bottom: 10px;
    }
    .unit-facilities ul li .facilities-title {
        font-size: 14px;
        color: #333;
        margin-bottom: 16px;
        font-weight: 400;
    }
    .unit-facilities ul li p {
        width: 20%;
        display: inline-block;
        font-size: 14px;
        color: #666;
        line-height: 20px;
        margin-bottom: 15px;
    }
    .unit-facilities ul li i {
        width: 20px;
        height: 20px;
        display: inline-block;
        background-repeat: no-repeat;
        margin-right: 13px;
        vertical-align: middle;
    }
    /*精灵图片*/
    /*基础设施*/
    .icon-wifi, .icon-window {
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-wifi{
        background-position: -192px -216px;
    }
    .icon-airconditioner, .icon-atm{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-airconditioner{
        background-position: 0 -24px;
    }
    .icon-towel, .icon-tv{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-tv{
        background-position: -216px -192px;
    }
    .icon-washer, .icon-washingmachine{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-washingmachine{
        background-position: -96px -216px;
    }
    .icon-humidifier, .icon-icebox{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-icebox{
        background-position: -144px -120px;
    }
    /*卫浴设施*/
    .icon-heating, .icon-hotwater{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-hotwater{
        background-position: -144px -72px;
    }
    .icon-hairandbodylotion, .icon-hairdryer{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-hairandbodylotion{
        background-position: -144px 0;
    }
    .icon-towel{
        background-position: -216px -168px;
    }
    .icon-tissues, .icon-toothbrush{
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-toothbrush{
        background-position: -216px -144px;
    }
    .icon-independentbathroom, .icon-inductioncooking {
        background-image: url("../../assets/img/upload/static/houseService-icon.ab820730.png");
        width: 20px;
        height: 20px;
    }
    .icon-independentbathroom{
        background-position: 0 -144px;
    }
    /*地图*/

    /*交易规则*/
    .unit-trade__all .trade-lists{
        position: relative;
    }
    .unit-trade__all .trade-lists>li{
        letter-spacing: 0;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        position: relative;
        margin-bottom: 14px;
    }
    /*入住须知*/
    .services-lists{
        position: relative;
    }
    .services-lists>li {
        margin: 0;
        padding: 0;
        letter-spacing: 0;
        overflow: auto;
        position: relative;
        margin-bottom: 14px;
    }
    .services-lists>li>span {
        color: #333;
        float: left;
    }
    .services-lists>li>div {
        color: #666;
        overflow: hidden;
        position: relative;
    }
    /*评论*/
    .unit-comment {
        padding: 0 30px;
        background: #fff;
        -webkit-box-shadow: 0 1px 3px 0 rgba(0,0,0,.05);
        box-shadow: 0 1px 3px 0 rgba(0,0,0,.05);
    }
    .unit-comment .unit-comment__nav{
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        height: 60px;
        padding-top: 22px;
        position: relative;
        cursor: pointer;
    }
    .unit-comment .unit-comment__nav>span.nav--choose{
        color: #fd8238;
    }
    .unit-comment .unit-comment__nav>span.unit-comment__nav__this{
        margin-right: 40px;
    }
    .unit-comment .unit-comment__nav>span{
        color: #666;
    }
    /*分割线*/
    .unit-comment .one-px__height{
        width: 810px;
        height: 1px;
        background: #eee;
        margin-left: -30px;
    }
    /*评分*/
    .unit-comment .unit-comment__container {
        margin-top: 30px;
    }
    .unit-comment .unit-comment__container .unit-comment__container__rate{
        margin-bottom: 15px;
        line-height: 24px;
        position: relative;
        color: #666;
        display: flex;
        justify-content: flex-start;
    }
    .unit-comment__container__rate>span{
        font-size: 18px;
    }
    .unit-comment__container__rate>span:last-child {
        font-size: 14px;
        height: 20px;
        line-height: 20px;
        margin-left: 4px;
        bottom: 1px;
    }
    /*评论列表*/
    .unit-comment__container__comments .unit-comment__container__item:first-child {
        padding-top: 35px;
    }
    .unit-comment__container__comments .unit-comment__container__item {
        padding: 30px 0;
    }
    .unit-comment__container__comments .unit-comment__container__item .unit-comment__container__item__top {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .unit-comment__container__item__top__img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 15px;
        margin-top: 1.5px;
        border: 0;
        vertical-align: middle;
    }
    comment__container__item__top>div {
        color: #999;
        font-size: 12px;
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
    }
    comment__container__item__top>div p:first-child {
        font-size: 16px;
        color: #333;
        margin-bottom: 6px;
        height: 21px;
        line-height: 21px;
    }
    comment__container__item__top>div p:last-child {
        height: 16px;
        line-height: 16px;
    }
    .unit-comment__container__item>p{
        margin-top: 16px;
        padding-left: 55px;
        font-size: 14px;
        color: #333;
        letter-spacing: 0;
        line-height: 26px;
    }
    .unit-comment__container__item .one-px__border{
        margin: 30px 0 0 55px;
        border-bottom: 1px dotted #ddd;
    }
    /*页码条*/
    .unit-comment .unit-comment__container .unit-comment__container__page{
        padding-bottom: 42px;
        overflow: hidden;
    }
    .unit-comment__container__page >>> .el-pagination.is-background .el-pager li:not(.disabled).active {
        background-color: #FD7A3A;
        color: #fff;
    }
    /*预定*/
    .g-unit-detail .detail-container .detail-container__right {
        width: 360px;
        position: relative;
        z-index: 1;
        top: 28px;
        left: 0px;
    }
    /*固定在顶部*/
    .g-unitList-selected-isFixed{
        width: 360px;
        position: fixed;
        z-index: 1;
        top: 60px;
        margin-left: 830px;
    }
/*    .detail-container__right>div:first-child {
        width: 100%;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        padding: 30px 20px;
        background: #fff;
        position: relative;
    }*/
    .unit-price{
        border: 1px solid #f2f2f2;
        border-bottom: 0;
        width: 100%;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        padding: 30px 20px;
        background: #fff;
        position: relative;
    }
    .unit-price .unit-price__price {
        margin-bottom: 20px;
        display: flex;
        justify-content: flex-start;
    }
    .unit-price__price .unit-price__price--final {
        font-size: 14px;
        color: #333;
        line-height: 12px;
        letter-spacing: 0;
    }
    .unit-price__price--final>span{
        font-weight: 500;
    }
     .unit-price__price--final .price__count{
        font-family: tjFont;
        font-size: 36px;
        text-align: right;
        line-height: 34px;
        margin-right: 5px;
        font-weight: 600;
    }
    /*日期*/
    .unit-price__calandar{
        margin-bottom: 20px;
        position: relative;
        text-align: left;
        color:#FD7A3A ;
    }
    .unit-price .common__button{
        -webkit-appearance: none;
        text-align: center;
        white-space: nowrap;
        width: 100%;
        height: 50px;
        line-height: 50px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        outline: none;
        border: 0;
        font-size: 16px;
        margin: 0;
        color: #fff;
        background-image: linear-gradient(-221deg,#ff721e,#ff9b3e);
    }
    .common__button_price{
        font-size: 18px;
        line-height: 18px;
        display: inline-block;
        font-family: PingFangSC-Medium;
        letter-spacing: 0;
    }
    /*商家信息*/
    .unit-contact{
        position: relative;
        width: 100%;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        background: #fff;
        border: 1px solid #f2f2f2;
    }
    .unit-contact .unit-contact__land{
        padding: 30px 20px 20px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        cursor: pointer;
    }
    .unit-contact .unit-contact__land .unit-contact__land__photo{
        width: 60px;
        height: 60px;
        border-radius: 50%;
        overflow: hidden;
        margin-right: 20px;
        border: 0;
        vertical-align: middle;
    }
    .unit-contact .unit-contact__land .unit-contact__land__main {
        overflow: hidden;
        height: 45px;
        margin-top: 7px;
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        text-overflow: ellipsis;
    }
    .unit-contact .unit-contact__land .unit-contact__land__main a{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        letter-spacing: 0;
        font-size: 16px;
        line-height: 24px;
        font-weight: 500;
        color: #333;
        text-decoration: none;
        cursor: pointer;
    }
    .unit-contact .unit-contact__land .unit-contact__land__main p{
        padding-top: 3px;
        font-size: 12px;
        color: #666;
        margin: 0;
        padding: 0;
    }
    .unit-contact .unit-contact__land .unit-contact__land__main p span{
        letter-spacing: 0;
        line-height: 18px;
        position: relative;
    }
    /*拨打电话/在线咨询*/
    .unit-contact .unit-contact__contact{
        height: 60px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        cursor: pointer;
        background: #fbfbfb;
        border-top: 1px solid #f2f2f2;
    }
    .unit-contact .unit-contact__contact .unit-contact__contact__phone {
        color: #666;
        border-right: 1px solid #f2f2f2;
    }
    .unit-contact .unit-contact__contact>div{
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        text-align: center;
        font-size: 14px;
        letter-spacing: 0;
        line-height: 60px;
    }
    .unit-contact .unit-contact__contact .unit-contact__contact__consult{
        color: #3e97e2;
        text-decoration: none;
        cursor: pointer;
    }

</style>