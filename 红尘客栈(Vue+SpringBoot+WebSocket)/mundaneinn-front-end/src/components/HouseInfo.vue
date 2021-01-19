<!--房屋信息，添加/修改房屋-->
<template>
    <section class="g-createHouse-layout">
        <!--步骤条-->
        <div class="g-comStepNav-layout">
            <el-steps :active="active" finish-status="success" class="step-nav">
                <el-step title="房源位置"></el-step><!--地图定位-->
                <el-step title="房源概况"></el-step><!--出租类型，床数，人数，户型，标题-->
                <el-step title="设施和服务"></el-step><!--配套列表，房型列表-->
                <el-step title="照片和描述"></el-step><!--图片，描述-->
                <el-step title="时段和价格"></el-step><!--价格，时段-->
            </el-steps>
        </div>
        <!--内容-->
        <section class="g-hcComModal-layout" v-show="active===0">
            <div class="region-left">
                <p class="title">你的房源在哪里？</p>
                <h5>国家/城市</h5>
                <div class="country-city-box">
                    <el-input
                            placeholder="中国大陆"
                            :disabled="true"
                            style="width: 100px">
                    </el-input>
                    <el-input v-model="region" placeholder="请输入位置" style="width: 200px;" id="suggestId"></el-input>
                </div>
                <h5>房源位置</h5>
                <el-tag type="info" style="min-width: 150px;margin-top: 20px">{{cityAndRegion}}</el-tag>
            </div>
            <div class="region-right" id="map"></div>
        </section>
        <section class="g-hcComModal-layout" v-show="active===1">
            <div class="situation-item">
                <div class="item-title">
                    <h2>出租方式</h2>
                </div>
                <div class="item-contain">
                    <el-radio v-model="rentalType" label="complete">整套</el-radio>
                    <el-radio v-model="rentalType" label="single">单间</el-radio>
                </div>
                <div class="item-title">
                    <h2>床数</h2>
                </div>
                <div class="item-contain">
                    <el-select v-model="bedNumber" placeholder="请选择床数" size="small">
                        <el-option
                                v-for="item in Options"
                                :key="item.value"
                                :label="item.value+'床'"
                                :value="item.value"
                                >
                        </el-option>
                    </el-select>
                </div>
                <div class="item-title">
                    <h2>宜住人数</h2>
                </div>
                <div class="item-contain">
                    <el-select v-model="peopleNumber" placeholder="请选择人数" size="small">
                        <el-option
                                v-for="item in Options"
                                :key="item.value"
                                :label="item.value+'人'"
                                :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </div>
                <div class="item-title">
                    <h2>户型</h2>
                </div>
                <div class="item-contain">
                    <el-select v-model="houseType" placeholder="请选择户型" size="small">
                        <el-option
                                v-for="item in Options"
                                :key="item.value"
                                :label="item.value+'居'"
                                :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </div>
                <div class="item-title">
                    <h2>房屋标题</h2>
                </div>
                <div class="item-contain">
                    <el-input v-model="title" placeholder="请输入标题" style="width: 300px" size="small" maxlength="20" clearable></el-input>
                </div>
            </div>
        </section>
        <section class="g-hcComModal-layout" v-show="active===2">
            <div class="situation-item">
                <div class="item-title">
                    <h2>配套设施</h2>
                </div>
                <div class="item-contain">
                    <el-checkbox-group v-model="matchingList" class="item-checkList">
                        <el-checkbox label="无线网络" class="item-check"></el-checkbox>
                        <el-checkbox label="电梯" class="item-check"></el-checkbox>
                        <el-checkbox label="热水淋浴" class="item-check"></el-checkbox>
                        <el-checkbox label="洗衣机" class="item-check" style="margin-bottom: 20px"></el-checkbox>
                        <el-checkbox label="电视" class="item-check"></el-checkbox>
                        <el-checkbox label="空调" class="item-check"></el-checkbox>
                        <el-checkbox label="冰箱" class="item-check"></el-checkbox>
                        <el-checkbox label="浴缸" class="item-check" style="margin-bottom: 20px"></el-checkbox>
                        <el-checkbox label="卫浴用品" class="item-check"></el-checkbox>
                        <el-checkbox label="投影设备" class="item-check"></el-checkbox>
                        <el-checkbox label="麻将机" class="item-check"></el-checkbox>
                        <el-checkbox label="免费停车" class="item-check"></el-checkbox>
                    </el-checkbox-group>
                </div>
                <div class="item-title" style="margin-top: 50px">
                    <h2>房型</h2>
                </div>
                <div class="item-contain">
                    <el-checkbox-group v-model="houseList" class="item-checkList">
                        <el-checkbox label="酒店式公寓" class="item-check"></el-checkbox>
                        <el-checkbox label="公寓" class="item-check"></el-checkbox>
                        <el-checkbox label="别墅" class="item-check"></el-checkbox>
                        <el-checkbox label="复式" class="item-check" style="margin-bottom: 20px"></el-checkbox>
                        <el-checkbox label="四合院" class="item-check"></el-checkbox>
                        <el-checkbox label="房车营地" class="item-check"></el-checkbox>
                        <el-checkbox label="木屋" class="item-check"></el-checkbox>
                        <el-checkbox label="帐篷营地" class="item-check" style="margin-bottom: 20px"></el-checkbox>
                        <el-checkbox label="老洋房" class="item-check"></el-checkbox>
                    </el-checkbox-group>
                </div>
            </div>
        </section>
        <section class="g-hcComModal-layout" v-show="active===3">
            <div class="photo-wrapper" style="width: 100%">
                <div class="item-title">
                    <div class="item-title-left">
                        <h2>
                            房屋照片 <span>{{ImageNumber}}/5</span>
                            <p style="position: relative; left: -6px;">【必填】 至少上传1张</p>
                        </h2>
                    </div>
                </div>
                <div class="upload-container">
                    <div class="ivu-form-item" style="margin-bottom: 30px;">
                        <div class="ivu-form-item-content">
                            <div class="photo-List  photo-item">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess1"
                                        :http-request="Upload"
                                        :before-upload="beforeAvatarUpload"
                                        :on-error="handleError">
                                    <img v-if="imageUrl[0]" :src="imageUrl[0]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-List  photo-item">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess2"
                                        :http-request="Upload"
                                        :before-upload="beforeAvatarUpload"
                                        :on-error="handleError">
                                    <img v-if="imageUrl[1]" :src="imageUrl[1]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-List  photo-item">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess3"
                                        :http-request="Upload"
                                        :before-upload="beforeAvatarUpload"
                                        :on-error="handleError">
                                    <img v-if="imageUrl[2]" :src="imageUrl[2]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-List  photo-item">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess4"
                                        :http-request="Upload"
                                        :before-upload="beforeAvatarUpload"
                                        :on-error="handleError">
                                    <img v-if="imageUrl[3]" :src="imageUrl[3]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                            <div class="photo-List  photo-item">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess5"
                                        :http-request="Upload"
                                        :before-upload="beforeAvatarUpload"
                                        :on-error="handleError">
                                    <img v-if="imageUrl[4]" :src="imageUrl[4]" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="g-hcComModal-layout" v-show="active===4">
            <div class="situation-item">
                <div class="item-title">
                    <h2>每日出租价格/晚</h2>
                </div>
                <div class="item-contain">
                    <el-input v-model="housePrice" placeholder="请输入价格" style="width: 150px" size="small" maxlength="6" clearable></el-input>
                </div>
                <div class="item-title">
                    <h2>请选择出租时段</h2>
                </div>
                <div class="item-contain">
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
                <div class="item-title">
                    <h2>描述您的房屋</h2>
                </div>
                <div class="item-contain">
                    <div class="input-item-contain">
                        <quill-editor ref="text" v-model="describe" class="myQuillEditor" :options="editorOption"
                        @change="onEditorChange($event)"/>
                    </div>
                </div>
            </div>
        </section>
        <div class="footer">
            <el-button style="margin-top: 12px;" @click="prev" v-show="active!=0">上一步</el-button>
            <el-button style="margin-top: 12px;" @click="next" v-show="active!=4">下一步</el-button>
            <el-button style="margin-top: 12px;" @click="addHouse('add')" v-show="active===4&&operation=='addHouse'">添加房屋</el-button>
            <el-button style="margin-top: 12px;" @click="addHouse('update')" v-show="active===4&&operation=='updateHouse'">确认修改</el-button>
        </div>
    </section>
