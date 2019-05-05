<%@ page import="com.sbk.order.pojo.OrderInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sbk.pojo.TbOrderItem" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
   <meta http-equiv="pragma" content="no-cache">
   <meta http-equiv="cache-control" content="no-cache">
   <meta http-equiv="expires" content="0"> 
   <meta name="format-detection" content="telephone=no">  
   <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
   <meta name="format-detection" content="telephone=no">
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" type="text/css" href="/css/jquery.alerts.css?v=20160713" />
<link rel="stylesheet" type="text/css" href="/css/head.css?v=20160713" />
<link rel="stylesheet" type="text/css" href="/css/cart.css?v=20160630">
<link rel="stylesheet" type="text/css" href="/css/common.css?v=201321222" />
<script type="text/javascript" src="/js/jquery-1.5.1.min.js?v=20160713"></script>
<script type="text/javascript" src="/js/jquery.price_format.2.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.alerts.js?v=20160713"></script>
<script type="text/javascript" src="/js/cart.js?v=20160713"></script>
 <script type="text/javascript" src="/js/cookie.js?v=20160416222"></script>
<script type="text/javascript" src="/js/shadow.js?v=20160416"></script>
<script type="text/javascript" src="/js/common.js"></script>


   <title>我的订单 - 沙巴克商城</title>

