package com.hackaton.project.controller;

import com.hackaton.project.model.*;
import com.hackaton.project.repository.ProductRepository;
import com.hackaton.project.repository.RoleRepository;
import com.hackaton.project.repository.UserRepository;
import com.hackaton.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    private ResponseEntity<?> signupClient(@RequestBody AuthenticationRequest auth){
        String username = auth.getUsername();
        String password = auth.getPassword();
        String name = auth.getName();
        String email = auth.getEmail();
        String phoneNumber = auth.getPhoneNumber();
        int roleId = auth.getRoleId();
        User user = new User();
        if(userRepository.findByUsername(username)==null){
            user.setId(service.getSequence(User.sequenceName));
            user.setUsername(username);
            user.setPassword(password);
            user.setRoleId(roleId);
            user.setProductId(user.getId());
            user.setName(name);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
        }else {
            return ResponseEntity.ok(new AuthenticationResponse("Username already exists"));
        }
        try {
            userRepository.save(user);
        }catch (Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("Error during Sign in "+username));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Successful Sign In for client "+ username));
    }
    @PostMapping("/login")
    private ResponseEntity<?> loginClient(@RequestBody AuthenticationRequest auth){
        String username = auth.getUsername();
        String password = auth.getPassword();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("Error during Log in"));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Successful Log in for client "+ username));

    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/users/{_id}")
    public Object getUser(@PathVariable int _id){
        return userRepository.findById(_id);
    }
    @DeleteMapping("/deleteUser/{productId}")
    public ResponseEntity<?> delete( @PathVariable int productId){
        try {
            userRepository.deleteById(productId);
            productRepository.deleteByProductId(productId);
        }catch(Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("User failed to be deleted"));
        }
        return ResponseEntity.ok(new AuthenticationResponse("User deleted successfully"));
    }

    @PostMapping("/addProduct/{productId}")
    public ResponseEntity<?> addProduct(@PathVariable int productId, @RequestBody ProductRequest productRequest) {

        String productName = productRequest.getProductName();
        String productDetails = productRequest.getProductDetails();
        String category = productRequest.getCategory();
        String availability = productRequest.getAvailability();
        int quantity = productRequest.getQuantity();
        Product product = new Product();

        product.setId(service.getSequence(Product.sequenceName));
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductDetails(productDetails);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setAvailability(availability);
        try{
            productRepository.save(product);
        }catch (Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("Product failed to be added"));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Product successfully added"));
    }

    @GetMapping("/listProducts/{productId}")
    public List<Product> listProducts(@PathVariable int productId){
        return productRepository.findByProductId(productId);
    }
    @DeleteMapping("/deleteProduct/{_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int _id){
        productRepository.deleteById(_id);
        return ResponseEntity.ok(new AuthenticationResponse("Product successfully deleted"));
    }

    @PostMapping("/updateProduct/{_id}")
    public ResponseEntity<?>updateProduct(@PathVariable int _id, @RequestBody ProductRequest productRequest){
        Product foundedProduct = productRepository.findById(_id);
        try{
            foundedProduct.setProductName(productRequest.getProductName());
            foundedProduct.setProductDetails(productRequest.getProductDetails());
            foundedProduct.setQuantity(productRequest.getQuantity());
            productRepository.save(foundedProduct);
        }catch(Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("Update Failed"));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Product successfully updated"));
    }

}
