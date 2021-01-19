<!--房屋管理-->
<template>
    <section class="m-view g-houseList-layout">
        <div class="page-header">
            <p class="page-title">房源管理</p>
        </div>
        <div class="page-content">
            <div class="page-content-list">
                <div class="list-header">
                    <div class="house-info">房源</div>
                    <div class="house-state">房屋状态</div>
                    <div class="create-time">出租时段</div>
                    <div class="operation">预览</div>
                    <div class="operation">操作</div>
                </div>
                <div class="g-comHouseListItem-layout">
                    <div class="item-box" v-for="(item,key) in houseData" :key="key">
                        <div class="content">
                            <div class="item-house-info">
                                <div class="info-left">
                                    <img :src="item.img.split(',')[0]" alt="">
                                    <div>房源编号. {{item.id}}</div>
                                </div>
                                <div class="info-right">
                                    <div class="title"><span>{{item.title}}</span></div>
                                    <div class="desc">{{item.houseType}}居{{item.bedNumber}}床{{item.peopleNumber}}人 {{item.cityAndRegion}}</div>
                                </div>
                            </div>
                            <div class="item-house-state">
                                <span v-if="item.state==0" style="background: #E6A23C">待审核</span>
                                <span v-if="item.state==1" style="background: #67C23A">已上架</span>
                                <span v-if="item.state==2" style="background: #F56C6C">审核失败</span>
                                <span v-if="item.state==3" style="background: #909399">已下架</span>
                            </div>
                            <div class="item-create-time">{{item.chooseDate.toString().replace(",","至")}}</div>
                            <div class="item-operation">
                                <div style="margin-top: 15px"><el-button size="mini" type="info" @click="toDetail(item)">查看详情</el-button></div>
                            </div>
                            <div class="item-operation">
                                 <div style="margin-bottom: 5px"><el-button size="mini" type="warning" @click="updateHouse(item)">修改房屋</el-button></div>
                                 <div><el-button size="mini"  type="danger" :disabled="item.state==3 || !allowShelf[key]" @click="offShelf(item)">下架房屋</el-button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
    export default {
        data() {
            return {
                houseData: [],/*房屋数据*/
                allowShelf:[],/*是否允许下架*/
            }
        },
        async created() {
            await this.getHouseData()
        },
        methods: {
            async getHouseData(){//得到房屋数据
                const _this=this
                let data;
                await axios.get(this.API.GetHouse+this.$store.state.userInfo.username+'/null').then((resp)=>{
                    data=resp.data
                })
                //得到房屋已存在的订单,查看是否有正在进行的，如果有，则不允许下架
                this.allowShelf.length=data.length
                for(let i=0;i<this.allowShelf.length;i++){
                    this.allowShelf[i]=true
                }
                for(let i=0;i<data.length;i++) {
                    await axios.get(this.API.GetOrderByTitle+data[i].username+"/"+data[i].title).then(resp=>{
                        for(let j=0;j<resp.data.length;j++){
                            if(resp.data[j].state==1){
                                _this.allowShelf[i]=false
                                break
                            }
                        }
                    })
                }
                //最后渲染数据,如果data提前渲染会导致禁用失效
                this.houseData=data
            },
            offShelf(item){//下架房屋
                item.state=3
                const _this=this
                axios.put(this.API.UpdateHouse,item).then((resp)=>{
                    if (resp.data == 'success') {
                        _this.$message({
                            message: '房屋已下架',
                            type: 'success'
                        })
                    }
                    if(resp.data=='error'){
                        _this.$message({
                            showClose: true,
                            message: '系统错误',
                            type: 'error'
                        })
                    }
                })
            },
            updateHouse(item){//修改房屋
                this.$router.push({
                    path:"/HouseInfo/updateHouse",
                    query:{
                        data:JSON.stringify(item)
                    }
                    }
                )
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
    *{
        margin: 0;
        padding: 0;
    }
    .m-view{
        margin:0 auto;
        color: #333;
        width: 80%;
    }

    .g-houseList-layout{
        /* margin-bottom: 40px; */
        -webkit-box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        -webkit-border-radius: 2px;
        border-radius: 2px;
        background-color: white;
    }
    .page-header{
        width: calc(100% - 40px);
        margin-left: 20px;
        padding: 20px 0 16px;
        box-shadow: 0 2px 0 0 #eee;
        border-radius: 4px 4px 0 0;
        display: flex;
        -webkit-box-pack: justify;
        justify-content: space-between;
    }
    .page-title{
        font-size: 18px;
        font-weight: 700;
        color: #333;
    }
    .g-houseList-layout .page-content{
        position: relative;
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    .page-content .page-content-list{
        position: relative;
        padding-top: 20px;
    }
    .page-content .page-content-list .list-header{
        width: -webkit-calc(100% - 40px);
        width: calc(100% - 40px);
        margin-left: 20px;
        height: 44px;
        line-height: 44px;
        background: #f5f5f5;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        font-size: 14px;
        color: #333;
    }
    .page-content .page-content-list .list-header .house-info{
        width: 44%;
        padding-left: 30px;
        text-align: left;
    }
    .examine-state,  .house-state {
        width: 14%;
    }
    .page-content .page-content-list .list-header .create-time{
        width: 16%;
    }
    .page-content-list .list-header .operation{
        width: 14%;
    }
    /*内容子项*/
    .g-comHouseListItem-layout .item-box{
        padding: 30px 20px;
        border-bottom: 1px solid #eee;
    }
    .g-comHouseListItem-layout .item-box .content{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
    }
    .item-box .content .item-house-info{
        width: 44%;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
    }
    .g-comHouseListItem-layout .item-box .content .item-house-info .info-left{
        margin-right: 10px;
    }
    .item-box .content .item-house-info .info-left img{
        width: 120px;
        height: 80px;
        cursor: pointer;
        -o-object-fit: cover;
        object-fit: cover;
        border-style: none;
    }
    .item-box .content .item-house-info .info-left div{
        line-height: 14px;
        font-size: 12px;
        color: #666;
        margin-top: 10px;
    }
    .info-right{
        width: 200px;
    }
    .title{
        text-align: left;
    }
    .item-house-info .info-right .title {
        line-height: 24px;
        min-height: 45px;
        font-size: 16px;
        color: #333;
        margin: 0;
        padding: 0;
    }
    .item-house-info .info-right .title span{
        width: -moz-fit-content;
        width: fit-content;
        max-width: 200px;
        cursor: pointer;
        overflow: hidden;
        -o-text-overflow: ellipsis;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .item-box .content .item-house-info .info-right .desc{
        margin-top: 14px;
        font-size: 14px;
        color: #666;
        text-align: left;
    }
    .item-box .content .item-house-state{
        width: 14%;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        padding-top: 30px;
        justify-content: center;
    }
    .item-box .content .item-house-state span{
        font-size: 12px;
        height: 18px;
        color: #333;
    }
    .item-box .content .item-examine-state {
        width: 14%;
        padding-top: 30px;
        font-size: 12px;
        color: #666;
        display: flex;
        justify-content: center;
    }
    .item-box .content .item-examine-state span{
        display: block;
        width: 48px;
        font-size: 12px;
        height: 18px;
    }
    .item-box .content .item-create-time {
        width: 16%;
        padding-top: 30px;
        font-size: 12px;
        color: #666;
    }
    .item-box .content .item-operation{
        width: 12%;
        position: relative;
        padding-top: 10px;
        margin-left: 20px;

    }
</style>