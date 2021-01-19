<template>
    <el-container>
        <el-header class="header-wrapper">
            <!--登录-->
            <div class="login-dialog">
                <el-dialog :visible.sync="showLogin" width="30%">
                    <div class="m-login-wrap">
                        <div class="login-tabs">
                            <a :class="{'isActive ':toggleLoginBody}" @click="toggleLoginBody=true,phoneNumber='',passWord='',showError=false">短信快捷登录/注册</a>
                            <a :class="{'isActive':!toggleLoginBody}" @click="toggleLoginBody=false,phoneNumber='',passWord='',showError=false">普通登录</a>
                        </div>
                        <div class="login-body" v-show="toggleLoginBody">
                            <div class="control-group">
                                <el-input placeholder="请输入手机号" v-model="phoneNumber">
                                    <template slot="prepend">+86</template>
                                </el-input>
                            </div>
                            <div class="controls">
                                <el-input placeholder="动态验证码" v-model="passWord" ></el-input>
                                <el-button type="primary" @click="sendCode">获取验证码</el-button>
                            </div>
                            <el-alert
                                    :title="errorInfo"
                                    type="error" v-if="showError" style="margin-bottom: 20px" :closable="false">
                            </el-alert>
                            <el-button type="warning" class="login-btn" @click="loginOrRegister(false)">登录/注册</el-button>
                        </div>
                        <div class="login-body" v-show="!toggleLoginBody">
                            <div class="control-group">
                                <el-input placeholder="请输入手机号" v-model="phoneNumber">
                                    <template slot="prepend">+86</template>
                                </el-input>
                            </div>
                            <div class="controls">
                                <el-input placeholder="输入密码" v-model="passWord" show-password ></el-input>
                            </div>
                            <el-alert
                                    :title="errorInfo"
                                    type="error" v-if="showError" style="margin-bottom: 20px" :closable="false">
                            </el-alert>
                            <el-button type="warning" class="login-btn" @click="loginOrRegister(true)">登录</el-button>
                        </div>
                    </div>
                </el-dialog>
            </div>
            <!--背景-->
            <div class="slider-wrapper">
                <div class="bg-wrapper">
                    <div class="bg-wrapper-item">
                        <div class="wrapper-item-img"></div>
                    </div>
                </div>
            </div>
            <!--顶部导航-->
            <Header :thatnotice.sync="notice" :thatshow-login.sync="showLogin"></Header>
            <!--大搜索框-->
            <div class="hc-home-search-wrapper animate__animated " :class="!showLogin?'animate__bounceIn':''">
                <section class="city-search-wrapper">
                    <div class="city-wrap"  @click="toggleClass()">
                        {{currentCityName}}<span  :class="{'el-icon-caret-bottom':toggle,'el-icon-caret-top':!toggle}"></span>
                    </div>
                    <section class="search-city-panel-wrap" v-show="!toggle">
                        <div class="city-search-input">
                            <el-autocomplete
                                    class="inline-input"
                                    :fetch-suggestions="querySuggestedCities"
                                    placeholder="请输入城市名称"
                                    :trigger-on-focus="false"
                                    v-model="queryCityName"
                                    style="width: 100%"
                                    prefix-icon="el-icon-search"
                                    @select="citySelect"></el-autocomplete>
                        </div>
                        <section class="hc-search-group-tabs">
                            <ul class="search-group-tab-list" >
                                <li class="search-group-tab-list-item active">热门城市</li>
                                <li class="search-group-tab-list-item">按字母排序</li>
                            </ul>
                            <ul class="group-list"  v-loading="loadingCity">
                                <li class="search-group-tab-list-item group-list-item" v-for="(item,key) in cities"
                                @click="currentCityName=item.value,toggle=true,getRegionAsync(item.value,'hotsearch')">{{item.value}}</li>
                            </ul>
                        </section>
                    </section>
                </section>
                <div class="divider"></div>
                <section class="keyword-search-wrapper">
                    <section  @click="toggleKeywordClass()">
                        <el-input class="keyword-search-input" v-model="currentRegion" placeholder="位置位置关键字" clearable></el-input>
                    </section>
                    <section class="hc-search-keyword-panel" v-if="keywordToggle">
                        <section class="hc-search-group-tabs">
                            <ul class="search-group-tab-list">
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 0}" @click="getRegionAsync(currentCityName,'hotsearch'),currentRegionTypeClass(0)">热门搜索</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 1}" @click="getRegionAsync(currentCityName,'scenicspot'),currentRegionTypeClass(1)">景点</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 2}" @click="getRegionAsync(currentCityName,'tradingarea'),currentRegionTypeClass(2)">商圈</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 3}" @click="getRegionAsync(currentCityName,'metroline'),currentRegionTypeClass(3)">地铁站</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 4}" @click="getRegionAsync(currentCityName,'housetype'),currentRegionTypeClass(4)">房型</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 5}" @click="getRegionAsync(currentCityName,'district'),currentRegionTypeClass(5)">行政区</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 6}" @click="getRegionAsync(currentCityName,'airportorstation'),currentRegionTypeClass(6)">机场车站</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 7}" @click="getRegionAsync(currentCityName,'universities'),currentRegionTypeClass(7)">高校</li>
                                <li class="search-group-tab-list-item" :class="{ active:currentRegionTypeIndex == 8}" @click="getRegionAsync(currentCityName,'hospital',currentRegionTypeClass(8))">医院</li>
                            </ul>
                            <ul class="group-list">
                                <li class="group-item-list-item" v-for="(item,key) in region" @click="currentRegion=item.name,keywordToggle=false"><a class="group-item-name">{{item.name}}</a></li>
                            </ul>
                        </section>
                    </section>
                </section>
                <div class="divider"></div>
                <section class="date-search-wrapper">
                    <div class="block">
                        <el-date-picker style=""
                                v-model="chooseDate"
                                value-format="yyyy-MM-dd"
                                type="daterange"
                                range-separator="-"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions"
                        >
                        </el-date-picker>
                    </div>
                </section>
                <el-button class="search" @click="toCityHotel">搜索</el-button>
            </div>
        </el-header>
        <el-main class="hc-main">
            <div class="g-home-hot-layout">
                <div class="g-home-hot-wrapper">
                    <h2>热门城市</h2>
                    <ul class="hot-module">
                        <li class="hot-item">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/img/upload/static/宽窄巷子.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>巴适成都</h3>
                                <p>来这儿体验慢生活，暂别奔忙</p>
                            </div>
                        </li>
                        <li class="hot-item">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/img/upload/static/古都西安.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>古都西安</h3>
                                <p>闲庭信步古城墙，一夜看尽长安花。</p>
                            </div>
                        </li>
                        <li class="hot-item">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/img/upload/static/文艺厦门.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>文艺厦门</h3>
                                <p>碧海白鹭，老街古厝，温柔好时光。</p>
                            </div>
                        </li>
                        <li class="hot-item">
                            <a href=""><div class="photo-wrapper">
                                <img src="../assets/img/upload/static/摩登上海.jpg">
                            </div></a>
                            <div class="info-wrapper">
                                <h3>摩登上海</h3>
                                <p>体味老弄堂与摩天楼的双面风情。</p>
                            </div>
                        </li>
                    </ul>
                </div>   
            </div>
            <div class="g-home-special-layout">
                <div class="g-home-special-wrapper">
                    <h2>超值特惠</h2>
                    <div class="special-module">
                        <div class="special-triple-wrapper">
                            <div class="left-col">
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/img/upload/static/xfyhpc2.png" alt="新房特惠">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>新房特惠</h3>
                                        <p>新房让利，抢先体验</p>
                                    </div>
                                </a>
                            </div>
                            <div class="right-col">
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/img/upload/static/jrsmpc3.png" alt="今日甩卖">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>今日甩卖</h3>
                                        <p>特价房限时购</p>
                                    </div>
                                </a>
                                <a href="" class="special-card-item">
                                    <div class="photo-wrapper">
                                        <img src="../assets/img/upload/static/lzyhpc3.png" alt="连住优惠">
                                    </div>
                                    <div class="info-wrapper">
                                        <h3>连住优惠</h3>
                                        <p>住多天更优惠</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </el-main>
        <el-footer style="padding: 0">
            <Footer></Footer>
        </el-footer>
    </el-container>
