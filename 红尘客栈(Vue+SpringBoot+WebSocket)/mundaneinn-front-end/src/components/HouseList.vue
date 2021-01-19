<!--房屋列表-->
<template>
    <article style="height: 100%">
        <div class="loading"  v-loading="loading" v-show="loading"></div>
        <!--搜索-->
        <div class="search" v-if="operation=='seeAll'">
            <span>房屋状态：</span>
            <el-select v-model="state" slot="prepend" placeholder="请选择" style="width: 150px">
                <el-option label="待审核" value="0"></el-option>
                <el-option label="已上架" value="1"></el-option>
                <el-option label="审核失败" value="2"></el-option>
                <el-option label="已下架" value="3"></el-option>
                <el-option label="全部房屋" value="null"></el-option>
            </el-select>
            <span>所在城市：</span>
            <el-input
                    placeholder="请输入内容"
                    v-model="city"
                    style="width: 150px"
                    clearable>
            </el-input>
            <span>用户名：</span>
            <el-input
                    placeholder="请输入内容"
                    v-model="username"
                    style="width: 150px"
                    clearable>
            </el-input>
            <span>房屋名称：</span>
            <el-input
                    placeholder="请输入内容"
                    v-model="title"
                    style="width: 250px"
                    clearable>
            </el-input>
            <div style="margin-left: 20px;height: 40px;padding-top: 5px">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="getHouseData()">搜索</el-button>
            </div>
            <span>共{{total}}条搜索结果</span>
        </div>
        <!--内容区域-->
        <el-table
                :data="tableData"
                height="90%"
                border
                >
            <el-table-column
                    prop="id"
                    label="房屋id"
                    width="80"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="房屋标题"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="房屋照片"
                    width="180"
                    align="center">
                <template slot-scope="scope">
                    <el-carousel trigger="click" :autoplay="false" height="96px" indicator-position="none">
                        <el-carousel-item v-for="(imgItem,key) in scope.row.img.split(',')" :key="key">
                            <img ref="imgHeight" :src=imgItem  width="100%" height="100%" object-fit="cover" v-show="imgItem!=null">
                        </el-carousel-item>
                    </el-carousel>
                </template>
            </el-table-column>
            <el-table-column
                    prop="username"
                    label="用户"
                    width="120"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="housePrice"
                    label="价格/晚"
                    width="100"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="cityAndRegion"
                    label="房屋位置"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="配套列表"
                    width="180"
                    align="center">
                <template slot-scope="scope">
                    <span>{{scope.row.matchingList.toString()}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="房型列表"
                    width="180"
                    align="center">
                <template slot-scope="scope">
                    <span>{{scope.row.houseList.toString()}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="出租时段"
                    width="180"
                    align="center">
                <template slot-scope="scope">
                    <span>{{scope.row.chooseDate.toString().replace(",","至")}}</span>
                </template>
            </el-table-column>
            <el-table-column

                    label="房屋描述"
                    width="250"
                    align="center"
                   >
                <template slot-scope="scope">
                    <div style="height: 100px;overflow: scroll;text-align: left">{{scope.row.describe}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    label="房屋状态"
                    width="100"
                    align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.state==0" style="background: #E6A23C">待审核</span>
                    <span v-if="scope.row.state==1" style="background: #67C23A">已上架</span>
                    <span v-if="scope.row.state==2" style="background: #F56C6C">审核失败</span>
                    <span v-if="scope.row.state==3" style="background: #909399">已下架</span>
                </template>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100"
                    v-if="operation=='examine'">
                <template slot-scope="scope">
                    <el-button @click="pass(scope.row)" type="text" size="small">通过</el-button>
                    <el-button @click="refuse(scope.row)" type="text" size="small">拒绝</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--标题，图片，商户的用户名，房屋价格,城市和地区,配套列表,房型列表，出租的时间段,房屋描述-->
        <el-pagination
                background
                layout="prev, pager, next"
                :page-size=pageSize
                :total="total"
                :current-page="currentPage"
                @current-change="page">
        </el-pagination>
    </article>
</template>

<script>
    export default {
        props:["operation"],
        data() {
            return {
                tableData: [],/*表格数据*/
                pageSize:6,/*每页显示数量*/
                total:0,/*房屋总数*/
                currentPage:1,/*当前页码*/
                loading:false,/*整个页面是否显示加载图标*/
                state:'',/*状态*/
                city:'',/*城市*/
                username:'',/*用户名*/
                title:'',/*房屋标题*/
            }
        },
        created() {
            this.getHouseData()
        },
        methods:{
            async pass(row){//审核通过
                row.state=1
                const _this=this
                await this.updateHouse(row)
                await this.getHouseData()
            },
            async refuse(row){//审核拒绝
                row.state=2
                await this.updateHouse(row)
                await this.getHouseData()
            },
            async updateHouse(item){//修改房屋
                const _this=this
                await axios.put(this.API.UpdateHouse,item).then((resp)=>{
                    if (resp.data == 'success') {
                        _this.$message({
                            message: '操作完成',
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
            async getHouseData(Page){//得到房屋数据
                if(Page!=null&&Page!='undefined'){
                    this.currentPage=Page
                }
                else{
                    this.currentPage=1
                }
                let state=this.state==''?'null':this.state
                let city=this.city==''?'null':this.city
                let username=this.username==''?'null':this.username
                let title=this.title==''?'null':this.title
                this.loading=true
                const _this=this
                if(this.operation=="examine"){
                    await axios.get(this.API.GetHouseTotalByAdmin+'0/null/null/null').then(resp=>{
                        _this.total=resp.data
                    })
                    await axios.get(this.API.GetHouseByAdmin+"0/null/null/null/"+this.currentPage+"/"+this.pageSize).then(resp=>{
                        _this.tableData=resp.data
                    })
                }
                if(this.operation=="seeAll"){
                    await axios.get(this.API.GetHouseTotalByAdmin+state+"/"+city+'/'+username+'/'+title).then(resp=>{
                        _this.total=resp.data
                    })
                    await axios.get(this.API.GetHouseByAdmin+state+"/"+city+'/'+username+'/'+title+'/'+this.currentPage+"/"+this.pageSize).then(resp=>{
                        _this.tableData=resp.data
                    })
                }
                this.loading=false
            },
            page(currentPage){//换页
                this.getHouseData(currentPage)
            },
        }
    }
</script>

<style scoped>
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
    /*加载图标*/
    .loading{
        position: fixed;
        width: 100vw;
        height: 100vh;
        z-index: 100;
        top:0%;
    }
    /*查询*/
    .search{
        height: 45px;
        display: flex;
    }
    .search>span{
        line-height: 35px;
        font-weight: 700;
        margin: 0 5px;
        height: 100%;
    }
</style>