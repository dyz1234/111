package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller	
public class CartController {
	
	/*
	 * 查询我的购物车
	 */
	@Autowired
	private CartService cartService;
	@RequestMapping("cart/query/{userId}")
	@ResponseBody
	public SysResult queryCartList(@PathVariable 
			Long userId){
		List<Cart> cartList=cartService.queryCartList(userId);
		return SysResult.oK(cartList);
	}
	/*
	 * 保存我的购物车
	 */
	@RequestMapping("cart/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		cartService.saveCart(cart);
		return SysResult.oK();
	}
	//*更新商品数量
	///cart/update/num/{userId}/{itemId}/{num}
	@RequestMapping("cart/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId,
			@PathVariable Long itemId,@PathVariable Integer num){
		cartService.updateCartNum(userId,itemId,num);
		return SysResult.oK();
	}
	/*
	 * 删除cart商品
	 * http://cart.jt.com/cart/delete/{userId}/{itemId}
	 */
	@RequestMapping("cart/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId,
			@PathVariable Long itemId){
		cartService.deleteCart(userId,itemId);
		return SysResult.oK();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
