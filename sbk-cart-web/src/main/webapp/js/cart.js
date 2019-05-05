var CART = {
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".do",function(data){
				CART.refreshTotalPrice(_thisInput.attr("itemId"));
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".do",function(data){
				CART.refreshTotalPrice(_thisInput.attr("itemId"));
			});
		});
		/*$(".itemnum").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				CART.refreshTotalPrice();
			});
		});*/
	},
	refreshTotalPrice : function(id1){ //重新计算总价
		var total = 0;
		var subtotal = new Array()
		$(".itemnum").each(function(i,e){
			var _this = $(e);
			subtotal[i] = (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$("#allMoney2").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '¥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
		$(".clearit").each(function (i,e) {
           /* var nodeT = $(e);
            alert(nodeT.childNodes[6])*/
           $(e).children().children("span").eq(0).text(
             /*  "¥"+
           	subtotal[i]/100
			   +".00"*/
            new Number(subtotal[i]/100).toFixed(2)).priceFormat({ //价格格式化插件
                prefix: '¥',
                thousandsSeparator: ',',
                centsLimit: 2
            }
		   )
        })

        // html($("#amountdanjian-0-229363").val())

    }
};

$(function(){
	CART.itemNumChange();
});