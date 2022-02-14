package com.lcomputerstudy.example.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Receiver;

import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcomputerstudy.example.config.JwtUtils;
import com.lcomputerstudy.example.domain.OrderInfo;
import com.lcomputerstudy.example.domain.OrderRequest;
import com.lcomputerstudy.example.domain.Product;
import com.lcomputerstudy.example.domain.QABoard;
import com.lcomputerstudy.example.domain.ReceiverInfo;
import com.lcomputerstudy.example.domain.UserInfo;
import com.lcomputerstudy.example.response.WishListResponse;
import com.lcomputerstudy.example.service.BoardService;
import com.lcomputerstudy.example.service.OrderService;
import com.lcomputerstudy.example.service.ProductService;
import com.lcomputerstudy.example.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	BoardService boardService;
	
	@PostMapping("wishlist-details")
	public ResponseEntity<?> getWishListDetails(@Validated @RequestBody List<OrderRequest> list) {

		for(OrderRequest item : list) {
			Product p = productService.getProductDetails(item.getCode());
			item.setProduct(p);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("heart-items")
	public ResponseEntity<?> getHeartImems(@Validated @RequestBody List<Integer> list){
		
		List<Product> items = new ArrayList<Product>();
		for(int item : list) {
			Product p = productService.getProductDetails(item);
			items.add(p);
		}
		
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@PostMapping("orderlist") 
	public ResponseEntity<?> getOrderList(@Validated @RequestBody List<OrderRequest> request) {
		
		for(OrderRequest order : request) {
			Product product = productService.getProductDetails(order.getCode());
			order.setProduct(product);
		}
		
		return new ResponseEntity<>(request, HttpStatus.OK);
	}
	
	@PostMapping("kakaopay")
	public ResponseEntity<?> kakaopay(@Validated @RequestBody OrderInfo order) throws IOException {
		try {
			System.out.println(order.getTotal());
			// 보내는 부분
			URL address = new URL("https://kapi.kakao.com/v1/payment/ready");
			HttpURLConnection connection = (HttpURLConnection) address.openConnection(); // 서버연결
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "KakaoAK c912ec447438b361454267741806c2e0"); // 어드민 키
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setDoOutput(true); // 서버한테 전달할게 있는지 없는지
			String parameter = "cid=TC0ONETIME" // 가맹점 코드
					+ "&partner_order_id=partner_order_id" // 가맹점 주문번호
					+ "&partner_user_id=partner_user_id" // 가맹점 회원 id
					+ "&item_name=초코파이" // 상품명
					+ "&quantity=1" // 상품 수량
					+ "&total_amount="+order.getTotal() // 총 금액
					+ "&vat_amount=200" // 부가세
					+ "&tax_free_amount=0" // 상품 비과세 금액
					+ "&approval_url=http://localhost:8080/shop/kakaopay-success" // 결제 성공 시
					+ "&fail_url=http://localhost:8080/shop/kakaopay-fail" // 결제 실패 시
					+ "&cancel_url=http://localhost:8080/shop/kakaopay-fail"; // 결제 취소 시
			OutputStream send = connection.getOutputStream(); // 이제 뭔가를 를 줄 수 있다.
			DataOutputStream dataSend = new DataOutputStream(send); // 이제 데이터를 줄 수 있다.
			dataSend.writeBytes(parameter); // OutputStream은 데이터를 바이트 형식으로 주고 받기로 약속되어 있다. (형변환)
			dataSend.close(); // flush가 자동으로 호출이 되고 닫는다. (보내고 비우고 닫다)
			
			int result = connection.getResponseCode(); // 전송 잘 됐나 안됐나 번호를 받는다.
			InputStream receive; // 받다
			System.out.println(result);
			
			if(result == 200) {
				receive = connection.getInputStream();
			}else {
				receive = connection.getErrorStream(); 
				
				return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 읽는 부분
			InputStreamReader read = new InputStreamReader(receive); // 받은걸 읽는다.
			BufferedReader change = new BufferedReader(read); // 바이트를 읽기 위해 형변환 버퍼리더는 실제로 형변환을 위해 존제하는 클레스는 아니다.
			// 받는 부분
			String result_ = change.readLine(); // 문자열로 형변환을 알아서 해주고 찍어낸다 그리고 본인은 비워진다.
			return new ResponseEntity<>(result_, HttpStatus.OK);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@PostMapping("order")
	public ResponseEntity<?> insertOrderList(@Validated @RequestBody OrderInfo order) {
		
		order.setState("주문확인중");
		order.setUser(order.getUserInfo().getUsername());
		
		orderService.insertOrderInfo(order);
		
		if(order.getReceiverInfo().getAddress() == null) {
			order.getReceiverInfo().setSame("주문자와 동일");
		}
		orderService.insertUserInfo_order(order.getUserInfo());
		orderService.insertReceiverInfo(order.getReceiverInfo());
		int code = orderService.getOrderCode();
		order.setOrderCode(code);
		
		int totalGivePoint = 0;
		
		for(OrderRequest o : order.getProducts()) {

			if(o.getProduct().getPoint().equals("판매가기준 설정비율")) {
				//3%적립	
				int p = (int) (o.getProduct().getPrice() * 0.03 * o.getCount());
				o.setGivePoint(p);
			}
			totalGivePoint += o.getGivePoint();
			
			o.setOrder_num(code);
			orderService.insertOrderDetails(o);
		}

		order.setGivePoint(totalGivePoint);
		orderService.insertGivePoint(order);
			
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@GetMapping("order-success")
	public ResponseEntity<?> getOrderSuccessList(@Validated String id) {
		
		List<OrderInfo> orders = orderService.getOrderInfo(id);
		
		for(OrderInfo order : orders) {
			List<OrderRequest> orderDetails = orderService.getOrderDetails(order.getOrderCode());
			for(OrderRequest request : orderDetails) {
				Product p = productService.getProductDetails(request.getCode());
				request.setProduct(p);
			}
			order.setProducts(orderDetails);
			
			ReceiverInfo receiverInfo = orderService.getReceiverInfo(order.getOrderCode());
			order.setReceiverInfo(receiverInfo);
			UserInfo user = orderService.getUserInfo(order.getOrderCode());
			order.setUserInfo(user);
		}

		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@PostMapping("wishitems")
	public ResponseEntity<?> insertWishItems(HttpServletRequest request, @Validated @RequestBody List<OrderRequest> list) {
		
		String token = new String();
		token = request.getHeader("Authorization");

		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
		}
		
		String username = jwtUtils.getUserEmailFromToken(token);
		
		for(OrderRequest item : list) {
			item.setId(username);
			userService.insertWishItems(item);
		}
		
		List<OrderRequest> wishItems = userService.getWishItems(username);
		for(OrderRequest item : wishItems) {
			Product p = productService.getProductDetails(item.getCode());
			item.setProduct(p);
		}
		UserInfo user = userService.readUser_refresh(username);
		user.setWishItems(wishItems);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping("wishitems")
	public ResponseEntity<?> deleteWishItems(@Validated @RequestBody List<OrderRequest> list) {
		
		for(OrderRequest item : list) {
			userService.delete_WishItem(item.getNum());
		}
	
		List<OrderRequest> wishlist = userService.getWishItems(list.get(0).getId());
		for(OrderRequest item : wishlist) {
			Product p = productService.getProductDetails(item.getCode());
			item.setProduct(p);
		}
		
		return new ResponseEntity<>(wishlist, HttpStatus.OK);
	}
	
	@GetMapping("orderlist")
	public ResponseEntity<?> getAllOrderList() {
		
		List<OrderInfo> orders = orderService.getOrderInfo_All();
		
		for(OrderInfo order : orders) {
			List<OrderRequest> orderDetails = orderService.getOrderDetails(order.getOrderCode());
			for(OrderRequest request : orderDetails) {
				Product p = productService.getProductDetails(request.getCode());
				request.setProduct(p);
			}
			order.setProducts(orderDetails);
			
			ReceiverInfo receiverInfo = orderService.getReceiverInfo(order.getOrderCode());
			order.setReceiverInfo(receiverInfo);
			UserInfo user = orderService.getUserInfo(order.getOrderCode());
			order.setUserInfo(user);
		}
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@DeleteMapping("orderinfo")
	public ResponseEntity<?> deleteFailOrderInfo(HttpServletRequest request) {
	
		String token = new String();
		token = request.getHeader("Authorization");

		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
		}
		
		String id = jwtUtils.getUserEmailFromToken(token);
		
		orderService.deleteFailOrderInfo(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("user-point")
	public ResponseEntity<?> updateUserPoint(HttpServletRequest request) {

		String token = new String();
		token = request.getHeader("Authorization");

		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
		}
		
		String id = jwtUtils.getUserEmailFromToken(token);
		
		OrderInfo order = orderService.getOrderinfoById(id);
//		List<OrderRequest> orderDetails = orderService.getOrderDetails(order.getOrderCode());
//		for(OrderRequest o : orderDetails) {
//			int p_code = o.getCode();
//			int count = o.getCount();
//			
//			productService.updateProductStock(p_code, count);
//		}
		int point = order.getPoint();
		userService.updatePoint(id, point);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("q-post")
	public ResponseEntity<?> saveQAPost(@Validated @RequestBody QABoard post) {
		
		boardService.insertQAPost(post);
		
		List<QABoard> list = boardService.getQABoardList();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}