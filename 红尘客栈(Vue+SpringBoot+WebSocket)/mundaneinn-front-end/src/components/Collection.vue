<!--收藏-->
<template>
    <!--房屋子项-->
    <section class="tj-unit-list clearfix">
        <article class="tj-unit-item-layout"  v-for="(item,key) in houseData" :key="key" >
            <div class="tj-unit-item">
                <div class="unit-item-pic-wrapper">
                    <div class="swiper-container pic-swiper swiper-container-horizontal" @click="toDetail(item)">
                        <el-carousel trigger="click" :autoplay="false" height="265px">
                            <el-carousel-item v-for="imgItem in item.img.split(',')">
                                <img ref="imgHeight" :src=imgItem  width="100%" height="100%" object-fit="cover">
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                    <i class="collection-icon icon-collection" @click="cancelCollection(key)"></i>
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
</template>

<script>
    export default {
        name: "Collection",
        data(){
            return{
                houseData:[],
                collectionList:[],/*收藏列表*/
                isCollection:[],/*该房屋是否收藏,有多少条数据就有及格开关*/
            }
        },
        async created() {
            await this.getCollection()
        },
        methods:{
            async getCollection(){//得到收藏
                const _this=this
                await axios.get(this.API.GetCollect+this.$store.state.userInfo.username).then(function (resp) {
                    _this.collectionList=resp.data
                })
                if(_this.collectionList==''){
                    return
                }
                for(let i=0;i<this.collectionList.length;i++){//得到收藏的房屋
                    await axios.get(this.API.GetHouse+this.collectionList[i].username+'/'+this.collectionList[i].title)
                        .then((resp)=>{
                            _this.houseData.push(resp.data[0])
                    })
                }
            },
            cancelCollection(key){//取消收藏
                axios.delete(this.API.DeleteCollect+this.collectionList[key].id);
                this.collectionList.splice(key,1)
                this.houseData.splice(key,1);
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
        }

    }
</script>

<style scoped>
    /*房屋内容项区域*/
    .tj-unit-list{
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
            width: 33%;
        }
    }
    .tj-unit-item {
        margin-bottom: 30px;
        cursor: pointer;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        position: relative;
        background-color: white;
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
        height: 265px;
        min-height: 200px;
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
        background-image: url(../assets/img/upload/static/component-unitItem.12d52104.png);
        background-position: 0 0;
        width: 52px;
        height: 48px;
    }
    .icon-collection{
        background-image: url(../assets/img/upload/static/component-unitItem.12d52104.png);
        background-position: -56px 0;
        width: 50px;
        height: 45px;
    }
</style>