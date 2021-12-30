package com.vnk.authserver.Util;

import com.vnk.authserver.Dto.AccountDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final Integer EXPIRED_TIME = 1000 * 60 * 60 * 10;

    public String extractUsername(String token) {
        return (String) extractClaim(token, claims -> claims.get(Constants.getConstant("username")));
    }

    public String extractRoles(String token) {
        return (String) extractClaim(token, claims -> claims.get(Constants.getConstant("roles")));
    }

    public AccountDto extractInfo(String token) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(Long.valueOf(extractClaim(token, Claims::getSubject)));
        accountDto.setUsername(extractUsername(token));
        accountDto.setRole(extractRoles(token));
        accountDto.setPermissions(extractPermission(token));
        return accountDto;
    }

    public List<String> extractPermission(String token) {
        return (List<String>) extractClaim(token, claims -> claims.get(Constants.getConstant("permissions")));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateCustomToken(AccountDto obj) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.getConstant("username"), obj.getUsername());
        claims.put(Constants.getConstant("roles"), obj.getRole());
        claims.put(Constants.getConstant("permissions"), obj.getPermissions());
        return generateToken(claims, String.valueOf(obj.getId()));
    }

    private String generateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).
                setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}