<jsp:include page="commons/header.jsp" />
<div class="cartMain">
	<div class="cartHead">
		<h3 class="cartMy"><我的订单></我的订单></h3>
		<div class="clear"></div>
	</div>
	<div class="cartMain">
      <div class="cartThead">
        <div class="tCol tCheckbox"><input name="acart_list_check" id="selectAll" type="checkbox" > 全选</div>
		  <script type="text/javascript">

              // $(document).on('click', '#Zall', function () {
              //     if ($(this).is(":checked")) {
              //         $('.putong').prop('checked', true);
              //     } else {
              //         $('.putong').prop('checked', false);
              //     }
              // })

              $("#selectAll").click(function(){
                  if(this.checked){
                      $("input[name='order_list']").attr("checked","checked");
                  }else{
                      $("input[name='order_list']").attr("checked",false);
                  }
              });

		  </script>
        <div class="tCol tGoods">商品</div>
        <div class="tCol tPrice">单价</div>
        <div class="tCol tPromotion">优惠</div>
        <div class="tCol tQuantity">数量</div>
        <div class="tCol tWeight">重量(含包装)</div>
        <div class="tCol tSubtotal">小计</div>
        <div class="tCol tInventory">订单状态</div>
        <div class="tCol tOperator">操作</div>
      </div>
      <div class="cartTbody">
        </div>
              <%
                  List<OrderInfo> orderList = (List<OrderInfo>) request.getAttribute("orderList");
                  out.print("<form action=\"http://localhost:8093/order/order.html\" method=\"post\" id=\"cartForm\">");
                  for (OrderInfo info : orderList) {
                      out.print("<div class=\"cartColumnhd\">\n" +
                              "\t\t\t<div class=\"cartCheckbox\">\n" +
                              "                    <input name=\"order_list\" value=\"" +
                                                    info.getOrderId() +
                              "\" class=\"select_item\" id=\"Zpu\" type=\"checkbox\"\n" +
                              "                           onclick=\"PutongAll(this)\">订单号："+info.getOrderId()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间:"+info.getCreateTime()+"\n" +
                              "\t\t\t</div>\n" +
                              "        </div>");
                      List<TbOrderItem> orderItems = info.getOrderItems();
                      for (TbOrderItem orderItem : orderItems) {
                          out.print("<div class=\"cartPInfo\" id=\"danjian-0-229363\">\n" +
                                  "\t\t\t\t\t\t<div class=\"clearit\">\n" +
                                  "                            <div class=\"pItem pCheckbox\">\n" +
                                  "                            </div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pGoods\">\n" +
                                  "\t\t\t\t\t\t\t\t<div class=\"cart_pimg\">\n" +
                                  "\t\t\t\t\t\t\t\t\t<a target=\"_blank\" title=\"" +
                                                                         orderItem.getTitle() +
                                  "\"\n" +
                                  "\t\t\t\t\t\t\t\t\t   href=\"http://localhost:8087/item/" +
                                                                          orderItem.getItemId() +
                                  ".html\">\n" +
                                  "\t\t\t\t\t\t\t\t\t\t<img src=\"" +
                                                                        orderItem.getPicPath() +
                                  "\" style=\"width: 60px;height: 60px;\"/>\n" +
                                  "\t\t\t\t\t\t\t\t\t</a>\n" +
                                  "\t\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t\t<div class=\"cart_pname\">\n" +
                                  "\t\t\t\t\t\t\t\t\t<div>\n" +
                                  "\t\t\t\t\t\t\t\t\t\t<a target=\"_blank\"\n" +
                                  "\t\t\t\t\t\t\t\t\t\t   href=\"http://localhost:8087/item/" +
                                                                        orderItem.getItemId() +
                                  ".html\">" +
                                                                        orderItem.getTitle() +
                                  "</a>\n" +
                                  "\t\t\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t\t\t<div class=\"cdzg\">产地直供</div>\n" +
                                  "\t\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pPrice\">\n" +
                                  "\t\t\t\t\t\t\t\t<div style=\"position: relative;\">\n" +
                                  "\t\t\t\t\t\t\t\t\t<strong>¥" +
                                                                        orderItem.getPrice()/100 +
                                  ".00</strong>\n" +
                                  "\t\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pPromotion\">&nbsp;</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pQuantity\">\n" +
                                  "\t\t\t\t\t\t\t\t<div class=\"cartAmount\">\n" +
                                  "\t\t\t\t\t\t\t\t\t" +
                                                                            orderItem.getNum() +
                                  "\n" +
                                  "\t\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pWeight\">0.05kg<br></div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pSubtotal\">\n" +
                                  "\t\t\t\t\t\t\t\t<span id=\"total_price\" class=\"totalprice\">¥" +
                                  orderItem.getPrice()/100*orderItem.getNum() +
                                  ".00\n" +
                                  "</span>\n" +
                                  "\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pInventory\">");

                          if(info.getStatus()==1){
                              out.print("<a href=\"/goAlipay.html?out_trade_no=" +
                                                                                  info.getOrderId() +
                                      "&total_amount=" +
                                                                                  info.getPayment() +
                                      "&subject=" +
                                                                                  "订单名称" +
                                      "\">未付款</a>");
                          }else if(info.getStatus()==2){
                              out.print("已付款");
                          }
                          out.print("</div>\n" +
                                  "\t\t\t\t\t\t\t<div class=\"pItem pOperator\">\n" +
                                  "\t\t\t\t\t\t\t\t<a id=\"cartDel\" href=\"/order/delete/" +
                                  orderItem.getOrderId() +
                                  ".html\">删除</a>\n" +
                                  "\t\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t\t</div>\n" +
                                  "\t\t\t\t\t</div>");
                      }
                  }
                  out.print("</form>");
              %>
          <div class="cartList youxuan" id="all_putong">
		<div style="margin: 20px; text-align: center; display: none;" id="danjianload"></div>
		<div id="danjian">
			<div class="cartItem">
		</div>
	</div>
	</div>
	
	</div>
      <div class="cartOrderCount" id="orderCount">
        <div class="cartButtons">
          <input type="button" value="删除选中的订单" onclick="delOrder()" class="cartclear">
          <input type="button" value="清空订单" onclick="clearOrder()" class="cartclear">
        </div>
        <div class="cartTotalItem">
        </div>
      </div>
      <div class="cartJsuan">
         <input onclick="javascript:document.location.href='http://localhost:8083'" class="jiesuan youxuan" value="继续购物" type="button">
      </div>
    </div>

</div>
<!-- footer start -->
<jsp:include page="commons/footer.jsp" />
<!-- footer end -->
<script type="text/javascript">
	function doSubmit() {
		// alert("submit");
		var count = 0;
        $(".select_item").each(function (i,e) {
            if(e.checked==true){
                count += 1;
			}
        })
		if(count > 0){
            $("#cartForm").submit();
        }else {
            jAlert("请至少选择一个订单!")
		}
    }

    function delOrder() {
        document.getElementById('cartForm').action = "http://localhost:8093/order/delOrder.html";
        doSubmit();
    }

    function clearOrder() {
        document.getElementById('cartForm').action = "http://localhost:8093/order/clearOrder.html";
        document.getElementById('cartForm').submit();
    }

</script>

</html>