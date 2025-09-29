package com.example.ldap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

/**
 * LDAP authentication with intentional LDAP injection vulnerability
 * to demonstrate CodeQL detection capabilities.
 */
public class LdapAuth {
    
    private static final String LDAP_URL = "ldap://localhost:389";
    private static final String BASE_DN = "dc=example,dc=com";
    
    /**
     * VULNERABLE: LDAP injection vulnerability - user input directly concatenated
     * This should trigger a high/critical CodeQL alert
     */
    public boolean authenticateUser(String username, String password) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            
            DirContext ctx = new InitialDirContext(env);
            
            // VULNERABILITY: Direct concatenation allows LDAP injection
            String filter = "(&(uid=" + username + ")(userPassword=" + password + "))";
            
            System.out.println("LDAP filter: " + filter);
            
            NamingEnumeration<SearchResult> results = ctx.search(BASE_DN, filter, null);
            boolean authenticated = results.hasMore();
            
            results.close();
            ctx.close();
            
            return authenticated;
            
        } catch (Exception e) {
            System.err.println("LDAP authentication failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * VULNERABLE: Another LDAP injection pattern
     */
    public String getUserInfo(String userId) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            
            DirContext ctx = new InitialDirContext(env);
            
            // VULNERABILITY: LDAP injection in search filter
            String searchFilter = "(uid=" + userId + ")";
            NamingEnumeration<SearchResult> results = ctx.search(BASE_DN, searchFilter, null);
            
            if (results.hasMore()) {
                SearchResult result = results.next();
                Attributes attrs = result.getAttributes();
                return attrs.toString();
            }
            
            results.close();
            ctx.close();
            
        } catch (Exception e) {
            System.err.println("LDAP search failed: " + e.getMessage());
        }
        
        return null;
    }
}