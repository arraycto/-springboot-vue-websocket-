<!--通知-->
<template>
    <div class="notice">
        <div class="notice-head">
            <el-input placeholder="请输入用户名" v-model="username" size="small " :disabled="type=='all'">
                <template slot="prepend">收信人:</template>
            </el-input>
        </div>
        <div >
            <el-select v-model="noticeType" placeholder="请选择通知类型" style="width: 100%" size="small ">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
        </div>
        <div class="notice-contain">
            <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    :autosize="{ minRows: 10}"
                    v-model="describe">
            </el-input>
        </div>
        <div style="width: 100%;height: 50px ;">
            <el-button plain style="width: 100%" @click="sendNotice">发送</el-button>
        </div>

    </div>
</template>

<script>
    export default {
        props:["type"],
        data() {
            return {
                username:'',/*收信人*/
                describe: '',/*内容*/
                notice:'',/*通知*/
                options: [{
                    value: 'system',
                    label: '系统通知'
                }, {
                    value: 'discount',
                    label: '优惠促销'
                }],
                noticeType: ''
            }
        },
        created() {
            if(this.type=="single"){
                this.username=''
            }
            if(this.type=="all"){
                this.username='所有人'
            }
        },
        methods:{
            sendNotice(){//发送通知
                if(this.username.trim()==''||this.noticeType==''||this.describe.trim()==''){
                    this.$message({
                        showClose: true,
                        message: '请填写完整信息',
                        type: 'error'
                    })
                    return false
                }
                if(this.type=="single"){
                    this.notice={"id":1,"username":this.username,"noticeType":this.noticeType,'datetime':new Date,'describe':this.describe,"url":''}
                }
                if(this.type=="all"){
                    this.notice={"id":1,"username":"ALL","noticeType":this.noticeType,'datetime':new Date,'describe':this.describe,"url":''}
                }
                const _this=this
                axios.post(this.API.AddNotice,this.notice).then(resp=>{
                    if (resp.data == 'success') {
                        _this.$message({
                            message: '发送成功',
                            type: 'success'
                        })
                        this.noticeType=''
                        this.describe=''
                    }
                    if(resp.data=='error'){
                        _this.$message({
                            showClose: true,
                            message: '发送失败',
                            type: 'error'
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .notice{
        width: 400px;
        margin-left: 10%;
        margin-top: 50px;
        padding: 20px;
        box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        border-radius: 2px;
    }
    .notice-head{
        width: 100%;
        display: flex;
        justify-content: flex-start;
    }
</style>