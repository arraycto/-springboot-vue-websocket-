import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "../views/Index";
import CityHotel from "../views/customer/CityHotel";
import Detail from "../views/customer/Detail";
import OrderInfo from "../views/customer/OrderInfo";
import PersonalCenter from "../views/customer/PersonalCenter";
import Order from "../components/Order";
import UserInfo from "../components/UserInfo";
import Collection from "../components/Collection";
import Message from "../components/Message";
import MerchantManage from "../views/landlord/MerchantManage";
import HouseManager from "../components/HouseManager";
import HouseInfo from "../components/HouseInfo";
import Administrator from "../views/administrators/Administrator";
import HouseList from "../components/HouseList";
import Notice from "../components/Notice";
import CashBox from "../components/CashBox";
import Page404 from "../views/Page404";
Vue.use(VueRouter)

const routes = [
  {
    path: '',
    name: 'Index',
    component:Index,
  },
  {
    path: '/',
    name: 'Index',
    component:Index,
  },
  {
    path: '/CityHotel',
    name: 'CityHotel',
    component:CityHotel,
    props:function(route){
      return {
        city:route.query.city,
        chooseregion:route.query.region,
        timeslot:route.query.timeslot,
        showLogin:route.query.showLogin
      }
    },
  },
  {
    path:'/Detail',
    name:"Detail",
    component:Detail,
    props:function(route){
      return {
        chooseHouse:route.query.chooseHouse,
        showLogin:route.query.showLogin,
      }
    },
  },
  {
    path:'/OrderInfo',
    name:"OrderInfo",
    component:OrderInfo,
    props:function(route){
      return {
        houseData:route.query.houseData,
        totalPrice:route.query.totalPrice,
      }
    },
    beforeEnter: (to, from, next) => {//在访问之前，路由守卫
      if(JSON.parse(sessionStorage.getItem('store')).userInfo.isCertified=='false'){
        alert("请先实名！")
        next({path:"/PersonalCenter"})
      }
      next()
    }
  },
  {
    path:'/PersonalCenter',
    name:"PersonalCenter",
    component:PersonalCenter,
    redirect:"/UserInfo",
    children:[
      {
        path:'/Order/:type',
        name:"Order",
        component:Order,
        props:function(route){
          return {
            type:route.params.type,
          }
        },
      },
      {
        path:'/UserInfo',
        name:"UserInfo",
        component:UserInfo,
      },
      {
        path:'/Collection',
        name:"Collection",
        component:Collection,
      },
      {
        path:'/Message',
        name:"Message",
        component:Message,
      },
    ]
  },
  {
    path:'/MerchantManage',
    name:"MerchantManage",
    component:MerchantManage,
    redirect:"/MerchantOrder/landlord",
    children:[
      {
        path:'/MerchantOrder/:type',
        name:"MerchantOrder",
        component:Order,
        props:function(route){
          return {
            type:route.params.type,
          }
        },
      },
      {
        path:'/HouseManager',
        name:"HouseManager",
        component:HouseManager,
      },
      {
        path:'/HouseInfo/:operation',
        name:"HouseInfo",
        component:HouseInfo,
        props:function(route){
          return {
            operation:route.params.operation,
            houseData:route.query.data,
          }
        },
      },
      {
        path:'/MerchantMessage',
        name:"MerchantMessage",
        component:Message,
      },
      {
        path:'/CashBox',
        name:'CashBox',
        component:CashBox,
      }
    ],
    beforeEnter: (to, from, next) => {//在访问之前，路由守卫
      if(JSON.parse(sessionStorage.getItem('store')).userInfo.isCertified=='false'){
        alert("请先实名！")
        next({path:"/PersonalCenter"})
      }
      next()
    }
  },
  {
    path: '/Administrator',
    name:'Administrator',
    component: Administrator,
    redirect:"/HouseList/examine",
    beforeEnter: (to, from, next) => {//在访问之前，路由守卫
      if(JSON.parse(sessionStorage.getItem('store')).userInfo.role!='admin'){
        alert("您没有权限访问！")
        next({path:"/"})
      }
      next()
    },
    children:[
      {
        path:'/HouseList/:operation',
        name:"HouseList",
        component:HouseList,
        props:function(route){
          return {
            operation:route.params.operation,
          }
        },
      },
      {
        path:'/Notice/:type',
        name:"Notice",
        component:Notice,
        props:function(route){
          return {
            type:route.params.type,
          }
        },
      },
    ]
  },
  {
    path:"*",
    component:Page404
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to,from,next)=>{//跳转之前，全局所有页面跳转都会触发
  let isLogin=false;
  if(JSON.parse(sessionStorage.getItem('store'))!=null){
     isLogin=JSON.parse(sessionStorage.getItem('store')).isLogin
  }
  if(isLogin || to.path=='/'){next()}//next控制跳转至下一个页面
  else{
    alert("请先登录")
    next({path:""})
  }
})
export default router
