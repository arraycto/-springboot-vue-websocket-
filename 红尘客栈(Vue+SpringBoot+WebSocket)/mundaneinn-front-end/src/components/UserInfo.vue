<!--用户信息-->
<template>
    <div  class="form-info">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <div class="info-group">
                <div class="group-left">
                    <div class="head-img">
                        <el-upload
                                class="avatar-uploader"
                                action=""
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :http-request="Upload"
                                :before-upload="beforeAvatarUpload"
                                :on-error="handleError">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </div>
                </div>
                <div class="group-right">
                    <el-form-item label="姓名：" prop="name">
                        <el-input v-model="ruleForm.name" :disabled="!isEdit"></el-input>
                    </el-form-item>
                    <el-form-item label="性别：" prop="sex">
                        <el-select v-model="ruleForm.sex" placeholder="请选择性别" :disabled="!isEdit">
                            <el-option label="男" value="0"></el-option>
                            <el-option label="女" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                </div>
            </div>
            <div class="info-group">
                <el-form-item label="电话号码：" prop="phone" >
                    <el-input v-model="ruleForm.phone" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="密码：" prop="password" >
                    <el-input v-model="ruleForm.password" show-password :disabled="!isEdit"></el-input>
                </el-form-item>
            </div>
            <div class="info-group">
                <el-form-item label="所在城市" prop="city">
                    <el-input v-model="ruleForm.city" :disabled="!isEdit"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age" style="width: 50%">
                    <el-input type="age" v-model.number="ruleForm.age" autocomplete="off" :disabled="!isEdit"></el-input>
                </el-form-item>

            </div>
            <el-form-item label="身份证号：" prop="idnumber">
                <el-input v-model="ruleForm.idnumber" :disabled="!isEdit"></el-input>
            </el-form-item>
            <el-form-item class="item-btn">
                <el-button :type="isEdit?'info':'warning'"  @click="isEdit=!isEdit"  style="margin-right:50%">{{isEdit?'关闭编辑':'编辑信息'}}</el-button>
                <el-button type="primary"  @click="submitForm('ruleForm')" v-if="!isCertified" style="margin: 0px">完善信息</el-button>
                <el-button type="primary"  @click="submitForm('ruleForm')" v-if="isCertified"  style="margin: 0px">修改信息</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {client} from "../utils/alioss";

    export default {
        data() {
            return {
                ruleForm: {
                    phone: '',
                    password: '',
                    name: '',
                    idnumber: '',
                    sex: '',
                    age: '',
                    city: '',
                },
                rules: {
                    phone: [
                        { required: true, message: '请输电话号码', trigger: 'blur' },
                        {min: 11, max: 11,message: '11位数字', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '请输名称', trigger: 'blur' },
                        { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
                    ],
                    idnumber: [
                        { required: true, message: '请输入身份证号', trigger: 'blur' },
                        { min: 18, max: 18, message: '18位字符', trigger: 'blur' }
                    ],
                    sex: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    age: [
                        { required: true, message: '年龄不能为空'},
                        { type: 'number', message: '年龄必须为数字值'}
                    ],
                    city: [
                        { required: true, message: '请输入所在城市', trigger: 'blur' }
                    ]
                },
                isCertified:false,/*信息是否完整*/
                imageUrl: this.$store.state.userInfo.headImg,/*头像*/
                isEdit:false,/*是否编辑*/
            };
        },
        created() {
            let userInfo=this.$store.state.userInfo
            this.ruleForm.phone=userInfo.username
            this.ruleForm.password=userInfo.password
            this.ruleForm.name=userInfo.alias
            this.ruleForm.idnumber=userInfo.idNumber
            this.ruleForm.sex=userInfo.sex
            this.ruleForm.age=userInfo.age
            this.ruleForm.city=userInfo.city
            if(!(this.ruleForm.password==''||this.ruleForm.name==''||this.ruleForm.idnumber==''||this.ruleForm.sex
            ==''||this.ruleForm.age==''||this.ruleForm.city==''))
                this.isCertified=true
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.imageUrl = res;
                this.$message.success("上传成功!")
            },
            handleError() {
                this.$message("上传失败,请重新上传图片!");
            },
            //上传头像
            async Upload(file) {
                let fileName = 'user' + `${Date.parse(new Date())}`+'.png';  //定义唯一的文件名
                let url;
                await client().multipartUpload(fileName, file.file,{headers: {
                        'Content-Disposition': 'inline',//设置头部，否则阿里oss的地址是下载而不是浏览图片
                        'Content-Type': 'png'//注意：根据图片或者文件的后缀来设置
                    }}).then(result => {
                    //得到上传后的文件地址
                    let index=result.res.requestUrls[0].indexOf('?')
                    if(index==-1){
                        url=result.res.requestUrls[0]
                    }
                    else{
                        url=result.res.requestUrls[0].substring(0,index)
                    }
                })
                return url
            },
            beforeAvatarUpload(file) {
                const isPNG = file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isPNG) {
                    this.$message.error('上传头像图片只能是 PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isPNG && isLt2M;
            },
            async submitForm(formName) {//提交表单
                const _this =this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.ruleForm)
                        this.$store.state.userInfo.password=this.ruleForm.password
                        this.$store.state.userInfo.alias=this.ruleForm.name
                        this.$store.state.userInfo.idNumber=this.ruleForm.idnumber
                        this.$store.state.userInfo.sex=this.ruleForm.sex
                        this.$store.state.userInfo.age=this.ruleForm.age
                        this.$store.state.userInfo.city=this.ruleForm.city
                        this.$store.state.userInfo.isCertified="true"
                        this.$store.state.userInfo.headImg=this.imageUrl
                        sessionStorage.setItem('store', JSON.stringify(this.$store.state))//把json解析成字符串存入session
                        axios.put(this.API.UpdateUser,this.$store.state.userInfo)
                        this.$message('信息修改成功');
                    } else {
                        return false;
                    }
                })
                //更改对应房屋的头像
                let house;
                await axios.get(this.API.GetHouse+this.$store.state.userInfo.username+'/null').then(resp=>{
                    house=resp.data
                })
                for(let i = 0;i<house.length;i++){
                    house[i].headImg=_this.imageUrl
                    await axios.put(this.API.UpdateHouse, house[i])
                }
            },
        }
    }
</script>

<style scoped>
    .form-info{
        width: 50%;
        height: 100%;
        margin: 0 auto;
        background-color: white;
        padding:40px 5px;

    }
    /* 利用穿透，设置input边框隐藏 */
    .form-info>>>.el-input__inner {
    }
    .info-group{
        display: flex;
        margin-bottom: 20px;
    }
    .group-left{
        height: 100px;
        width: 47.7%;
        display: flex;
        justify-content: center;
        padding: 5px;
    }
     /*头像*/
   .head-img{
        width: 100px;
        height: 100px;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        width: 100%
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }
    .avatar {
        width: 100px;
        height: 100px;
        display: block;
    }
    /*按钮*/
    .item-btn{
        padding-right: 100px;
    }
</style>