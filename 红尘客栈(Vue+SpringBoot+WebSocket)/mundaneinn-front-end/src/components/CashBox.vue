<template>
    <section class="g-cash-box-index-layout m-view">
        <div class="cash-box-header">
            <p class="title">
                <span class="name">钱箱</span>
            </p>
        </div>
        <div class="cash-box-info-panel">
            <div class="info-panel-left">
                <h1 class="title">账户余额</h1>
                <p class="amount">￥{{$store.state.userInfo.balance}}</p>
                <button class="ivn-btn ivu-btn-primary"><span>提现</span></button>
            </div>
            <div class="info-panel-right">
                <div class="info-panel-right-item border-left-blue">
                    <p class="title">本月房费</p>
                    <p class="amount">￥{{monthFee}}</p>
                </div>
                <div class="info-panel-right-item border-left-yellow">
                    <p class="title">待入账</p>
                    <p class="amount">￥{{recorded}}</p>
                </div>
            </div>
        </div>
        <div class="cash-box-table">
            <div class="table-select">
                <p class="title">收支明细</p>
<!--                <section class="g-cash-select-item-layout table-select-item">
                    <span class="select-item-name">收支</span>
                    <div class="select-item ivu-select ivu-select-single">
                        <el-select v-model="type" placeholder="请选择" size="small">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </div>
                </section>-->
            </div>
            <el-table
                    :data="tableData"
                    border
                    height="276"
                    :summary-method="getSummaries"
                    show-summary
                    style="width: 100%;text-align: center"
                    :header-cell-style="{
                         'background-color': '#F9F9F9',
                          'border-bottom': '1px RGBA(199, 199, 199, 1) solid'
}">
                <el-table-column
                        prop="title"
                        label="房屋名称"
                        width="200">
                </el-table-column>
                <el-table-column
                        label="出租时段"
                        width="200">
                    <template slot-scope="scope">
                        <span>{{ scope.row.timeSlot.replace(",","至") }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="customer"
                        label="客户电话"
                        width="120" >
                </el-table-column>
                <el-table-column
                        label="支付时间"
                        width="160">
                    <template slot-scope="scope">
                        <span>{{ dateFormat(scope.row.time) }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="money"
                        label="支付金额">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </section>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [{
                    customer: "12356",
                    id: 4,
                    money: 500,
                    time: "2020-12-20T00:00:00.000+00:00",
                    timeSlot: "2020-11-8,2020-11-9",
                    title: "ggggg",
                    username: "17365560614",
                }],
                options:[{"label":"已支付","value":"2"},{"label":"待支付","value":"1"}],
                type:'',/*支付类型*/
                dateSet:'',//订单
                monthFee:0,/*当月获得费用*/
                recorded:0,/*待入账*/
            };
        },
        async created() {
            await this.getUserInfo()
            //得到账户余额
            this.recorded=this.$store.state.userInfo.balance
            await this.getOrder()
            let date=this.getDateRange()
            //计算待入账金额
            for(let i=0;i<this.dateSet.length;i++){
                if(this.dateSet[i].state==2&&this.dateSet[i].timeSlot[0]>=date[0]&&this.dateSet[i].timeSlot[1]<=date[1]){
                    this.monthFee=this.monthFee+this.dateSet[i].money
                }
            }
            await this.getPayment()
        },
        methods: {
            getSummaries(param) {
                const { columns, data } = param;
                const sums = [];
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '合计';
                        return;
                    }
                    if (index === 2) {
                        sums[index] = 'N/A';
                        return;
                    }
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] += ' 元';
                    } else {
                        sums[index] = 'N/A';
                    }
                });
                return sums;
            },
            async getUserInfo(){//得到用户信息
                const _this =this
                await axios.get(this.API.Login+ this.$store.state.userInfo.username+'/null/null').then((resp)=>{
                    _this.$store.state.userInfo=resp.data
                })
                sessionStorage.setItem('store', JSON.stringify(this.$store.state))//把json解析成字符串存入session
            },
            async getOrder(){//得到房屋已存在的订单
                const _this=this
                await axios.get(this.API.GetOrderList+this.$store.state.userInfo.username+'/null').then(function (resp) {
                    _this.dateSet=resp.data
                })
                for(let i=0;i<_this.dateSet.length;i++){
                    _this.dateSet[i].timeSlot=_this.dateSet[i].timeSlot.split(",")
                }
            },
            dateFormat(date) {//格式化日期
                var date = new Date(date);
                var year = date.getFullYear();
                var month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : "" + (date.getMonth() + 1);
                var day = date.getDate() < 10 ? "0" + date.getDate() : "" + date.getDate();
                var hour = date.getHours() < 10 ? "0" + date.getHours() : "" + date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : "" + date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : "" + date.getSeconds();
                return (year + "-" + month + "-" + day+" "+hour+":"+minutes+":"+seconds);
            },
            getDateRange() {//得到当月的第一天和最后一天
                var date = new Date();
                var year = date.getFullYear();
                var month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : "" + (date.getMonth() + 1);
                return [year + "-" + month + "-1",year + "-" + month + "-31"];
            },
            async getPayment(){//得到付款记录
                const _this=this
                await axios.get(this.API.GetPayment+this.$store.state.userInfo.username).then(resp=>{
                    this.tableData=resp.data
                })
            },
            async handleDelete(index, row) {//删除付款记录
                await axios.delete(this.API.DeletePayment+row.id)
                this.$message({
                    message: '删除成功',
                    type: 'success'
                })
                this.getPayment()
            }
        }
    };
