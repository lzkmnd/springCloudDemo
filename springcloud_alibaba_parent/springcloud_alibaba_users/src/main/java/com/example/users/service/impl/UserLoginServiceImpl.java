package com.example.users.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.users.dto.UserDto;
import com.example.users.mapper.TbUserMapper;
import com.example.users.model.ResultResponse;
import com.example.users.model.TbUser;
import com.example.users.service.UserLoginService;
import com.example.users.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Resource
    private TbUserMapper tbUserMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    static int OUT_TIME = 1;

    @Override
    public String login(String username, String password) {
        String s = null;
        String uu = "当前用户:";
        String s1 = stringRedisTemplate.opsForValue().get(uu + username);
        if (s1 != null) {
            if (Integer.parseInt(s1) == 3) {
                OUT_TIME = 1;
                Long expire = stringRedisTemplate.getExpire(uu + username);
                return "请等" + expire + "秒后尝试";
            }
        } else {
            OUT_TIME = 1;
            TbUser username1 = tbUserMapper.selectOne(new QueryWrapper<TbUser>()
                    .eq("user_name", username));
            //判断账号
            if (username1 == null) return "账号不存在";
            TbUser login = tbUserMapper.selectOne(new QueryWrapper<TbUser>()
                    .eq("user_name", username)
                    .eq("user_pass", password));
            //判断密码
            if (login == null) {
                stringRedisTemplate.opsForValue().set(uu + username, OUT_TIME++ + "", 2L, TimeUnit.MINUTES);
                return "密码错误";
            }
            //生成token
            s = JwtUtil.makeToken(login.getUserId(), login.getUserName(), String.valueOf(login.getGender()));
            stringRedisTemplate.opsForValue().set(login.getUserName(), s, 2L, TimeUnit.DAYS);
            //新 token
            stringRedisTemplate.opsForValue().set(s, s, 2L, TimeUnit.MINUTES);
        }
        return s;

    }

    @Override
    public List listUser() {
        return tbUserMapper.selectList(null);
    }

    @Override
    public String insertOrUpdateuserDto(UserDto userDto) {
        TbUser user = new TbUser();
        BeanUtils.copyProperties(userDto, user);
        if (user.getUserId() != null) {
            tbUserMapper.updateById(user);
        } else {
            tbUserMapper.insert(user);
        }
        return "ok";
    }
}
