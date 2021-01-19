<!--订单-->
<template>
    <div style="width: 1100px; margin: 0 auto" >
        <el-table
                :data="tableData"
                style="width: 100%"
                :header-cell-style="{
                         'background-color': '#F9F9F9',
                          'border-bottom': '1px RGBA(199, 199, 199, 1) solid'
}">
            <el-table-column
                    label="日期"
                    width="220"
                    style="text-align: center">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.timeSlot.replace(",","至") }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="房屋名称"
                    width="380"
                    style="text-align: center">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>房东电话: {{ scope.row.username }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.title}}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column
                    prop="customerId"
                    label="客户电话"
                    width="200"
                    style="text-align: center">
            </el-table-column>
            <el-table-column
                    label="支付金额"
                    width="100"
                    >
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.money }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="状态"
                    width="100">
                <template slot-scope="scope">
                    <span style="margin-left: 10px" v-if="scope.row.state==0">待支付</span>
                    <span style="margin-left: 10px" v-if="scope.row.state==1">待入住</span>
                    <span style="margin-left: 10px" v-if="scope.row.state==2">已离店</span>
                    <span style="margin-left: 10px" v-if="scope.row.state==3">已取消</span>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            v-if="scope.row.state==0||scope.row.state==1"
                            size="mini"
                            @click="handleCancel(scope.$index, scope.row)">取消订单</el-button>
                    <el-button
                            v-if="type=='landlord'&&(scope.row.state==2||scope.row.state==3)"
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除订单</el-button>
                    <el-button
                            v-if="type=='user'&&scope.row.state==2"
                            size="mini"
                            @click="dialogVisible=true,currentUsername=scope.row.username,currentTitle=scope.row.title">评论</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog
                :title="'评论'"
                :visible.sync="dialogVisible"
                width="30%"
                class="dialog"
                >
             <div style="width:100%;">
                 <p>{{currentTitle}}</p>
                 <div style="text-align: left;margin-bottom: 10px">评论星级:</div>
                 <el-rate v-model="score" style="float: left;margin-bottom: 10px"></el-rate>
                 <el-input
                         type="textarea"
                         :rows="2"
                         placeholder="请输入内容"
                         v-model="describe">
                 </el-input>
             </div>
             <span slot="footer" class="dialog-footer">
                 <el-button @click="dialogVisible = false">取 消</el-button>
                 <el-button type="primary" @click="comment(),dialogVisible = false">确 定</el-button>
             </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        props:["type"],
        data() {
            return {
                tableData: [],
                dialogVisible: false,
                currentUsername:'',/*评论房屋的商家号*/
                currentTitle:'',/*评论房屋的标题*/
                score:0,/*评分*/
                describe:'',/*评论内容*/
            }
        },
        created() {
            this.getOrder()
        },
        methods: {
            async handleCancel(index, row) {//取消订单
                if(this.dateFormat(new Date())>=row.timeSlot.split(",")[0]){
                    this.$message({
                        showClose: true,
                        message: '房间已经开始入住了，不能取消',
                        type: 'error'
                    })
                    return false
                }
                const _this=this
                await axios.post(this.API.CancelOrder,row).then((resp)=> {
                    if (resp.data == 'success') {
                        _this.$message({
                            message: '订单取消成功',
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
                //通知用户订单情况
                let notice
                if(this.type=="user"){
                    notice={"id":1,"username":row.username,"noticeType":"system",'datetime':new Date,'describe':"您在'"+row.title+"'的订单被用户取消了","url":''}
                }
                if(this.type=="landlord"){
                    notice={"id":1,"username":row.customerId,"noticeType":"system",'datetime':new Date,'describe':"您在'"+row.title+"'的订单被房东取消了","url":''}
                }
                await axios.post(this.API.AddNotice,notice)
                await this.getOrder()
            },
            async handleDelete(index, row) {//删除订单
                await axios.delete(this.API.DeleteOrder+row.id)
                this.$message({
                    message: '删除成功',
                    type: 'success'
                })
                this.getOrder()
            },
            async getOrder(){//得到订单
                const _this=this
                if(this.type=="user"){//得到用户订单
                   await axios.get(this.API.GetOrderList+'null/'+this.$store.state.userInfo.username).then(function (resp) {
                        _this.tableData=resp.data
                   })
                }
                if(this.type=="landlord"){//得到房东订单
                   await axios.get(this.API.GetOrderList+this.$store.state.userInfo.username+'/null').then(function (resp) {
                        _this.tableData=resp.data
                   })
                }
            },
            async comment(){//发表评论
                if(this.describe==''){
                    this.$message.error('请输入评论内容');
                    return false
                }
                let comm={
                    "id":null,
                    "userId":this.$store.state.userInfo.username,
                    "username":this.currentUsername,
                    "title":this.currentTitle,
                    "describe":this.describe,
                    "commentTime":null,
                    "score":this.score,
                    "headImg":this.$store.state.userInfo.headImg
                }
                const _this=this
                axios.post(this.API.AddComment,comm).then((resp)=>{
                    if(resp.data=='success'){
                        _this.$message({
                            message: '评论成功',
                            type: 'success'
                        });
                    }
                    if(resp.data=='error'){
                        _this.$message({
                            message: '你已评论，不能重复评论',
                            type: 'warning'
                        });
                        //return false
                    }
                })
                //统计房屋的评分和评论人数
                let  house
                await axios.get(this.API.GetHouse+this.currentUsername+'/'+this.currentTitle).then(resp=>{
                    house=resp.data[0]
                })
                house.score=(house.score*house.commentsNumber+this.score)/(house.commentsNumber+1)
                house.commentsNumber=house.commentsNumber+1
                await axios.put(this.API.UpdateHouse,house)
                this.score=0;
                this.describe=''
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
        }
    }
</script>

<style scoped>
    .dialog>>>.el-dialog__body {
        padding: 0px 20px;
        color: #606266;
        font-size: 14px;
        word-break: break-all;
    }
</style>