</template>

<script>
 import Footer from '../components/Footer'
 import Header from"../components/Header";
    export default {
        components: {Header, Footer},
        data() {
            return {
                phoneNumber:'',//用户名
                passWord:'',//密码
                errorInfo:'错误',//错误提示
                showError:false,/*是否显示错误信息*/
                toggleLoginBody:true,/*切换登录方式*/
                showLogin:!this.$store.state.isLogin,/*是否显示登录界面*/
                queryCityName:'',/*建议的城市名称*/
                currentCityName:"北京",/*当前选择的城市*/
                loadingCity:true,/*是否显示城市正在加载界面*/
                currentRegion:"",/*当前选择的地区*/
                toggle:true,/*是否显示城市搜索*/
                keywordToggle:false,/*是否显示关键字搜索*/
                chooseDate:'',/*选择的日期*/
                cities:[],/*城市列表*/
                region:[],/*地区列表*/
                currentRegionTypeIndex:0,/*当前地区类型的索引*/
                notice:[],/*通知*/
                showNoticeType:"system",/*显示通知的类型*/
                pickerOptions: {/*限制只能选择今天及以后的时间*/
                    disabledDate(time) {
                        return time.getTime() < Date.now() - 8.64e7;
                    }
                },
            }
        },
        beforeCreate(){//在创造之前数据和方法还未绑定到app上
        },
        created() {
            this.getCityAsync()//得到城市
            this.getRegionAsync(this.currentCityName,"hotsearch")//得到默认地区
            if(JSON.parse(sessionStorage.getItem('store'))!=null){
                this.getUserNotice(this.$store.state.userInfo.username)//得到用户通知
            }
        },
        mounted() {//挂载之后数据已渲染在页面上,类名可以获取
            //绑定键盘按下事件
            window.addEventListener('keydown',this.keyDown);
        },
        beforeDestroy(){//销毁之前
        },
        destroyed(){//销毁之后
            window.removeEventListener('keydown',this.keyDown,false);
        },
        methods:{
            async sendCode(){/*发送验证码*/
                const _this =this
                if(this.phoneNumber.trim()==''){
                    this.$message.error('手机号不能为空!')
                    return false
                }
                await axios.get(this.API.SendMsg+this.phoneNumber).then(function (resp) {
                    if(resp.data=="error"){
                        _this.errorInfo="验证码发送失败，手机号错误"
                        _this.showError=true
                        _this.passWord=''
                        console.log(resp.data);
                        return false
                    }
                    _this.$message("验证码已发送");
                }).catch(function (error) {
                    console.log(error)
                });
            },
            async loginOrRegister(onlyLogin){//点击登录或者注册
                const _this =this
                if(this.phoneNumber.trim()==''){
                    this.$message.error('手机号不能为空!')
                    return false
                }
                let isSuccess=false;
                //加载提示
                const loading = this.$loading({
                    lock: true,
                    text: '登录中',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.3)'
                });
                if(onlyLogin){//仅登录
                    if(this.passWord.trim()==''){
                        this.$message.error('密码不能为空!')
                        loading.close();
                        return false
                    }
                    //根据用户名和密码查询用户信息
                    await axios.get(this.API.Login+this.phoneNumber+'/null/'+this.passWord).then(function (resp) {
                        if(resp.data==""){
                            _this.errorInfo="手机号或密码错误"
                            _this.showError=true;
                            _this.passWord=''
                            return false
                        }
                        _this.$store.state.userInfo=resp.data
                        isSuccess=true;
                    }).catch(function (error) {
                        console.log(error)
                    });
                }
                else {//登录或者注册
                    if(this.passWord.trim()==''){
                        this.$message.error('验证码不能为空!')
                        loading.close();
                        return false
                    }
                    //根据用户名和验证码查询用户信息
                    await axios.get(this.API.Login+this.phoneNumber+'/'+this.passWord+"/null").then(function (resp) {
                        if(resp.data==""){
                            _this.errorInfo="手机号或验证码错误"
                            _this.showError=true;
                            _this.passWord=''
                            return false
                        }
                        _this.$store.state.userInfo=resp.data
                        isSuccess=true;
                    }).catch(function (error) {
                        console.log(error)
                    });
                }
                if(isSuccess){
                    //登录成功
                    this.$store.state.isLogin=true
                    this.showLogin=false
                    sessionStorage.setItem('store', JSON.stringify(this.$store.state))//把json解析成字符串存入session
                    //修改用户在线状态
                    _this.$store.state.userInfo.isOnline='true'
                    await axios.put(this.API.UpdateUser, _this.$store.state.userInfo)
                    this.getUserNotice(this.$store.state.userInfo.username)//得到用户通知
                    this.passWord=''
                    this.phoneNumber=''
                    if(_this.$store.state.userInfo.role=='admin'){//如果是管理员
                        await _this.$router.push({
                                path:"/Administrator",
                                query:{
                                }
                            }
                        )
                    }
                }
                loading.close();
            },
            //查询城市建议
            querySuggestedCities(queryString, cb) {
                let restaurants = this.cities
                let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            //城市查询过滤器
            createFilter(queryString) {
                return (restaurant) => {
                    return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                }
            },
            //选择建议的城市后
            citySelect(item){
                this.currentCityName=item.value
                this.toggle=true//关闭城市选择框
                this.getRegionAsync(this.currentCityName,'hotsearch')
            },
            //切换选择城市模态框
            toggleClass(){
                if(this.keywordToggle)
                    this.keywordToggle=false
                this.toggle=!this.toggle
            },
            //是否显示关键字查询模态框
            toggleKeywordClass(){
                if(!this.toggle){
                    this.toggle=true
                }
                this.keywordToggle=!this.keywordToggle

            },
            //当前地区索引的样式
            currentRegionTypeClass(index){
                this.currentRegionTypeIndex=index
            },
            //得到城市
            getCityAsync() {
                //故意制造延迟，让加载效果出来
                setTimeout(() => {
                    this.cities = [{"value": "北京"}, {"value": "上海"}, {"value": "广州"}, {"value": "成都"}, {"value": "重庆"},{"value": "德阳"},
                        {"value": "自贡"}, {"value": "泸州"}, {"value": "内江"}, {"value": "宜宾"}, {"value": "重庆"},{"value": "福州"},
                        {"value": "南昌"}, {"value": "郑州"}, {"value": "黄冈"}, {"value": "珠海"}, {"value": "乌鲁木齐"},{"value": "兰州"},
                        {"value": "昆明"}, {"value": "玉溪"}, {"value": "大理"},
                    ]
                    this.loadingCity = false;
                }, 3000);
            },
            getRegionAsync(cityName,type){//得到地区
                const _this=this
                this.currentRegion=''//每次切换选择的城市后把当前选择的城市清空
                axios.get(this.API.GetRegion+cityName+"/"+type).then(function (resp) {
                    _this.region=resp.data
                })
            },
            async getUserNotice(username){//得到用户通知
                const _this=this
                await axios.get(this.API.GetNotice+username).then(function (resp) {
                    _this.notice=resp.data
                })
            },
            //如果是回车则执行登录方法
            keyDown(e){
                if(e.keyCode == 13 && this.showLogin ){//如果键盘按下回车并且登录页面打开
                    if(this.toggleLoginBody){//如果是在登录/注册页面
                        this.loginOrRegister(false)
                    }
                    else{//如果是在仅登录页面
                        this.loginOrRegister(true)
                    }
                    return false//阻止冒泡
                }
            },
            //跳转去城市旅馆页面
            toCityHotel(){
                this.$router.push({
                    path:"/CityHotel",
                    query:{
                        city:this.currentCityName,
                        region:this.currentRegion,
                        timeslot:this.chooseDate,//[{…}, {…}]this.chooseDate["xxx", "aaa"]
                        showLogin:this.showLogin
                    }
                    }
                )
            }
        },
    }
