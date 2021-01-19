<template>
    <article style="width: 100%;overflow: hidden" >
        <div class="loading"  v-loading="loading" v-show="loading"></div>
        <nav>
            <!--顶部导航-->
            <Header :thatnotice.sync="thisnotice" :thatshow-login.sync="thisshowLogin"></Header>
            <!--大搜索框-->
            <div class="hc-home-search-wrapper">
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
                                    @click="currentCityName=item.value,toggle=true,getRegionAsync(item.value,'hotsearch'),getClassifyLocation(item.value),currentPage=1,searchHotelByAllFilters()">{{item.value}}</li>
                            </ul>
                        </section>
                    </section>
                </section>
                <div class="divider"></div>
                <section class="keyword-search-wrapper" >
                    <section @click="toggleKeywordClass()" >
                        <el-input class="keyword-search-input" v-model="currentRegion" placeholder="位置关键字"  clearable></el-input>
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
                                <li class="group-item-list-item" v-for="(item,key) in region" @click="currentRegion=item.name,keywordToggle=false,currentPage=1,searchHotelByAllFilters()"><a class="group-item-name">{{item.name}}</a></li>
                            </ul>
                        </section>
                    </section>
                </section>
                <div class="divider"></div>
                <section class="date-search-wrapper">
                    <div class="block">
                        <el-date-picker format="yyyy-MM-dd"
                                        value-format="yyyy-MM-dd"
                                        v-model="chooseDate"
                                        type="daterange"
                                        range-separator="-"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                                        :picker-options="pickerOptions"
                                        @change="currentPage=1,searchHotelByAllFilters()"
                        >
                        </el-date-picker>
                    </div>
                </section>
            </div>
        </nav>
        <section class="g-unitList-select-layout">
            <!--人数，床数，位置分类-->
            <section class="selectRow">
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">人数</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="choosePeopleNumber" placement="bottom">
                                <section @click="showPeopleNumber=!showPeopleNumber">
                                    <el-input  v-model="peopleNumber" placeholder="请选择" :suffix-icon="!showPeopleNumber?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true ></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown" style="min-width: 219px;max-height: 200px;overflow: scroll">
                                    <el-dropdown-item :command="item+'人'" v-for="(item,key) in 9">{{item}}人</el-dropdown-item>
                                    <el-dropdown-item command="10人">10人</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">床数</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="chooseBedNumber" placement="bottom">
                                <section @click="showBedNumber=!showBedNumber">
                                    <el-input  v-model="bedNumber" placeholder="请选择" :suffix-icon="!showBedNumber?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown" style="min-width: 219px;max-height: 200px;overflow: scroll">
                                    <el-dropdown-item :command="item+'床'" v-for="(item,index) in 9">{{item}}床</el-dropdown-item>
                                    <el-dropdown-item command="10床+">10床+</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <section class="g-unitList-select-location-layout">
                    <section class="locationUI">
                        <span class="title">位置</span>
                        <el-dropdown @command="chooseLocation" placement="bottom">
                            <span class="locationItem el-dropdown-link"> 热门推荐 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in hotSearch"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 观光景点 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in scenicSpot"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 商圈 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in businessDistrict"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 行政区 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in district"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 地铁线路 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in metroLine"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 机场/车站 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in airportOrStation"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 高校 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in universities"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                        <el-dropdown @command="chooseLocation">
                            <span class="locationItem el-dropdown-link"> 医院 <i class="el-icon-caret-bottom"></i></span>
                            <el-dropdown-menu slot="dropdown" class="suggest">
                                <ul class="suggest-contain">
                                    <li v-for="(item,key) in hospital"><el-dropdown-item  :command="item.name" >{{item.name}}</el-dropdown-item></li>
                                </ul>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </section>
                </section>
            </section>
            <!--户型，房价-->
            <section class="selectRow">
                <section class="g-unitList-radioUI-layout">
                    <section class="radioUI">
                        <span class="title">户型</span>
                        <el-radio-group v-model="houseType"  @change="changeHouseType">
                            <el-radio :label="1">一居</el-radio>
                            <el-radio :label="2">二居</el-radio>
                            <el-radio :label="3">三居</el-radio>
                            <el-radio :label="4">四居及以上</el-radio>
                        </el-radio-group>
                    </section>
                </section>
                <section class="g-unitList-select-housePrice-layout">
                    <section class="housePriceUI">
                        <span class="title">房价</span>
                        <el-progress :percentage="housePrice" :color="customColor" class="progress" :format="format"></el-progress>
                        <div class="progress-controller"  @mouseleave="searchOfHousePrice">
                            <el-button-group>
                                <el-button icon="el-icon-minus" @click="cutHousePrice"></el-button>
                                <el-button icon="el-icon-plus" @click="addHousePrice" ></el-button>
                            </el-button-group>
                        </div>
                    </section>
                </section>
            </section>
            <!--出租类型，配套-->
            <section class="selectRow">
                <section class="g-unitList-radioUI-layout" style="width: 20%">
                    <section class="radioUI">
                        <span class="title">出租类型</span>
                        <el-radio-group v-model="rentalType"  @change="changeRentalType">
                            <el-radio :label="'complete'">整套</el-radio>
                            <el-radio :label="'single'">单间</el-radio>
                        </el-radio-group>
                    </section>
                </section>
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">配套</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="" placement="bottom">
                                <section @click="showMatching=!showMatching">
                                    <el-input  v-model="matchNumber" placeholder="请选择" :suffix-icon="!showMatching?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown">
                                    <div class="matching">
                                        <el-checkbox-group v-model="matchingList"  size="medium">
                                            <el-checkbox label="无线网络" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="电梯" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="热水淋浴" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="洗衣机" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="电视" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="空调" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="冰箱" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="浴缸" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="卫浴用品" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="投影设备" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="麻将机" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="免费停车" class="matching-checkbox"></el-checkbox>
                                        </el-checkbox-group>
                                        <div class="matching-options">
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button @click="matchingOptions('clear')">清除条件</el-button>
                                            </el-dropdown-item>
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button style="width: 100px" @click="matchingOptions('fix')">
                                                   确定
                                                </el-button>
                                            </el-dropdown-item>
                                        </div>
                                    </div>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
                <section class="g-unitList-selectUI-layout">
                    <section  class="selectUI">
                        <span class="title">房型</span>
                        <div class="el-select selectBox">
                            <el-dropdown trigger="click" @command="" placement="bottom">
                                <section @click="showHouse=!showHouse">
                                    <el-input  v-model="houseNumber" placeholder="请选择" :suffix-icon="!showHouse?
                                    'el-icon-caret-bottom':'el-icon-caret-top'" :readonly=true></el-input>
                                </section>
                                <el-dropdown-menu slot="dropdown">
                                    <div class="matching">
                                        <el-checkbox-group v-model="houseList"  size="medium">
                                            <el-checkbox label="酒店式公寓" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="公寓" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="别墅" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="复式" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="四合院" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="房车营地" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="木屋" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="帐篷营地" class="matching-checkbox"></el-checkbox>
                                            <el-checkbox label="老洋房" class="matching-checkbox"></el-checkbox>
                                        </el-checkbox-group>
                                        <div class="matching-options">
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button @click="houseOptions('clear')">清除条件</el-button>
                                            </el-dropdown-item>
                                            <el-dropdown-item command="close" class="dropdown-item">
                                                <el-button style="width: 100px" @click="houseOptions('fix')">
                                                    确定
                                                </el-button>
                                            </el-dropdown-item>
                                        </div>
                                    </div>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </section>
                </section>
            </section>
            <section class="selectRow"></section>
        </section>
        <section id="fixedHeaderRoot" class="g-unitList-selected-layout" :class="isFixedTop?'g-unitList-selected-isFixed':''">
            <section class="selectedBox">
                <span v-show="allFilters.chooseDate!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.chooseDate}} <i class="el-icon-close" @click="chooseDate='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.peopleNumber!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.peopleNumber}}人 <i class="el-icon-close" @click="peopleNumber='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.bedNumber!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.bedNumber}}床 <i class="el-icon-close" @click="bedNumber='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.currentRegion!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.currentRegion}} <i class="el-icon-close" @click="currentRegion='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.houseType!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.houseType}}居<i class="el-icon-close" @click="houseType='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.housePrice!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.housePrice}}元以内<i class="el-icon-close" @click="realHousePrice='',housePrice=0,searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.rentalType!=''">
                        <span class="el-tag el-tag--info">
                                {{allFilters.rentalType=='single'?'单间':'整套'}} <i class="el-icon-close" @click="rentalType='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.matchingList!=''">
                        <span class="el-tag el-tag--info" v-for="(item,key) in allFilters.matchingList" :key="key">
                                {{item}}<i class="el-icon-close" @click="matchingList=[],matchNumber='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span v-show="allFilters.houseList!=''">
                        <span class="el-tag el-tag--info" v-for="(item,key) in allFilters.houseList" :key="key">
                                {{item}} <i class="el-icon-close" @click="houseList=[],houseNumber='',searchHotelByAllFilters()"></i>
                        </span>
                </span>
                <span class="closeAll" @click="clearAllFilters">清除条件</span>
            </section>
        </section>
        <section class="g-unitList-list-layout">
            <section>
                <!--排序-->
                <section class="sortBox">
                    <span class="anchor"><span class="houseNum">{{total}}套房屋符合条件</span></span>
                    <section class="sort">
                        <span :class="sortOrder=='price'?'currentSortType':''" @click="sortOrder='price',houseSort('price')">
                            价格
                            <span class="caret-wrapper">
                                <img src="../../assets/img/upload/static/排序.png" v-if="sortImg==''">
                                <img src="../../assets/img/upload/static/升序.png" v-if="sortImg=='upper'">
                                <img src="../../assets/img/upload/static/降序.png" v-if="sortImg=='down'">
                            </span>
                        </span>
                        <span :class="sortOrder=='comment'?'currentSortType':''" @click="sortOrder='comment',sortImg='',houseSort('comment')">好评优先</span>
                    </section>
                </section>
                <!--房屋子项-->
                <section class="tj-unit-list clearfix">
                    <article class="tj-unit-item-layout"  v-for="(item,key) in houseData" :key="key" >
                        <div class="tj-unit-item">
                            <div class="unit-item-pic-wrapper">
                                <div class="swiper-container pic-swiper swiper-container-horizontal" @click="toDetail(item)">
                                    <el-carousel trigger="click" :autoplay="false" height="296px">
                                        <el-carousel-item v-for="imgItem in item.img.split(',')">
                                            <img ref="imgHeight" :src=imgItem  width="100%" height="100%" object-fit="cover">
                                        </el-carousel-item>
                                    </el-carousel>
                                </div>
                                <i class="collection-icon" :class="isCollection[key]?'icon-collection':'icon-no-collection'" @click="cancelCollection(key)"></i>
                            </div>
                            <div class="unit-item-content" @click="toDetail(item)">
                                <h3 class="unit-title">
                                    <a>{{item.title}}</a>
                                </h3>
                                <ul class="unit-desc-list">
                                    <li><span>{{item.rentalType=='complete'?'整套':'单间'}}</span></li>
                                    <li><span>{{item.houseType}}居{{item.bedNumber}}床{{item.peopleNumber}}人</span></li>
                                    <li><span>{{item.score}}分/{{item.commentsNumber}}点评</span></li>
                                </ul>
                                <div class="unit-desc-list-extra">
                                    <ul class="unit-little-label-list">
                                        <li class="unit-little-label-list-item">近地铁</li>
                                        <li class="unit-little-label-list-item">宽松取消</li>
                                        <li class="unit-little-label-list-item">立即确认</li>
                                    </ul>
                                </div>
                                <div class="unit-price-content">
                                    <p class="unit-price">
                                        <span class="text">￥{{item.housePrice}}</span>
                                        <span class="text">/</span>
                                        <span>晚</span>
                                    </p>
                                </div>
                                <section class="tj-avatar-layout landlord-avatar">
                                    <img :src="item.headImg" alt="" class="avatar-img" lazy="loaded">
                                </section>
                            </div>
                        </div>
                    </article>
                </section>
                <!--页码条-->
                <div class="tj-pagination el-pagination is-background">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :total="total"
                            :page-size="pageSize"
                            :current-page="currentPage"
                            @current-change="pageChange"   >
                    </el-pagination>
                </div>
            </section>
        </section>
        <Footer></Footer>
    </article>
