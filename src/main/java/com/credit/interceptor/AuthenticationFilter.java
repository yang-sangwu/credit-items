package com.credit.interceptor;

import com.credit.util.JwtUtils;
import com.credit.util.ReturnUtil;
import com.credit.util.UserVerify;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Resource
    private JwtUtils jwtUtils;


    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息

//            UserVerify loginUser = new ObjectMapper().readValue(request.getInputStream(), UserVerify.class);
        String username = request.getParameter("usercode");
        String password = request.getParameter("password");

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        UserVerify jwtUser = (UserVerify) authResult.getPrincipal();
        Integer id = jwtUser.getId();
        String usercode = jwtUser.getUsercode();
        String username = jwtUser.getUsername();

        String password = jwtUser.getPassword();
        String faculty = jwtUser.getFaculty();
        List<GrantedAuthority> authorities = jwtUser.getAuthorities();
        log.info("jwtUser:" + jwtUser.toString());

        String token = jwtUtils.createToken(id,usercode,password,username,faculty,authorities);

        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("token", token);
        Map map = new HashMap();
        map.put("response", response);
        map.put("token", token);
        ReturnUtil.success("登陆成功！", map);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

}
