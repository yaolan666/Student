package com.youzhi.service;

import com.jfinal.plugin.activerecord.Page;
import com.youzhi.model.Cart;
import com.youzhi.model.Student;

public class CartService {
    private Cart dao = new Cart().dao();
    public Page<Cart> paginate(int pageNumber, int pageSize){
        return dao.paginate(pageNumber,pageSize,"select *","from cart order by id asc");
    }
    public Cart findById(int id){
        return  dao.findById(id);
    }
    public void deleteById(int id){
        dao.deleteById(id);
    }
}