</template>

<script>
    import { client } from '../utils/alioss';
    import { quillEditor } from 'vue-quill-editor';
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    export default {
        components:{quillEditor},
        props:["operation","houseData"],
        data() {
            return {
                active: 0,/*活动的步骤*/
                region:"",/*地区*/
                cityAndRegion:"",/*当前地区*/
                rentalType:"",/*出租类型*/
                Options: [{value: '1'}, {value: '2'}, {value: '3'}, {value: '4'}, {value: '5'},{value: '10'}],
                bedNumber: '',/*床数*/
                peopleNumber:'',/*人数*/
                houseType:'',/*户型*/
                title:'',/*标题*/
                matchingList: [],/*配套列表*/
                houseList:[],/*房型类别*/
                imageUrl: [],/*图片*/
                ImageNumber:0,/*当前图片个数*/
                housePrice:'',/*房屋价格*/
                chooseDate:'',/*出租时段*/
                pickerOptions: {/*限制只能选择今天及以后的时间*/
                    disabledDate(time) {
                        return time.getTime() < Date.now() - 8.64e7;
                    }
                },
                score:5.0,/*评分*/
                commentsNumber:0,/*评论人数*/
                state:"0",/*房屋状态*/
                house:[],/*房屋数据*/
                describe:'',/*房屋描述*/
                id:1,//房屋id
                editorOption: {
                    modules: {
                        toolbar: {
                            container:[
                                ['bold', 'italic', 'underline', 'strike'],    //加粗，斜体，下划线，删除线
                                ['blockquote', 'code-block'],     //引用，代码块

                                [{ 'header': 1 }, { 'header': 2 }],        // 标题，键值对的形式；1、2表示字体大小
                                [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
                                [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
                                [{ 'indent': '-1'}, { 'indent': '+1' }],     // 缩进
                                [{ 'direction': 'rtl' }],             // 文本方向

                                [{ 'size': ['small', false, 'large', 'huge'] }], // 字体大小
                                [{ 'header': [1, 2, 3, 4, 5, 6, false] }],     //几级标题

                                [{ 'color': [] }, { 'background': [] }],     // 字体颜色，字体背景颜色
                                [{ 'font': [] }],     //字体
                                [{ 'align': [] }],    //对齐方式

                                ['clean'],    //清除字体样式
                               // ['image','video']    //上传图片、上传视频
                            ], // 工具栏
                        },
                    },
                    placeholder: '请输入正文...1000字以内'
                },/*富文本编辑器配置*/
            };
        },
        mounted() {
            let _this=this
            // 百度地图API功能
            let map = new BMap.Map("map");// 创建Map实例
            map.centerAndZoom("北京", 12);// 初始化地图,设置城市和地图级别。
            map.enableScrollWheelZoom();//鼠标缩放地图

            let geoc = new BMap.Geocoder();
            //得到输入框的建议
            let ac = new BMap.Autocomplete(
                {
                    "input": "suggestId"
                    , "location": map
                });
            //点击地图得到位置，显示省份，城市
            map.addEventListener("click", function (e) {
                let pt = e.point;
                geoc.getLocation(pt, function (rs) {
                    let addComp = rs.addressComponents;
                    _this.cityAndRegion=addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber;
                });
            });
            //鼠标点击下拉列表后的事件
            let myValue;
            ac.addEventListener("onconfirm", function (e) {
                let _value = e.item.value;
                //省-市-县-街道-商店名
                myValue = _value.province +','+ _value.city + ','+_value.district +','+ _value.street +','+ _value.business;
                SuggestIdAnction(myValue);
                setPlace();
            });
            //把选择的地区赋值给另一个变量
            function SuggestIdAnction(val) {
                _this.cityAndRegion=val
            }
            //清除地图上所有覆盖物,重新绘制地图
            function setPlace() {
                map.clearOverlays();
                function myFun() {
                    let pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                    map.centerAndZoom(pp, 12);
                    map.addOverlay(new BMap.Marker(pp));    //添加标注
                }
                let local = new BMap.LocalSearch(map, { //智能搜索
                    onSearchComplete: myFun
                });
                local.search(myValue);
            }
            //定位当前位置1
/*            function myFun(result){
                var cityName = result.name;
                map.setCenter(cityName);
                _this.cityAndRegion=cityName
            }
            let myCity = new BMap.LocalCity();
            myCity.get(myFun);*/

            //定位当前位置2
/*           let geolocation = new BMap.Geolocation();
           geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    var mk = new BMap.Marker(r.point);
                    map.addOverlay(mk);
                    map.panTo(r.point);
                    //alert('您的位置：' + r.point.lng + ',' + r.point.lat);
                    var pt = r.point;
                    geoc.getLocation(pt, function (rs) {//坐标转换为地址显示
                        var addComp = rs.addressComponents;
                        $("#place").text(addComp.province + "-" + addComp.city + "-" + addComp.district + "-" + addComp.street + "-" + addComp.streetNumber);
                    });
                }
                else {
                    alert('定位错误，状态' + this.getStatus());
                }
           }, { enableHighAccuracy: true })*/
        },
        created() {
            if(this.operation=="addHouse"){
            }
            if(this.operation=="updateHouse"){
                this.house=JSON.parse(this.houseData)
                this.id=this.house.id
                this.title=this.house.title
                this.rentalType=this.house.rentalType
                this.bedNumber=this.house.bedNumber
                this.peopleNumber=this.house.peopleNumber
                this.houseType=this.house.houseType
                this.score=this.house.score
                this.commentsNumber=this.house.commentsNumber
                this.imageUrl=this.house.img.split(",")
                this.housePrice=this.house.housePrice
                this.cityAndRegion=this.house.cityAndRegion
                this.matchingList=this.house.matchingList.split(",")
                this.houseList=this.house.houseList.split(",")
                this.chooseDate=this.house.chooseDate.split(",")
                this.describe=this.house.describe
                this.state=this.house.state

                this.ImageNumber=this.imageUrl.length
            }
        },
        methods: {
            //上一步
            prev(){
                --this.active
                if (this.active < 0) this.active = 0;
            },
            //下一步
            next() {
                ++this.active
                if (this.active > 4) this.active = 4;
            },
            //头像上传之前
            beforeAvatarUpload(file) {
                const isPNG = file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isPNG) {
                    this.$message.error('上传图片只能是 PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传图片大小不能超过 2MB!');
                }
                return isPNG && isLt2M;
            },
            //上传头像
            async Upload(file) {
                let fileName = 'house' + `${Date.parse(new Date())}`+'.png';  //定义唯一的文件名
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
            //头像上传成功
            handleAvatarSuccess(res, file){
                this.ImageNumber++;//处理图片未刷新问题，不知道原因，可能是数据必须发送变化
                this.ImageNumber=this.imageUrl.length
                this.$message.success("上传成功!")
            },
            handleAvatarSuccess1(res, file) {
                this.imageUrl[0] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess2(res, file) {
                this.imageUrl[1] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess3(res, file) {
                this.imageUrl[2] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess4(res, file) {
                this.imageUrl[3] = res;
                this.handleAvatarSuccess(res, file)
            },
            handleAvatarSuccess5(res, file) {
                this.imageUrl[4] = res;
                this.handleAvatarSuccess(res, file)
            },
            //上传失败
            handleError() {
                this.$message("上传失败,请重新上传图片!");
            },
            onEditorChange(event){//限制内容在1000字
                event.quill.deleteText(1000,4)

            },
            async addHouse(option){//添加/修改房屋
                if(isNaN(parseInt(this.housePrice))){
                    this.$message.warning("价格必须是数字!");
                    return false
                }
                this.house= {
                    "id":this.id,
                    "title":this.title,
                    "rentalType":this.rentalType,
                    "bedNumber":this.bedNumber,
                    "peopleNumber":this.peopleNumber,
                    "houseType":this.houseType,
                    "score":this.score,
                    "commentsNumber":this.commentsNumber,
                    "img":this.imageUrl.toString(),
                    "headImg":this.$store.state.userInfo.headImg,
                    "username":this.$store.state.userInfo.username,
                    "housePrice":this.housePrice,
                    "cityAndRegion":this.cityAndRegion,
                    "matchingList":this.matchingList.toString(),
                    "houseList":this.houseList.toString(),
                    "chooseDate":this.chooseDate.toString(),
                    "describe":this.describe,
                    "state":0 //只要修改或者添加了房屋，那么状态变成待审核
                }
                let key
                for(key in this.house)
                {
                    if(this.house[key]===""){
                        this.$message({
                            message: '请先完善房屋信息',
                            type: 'warning'
                        })
                        return false
                    }
                }
                const _this=this
                if(option=='add'){
                    await axios.post(this.API.AddHouse,this.house).then(resp=>{
                        if (resp.data == 'success') {
                            _this.$message({
                                message: '添加房屋数据成功',
                                type: 'success'
                            })
                            setTimeout(() => {
                                _this.$router.push({
                                        path:"/MerchantManage"
                                    }
                                )
                            }, 1000);
                        }
                        if(resp.data=='error'){
                            _this.$message({
                                showClose: true,
                                message: '添加失败，房屋名称重复',
                                type: 'error'
                            })
                        }
                    })
                }
                else if(option=='update'){
                    await axios.put(this.API.UpdateHouse,this.house).then(resp=>{
                        if (resp.data == 'success') {
                            _this.$message({
                                message: '修改房屋数据成功',
                                type: 'success'
                            })
                            setTimeout(() => {
                                _this.$router.push({
                                        path:"/MerchantManage"
                                    }
                                )
                            }, 1000);
                        }
                        if(resp.data=='error'){
                            _this.$message({
                                showClose: true,
                                message: '修改失败，房屋名称重复',
                                type: 'error'
                            })
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped>
    *{
        margin: 0;
        padding: 0;
    }
    .g-createHouse-layout{
        background: #fff;
        border: 1px solid rgba(0,0,0,0);
        -webkit-box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        box-shadow: 0 0 0 1px rgba(63,63,68,.05), 0 1px 3px 0 rgba(63,63,68,.15);
        -webkit-border-radius: 2px;
        border-radius: 2px;
        color: #333;
        text-align: left;
    }
    .g-comStepNav-layout{
        box-shadow: 0 4px 48px 20px rgba(0,0,0,.06);
    }
    .step-nav{
        padding: 5px 20px 0 20px;
    }
    /*主要步骤区域*/
    .g-hcComModal-layout{
        height: 496px;
        padding: 20px 40px 0 40px;
        display: flex;
        overflow: hidden;
    }
    .footer{
        padding-right: 20px;
        display: flex;
        justify-content: flex-end;
    }
    /*位置*/
    .g-hcComModal-layout .title{
        font-size: 24px;
        font-weight: 700;
    }
    .region-left{
        width: 30%;
    }
    .region-right{
        width: 70%;
    }
    .g-hcComModal-layout h5{
        margin-top: 60px;
        font-weight: 700;
        font-size: 16px;
        color: #333;
    }
    .country-city-box {
        margin-top: 12px;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
    }
    /*概况*/
    .situation-item{
        width: 100%;
    }
    .item-title,.item-contain{
        width: 100%;
        margin: 15px 10px 10px 10px;
        padding-left: 50px;
    }
    .item-checkList{
        width: 40%;
        padding: 5px;
    }
    .item-check{
        width: 80px;
    }
    /*照片*/
    .photo-wrapper .item-title {
        background: #f5f5f5;
        height: 60px;
        padding: 0px 40px;
        margin: 40px 0 0 0;
        width: 100%;
    }
    .item-title-left{
        float: left;
        height: 100%;
        display: flex;
        -webkit-box-align: center;
        align-items: center;
    }
    .item-title-left h2{
        font-weight: 700;
        font-size: 16px;
        color: #333;
        line-height: 21px;
    }
    .item-title-left h2 span{
        font-size: 12px;
        font-weight: 400;
        line-height: 16px;
        margin-left: 9px;
    }
    .item-title-left p{
        font-size: 12px;
        font-weight: 400;
        color: #999;
        line-height: 16px;
        margin-top: 4px;
        text-align: left;
    }
    .upload-container{
        margin-top: 5px;
        margin-right: 20px;
    }
    .ivu-form-item {
        vertical-align: top;
    }
    .ivu-form-item-content {
        position: relative;
        line-height: 32px;
        font-size: 12px;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .photo-item{
        margin-right: 10px;
        margin-bottom: 10px;
        width: 263px;
    }
    .photo-List{
        height: 180px;
        border-radius: 2px;
        border: 1px solid #ddd;
        margin-left: 20px;
    }
    .photo-List .img-con {
        width: 100%;
        height: 100%;
        position: relative;
        cursor: pointer;
    }
    .photo-List .uploadImg {
        width: 100%;
        height: 100%;
        border-radius: 2px;
        object-fit: contain;
        border-style: none;
    }

    /*时段和价格*/
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
        width: 256px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 260px;
        height: 178px;
        display: block;
    }
    .input-item-contain{
        width: 400px;
        height: 242px;
    }
</style>
<style>
    .ql-editor{
        height:120px;
    }
</style>