</script>

<style scoped>
    .g-cash-box-index-layout{
        width: 960px;
        min-height: 630px;
        padding: 0 20px;
        border-radius: 4px;
        box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        font-size: 14px;
        font-family: MicrosoftYaHei;
        background: #fff;
        margin-left: 130px;
        color: #333;
        height: auto;
    }
    .cash-box-header{
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        align-items: center;
        justify-content: space-between;
        height: 60px;
        border-bottom: 2px solid #eee;
    }
    .cash-box-header .title .name {
        line-height: 24px;
        font-size: 18px;
        font-weight: 500;
    }
    .cash-box-info-panel{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        margin: 20px 0 20px 0;
        padding: 20px 30px;
        -webkit-box-shadow: 0 0 0 1px rgba(0,0,0,.05), 0 1px 3px 0 rgba(0,0,0,.1);
        box-shadow: 0 0 0 1px rgba(0,0,0,.05), 0 1px 3px 0 rgba(0,0,0,.1);
        -webkit-border-radius: 4px;
        border-radius: 4px;
    }
    .info-panel-left{
        width: 378px;
        border-right: 1px solid #eee;
        font-weight: 500;
    }
    .info-panel-left .title {
        line-height: 24px;
        font-size: 18px;
    }
    .info-panel-left .amount{
        margin: 10px 0;
        line-height: 28px;
        font-size: 24px;
        color: #ff9645;
    }
    .ivn-btn{
        display: inline-block;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        vertical-align: middle;
        outline: 0;
        touch-action: manipulation;
        background-image: none;
        border: 1px solid rgba(0,0,0,0);
        white-space: nowrap;
        user-select: none;
        padding: 6px 15px;
        font-size: 12px;
        border-radius: 4px;
        transition: color .2s linear,background-color .2s linear,border .2s linear,box-shadow .2s linear,-webkit-box-shadow .2s linear;
    }
    .ivu-btn-primary{
        color: #bbbec4;
        background-color: #f7f7f7;
        border-color: #dddee1;
    }
    .info-panel-right{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        padding-left: 40px;
    }
    .border-left-blue{
        border-left: 2px solid rgba(110,128,227,.5);
    }
    .info-panel-right-item{
        width: 223px;
        padding-left: 20px;
    }
    .info-panel-right-item .title{
        line-height: 21px;
        font-size: 16px;
    }
    .info-panel-right-item .amount{
        margin: 10px 0;
        line-height: 24px;
        font-size: 18px;
    }
    .border-left-yellow {
        border-left: 2px solid rgba(253,179,107,.5);
    }
    .cash-box-table .table-select {
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        -webkit-box-pack: justify;
        -webkit-justify-content: space-between;
        -ms-flex-pack: justify;
        justify-content: flex-start;
        margin-bottom: 20px;
    }
    .cash-box-table .table-select .title {
        margin-right: 44px;
        line-height: 18px;
    }
    .g-cash-select-item-layout{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        width: 156px;
    }
    .g-cash-select-item-layout .select-item-name{
        line-height: 12px;
        font-family: PingFangSC-Regular;
        font-size: 12px;
    }
    .g-cash-select-item-layout .select-item{
        width: 120px;
    }
    .ivu-select {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        display: inline-block;
        width: 100%;
        vertical-align: middle;
        color: #495060;
        font-size: 14px;
        line-height: normal;
        color: #333!important;
    }
</style>