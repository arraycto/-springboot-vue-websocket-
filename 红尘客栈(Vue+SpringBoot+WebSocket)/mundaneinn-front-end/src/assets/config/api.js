const ApiRootUrl = 'http://localhost:8081/';

const API = {
  SendMsg: ApiRootUrl + 'user/sendMsg/', //发送短信数据接口，+/phoneNumber
  Login: ApiRootUrl + 'user/login/',  //登录接口，+this.phoneNumber+'/null/'+this.passWord
  UpdateUser: ApiRootUrl + 'user/updateUser',  // 修改用户接口,User

  GetRegion:ApiRootUrl + 'region/getRegion/',//地区接口，+cityName+"/"+type

  GetNotice:ApiRootUrl + 'notice/getNotice/',//用户通知接口,+username
  AddNotice:ApiRootUrl + 'notice/addNotice',//添加通知接口，notice

  GetHouseTotal:ApiRootUrl + 'house/getHouseTotal',//得到房屋总数量接口,allFilters
  GetHouseList:ApiRootUrl + 'house/getHouseList/',//得到房屋列表接口，1/'+this.pageSize,this.allFilters
  GetHouse:ApiRootUrl +'house/getHouse/',//得到单个房屋接口，+this.$store.state.userInfo.username+'/null'
  GetHouseByAdmin:ApiRootUrl +'house/getHouseByAdmin/',//管理员得到房屋接口，+state
  GetHouseTotalByAdmin:ApiRootUrl + 'house/getHouseTotalByAdmin/',//管理员得到得到房屋数量，+state
  UpdateHouse:ApiRootUrl +'house/updateHouse',//修改房屋接口，house
  AddHouse:ApiRootUrl + 'house/addHouse',//添加房屋接口,house

  GetCollect:ApiRootUrl + 'collect/getCollect/',//得到收藏接口，+this.$store.state.userInfo.username
  AddCollect:ApiRootUrl + 'collect/addCollect/',//添加收藏接口，collect
  DeleteCollect:ApiRootUrl + 'collect/deleteCollect/',//取消收藏接口，+this.collectionList[j].id

  GetCommentTotal:ApiRootUrl + 'comment/getCommentTotal/',//得到房屋评论数量接口，+this.houseData.username+'/'+this.houseData.title
  GetCommentList:ApiRootUrl + 'comment/getCommentList/',//得到房屋评论接口，this.houseData.username+'/'+this.houseData.title+'/'+this.currentPage+'/'+this.pageSize
  AddComment:ApiRootUrl + 'comment/addComment',//添加评论接口，comment

  GetChatList:ApiRootUrl + 'chat/getChatList/',//得到单个聊天记录接口，+this.$store.state.userInfo.username+'/'+this.thisusername
  GetUserChatList:ApiRootUrl + 'chat/getUserChatList/',//得到用户当前所有的聊天列表接口,即其他用户给他发的最后一条消息
  AddChat:ApiRootUrl + 'chat/addChat',//添加聊天记录

  AddOrder:ApiRootUrl + 'order/addOrder',//添加订单接口，order
  GetOrderList:ApiRootUrl +'order/getOrderList/',//得到订单接口，+'null/'+this.$store.state.userInfo.username
  GetOrderByTitle:ApiRootUrl + 'order/getOrderByTitle/',//根据房屋和房东名得到当前房屋已存在订单的接口，+this.houseData.username+"/"+this.houseData.title
  UpdateOrder:ApiRootUrl +'order/updateOrder',//修改订单接口，order
  DeleteOrder:ApiRootUrl +'order/deleteOrder/',//删除订单接口，id
  CancelOrder:ApiRootUrl + 'order/cancelOrder',//取消订单，order

  GetPayment:ApiRootUrl + '/payment/getPaymentByUser/',/*得到付款记录接口*/
  DeletePayment:ApiRootUrl + '/payment/deletePaymentById/',/*删除付款记录接口*/

  WebSocketPath:'ws://127.0.0.1:8081/websocket/',//WebSocket连接地址，+this.$store.state.userInfo.username

  Pay: ApiRootUrl + '/pay', //支付宝支付接口


};
export default API