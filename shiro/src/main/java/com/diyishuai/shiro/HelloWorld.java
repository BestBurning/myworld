package com.diyishuai.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.env.DefaultEnvironment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author Bruce
 * @since 2018/4/8
 */
public class HelloWorld {

    @Test
    public void testHelloWorld(){
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        if ( !subject.isAuthenticated() ) {
            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            //(do you know what movie this is from? ;)
            UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
            //this is all you have to do to support 'remember me' (no config - built in!):
            token.setRememberMe(true);
            subject.login(token);
            System.out.println(subject.getPrincipal());
        }
        if ( subject.hasRole( "schwartz" ) ) {
            System.out.println("May the Schwartz be with you!" );
        } else {
            System.out.println( "Hello, mere mortal." );
        }
        System.out.println(subject.isAuthenticated());
    }

}