</template>

<script>
    import Header from "../../components/Header";
    import Footer from "../../components/Footer";
    export default {
        components: {Footer, Header},
        props:['city','chooseregion','timeslot','showLogin'],
        data(){
            return{
                thisnotice:'',/*通知*/
                thisshowLogin:this.showLogin,/*是否显示登录*/
                queryCityName:'',/*建议的城市名称*/
                currentCityName:this.city,/*当前选择的城市*/
                loadingCity:true,/*是否显示城市正在加载界面*/
                currentRegion:this.chooseregion,/*当前选择的地区*/
                toggle:true,/*是否显示城市搜索*/
                keywordToggle:false,/*是否显示关键字搜索*/
                chooseDate:this.timeslot,/*选择的日期*/
                cities:[],/*城市列表*/
                region:[],/*地区列表*/
                currentRegionTypeIndex:0,/*当前地区类型的索引*/
                peopleNumber:'',/*人数*/
                showPeopleNumber:false,/*是否显示人数选择下拉框*/
                bedNumber:'',/*床数*/
                showBedNumber:false,/*是否显示床数下拉框*/
                hotSearch:[],/*热门搜索*/
                scenicSpot:[],/*观光景点*/
                businessDistrict:[],/*商圈*/
                district:[],/*行政区*/
                metroLine:[],/*地铁线路*/
                airportOrStation:[],/*机场或者车站*/
                universities:[],/*高校*/
                hospital:[],/*医院*/
                houseType:'',/*户型*/
                loading:false,/*整个页面是否显示加载图标*/
                housePrice:20,/*房价*/
                realHousePrice:'',/*真实房价*/
                customColor:'#ff9645',/*进度条颜色*/
                rentalType:'',/*出租类型*/
                matchNumber:'',/*配套条件个数*/
                showMatching:false,/*是否显示配套信息*/
                matchingList:[],/*配套列表*/
                houseNumber:'',/*房型条件的个数*/
                showHouse:false,/*是否显示房型信息*/
                houseList:[],/*房型列表*/
                allFilters:{"peopleNumber":"","bedNumber":"","currentRegion":this.chooseregion,"houseType":"","housePrice":"",
                    "rentalType":"","matchingList":[],"houseList":[],"chooseDate":this.timeslot.toString(),"currentCityName":this.city},/*所有筛选条件*/
                pickerOptions: {/*限制只能选择今天及以后的时间*/
                    disabledDate(time) {
                        return time.getTime() < Date.now()-8.64e7;
                    }
                },
                isFixedTop:false,/*是否固定在顶部*/
                sortImg:'',/*排序图标*/
                sortOrder:'',/*排序方式*/
                houseData: [],/*房屋数据*/
                isCollection:[],/*该房屋是否收藏,有多少条数据就有及格开关*/
                collectionList:[],/*用户收藏列表*/
                pageSize:6,/*每页显示的内容个数*/
                total:1,/*总内容个数*/
                currentPage:1,/*当前页数*/
            }
        },
        created() {
            if(JSON.parse(sessionStorage.getItem('store'))!=null){
                this.getUserNotice(this.$store.state.userInfo.username)//得到用户通知
            }
            this.getCityAsync()//得到城市
            this.getRegionAsync(this.currentCityName,"hotsearch",this.currentRegion)//得到默认地区
            this.getClassifyLocation(this.currentCityName)//得到分类位置
            this.getHouseData(this.allFilters,this.currentPage)//得到房屋
        },
        mounted(){
            // 滚动条的获取
            window.addEventListener('scroll', this.handleScroll, true)
        },
        methods:{
            //切换选择城市模态框
            toggleClass(){
                if(this.keywordToggle)
                    this.keywordToggle=false
                this.toggle=!this.toggle
            },
            //切换显示关键字查询模态框
            toggleKeywordClass(){
                if(!this.toggle){
                    this.toggle=true
                }
                else{
                    this.keywordToggle=!this.keywordToggle
                }
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
                this.getClassifyLocation(item.value)
                this.searchHotelByAllFilters()
                this.currentPage=1
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
            getRegionAsync(cityName,type,currentRegion){//得到地区
                this.currentRegion=''//每次切换选择的城市后把当前选择的地区清空
                if(currentRegion!=null){//如果首页传过来有地区，则显示
                    this.currentRegion=currentRegion
                }
                const _this=this
                axios.get(this.API.GetRegion+cityName+"/"+type).then(function (resp) {
                    _this.region=resp.data
                })
            },
            async getUserNotice(username){//得到用户通知
                const _this=this
                await axios.get(this.API.GetNotice+username).then(function (resp) {
                    _this.thisnotice=resp.data
                })
            },
            getClassifyLocation(city){//得到分类位置
                const _this=this
                axios.get(this.API.GetRegion+city+"/hotsearch").then(function (resp) {
                    _this.hotSearch=resp.data
                })
                axios.get(this.API.GetRegion+city+"/scenicspot").then(function (resp) {
                    _this.scenicSpot=resp.data
                })
                axios.get(this.API.GetRegion+city+"/tradingarea").then(function (resp) {
                    _this.businessDistrict=resp.data
                })
                axios.get(this.API.GetRegion+city+"/district").then(function (resp) {
                    _this.district=resp.data
                })
                axios.get(this.API.GetRegion+city+"/metroline").then(function (resp) {
                    _this.metroLine=resp.data
                })
                axios.get(this.API.GetRegion+city+"/airportorstation").then(function (resp) {
                    _this.airportOrStation=resp.data
                })
                axios.get(this.API.GetRegion+city+"/universities").then(function (resp) {
                    _this.universities=resp.data
                })
                axios.get(this.API.GetRegion+city+"/hospital").then(function (resp) {
                    _this.hospital=resp.data
                })
            },
            //选择人数，查询结果
            choosePeopleNumber(number){
                this.peopleNumber=number
                this.showPeopleNumber=!this.showPeopleNumber
                this.searchHotelByAllFilters()//查询结果
            },
            //选择床数，查询结果
            chooseBedNumber(number){
                this.bedNumber=number
                this.showBedNumber=!this.showBedNumber
                this.searchHotelByAllFilters()//查询结果
            },
            changeRegoin(){
              alert("ok")
            },
            //选择位置，查询结果
            chooseLocation(location){
                this.currentRegion=location
                this.searchHotelByAllFilters()//查询结果
            },
            //选择户型，查询结果
            changeHouseType(value){
                this.searchHotelByAllFilters()
            },
            /*增加房价*/
            addHousePrice() {
                this.housePrice += 10;
                if (this.housePrice > 100) {
                    this.housePrice = 100;
                }
            },
            /*减少房价*/
            cutHousePrice() {
                this.housePrice -= 10;
                if (this.housePrice < 0) {
                    this.housePrice = 0;
                }
            },
            /*格式化房价显示*/
            format(percentage){
                if(percentage<40){
                    return percentage*10;
                }
                if(percentage<60){
                    return percentage*15;
                }
                if(percentage<80){
                    return percentage*20;
                }
                return percentage*100;
            },
            /*选择房价，查询结果*/
            searchOfHousePrice(){
                if(this.housePrice<40){
                    this.realHousePrice =this.housePrice*10
                }
                else if(this.housePrice<60)   {
                    this.realHousePrice =this.housePrice*15
                }
                else if(this.housePrice<80)   {
                    this.realHousePrice =this.housePrice*20
                }
                else {
                    this.realHousePrice =this.housePrice*100
                }
                this.searchHotelByAllFilters()//查询结果
            },
            //选择出租类型，查询结果
            changeRentalType(value){
                this.searchHotelByAllFilters()
            },
            //选择配套，查询结果
            matchingOptions(option){
                if(option=='clear'){
                    this.matchingList=[]
                    this.matchNumber=''
                }
                if(option=='fix'){
                    this.matchNumber=this.matchingList.length+"项"
                }
                this.searchHotelByAllFilters()
            },
            //选择房型，查询结果
            houseOptions(option){
                if(option=='clear'){
                    this.houseList=[]
                    this.houseNumber=''
                }
                if(option=='fix'){
                    this.houseNumber=this.houseList.length+"项"
                }
                this.searchHotelByAllFilters()//查询结果
            },
            /*根据所有筛选条件查询民宿结果*/
            searchHotelByAllFilters(){
                //得到所有筛选条件
                this.allFilters.peopleNumber=this.peopleNumber.replace("人","").replace("人+","")//人数，1...
                this.allFilters.bedNumber=this.bedNumber.replace("床","").replace("床+","")//床数，1...
                this.allFilters.currentRegion=this.currentRegion//地区，昌平区..
                this.allFilters.houseType=this.houseType//户型，一居...
                this.allFilters.housePrice=this.realHousePrice//房价，400...
                this.allFilters.rentalType=this.rentalType//出租类型，single（单间），complete（整套）...
                this.allFilters.matchingList=this.matchingList//配套，无线网络...
                this.allFilters.houseList=this.houseList//房型，公寓...
                this.allFilters.chooseDate=this.chooseDate.toString()//选择日期，['2020-11-30','2020-12-01']
                this.allFilters.currentCityName=this.currentCityName//当前城市，成都...
                //清除排序
                this.sortOrder=''
                this.sortImg=''
                console.log(this.allFilters)
                this.getHouseData(this.allFilters,this.currentPage)//得到房屋数据
            },
            clearAllFilters(){/*清除所有条件*/
                this.peopleNumber=''
                this.bedNumber=''
                this.currentRegion=''
                this.houseType=''
                this.realHousePrice=''
                this.rentalType=''
                this.matchingList=[]
                this.houseList=[]
                this.chooseDate=[]
                this.matchNumber=''//.
                this.houseNumber=''//.
                this.housePrice=0//.
                this.searchHotelByAllFilters()
            },
            /*下拉滚动条时将筛选条件固定在顶部*/
            handleScroll(){
                if(window.pageYOffset>340){
                    this.isFixedTop=true
                }
                if(window.pageYOffset<340){
                    this.isFixedTop=false
                }
            },
            //房屋排序
            houseSort(type){
                if(type=='price'){//按价格排序
                    if(this.sortImg=='upper'){//降序
                        this.sortImg='down'
                        this.houseData=this.sortByKey(this.houseData,'housePrice').reverse()
                    }
                    else{//升序
                        this.sortImg='upper'
                        this.houseData=this.sortByKey(this.houseData,'housePrice')
                    }
                }
                if(type=='comment'){//按好评排序
                    this.houseData=this.sortByKey(this.houseData,'score').reverse()
                }
            },
            async getHouseData(filter,page){//得到房屋数据,参数筛选条件,排序方式,页码
                this.loading=true
                const _this=this
                await axios.post(this.API.GetHouseTotal,this.allFilters).then(function (resp) {
                    _this.total=resp.data//得到记录条数
                })
                await axios.post(this.API.GetHouseList+page+'/'+this.pageSize,this.allFilters).then(function (resp) {
                    _this.houseData=resp.data//得到记录
                })
                console.log(this.houseData)
                //排序和筛选
                //currentCityName,peopleNumber，bedNumber，houseType，realHousePrice，rentalType,chooseDate这几个条件由数据库查询语句筛选
                //排序由数据库排序
                //matchingList，houseList,currentCityNameAndCurrentRegion由前端筛选
                let len=this.houseData.length
                for(let i=0;i<len;i++){
                    if(!(this.isContained(this.houseData[i].matchingList.split(","),filter.matchingList)&&this.isContained(this.houseData[i].houseList.split(","),filter.houseList)
                        &&this.isContained(this.houseData[i].cityAndRegion.split(","),[filter.currentCityName,filter.currentRegion]))){
                        //console.log(this.houseData[i])
                        this.houseData.splice(i,1)//移除不满足匹配条件的
                        i--
                        len--
                    }
                }
                await this.getCollection()//得到收藏
                this.loading=false
            },
            //得到收藏
            async getCollection(){
                const _this=this
                await axios.get(this.API.GetCollect+this.$store.state.userInfo.username).then(function (resp) {
                    _this.collectionList=resp.data
                })
                this.isCollection.length=this.houseData.length
                for(let i=0;i<this.houseData.length;i++){
                    this.isCollection[i]=false
                }
                //将页面的子项数据与收藏列表对比，相同则显示收藏
                for(let i=0;i<this.houseData.length;i++){
                    for(let j=0;j<this.collectionList.length;j++){
                        if(this.houseData[i].username==this.collectionList[j].username&&this.houseData[i].title==this.collectionList[j].title)
                        {
                            this.isCollection[i]=true;
                        }
                    }
                }
            },
            //取消/添加收藏
            cancelCollection(key){
                if(this.isCollection[key]==false||this.isCollection[key]=='false'){
                    //向数据库添加收藏
                    let collect={"id":null,"userId":this.$store.state.userInfo.username,"username":this.houseData[key].username,"title":this.houseData[key].title}
                    axios.post(this.API.AddCollect,collect)
                    this.$set(this.isCollection, key, true);//必须这样添加才能双向绑定
                }
                else{
                    for(let j=0;j<this.collectionList.length;j++) {
                        if(this.houseData[key].username==this.collectionList[j].username&&this.houseData[key].title==this.collectionList[j].title)
                        {
                            //向数据库删除收藏
                            axios.delete(this.API.DeleteCollect+this.collectionList[j].id);
                            this.$set(this.isCollection, key, false);//必须这样删除才能双向绑定
                            break
                        }
                    }

                }
            },
            pageChange(Page){/*换页*/
                this.currentPage=Page
                this.getHouseData(this.allFilters,this.currentPage)
            },
            //小数组是否被大数组包含,是返回true,不是返回false
            isContained:(max, min)=>{
                if(!(max instanceof Array) || !(min instanceof Array)) return false;
                if(max.length < min.length) return false;
                var aStr = max.toString();
                for(var i = 0, len = min.length; i < len; i++){
                    if(aStr.indexOf(min[i]) == -1) return false;
                }
                return true;
            },
            //根据键对json进行排序
            sortByKey(array,key) {
                return array.sort(function (a, b) {
                    let x = a[key];
                    let y = b[key];
                    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
                });
            },
            toDetail(item){//去详情页
                this.$router.push({
                        path:"/Detail",
                        query:{
                            showLogin:this.thisshowLogin,
                            chooseHouse:JSON.stringify(item),
                        }
                    }
                )
            }
        },
        watch: {

        },
        computed:{
        }
    }
</script>

<style scoped>
    nav{
        width: 100%;
        height: 70px;
    }
    /*大搜索框*/
    .hc-home-search-wrapper{
        position: relative;
        bottom: 144px;
        margin-left: 220px;
        z-index: 99;
        height: 50px;
        width: 809px;
        display: flex;
        align-items: center;
        border-radius: 50px;
        background:#f8f9fa;
        top:8px;
    }
    /*城市查询*/
    .city-search-wrapper{
        width: 130px;
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
        padding-right: 0px;
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
        background-color: #f8f9fa;
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
        height: 100%;
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
    /*条件选择区域*/
    /*人数/床数*/
    .g-unitList-select-layout{
        width: 100%;
        padding: 20px;
        background: #f7f9fb;
        min-width: 1330px;
    }
    .g-unitList-select-layout .selectRow{
        display: flex;
        margin-bottom: 10px;
    }
    .g-unitList-selectUI-layout{
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        width: 20%;
    }
    .g-unitList-selectUI-layout .selectUI{
        padding: 10px 20px;
        width: 100%;
        position: relative;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .g-unitList-selectUI-layout .title{
        line-height: 40px;
        white-space: nowrap;
    }
    .g-unitList-selectUI-layout .el-select {
        flex-grow: 1;
        display: inline-block;
        position: relative;

    }
    /* 利用穿透，设置input边框隐藏 */
    .el-select>>>.el-input__inner {
        border-style: none;
        font-size: 18px;
        color: #ff9645;
        text-align: center;
        padding: 0;
    }
    /*位置/热门推荐...*/
    .g-unitList-select-location-layout:last-child {
        margin-right: 0;
    }
    .g-unitList-select-location-layout {
        width: 56%;
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        -webkit-box-pack: start;
        -ms-flex-pack: start;
        justify-content: flex-start;
    }
    .g-unitList-select-location-layout .locationUI {
        padding: 20px;
    }
    .g-unitList-select-location-layout .locationUI .title {
        white-space: nowrap;
    }
    .g-unitList-select-location-layout .locationUI .locationItem {
        color: #666;
        margin-left: 20px;
        cursor: pointer;
    }
    /*选择位置，弹出的建议*/
    .suggest-contain{
        padding: 5px;
        width: 400px;
        max-height: 250px;
        overflow: scroll;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    /*户型*/
    .g-unitList-radioUI-layout{
        background: #fff;
        -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        margin-right: 10px;
        font-size: 16px;
        color: #333;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;
        padding-right: 4px;
        box-sizing: content-box;
        width: 40%;
    }
    .g-unitList-radioUI-layout .radioUI {
        padding: 20px;
        width: 100%;
    }
    .g-unitList-radioUI-layout .radioUI .title{
        margin-right:10px;
    }
    /*房价*/
    .g-unitList-select-housePrice-layout{
        width: 56.3%;
        background: #fff;
        box-shadow: 0 0 4px 0 rgba(0,0,0,.03);
        font-size: 16px;
        color: #333;
        display: flex;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        -webkit-box-pack: start;
        -ms-flex-pack: start;
        justify-content: flex-start;
        position: relative;
        margin-right: 0;
    }
    .g-unitList-select-housePrice-layout .housePriceUI{
        padding: 11px;
        width: 100%;
        position: absolute;
        bottom: 0;

    }
    .g-unitList-select-housePrice-layout .housePriceUI .title {
        white-space: nowrap;
        float: left;
        margin-right: 10px;
        line-height: 39px;
    }
    .progress{
        width: 55%;
        line-height: 39px;
        float: left;
    }
    .progress-controller{
        float: left;
    }
    /*配套*/
    .matching{
        clear: both;
        max-width: 480px;
        padding-left: 25px;
    }
    .matching-checkbox{
        width: 85px;
        height: 25px;
        margin-top: 20px;
        margin-left: 5px;
    }
    .matching-options{
        padding-right: 30px;
        display: flex;
        justify-content: space-between;
    }
    .dropdown-item:hover{
        clear: both;
    }
    /*浮动显示条件区域*/
    .g-unitList-selected-layout{
        background: #fff;
        -webkit-box-shadow: 0 1px 0 0 rgba(0,0,0,.15);
        box-shadow: 0 1px 0 0 rgba(0,0,0,.15);
        padding: 10px 0;
        border-bottom: 1px solid #e9e9e9;
        display: flex;
        justify-content: flex-start;
    }
    .g-unitList-selected-layout .selectedBox{
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
        width: 80%;
        line-height: 30px;
        vertical-align: top;
        padding-left: 20px;
    }
    .el-tag--info{
        display: inline-block;
        padding: 0 10px;
        font-size: 12px;
        border-width: 1px;
        border-style: solid;
        border-radius: 4px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        white-space: nowrap;
        height: 22px;
        line-height: 20px;
        margin-right: 10px;
        background-color: #f4f4f5;
        border-color: #e9e9eb;
        color: #909399;
    }
    .g-unitList-selected-layout .selectedBox .closeAll{
        font-size: 14px;
        color: #ff9645;
        letter-spacing: 0;
        cursor: pointer;
        display: inline-block;
    }
    .g-unitList-selected-isFixed{
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 20;
    }
    /*住宿内容列表区域*/
    .g-unitList-list-layout{
        width: 97.5%;
        padding: 0 20px;
        background-color: #fff;
    }
    /*加载图标*/
    .loading{
        position: fixed;
        width: 100vw;
        height: 100vh;
        z-index: 100;
        top:0%;
    }
    /*房屋排序*/
    .g-unitList-list-layout .sortBox{
        display: flex;
        justify-content: space-between;
        font-size: 18px;
        line-height: 26px;
        padding: 15px 0;
        width: 100%;
    }
    .g-unitList-list-layout .sortBox .anchor, .g-unitList-list-layout .sortBox .anchor a{
        color: #999;
    }
    .g-unitList-list-layout .sortBox .anchor .houseNum{
        padding-left: 10px;
    }
    .g-unitList-list-layout .sortBox .sort{
        float: right;
        display: inline-block;
        color: #666;
    }
    .g-unitList-list-layout .sortBox .sort .currentSortType{
        color: #ff9645;
    }
    .g-unitList-list-layout .sortBox .sort>span{
        margin-right: 36px;
        cursor: pointer;
    }
    .caret-wrapper{
        display: inline-flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        height: 26px;
        width: 4px;
        vertical-align: middle;
        cursor: pointer;
        overflow: initial;
        position: relative;
    }
    /*房屋内容项区域*/
    .g-unitList-list-layout .tj-unit-list{
        width: calc(100% + 20px);
        margin-left: -10px;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .tj-unit-item-layout{
        display: block;
        padding: 0 10px;
        width: 32%;
    }
    @media screen and (max-width: 1024px){
        .tj-unit-item-layout{
           width: 45%;
        }
    }
   @media screen and (max-width: 1500px) and (min-width: 1024px){
        .tj-unit-item-layout{
            width: 48%;
        }
    }
    @media screen and (max-width: 1550px) and (min-width: 1500px){
        .tj-unit-item-layout{
            width: 31.8%;
        }
    }
    /*     @media screen and (min-width: 1600px){
            .tj-unit-item-layout {
                width: 32.1%;
            }
        }*/

    .tj-unit-item {
        margin-bottom: 30px;
        cursor: pointer;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        position: relative;
    }
    .tj-unit-item .unit-item-pic-wrapper {
        position: relative;
    }
    .tj-unit-item .unit-item-content {
        position: relative;
        min-height: 139px;
        padding: 20px 5px 0;
        background-color: #fff;
    }
    .pic-swiper {
        width: 100%;
        height: 296px;
        min-height: 296px;
        border-radius: 10px;
    }
    .swiper-container {
        margin: 0 auto;
        position: relative;
        overflow: hidden;
        list-style: none;
        padding: 0;
        z-index: 1;
    }
    .tj-unit-item .unit-item-content .unit-title {
        font-size: 28px;
        line-height: 33px;
        font-weight: 700;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        margin: 0;
        text-align: left;
    }
    .tj-unit-item .unit-item-content .unit-title a,.tj-unit-item .unit-item-content .unit-title a:visited {
        color: #333;
        text-decoration: none;
        font-size: 28px;
    }
    .tj-unit-item .unit-item-content .unit-desc-list {
        display: flex;
        overflow: hidden;
        align-items: center;
        margin: 10px 0;
        line-height: 18px;
        color: #666;
        text-overflow: ellipsis;
        justify-content: flex-start;
        padding: 0;
        font-size: 18px;
    }
    .tj-unit-item .unit-item-content .unit-desc-list li {
        display: inline;
        padding: 0;
        margin: 0;
    }
    .tj-unit-item .unit-item-content .unit-desc-list-extra {
        height: 20px;
        overflow: hidden;
        font-size: 15px;
    }
    .tj-unit-item .unit-item-content .unit-little-label-list {
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        margin: 0;
        padding: 0;
    }
    .unit-item-content .unit-little-label-list .unit-little-label-list-item {
        line-height: 16px;
        height: 18px;
        margin: 0 5px 3px 0;
        border-radius: 2px;
        white-space: nowrap;
        padding: 0 5px;
        color: rgb(255, 150, 69);
        background: rgb(255, 245, 224);
        border: 0px solid rgb(255, 176, 0);
    }
    .tj-unit-item .unit-item-content .unit-price-content {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: end;
        -ms-flex-align: end;
        align-items: flex-end;
        margin-top: 8px;
        height: 36px;
        overflow: hidden;
    }
    .tj-unit-item .unit-item-content .unit-price-content .unit-price {
        font-weight: 700;
        font-size: 20px;
        color: #fd8238;
        white-space: nowrap;
        margin: 0 10px 0 0;
        padding: 0;
    }
    .tj-unit-item .unit-item-content .unit-price-content .unit-price .text {
        line-height: 35px;
        font-size: 30px;
    }
    .tj-unit-item .unit-item-content .landlord-avatar {
        position: absolute;
        right: 0;
        bottom: 0;
        z-index: 1;
        width: 64px;
        height: 64px;
        border-radius: 50%;
        display: block;
        margin: 0;
        padding: 0;
        cursor: pointer;
    }
    .tj-avatar-layout .avatar-img {
        display: block;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        border: 0;
        vertical-align: middle;
    }
    /*爱心图标，精灵图片*/
    .tj-unit-item .collection-icon {
        position: absolute;
        top: 12px;
        right: 12px;
        z-index: 1;
        cursor: pointer;
    }
    .icon-no-collection{
        background-image: url("../../assets/img/upload/static/component-unitItem.12d52104.png");
        background-position: 0 0;
        width: 52px;
        height: 48px;
    }
    .icon-collection{
        background-image: url("../../assets/img/upload/static/component-unitItem.12d52104.png");
        background-position: -56px 0;
        width: 50px;
        height: 45px;
    }
    /*分页条*/
    .g-unitList-list-layout .tj-pagination {
        text-align: center;
        padding: 30px 0 60px;
        margin: 0;
        white-space: nowrap;
        color: #303133;
        font-weight: 700;
    }
    /*分页样式*/
    .tj-pagination >>> .el-pagination.is-background  button,.tj-pagination >>> .el-pagination.is-background  .el-pager li{
        width:40px;
        height: 35px;
        line-height: 34px;
    }
    /*击穿修改页码颜色*/
    .tj-pagination >>> .el-pagination.is-background .el-pager li:not(.disabled).active {
        background-color: #FD7A3A;
        color: #fff;
    }
</style>
