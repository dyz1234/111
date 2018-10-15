package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service	
public class CartService {
	@Autowired
	private CartMapper cartMapper;
	public List<Cart> queryCartList(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
	List<Cart> cartList = cartMapper.select(cart);
		//select * from tb_cart where user_id=#{userId}
		return cartList;
	}
	public void saveCart(Cart cart) {
		//利用cart的信息查询商品
		//存在则更新,不存在则新增
		//中间参数,利用中间参数查询数据库是否有数据
		Cart _cart=new Cart();
		_cart.setUserId(cart.getUserId());
		_cart.setItemId(cart.getItemId());
		Cart existsCart=cartMapper.selectOne(_cart);
		if(null==existsCart){
			//需要新增
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else{
			//修改商品数量更新
			existsCart.setNum(existsCart.getNum()+cart.getNum());
			existsCart.setUpdated(new Date());
			cartMapper.updateByPrimaryKey(existsCart);
		}
	}
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		//两种办法,一种就是自定义xml,一种利用通用mapper先查出来历史数据
		//update tb_cart set num=#{num},updated=#{updated}
		//where user_id=#{userId} and item_id=#{itemId};
		//中间参数,封装userId itemId唯一的定位到历史数据
		Cart _cart =new Cart();
		_cart.setUserId(userId);
		_cart.setItemId(itemId);
		Cart existsCart=cartMapper.selectOne(_cart);
		existsCart.setNum(num);
		existsCart.setUpdated(new Date());
		cartMapper.updateByPrimaryKey(existsCart);
	}
	public void deleteCart(Long userId, Long itemId) {
		//中间参数,封装条件
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		//根据参数对象非空属性,拼接where条件
		//从通用mapper角度,不确定只删一条
		//从业务逻辑,一定只能删除一条
		cartMapper.delete(cart);
	}
	
}
