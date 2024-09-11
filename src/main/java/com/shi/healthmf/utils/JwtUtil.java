package com.shi.healthmf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import com.shi.healthmf.excs.BusinessException;

import java.util.Iterator;
import java.util.Map;

public class JwtUtil {

    public static String encode(Map<String, String> codeMap) {
        if (codeMap != null && !codeMap.isEmpty()) {
            JWTCreator.Builder builder = JWT.create();
            Iterator var2 = codeMap.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                builder.withClaim(key, (String)codeMap.get(key));
            }

            return builder.sign(Algorithm.HMAC256("AuthorizationJwtSignSecret8971289372911"));
        } else {
            throw new BusinessException("Jwt加密参数缺失");
        }
    }

    public static Map<String, Claim> decode(String sign) {
        if (StringUtils.isBlank(sign)) {
            throw new BusinessException("Jwt解密参数缺失");
        } else {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("AuthorizationJwtSignSecret8971289372911")).build();

            try {
                DecodedJWT verify = verifier.verify(sign);
                return verify.getClaims();
            } catch (Exception var3) {
                throw new BusinessException("签名解析异常");
            }
        }
    }

}
