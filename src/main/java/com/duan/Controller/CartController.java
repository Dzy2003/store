package com.duan.Controller;

import com.duan.Service.CartService;
import com.duan.util.R;
import com.duan.vo.CartVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    CartService service;
    @PostMapping
    public R<Void> addCart(@RequestParam("pid") Integer pid, @RequestParam("amount") Integer amount, HttpSession session) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        service.AddCart(uid,pid,amount,username);
        return R.success(null);
    }
    @GetMapping
    public R<List<CartVo>> getCart(HttpSession session) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        return R.success(service.findCartByUid(uid));
    }
    @PutMapping("/{cid}")
    public R<Integer> addCartNum(HttpSession session, @PathVariable Integer cid) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        return R.success(service.AddCartNum(cid,uid,username));
    }

    @GetMapping("/list")
    public R<List<CartVo>> getCartByCids(@RequestParam("cids") Integer[] cids, HttpSession session) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        List<CartVo> voByCids = service.getVOByCids(uid, cids);
        return R.success(voByCids);
    }

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }
}
