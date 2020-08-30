package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){

	    /*
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        if( !authHeader.startsWith("Bearer ") ){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        String token = authHeader.substring(7);
        Claims claims = jwtUtil.parseJWT(token);
        if(claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        if( !"admin".equals( claims.get("roles") ) ){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
*/
	    Claims claims=(Claims)  request.getAttribute("admin_claims");
	    if(claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }

        userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}


	/**
	 * 用户注册
	 * @param user
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result register(@RequestBody User user, @PathVariable String code ){
		userService.add(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}




	/**
	 *  登陆
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody Map<String,String> map ){
		User user = userService.findByMobileAndPassword(map.get("mobile"), map.get("password"));
		if(user!=null){

			String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");

			Map map1=new HashMap();
			map1.put("token",token);
			map1.put("name",user.getNickname());//昵称
			map1.put("avatar",user.getAvatar());//头像

			return new Result(true,StatusCode.OK,"登陆成功",map1);
		}else{
			return new Result(false,StatusCode.OK,"用户名或密码错误");
		}
	}

	/**
	 * 变更粉丝数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value = "/incfans/{userid}/{x}",method = RequestMethod.POST)
	public void incFanscount(@PathVariable  String userid,@PathVariable int x){
		userService.incFanscount(userid,x);
	}

	/**
	 * 变更关注数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value = "/incfollow/{userid}/{x}",method = RequestMethod.POST)
	public void incFollowcount(@PathVariable  String userid,@PathVariable int x){
		userService.incFollowcount(userid,x);
	}

}
