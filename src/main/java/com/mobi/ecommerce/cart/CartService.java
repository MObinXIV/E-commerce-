package com.mobi.ecommerce.cart;

import com.mobi.ecommerce.cartItem.CartItem;
import com.mobi.ecommerce.cartItem.CartItemId;
import com.mobi.ecommerce.cartItem.CartItemRepository;
import com.mobi.ecommerce.cartItem.CartItemRequest;
import com.mobi.ecommerce.exception.BusinessException;
import com.mobi.ecommerce.exception.NotFound;
import com.mobi.ecommerce.product.Product;
import com.mobi.ecommerce.product.ProductRepository;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final SecurityUtils securityUtils;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    public CartService(CartRepository cartRepository, SecurityUtils securityUtils, ProductRepository productRepository, CartItemRepository cartItemRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.securityUtils = securityUtils;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    public CartResponse createCart()
    {
        User user = securityUtils.getAuthenticatedUser();
        if(cartRepository.existsByUserId(user.getId())){

            throw new BusinessException("User already has an active cart");
        }
        List<CartItem>cartItems=new ArrayList<>();
        Cart cart = new Cart();
        cart.setCartItem(cartItems);
        cart.setUser(user);
        user.setCart(cart);
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartResponse(savedCart);
    }
    public  CartResponse getCart(){
        User user =securityUtils.getAuthenticatedUser();
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFound("Cart not found"));
        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse addItemsToCart(CartItemRequest request){
        User user = securityUtils.getAuthenticatedUser();
        Cart cart =cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFound("Cart not found"));
        Product product= productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFound("Product not found"));
        if (cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId()).isPresent()) {
            throw new BusinessException("Product already exists in cart");
        }
        CartItemId cartItemId= new CartItemId(request.getProductId(),cart.getId());
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPriceAtAddition(product.getProductPrice());
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(cartRepository.findById(cart.getId()).orElseThrow());
    }
    @Transactional
    public CartResponse removeItemFromCart(UUID productId){
        User user = securityUtils.getAuthenticatedUser();

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFound("Cart not found"));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new NotFound("Product not found in cart"));
        cartItemRepository.delete(cartItem);
        return cartMapper.toCartResponse(cartRepository.findById(cart.getId()).orElseThrow());
    }
}