</script>

<style scoped>
    /*登录模态框*/
    .m-login-wrap{
        padding: 0 40px;
        background: #fff;
    }
    .login-tabs{
        height: 44px;
        margin: 20px 0;
        border-bottom: 1px solid #ddd;
        font-size: 0;
        display: flex;
        justify-content: flex-start;
        overflow: hidden;
    }
    .isActive {
        color: #333;
        cursor: default;
        border-bottom: 2px solid #ff9645;
        text-decoration: none;
    }
    .m-login-wrap .login-tabs a{
        line-height: 44px;
        display: inline-block;
        width: 49%;
        font-size: 18px;
        text-align: center;
        color: #999;
        border-bottom-width: 2px;
        font-weight: bold;
        font-family: Microsoft Yahei,PingFang SC,Hiragino Sans GB,Helvetica Neue,
        Helvetica,tahoma,arial,Verdana,sans-serif,WenQuanYi Micro Hei,"\5B8B\4F53";
    }
    .login-body{

    }
    .control-group,.controls{
        margin-bottom: 20px;
    }
    .controls{
        position: relative;
        display: flex;
        justify-content: center;
    }
    .login-btn{
        width: 100%;
        height: 50px;
        background-color: #ff9645;
    }
    /*头部背景图*/
    .header-wrapper{
        height: 682px !important;/*提高权重，调整element组件头部固定高度*/
        padding: 0;
        height: 682px;
        position: relative;
        background-position: center 0;
    }
    .header-wrapper,.bg-wrapper-item{
        background-repeat: no-repeat;
        background-size: cover;
    }
    .slider-wrapper,.bg-wrapper,.bg-wrapper-item,.wrapper-item-img{
        width: 100%;
        height: 100%;
    }
    .bg-wrapper-item{
        display: flex;
        align-items: center;
        justify-content: center;
        background-position: 50%;
        background-color: #ddd;
        font-size: 0;
    }
    .wrapper-item-img{
        background-size: cover;
        background-position: 50%;
        background-image: url("../assets/img/upload/static/banner.jpg");
    }
    /*头部导航*/
    img{
        display: block;
        width: 100%;
    }
    .hc-home-header-wrapper span{
        color: #fff;
    }
    .dropdown-notice div{
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
    /*大搜索框*/
    .hc-home-search-wrapper{
        position: relative;
        bottom: 144px;
        margin: 0 auto;
        z-index: 99;
        height: 64px;
        width: 950px;
        display: flex;
        align-items: center;
        border-radius: 50px;
        background: #fff;
    }
    /*城市查询*/
    .city-search-wrapper{
        width: 158px;
        height: 34px;
        white-space: nowrap;
        text-align: center;
        display: block;
        margin: 0;
        padding: 0;
    }
    .city-wrap{
        font-size: 18px;
        line-height: 34px;
        color: #333;
        padding-right: 22px;
        position: relative;
        cursor: pointer;
        user-select: none;
        display: inline-block;
        max-width: 130px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
    /*查询面板*/
    .search-city-panel-wrap,.hc-search-keyword-panel{
        position: absolute;
        min-height: 152px;
        background: #fff;
        top: 115%;
        left: 0;
        width: 100%;
        box-shadow: 0 8px 20px 0 rgba(0,0,0,.1);
        border-radius: 12px;
    }
    .search-city-panel-wrap{
        padding: 12px;
    }
    .hc-search-group-tabs{
        margin-top: 16px;
        padding: 0 15px;

    }
    .search-group-tab-list{
        width: 100%;
        height: 35px;
        background-color: white;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: flex-start;
    }
    .search-group-tab-list-item{
        float: left;
        cursor: pointer;
        padding: 0 11px;
        position: relative;
        width: 84px;
        height: 34px;
        text-align: center;
        line-height: 34px;
        box-shadow: 0 2px 10px 0 rgba(0,0,0,.08);
        border-radius: 6px;
        margin-right: 8px;
    }
    .group-list-item{
        margin-top: 5px;
    }
    .group-list-item:hover{
        box-shadow: 0 2px 8px 0 rgba(255,118,50,.18);
    }

    /*分隔线*/
    .divider{
        display: block;
        width: 1px;
        height: 30px;
        background: #ddd;
    }
    /*关键字/位置/房屋查询*/
    .keyword-search-wrapper{
        margin: 0 10px;
        height: 34px;
    }
    .keyword-search-input{
        margin-top: -3px;
        font-size: 18px;
    }
    /* 利用穿透，设置input边框隐藏 */
    .keyword-search-wrapper>>>.el-input__inner {
        border: 0;
    }
     .active {
        color: #ff9645;
        font-weight: 600;
        box-shadow: 0 2px 8px 0 rgba(255,118,50,.18);
    }
    .group-list{
        margin: 0;
        padding: 3px;
        margin: 10px 0;
        max-height: 280px;
        min-height: 60px;
        overflow-y: auto;
        position: relative;
        padding-top: 20px;
        display: flex;
        justify-content:flex-start;
        flex-wrap:wrap;
    }
    .group-item-list-item{
        margin-bottom: 10px;
        display: inline-block;
        min-width: 84px;
        text-align: center;
        background: #f4f5f7;
        border-radius: 6px;
        line-height: 34px;
        font-size: 14px;
        color: #666;
        padding: 0 7px;
        margin-right: 8px;
        max-width: 100%;
    }
    .group-item-name{
        display: block;
        width: 100%;
    }
    /*日期选择*/
    .date-search-wrapper{
        margin-left: 5px;
        margin-right: 10px;
        position: static;
        width: 380px;
    }
    /*搜索按钮*/
    .search{
        height: 50px;
        line-height: 50px;
        font-size: 20px;
        color: #fff;
        background-image: linear-gradient(90deg,#ff7632,#ffa819);
        -webkit-box-shadow: 0 20px 22px -13px rgba(229,100,0,.2);
        box-shadow: 0 20px 22px -13px rgba(229,100,0,.2);
        border-radius: 25px;
        padding: 0 45px;
        margin-right: 5px;
        border: 0;
    }
    .search:hover{
        color: #fff;;
        background-image: linear-gradient(90deg,#ff7632,#ffa819);
    }
    /*内容区域*/
    .hc-main{
        background: #fafafa;
    }
    /*热门城市*/
    .g-home-hot-layout{
        padding-top: 10px;
    }
    .g-home-hot-wrapper{
        margin: 0 auto;
        width: 1180px;
    }
    .g-home-hot-layout h2{
        font-size: 32px;
        color: #333;
        text-align: center;
        line-height: 32px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .hot-module{
        margin-top: 40px;
        padding-left: 0;
        padding-bottom: 60px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .hot-item{
        width: 280px;
        margin-right: 20px;
        position: relative;
    }
    .hot-item .photo-wrapper{
        width: 280px;
        height: 420px;
        margin-bottom: 18px;
        border-radius: 12px;
        overflow: hidden;
    }
    .info-wrapper h3{
        font-size: 24px;
        color: #333;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .info-wrapper p{
        font-size: 16px;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    /*超值特惠*/
    .g-home-special-layout{
        padding-top: 0px;
    }
    .g-home-special-wrapper{
        margin: 0 auto;
        width: 1180px;
    }
    .g-home-special-wrapper h2{
        font-size: 32px;
        color: #333;
        text-align: center;
        line-height: 32px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .special-module{
        margin-top: 40px;
        padding-bottom: 60px;
    }
    .special-triple-wrapper{
        width: 100%;
        height: 580px;
        display: flex;

    }
    .special-triple-wrapper{
        display: block;
        position: relative;
    }
    .special-triple-wrapper .special-card-item{
        width: 100%;
        height: 100%;
        display: block;
        position: relative;
    }
    .left-col{
        width: 580px;
        height: 580px;
        margin-right: 20px;
        float: left;
    }
    .photo-wrapper{
        width: 100%;
        height: 100%;
        overflow: hidden;
        border-radius: 12px;
    }
    .special-triple-wrapper .info-wrapper{
        position: absolute;
        top: 86px;
        left: 86px;
        max-width: 408px;
        line-height: 1;
    }
    .special-triple-wrapper .info-wrapper h3{
        font-size: 44px;
        color: #2d475f;
    }
    .special-triple-wrapper .info-wrapper p{
        margin-top: 30px;
        font-size: 26px;
        color: #5d7588;
    }
    /*今日甩卖，连住优惠*/
    .special-triple-wrapper .right-col{
        height: 580px;
        width: 580px;
        float: right;
    }
    .special-triple-wrapper .right-col .special-card-item .info-wrapper {
        position: absolute;
        top: 15px;
        left: 58px;
        max-width: 408px;
    }
    .special-triple-wrapper .right-col .special-card-item{
        height: 280px;
    }
    .special-triple-wrapper .right-col .special-card-item:last-child {
        margin-top: 20px;
    }

</style>