package ci.ten.config.redis;

import ci.ten.config.aop.WebLogAspect;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    public static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    //session在redis中的过期时间:30分钟 30*60s
    private static final int expireTime = 1800;

    //redis中session名称前缀
    private static final String prefix = "sessionId:";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.info("创建session:{}" , session.getId());
        String key = prefix + sessionId.toString();
        redisTemplate.opsForValue().set(key, session);
        Object o = redisTemplate.opsForValue().get(key);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("读取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if(session == null){
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.info("更新session:{}",session.getId());
        String key = prefix + session.getId().toString();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    //删除session
    @Override
    protected void doDelete(Session session) {
        logger.info("删除session:"+session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }

    //获取当前活动的session
    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }


}
